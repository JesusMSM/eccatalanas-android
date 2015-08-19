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
        titulo.setText(intent.getStringExtra("titulo"));
        autor.setText(intent.getStringExtra("autor"));

        //Insertamos la cabecera al html con el estilo
        String head = "<head><style>@font-face {font-family: MilioHeavy;src: url(\"file:///android_asset/Milio-Heavy.ttf\")}h2{font-family: MilioHeavy;}img{max-width: 100%; width:auto; height: auto;}</style></head>";
        String htmlString ="<html>" + head + "<body>" + intent.getStringExtra("descripcion") + "</body></html>";
        descripcion.getSettings().setJavaScriptEnabled(true);
        descripcion.loadDataWithBaseURL("", htmlString, "text/html", "charset=UTF-8", null);
        descripcion.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Abrimos los links siempre en el navegador
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
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
        titulo.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Milio-Heavy.ttf"));
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
