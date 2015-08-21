package es.elconfidencial.eleccionesec.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

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
}