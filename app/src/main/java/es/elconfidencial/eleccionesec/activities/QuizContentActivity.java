package es.elconfidencial.eleccionesec.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

public class QuizContentActivity extends ActionBarActivity {

    private String url = "";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_content);

        //ActionBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled( true );
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title_quizes, null);
        ((TextView)v.findViewById(R.id.actionBarTitle)).setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Titillium-Light.otf"));
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Extraemos los datos del intent
        Intent intent = getIntent();
        url = intent.getStringExtra("link");
        title = intent.getStringExtra("title");


        //Preparamos el Webview
        WebView webview = (WebView)findViewById(R.id.webview_quiz);
        webview.getSettings().setJavaScriptEnabled(true);
       // webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setDefaultTextEncodingName("utf-8");

        //Comprobamos si tiene conexi�n a Internet
        //Si tiene conexi�n cargamos la url, si no tiene mostramos el mensaje de alerta
        if(haveNetworkConnection()){
            webview.loadUrl(url);
        } else {
            //Compramos el tipo de dispositivo y calculamos el tama�o de letra.
            String textSize= "";
            if (getSizeName().equals("xlarge")) {
                textSize="25px";
            } else if (getSizeName().equals("large")) {
                textSize="18px";
            } else if (getSizeName().equals("normal")) {
                textSize="16px";
            }else {
                textSize="14px";
            }

            String head = "<head><style>@font-face {font-family: MilioHeavy;src: url(\"file:///android_asset/Milio-Heavy.ttf\")}" +
                    "@font-face {font-family: TitilliumLight;src: url(\"file:///android_asset/Titillium-Light.otf\")}" +
                    "@font-face {font-family: TitilliumSemibold;src: url(\"file:///android_asset/Titillium-Semibold.otf\")}" +
                    "h2{font-family: MilioHeavy;}" +
                    "body{font-family:TitilliumLight;text-align:justify}" +
                    "a{text-decoration: none;color:black;} " +
                    "html { font-size: " + textSize + "}" +
                    "strong{font-family:TitilliumSemibold;}</style></head>";

            String htmlSinConexion ="<html>" + head + "<body><div>" + getResources().getString(R.string.alerta_conexion_quizes) + "</div></body></html>";
            webview.loadDataWithBaseURL("", htmlSinConexion, "text/html", "charset=UTF-8", null);
        }


        webview.setWebViewClient(
                new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        // disable scroll on touch
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });





    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private String getSizeName() {
        int screenLayout = getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(item.getItemId() == R.id.share){
            shareAction(url,title);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        System.gc();
        finish();
        super.onBackPressed();
    }

    public void shareAction(String url, String title){
        // Llama al sistema para que le muestre un diálogo al usuario con todas las aplicaciones que permitan compartir información
        Intent intent = new Intent();
        String textoCompartir = title + "\n\n" + url;

        intent.setAction( Intent.ACTION_SEND );
        intent.putExtra(Intent.EXTRA_TEXT, textoCompartir );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType( "text/plain" );

        startActivity(  Intent.createChooser( intent, getString(R.string.share) )  );
    }
}
