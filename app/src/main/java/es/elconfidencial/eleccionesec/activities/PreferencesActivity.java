package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class PreferencesActivity extends PreferenceActivity {

    ImageView header;
    Context context;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.activity_preferences);

        header = (ImageView) findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

        //Creo el boton programaticamente, porque en el layout da problemas con la lista
        button = new Button(this);
        button.setText("Guardar");
        button.setBackgroundColor(getResources().getColor(R.color.white));
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }});

        addPreferencesFromResource(R.xml.prefs);
        ListView v = getListView();
        v.addFooterView(button);

        //Se ejecutará al pulsar esa preferencia
        Preference myPref =  findPreference("seleccionidioma");
        myPref.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                new MaterialDialog.Builder(PreferencesActivity.this)
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
                return true;
            }
        });
    }

}
