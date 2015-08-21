package es.elconfidencial.eleccionesec.rss;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.elconfidencial.eleccionesec.model.Quiz;

/**
 * Created by Afll on 21/08/2015.
 */
public class RssQuizsHandler extends DefaultHandler {
    private List<Quiz> quizs;
    private Quiz quizActual;
    private StringBuilder sbTexto;
    private boolean firstTime;

    public List<Quiz> getQuizs(){
        return quizs;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        super.characters(ch, start, length);

        if (this.quizActual != null)
            sbTexto.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {

        super.endElement(uri, localName, name);

        if (this.quizActual != null) {
            if (localName.equals("title") && (firstTime)) {
             quizActual.setTitulo(sbTexto.toString());
             firstTime = false;
             } else if (localName.equals("id")) {
             quizActual.setLink(sbTexto.toString());
                //getHtmlString(sbTexto.toString());
             } else if (localName.equals("name")) {
             quizActual.setAutor(sbTexto.toString());
             } else if(localName.equals("entry")){
             quizs.add(quizActual);}

             sbTexto.setLength(0);
        }


    }

    @Override
    public void startDocument() throws SAXException {

        super.startDocument();

        quizs = new ArrayList<Quiz>();
        sbTexto = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName,
                             String name, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, name, attributes);

        if (localName.equals("entry")) {
         firstTime=true;
         quizActual = new Quiz();

         }

        if (localName.equals("link")) {
         if (attributes.getValue("rel").toString().equals("enclosure")) {
         quizActual.setImagenUrl(attributes.getValue("href").toString());
         }
         }
    }

    private String getHtmlString(String link){
        String html = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(link);
            HttpResponse response = client.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            in.close();
            html = str.toString();
            if(html.contains("datos.elconfidencial.com")){
                System.out.println(html.length());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return html;
    }

    private String getQuizLink (String html){
        String quizLink = "";
        Pattern p = Pattern.compile("<iframe[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher m = p.matcher(html);
        if (m.find()) {
            quizLink = m.group(2);
        }

        return quizLink;
    }

    private class HtmlTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder(16384);

            try {
                URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";

                while ((line = rd.readLine()) != null) {
                    builder.append(line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.contains("iframe")){
                System.out.print("YUUUUUUUUUUUUUUUUUP");
            }
        }
    }
}