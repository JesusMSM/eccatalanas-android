package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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

import com.squareup.picasso.Picasso;

import es.elconfidencial.eleccionesec.R;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

public class PoliticianCardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_card);

        //Extraemos el intent para leer los par?metros y rellenar los campos
        Intent intent = getIntent();

        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView edad = (TextView) findViewById(R.id.edad);
        //TextView partido = (TextView) findViewById(R.id.partido);
        TextView cargo = (TextView) findViewById(R.id.cargo);
        WebView perfil = (WebView) findViewById(R.id.perfil);

        try {
            //Screen size
            Display display = (this).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            int resid=intent.getIntExtra("imagen", 0);
            //Load Image
            Picasso.with(getApplicationContext()).load(resid).placeholder(R.drawable.nopicpoliticolow).resize(width, height * 2 / 3).centerInside().transform(new VignetteFilterTransformation(getApplicationContext(),
                    new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f)).into(imagen);
            /*BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inDither = true;
            opts.inSampleSize = 3;
            Bitmap bitmapImage = BitmapFactory.decodeResource(this.getResources(), resid, opts);
            imagen.setImageBitmap(bitmapImage);*/
        }catch (Exception e){e.printStackTrace();}

        nombre.setText(intent.getStringExtra("nombre"));
        edad.setText(intent.getStringExtra("edad"));
        //partido.setText(intent.getStringExtra("partido"));
        cargo.setText(intent.getStringExtra("cargo"));

        //Fonts
        nombre.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Milio-Heavy-Italic.ttf"));
        cargo.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        edad.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Semibold.otf"));

        //Insertamos la cabecera al html con el estilo
        String head = "<head><style>@font-face {font-family: MilioHeavy;text-align:justify; src: url(\"file:///android_asset/Milio-Heavy.ttf\")}" +
                "@font-face {font-family: TitilliumLight;src: url(\"file:///android_asset/Titillium-Light.otf\")}" +
                "@font-face {font-family: TitilliumSemibold;src: url(\"file:///android_asset/Titillium-Semibold.otf\")}" +
                "body{font-family:TitilliumLight;}" +
                "strong{font-family:TitilliumSemibold;}"+
                "@media (max-width: 1280px) {html { font-size: 25px;}} @media (max-width: 720px) { html { font-size: 18px;}} @media (max-width: 480px) { html { font-size: 16px; }}" +
                "img{max-width: 100%; width:auto; height: auto;}</style></head>";
        String htmlString ="<html>" + head + "<body style='text-align:justify;'>" + intent.getStringExtra("perfil") + "</body></html>";
        System.out.println(intent.getStringExtra("perfil"));
        perfil.getSettings().setJavaScriptEnabled(true);
        perfil.getSettings().setDefaultTextEncodingName("utf-8");
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
        getMenuInflater().inflate(R.menu.menu_politician_card, menu);
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

    @Override
    public void onBackPressed() {
        System.gc();
        super.onBackPressed();
    }
}
