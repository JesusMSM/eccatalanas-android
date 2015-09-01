package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 13/08/2015.
 */
public class NoticiaContentActivity extends ActionBarActivity {

    private String url = "";
    private String info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_noticia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("El Confidencial");
        //Extraemos el intent para leer los par�metros y rellenar los campos
        Intent intent = getIntent();

        //Datos para compartir
        url = intent.getStringExtra("link");
        info = Html.fromHtml(intent.getStringExtra("titulo")).toString();

        TextView titulo = (TextView) findViewById(R.id.titulo);
        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        WebView descripcion = (WebView)findViewById(R.id.descripcion);
        TextView autor = (TextView) findViewById(R.id.autor);
        TextView fecha = (TextView) findViewById(R.id.fecha);

        titulo.setText(Html.fromHtml(intent.getStringExtra("titulo")));
        autor.setText(Html.fromHtml(intent.getStringExtra("autor")));
        fecha.setText(getTimeAgo(intent.getStringExtra("fecha")));
        //Insertamos la cabecera al html con el estilo
        String head = "<head><style>@font-face {font-family: MilioHeavy;src: url(\"file:///android_asset/Milio-Heavy.ttf\")}" +
                "@font-face {font-family: TitilliumLight;src: url(\"file:///android_asset/Titillium-Light.otf\")}" +
                "@font-face {font-family: TitilliumSemibold;src: url(\"file:///android_asset/Titillium-Semibold.otf\")}" +
                "h2{font-family: MilioHeavy;}" +
                "img{max-width: 100%; width:auto; height: auto;}" +
                "body{font-family:TitilliumLight;text-align:justify}" +
                "a{text-decoration: none;color:black;} " +
                "strong{font-family:TitilliumSemibold;}</style></head>";

        String htmlString ="<html>" + head + "<body><div>" + intent.getStringExtra("descripcion") + "</div></body></html>";

        //String htmlStringFormatted = htmlString.replace("<img src[^>]*>", "");
        //Quitar la imagen del final

        descripcion.getSettings().setJavaScriptEnabled(true);
        descripcion.loadDataWithBaseURL("", htmlString, "text/html", "charset=UTF-8", null);
        descripcion.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Bloquear links
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        // disable scroll on touch
        descripcion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        //Estilo
        titulo.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Milio-Heavy-Italic.ttf"));
        autor.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Regular.otf"));
        fecha.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Regular.otf"));
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("imagenUrl")).placeholder(R.drawable.nopic).into(imagen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_noticia, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(item.getItemId() == R.id.share){
            shareAction(url,info);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void shareAction(String url, String info){
        // Llama al sistema para que le muestre un diálogo al usuario con todas las aplicaciones que permitan compartir información
        Intent intent = new Intent();
        String textoCompartir = info + "\n\n" + url;

        intent.setAction( Intent.ACTION_SEND );
        intent.putExtra(Intent.EXTRA_TEXT, textoCompartir );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType( "text/plain" );

        startActivity(  Intent.createChooser( intent, getString(R.string.share) )  );
    }

    //Formateadores para la fecha ( hace X minutos)
    public static String getTimeAgo(String timeCTE) {
        long time = 0;

        try{
            long epoch = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+02:00").parse(timeCTE).getTime();
            time=epoch;
        }catch(Exception e){
            e.printStackTrace();
        }
        final long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < C.MINUTE_MILLIS) {
            return "ahora";
        } else if (diff < 2 * C.MINUTE_MILLIS) {
            return "hace un minuto";
        } else if (diff < 50 * C.MINUTE_MILLIS) {
            return "hace "+diff / C.MINUTE_MILLIS + " minutos";
        } else if (diff < 90 * C.MINUTE_MILLIS) {
            return "hace una hora";
        } else if (diff < 24 * C.HOUR_MILLIS) {
            return "hace "+diff / C.HOUR_MILLIS + " horas";
        } else if (diff < 48 * C.HOUR_MILLIS) {
            return "ayer";
        } else {
            return "hace "+diff / C.DAY_MILLIS + " d\u00edas";
        }
    }
    public class C {
        /** One second (in milliseconds) */
        public static final int _A_SECOND = 1000;
        /** One minute (in milliseconds) */
        public static final int MINUTE_MILLIS = 60 * _A_SECOND;
        /** One hour (in milliseconds) */
        public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        /** One day (in milliseconds) */
        public static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    }
}
