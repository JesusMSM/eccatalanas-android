package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;
/*import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;*/


import java.util.ArrayList;
import java.util.Locale;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.adapters.IdiomaSpinnerAdapter;
import es.elconfidencial.eleccionesec.model.IdiomaSpinnerModel;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class WelcomeActivity extends Activity {

    public static Context context;
    public ArrayList<IdiomaSpinnerModel> arrayIdiomasSpinner = new ArrayList<IdiomaSpinnerModel>();
    private IdiomaSpinnerAdapter idiomaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**********INICIALIZAMOS PUSHWOOSH*******************/
        //Register receivers for push notifications
 /*       registerReceivers();

        //Create and start push manager
        PushManager pushManager = PushManager.getInstance(this);

        //Start push manager, this will count app open for Pushwoosh stats as well
        try {
            pushManager.onStartup(this);
        }
        catch(Exception e)
        {
            //push notifications are not available or AndroidManifest.xml is not configured properly
        }

        //Register for push!
        pushManager.registerForPushNotifications();

        checkMessage(getIntent());
*/
        setContentView(R.layout.activity_welcome);
        this.context = getApplicationContext();

        //Cargamos las preferencias sobre idiomas si las hay
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "ninguno"); //Si no existe, devuelve el segundo parametro
        setLocale(idioma); //Cambiamos el parametro de config locale

        //Titulo
        TextView titulo = (TextView) findViewById(R.id.eg2015);
        titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Semibold.otf"));

        //Provincia
        EditText chooseProvincia = (EditText) findViewById(R.id.provincia);
        chooseProvincia.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

        //Empezar Button
        Button empezar = (Button) findViewById(R.id.start);
        empezar.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Semibold.otf"));;

        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerIdioma);
        IdiomaSpinnerModel españolRow = new IdiomaSpinnerModel(getResources().getStringArray(R.array.idioma)[0],"(ES)",R.drawable.spainflag);
        IdiomaSpinnerModel catalanRow = new IdiomaSpinnerModel(getResources().getStringArray(R.array.idioma)[1],"(CA)",R.drawable.cataloniaflag);
        arrayIdiomasSpinner.add(españolRow);
        arrayIdiomasSpinner.add(catalanRow);

        idiomaAdapter = new IdiomaSpinnerAdapter(this,R.layout.row_custom_spinner,arrayIdiomasSpinner);
        spinner.setAdapter(idiomaAdapter);
        if(idioma.equals("catalan")){
            spinner.setSelection(1);
        }else{
            spinner.setSelection(0);
        }
        //Listener Spinner Idioma
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean initializing = true;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (initializing) {
                    initializing = false;
                }else {
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
    }

    /** Called when the user clicks the Start button */
    public void start(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
        finish();
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
      //  registerReceivers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister receivers on pause
     //   unregisterReceivers();
    }

    /***********************PUSHWOOSH*****************************/
    //Registration receiver
    /*BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver()
    {
        @Override
        public void onRegisterActionReceive(Context context, Intent intent)
        {
            checkMessage(intent);
        }
    };

    //Push message receiver
    private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
    {
        @Override
        protected void onMessageReceive(Intent intent)
        {
            //JSON_DATA_KEY contains JSON payload of push notification.
            //showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
        }
    };

    //Registration of the receivers
    public void registerReceivers()
    {
        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

        registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);

        registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
    }

    public void unregisterReceivers()
    {
        //Unregister receivers on pause
        try
        {
            unregisterReceiver(mReceiver);
        }
        catch (Exception e)
        {
            // pass.
        }

        try
        {
            unregisterReceiver(mBroadcastReceiver);
        }
        catch (Exception e)
        {
            //pass through
        }
    }
    private void checkMessage(Intent intent)
    {
        if (null != intent)
        {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
            {
                //showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
            }
            else if (intent.hasExtra(PushManager.REGISTER_EVENT))
            {
                //showMessage("register");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
            {
                //showMessage("unregister");
            }
            else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
            {
                //showMessage("register error");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
            {
                //showMessage("unregister error");
            }

            resetIntentValues();
        }
    }*/

    /**
     * Will check main Activity intent and if it contains any PushWoosh data, will clear it
     */
   /* private void resetIntentValues()
    {
        Intent mainAppIntent = getIntent();

        if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
        }

        setIntent(mainAppIntent);
    }

    private void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        checkMessage(intent);
    }*/
}
