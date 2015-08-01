package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class WelcomeActivity extends Activity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.context = getApplicationContext();

        //Cargamos las preferencias sobre idiomas si las hay
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "ninguno"); //Si no existe, devuelve el segundo parametro
        setLocale(idioma); //Cambiamos el parametro de config locale
    }

    /** Called when the user clicks the Start button */
    public void start(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    private void setLocale(String idioma){
        Locale locale;
        Resources standardResources = context.getResources();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration config = new Configuration(standardResources.getConfiguration());
        if(idioma.equals("catalan")) {
            locale = new Locale("ca", "ES");
        }
        else if(idioma.equals("espanol")){
            locale = new Locale("es", "ES");
        }
        else{
            locale = Locale.getDefault();
        }

        config.locale = locale;
        standardResources.updateConfiguration(config, metrics);
    }
}
