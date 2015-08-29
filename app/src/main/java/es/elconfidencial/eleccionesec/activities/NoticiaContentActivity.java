package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_noticia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("El Confidencial");
        //Extraemos el intent para leer los par�metros y rellenar los campos
        Intent intent = getIntent();

        TextView titulo = (TextView) findViewById(R.id.titulo);
        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        WebView descripcion = (WebView)findViewById(R.id.descripcion);
        TextView autor = (TextView) findViewById(R.id.autor);
        titulo.setText(Html.fromHtml(intent.getStringExtra("titulo")));
        autor.setText(Html.fromHtml(intent.getStringExtra("autor")));

        //Insertamos la cabecera al html con el estilo
        String head = "<head><style>@font-face {font-family: MilioItalic;src: url(\"file:///android_asset/Milio-Demibold-Italic.ttf\")}" +
                "@font-face {font-family: TitilliumLight;src: url(\"file:///android_asset/Titillium-Light.otf\")}" +
                "@font-face {font-family: TitilliumSemibold;src: url(\"file:///android_asset/Titillium-Semibold.otf\")}" +
                "h2{font-family: MilioItalic;}" +
                "img{max-width: 100%; width:auto; height: auto;}" +
                "body{font-family:TitilliumLight;text-align:justify}" +
                "a{text-decoration: none;color:black;} " +
                "strong{font-family:TitilliumSemibold;}</style></head>";

        String htmlString ="<html>" + head + "<body>" + intent.getStringExtra("descripcion") + "</body></html>";

        //Quitar links
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
                view.loadUrl("javascript:");
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
        autor.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Milio-Heavy.ttf"));
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("imagenUrl")).into(imagen);
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
        return super.onOptionsItemSelected(item);
    }

}
