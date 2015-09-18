package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;*/


import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.adapters.IdiomaSpinnerAdapter;
import es.elconfidencial.eleccionesec.adapters.ProvinciaSpinnerAdapter;
import es.elconfidencial.eleccionesec.model.IdiomaSpinnerModel;

import com.comscore.analytics.comScore;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class WelcomeActivity extends Activity {

    public static Context context;
    public ArrayList<IdiomaSpinnerModel> arrayIdiomasSpinner = new ArrayList<IdiomaSpinnerModel>();
    private IdiomaSpinnerAdapter idiomaAdapter;
    String selectedProvincia = "Barcelona";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        this.context = getApplicationContext();

        comScore.setAppContext(this.getApplicationContext());

        //Cargamos las preferencias sobre idiomas si las hay
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "ninguno"); //Si no existe, devuelve el segundo parametro
        setLocale(idioma); //Cambiamos el parametro de config locale

        //Titulo
        TextView titulo = (TextView) findViewById(R.id.eg2015);
        titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Semibold.otf"));

        //Introduce Provincia
        //TextView introduce = (TextView) findViewById(R.id.introduzcaProvincia);
        //introduce.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

        //Provincia Spinner
        Spinner spinnerProvincia = (Spinner) findViewById(R.id.spinnerProvincia);
        String[] arrayProvincias = getResources().getStringArray(R.array.provincias);
        List<String> spinnerArray =  new ArrayList<String>();

        //Default value
        spinnerArray.add(this.getResources().getString(R.string.introduce_tu_provincia));

        for (int i = 0; i<arrayProvincias.length;i++){
            spinnerArray.add(arrayProvincias[i]);
        }

        ProvinciaSpinnerAdapter adapter = new ProvinciaSpinnerAdapter(
                this, R.layout.row_custom_spinner_provincia, spinnerArray);

        spinnerProvincia.setAdapter(adapter);
        spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0: selectedProvincia = "-";break;
                    case 1: selectedProvincia = "Barcelona";break;
                    case 2: selectedProvincia = "Gerona";break;
                    case 3: selectedProvincia = "Lerida";break;
                    case 4: selectedProvincia = "Tarragona";break;
                    case 5: selectedProvincia = "Alava";break;
                    case 6: selectedProvincia = "Albacete";break;
                    case 7: selectedProvincia = "Alicante";break;
                    case 8: selectedProvincia = "Almeria";break;
                    case 9: selectedProvincia = "Asturias";break;
                    case 10: selectedProvincia = "Avila";break;
                    case 11: selectedProvincia = "Badajoz";break;
                    case 12: selectedProvincia = "Burgos";break;
                    case 13: selectedProvincia = "Caceres";break;
                    case 14: selectedProvincia = "Cadiz";break;
                    case 15: selectedProvincia = "Cantabria";break;
                    case 16: selectedProvincia = "Castellon";break;
                    case 17: selectedProvincia = "Ciudad Real";break;
                    case 18: selectedProvincia = "Cordoba";break;
                    case 19: selectedProvincia = "La Coruña";break;
                    case 20: selectedProvincia = "Cuenca";break;
                    case 21: selectedProvincia = "Granada";break;
                    case 22: selectedProvincia = "Guadalajara";break;
                    case 23: selectedProvincia = "Guipuzcoa";break;
                    case 24: selectedProvincia = "Huelva";break;
                    case 25: selectedProvincia = "Huesca";break;
                    case 26: selectedProvincia = "Islas Baleares";break;
                    case 27: selectedProvincia = "Jaen";break;
                    case 28: selectedProvincia = "Leon";break;
                    case 29: selectedProvincia = "Lugo";break;
                    case 30: selectedProvincia = "Madrid";break;
                    case 31: selectedProvincia = "Malaga";break;
                    case 32: selectedProvincia = "Murcia";break;
                    case 33: selectedProvincia = "Navarra";break;
                    case 34: selectedProvincia = "Orense";break;
                    case 35: selectedProvincia = "Palencia";break;
                    case 36: selectedProvincia = "Las Palmas";break;
                    case 37: selectedProvincia = "Pontevedra";break;
                    case 38: selectedProvincia = "La Rioja";break;
                    case 39: selectedProvincia = "Salamanca";break;
                    case 40: selectedProvincia = "Segovia";break;
                    case 41: selectedProvincia = "Sevilla";break;
                    case 42: selectedProvincia = "Soria";break;
                    case 43: selectedProvincia = "Tenerife";break;
                    case 44: selectedProvincia = "Teruel";break;
                    case 45: selectedProvincia = "Toledo";break;
                    case 46: selectedProvincia = "Valencia";break;
                    case 47: selectedProvincia = "Valladolid";break;
                    case 48: selectedProvincia = "Vizcaya";break;
                    case 49: selectedProvincia = "Zamora";break;
                    case 50: selectedProvincia = "Zaragoza";break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Empezar Button
        Button empezar = (Button) findViewById(R.id.start);
        empezar.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Semibold.otf"));;

        //Spinner
        Spinner spinnerIdioma = (Spinner) findViewById(R.id.spinnerIdioma);
        IdiomaSpinnerModel españolRow = new IdiomaSpinnerModel(getResources().getStringArray(R.array.idioma)[0],"(ES)",R.drawable.spainflag);
        IdiomaSpinnerModel catalanRow = new IdiomaSpinnerModel(getResources().getStringArray(R.array.idioma)[1],"(CA)",R.drawable.cataloniaflag);
        arrayIdiomasSpinner.add(españolRow);
        arrayIdiomasSpinner.add(catalanRow);

        idiomaAdapter = new IdiomaSpinnerAdapter(this,R.layout.row_custom_spinner_idioma,arrayIdiomasSpinner);
        spinnerIdioma.setAdapter(idiomaAdapter);
        if(idioma.equals("catalan")){
            spinnerIdioma.setSelection(1);
        }else{
            spinnerIdioma.setSelection(0);
        }
        //Listener Spinner Idioma
        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean initializing = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (initializing) {
                    initializing = false;
                } else {
                    //Cargamos la configuracion para cambiarla despues
                    Resources standardResources = context.getResources();
                    DisplayMetrics metrics = standardResources.getDisplayMetrics();
                    Configuration config = new Configuration(standardResources.getConfiguration());

                    //Cargamos las preferencias compartidas, es como la base de datos para guardarlas y que se recuerden mas tarde
                    SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    //Analizamos la opcion (idioma) elegida
                    switch (position) {
                        case 0:
                            Locale espanol = new Locale("es", "ES");
                            config.locale = espanol;
                            editor.putString("idioma", "espanol"); //Lo guardamos para recordarlo
                            editor.commit(); //Guardamos las SharedPreferences
                            //Actualizamos la configuracion
                            standardResources.updateConfiguration(config, metrics);
                            break;
                        case 1:
                            Locale catalan = new Locale("ca", "ES");
                            config.locale = catalan;
                            editor.putString("idioma", "catalan"); //Lo guardamos para recordarlo
                            editor.commit(); //Guardamos las SharedPreferences
                            //Actualizamos la configuracion
                            standardResources.updateConfiguration(config, metrics);
                            break;
                    }

                    //Codigo para recargar la app con la nueva config
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Powered
        TextView powered = (TextView) findViewById(R.id.powered);
        powered.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
    }

    /** Called when the user clicks the Start button */
    public void start(View view) {
        if(!selectedProvincia.equals("-")) {
            // Enviar provincia a parse
            try {
                Parse.enableLocalDatastore(this);
                //Autenticacion con Parse
                Parse.initialize(this, "7P82tODwUk7C6AZLyLSuKBvyjLZcdpNz80J6RT2Z", "3jhqLEIKUI7RknTCU8asoITvPC9PjHD5n2FDub4h");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Comunicacion con Parse.com
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Provincia");
            query.whereEqualTo("Nombre", selectedProvincia);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        object.increment("Valor");
                        object.saveInBackground();
                    } else {
                        //something went wrong
                    }
                }
            });
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(context,getResources().getString(R.string.error_provincia),Toast.LENGTH_SHORT).show();
        }
    }

    /*public void idioma(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.seleccion_idioma)
                .items(R.array.idioma)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //Cargamos la configuracion para cambiarla despues
                        Resources standardResources = context.getResources();
                        DisplayMetrics metrics = standardResources.getDisplayMetrics();
                        Configuration config = new Configuration(standardResources.getConfiguration());

                        //Cargamos las preferencias compartidas, es como la base de datos para guardarlas y que se recuerden mas tarde
                        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        //Analizamos la opcion (idioma) elegida
                        switch (which) {
                            case 0:
                                Locale espanol = new Locale("es", "ES");
                                config.locale = espanol;
                                editor.putString("idioma", "espanol"); //Lo guardamos para recordarlo
                                break;
                            case 1:
                                Locale catalan = new Locale("ca", "ES");
                                config.locale = catalan;
                                editor.putString("idioma", "catalan"); //Lo guardamos para recordarlo
                                break;
                        }
                        editor.commit(); //Guardamos las SharedPreferences
                        //Actualizamos la configuracion
                        standardResources.updateConfiguration(config, metrics);
                        //Codigo para recargar la app con la nueva config
                        finish();
                        startActivity(getIntent());
                        //Intent refresh = new Intent(HomeActivity.context, HomeActivity.class);
                        // startActivity(refresh);
                        return true;
                    }
                })
                .positiveText(R.string.cambiar)
                .show();

    }*/

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

    @Override
    protected void onResume() {
        super.onResume();
        //Re-register receivers on resume

        comScore.onEnterForeground();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister receivers on pause

        comScore.onExitForeground();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        comScore.onUxInactive();
    }
}
