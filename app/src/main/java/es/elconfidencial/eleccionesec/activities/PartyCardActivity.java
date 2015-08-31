package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

public class PartyCardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_card);

        //Extraemos el intent para leer los par?metros y rellenar los campos
        Intent intent = getIntent();

        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView representantes = (TextView) findViewById(R.id.representantes);
        TextView fundacion = (TextView) findViewById(R.id.fundacion);
        TextView escanos = (TextView) findViewById(R.id.escanos);
        TextView porcentajeVotos = (TextView) findViewById(R.id.porcentajeVotos);
        TextView ideologia = (TextView) findViewById(R.id.ideologia);
        TextView partidosRepresentados = (TextView) findViewById(R.id.partidosRepresentados);
        WebView perfil = (WebView) findViewById(R.id.perfil);


        nombre.setText(intent.getStringExtra("nombre"));
        representantes.setText(intent.getStringExtra("representantes"));
        fundacion.setText(intent.getStringExtra("fundacion"));
        escanos.setText(intent.getStringExtra("escanos"));
        porcentajeVotos.setText(intent.getStringExtra("porcentajeVotos"));
        ideologia.setText(intent.getStringExtra("ideologia"));
        partidosRepresentados.setText(intent.getStringExtra("partidosRepresentados"));

        try {
            //Screen size
            Display display = (this).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            int resid=intent.getIntExtra("imagen", 0);
            //Load Image
            //Picasso.with(getApplicationContext()).load(R.drawable.artur_mas2).placeholder(R.drawable.nopicpolitico).resize(width, height*2/3).onlyScaleDown().centerCrop().into(imagen);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inDither = true;
            opts.inSampleSize = 3;

            Bitmap bitmapImage = BitmapFactory.decodeResource(this.getResources(), resid, opts);
            imagen.setImageBitmap(bitmapImage);
        }catch (Exception e){e.printStackTrace();}

        //Fonts
        nombre.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Milio-Heavy-Italic.ttf"));
        representantes.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        fundacion.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        escanos.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        porcentajeVotos.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        ideologia.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        partidosRepresentados.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));

        //Insertamos la cabecera al html con el estilo
        String head = "<head><style>@font-face {font-family: MilioHeavy;text-align:justify; src: url(\"file:///android_asset/Milio-Heavy.ttf\")}" +
                "@font-face {font-family: TitilliumLight;src: url(\"file:///android_asset/Titillium-Light.otf\")}" +
                "@font-face {font-family: TitilliumSemibold;src: url(\"file:///android_asset/Titillium-Semibold.otf\")}" +
                "body{font-family:TitilliumLight;}" +
                "strong{font-family:TitilliumSemibold;}"+
                "img{max-width: 100%; width:auto; height: auto;}</style></head>";
        String htmlString ="<html>" + head + "<body style='text-align:justify;'>" + intent.getStringExtra("perfil") + "</body></html>";
        System.out.println(intent.getStringExtra("perfil"));
        perfil.getSettings().setJavaScriptEnabled(true);
        perfil.loadDataWithBaseURL("", htmlString, "text/html", "charset=UTF-8", null);
        perfil.setWebViewClient(new WebViewClient() {
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_party_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
