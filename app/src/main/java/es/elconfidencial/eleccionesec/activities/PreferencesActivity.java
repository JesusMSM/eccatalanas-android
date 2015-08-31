package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 30/07/2015.
 */
public class PreferencesActivity extends Activity {

    Context context;

    ImageView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.tab_preferences);

        header = (ImageView) findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

        Button saveButton = (Button) findViewById(R.id.guardar);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button buttonIdiomas = (Button) findViewById(R.id.idiomaButton);
        buttonIdiomas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
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
                                SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
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
            }
        });

        // --------- Ajuste de Fonts de Contenido-----------
        insertFonts();

        //Insertamos font del selector de idioma
        Typeface tr = Typeface.createFromAsset(context.getAssets(),
                "Titillium-Regular.otf");
        buttonIdiomas.setTypeface(tr);
    }




    private void insertFonts(){
        // --------- Ajuste de Fonts-----------

        //Semibold(títulos)
        Typeface ts = Typeface.createFromAsset(context.getAssets(),
                "Titillium-Semibold.otf");
        TextView suscripcion = (TextView) findViewById(R.id.suscripcion);
        TextView idioma = (TextView) findViewById(R.id.idioma);

        suscripcion.setTypeface(ts);
        idioma.setTypeface(ts);

        //Regular(resto del contenido)
        Typeface tr = Typeface.createFromAsset(context.getAssets(),
                "Titillium-Regular.otf");
        TextView suscripcion_descripcion = (TextView) findViewById(R.id.suscripcion_descripcion);
        TextView cdc = (TextView) findViewById(R.id.cdc);
        TextView pp = (TextView) findViewById(R.id.pp);
        TextView cs = (TextView) findViewById(R.id.cs);
        TextView psc = (TextView) findViewById(R.id.psc);
        TextView udc = (TextView) findViewById(R.id.udc);
        TextView csqep = (TextView) findViewById(R.id.csqep);
        TextView jps = (TextView) findViewById(R.id.jps);
        TextView cup = (TextView) findViewById(R.id.cup);
        TextView notificaciones_descripcion = (TextView) findViewById(R.id.notificaciones_descripcion);

        suscripcion_descripcion.setTypeface(tr);
        cdc.setTypeface(tr);
        pp.setTypeface(tr);
        cs.setTypeface(tr);
        psc.setTypeface(tr);
        udc.setTypeface(tr);
        csqep.setTypeface(tr);
        jps.setTypeface(tr);
        cup.setTypeface(tr);
        notificaciones_descripcion.setTypeface(tr);

    }
}
