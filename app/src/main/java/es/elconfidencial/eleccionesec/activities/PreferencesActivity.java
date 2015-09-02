package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.pushwoosh.PushManager;
import com.pushwoosh.SendPushTagsCallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 30/07/2015.
 */
public class PreferencesActivity extends Activity {

    Context context;
    ImageView header;
    ProgressDialog pd;

    private PushManager pushManager ;
    public final String NAME_TAGS = "PARTIDOS_TAGS";
    public String[] empty = {};
    public String[] tagPartidos = {"convergencia-democratica-de-catalunya-cdc-6665","xavier-garcia-albiol-14516","ines-arrimadas-15503","psc-6043","unio-democratica-de-catalunya-udc-7522","cataluna-si-que-es-pot-15843","junts-pel-si-15903","cup-15022"};
    public ArrayList<String> tagNames;
    public ArrayList<String> switchTags = new ArrayList<>(Arrays.asList(tagPartidos));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.tab_preferences);

        header = (ImageView) findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);
        pushManager = PushManager.getInstance(this);
        pd = ProgressDialog.show(PreferencesActivity.this, "", "Cargando...", true);

        Button saveButton = (Button) findViewById(R.id.guardar);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar en Pushwoosh los switch activados
                sendTagsToPushWoosh();

                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Idioma
        Button buttonIdiomas = (Button) findViewById(R.id.idiomaButton);
        buttonIdiomas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
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

        //Obtenemos las Tags de partidos que estan almacenadas en PW, almacenandolas en el ArrayList tagNames
        getTagsFromPushWoosh();

        //Listeners al modificar el estado de un switch
        SwitchCompat cdc = (SwitchCompat) findViewById(R.id.convergencia_democratica_de_catalunya_cdc_6665);
        SwitchCompat pp = (SwitchCompat) findViewById(R.id.xavier_garcia_albiol_14516);
        SwitchCompat cs = (SwitchCompat) findViewById(R.id.ines_arrimadas_15503);
        SwitchCompat psc = (SwitchCompat) findViewById(R.id.psc_6043);
        SwitchCompat udc = (SwitchCompat) findViewById(R.id.unio_democratica_de_catalunya_udc_7522);
        SwitchCompat catsiqueespot = (SwitchCompat) findViewById(R.id.cataluna_si_que_es_pot_15843);
        SwitchCompat junts = (SwitchCompat) findViewById(R.id.junts_pel_si_15903);
        SwitchCompat cup = (SwitchCompat) findViewById(R.id.cup_15022);

        cdc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        pp.setOnCheckedChangeListener(new OnChangeSwitchListener());
        cs.setOnCheckedChangeListener(new OnChangeSwitchListener());
        psc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        udc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        catsiqueespot.setOnCheckedChangeListener(new OnChangeSwitchListener());
        junts.setOnCheckedChangeListener(new OnChangeSwitchListener());
        cup.setOnCheckedChangeListener(new OnChangeSwitchListener());

    }

    //Comunicacion con Pushwoosh GET
    private void getTagsFromPushWoosh(){
        pd.show();
        pushManager.getTagsAsync(getApplicationContext(), new PushManager.GetTagsListener() {
            @Override
            public void onTagsReceived(Map<String, Object> map) {
                //Primera vez. No exista la TAG. Mandamos a PW un array vacío sin tags
                if(!map.containsKey(NAME_TAGS)){
                    Log.i("elecciones", "EsNull");
                    Object obj = empty;
                    Map<String,Object> tags = new HashMap<>();
                    tags.put(NAME_TAGS,obj);
                    pushManager.sendTags(getApplicationContext(), tags, new SendPushTagsCallBack() {
                        @Override
                        public void taskStarted() {

                        }

                        @Override
                        public void onSentTagsSuccess(Map<String, String> map) {
                            tagNames = new ArrayList<String>(Arrays.asList(empty));
                            pd.dismiss();
                        }

                        @Override
                        public void onSentTagsError(Exception e) {
                            Toast.makeText(PreferencesActivity.this, "No es posible contactar con el servicio de notificaciones, intentelo de nuevo en unos minutos", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                //Existe la TAG. Descargamos el contenido del array de PW y rellenamos los switchs
                }else{
                    Log.i("elecciones","NotNull");
                    ArrayList<String> partidos = new ArrayList<String>();
                    String[] arrayTags=map.get(NAME_TAGS).toString().split("\"");
                    for(String partidoTag :arrayTags) {
                        if(!(partidoTag.contains("[")||partidoTag.contains("]")||partidoTag.contains(","))){
                            partidos.add(partidoTag);
                            //Toast.makeText(ControlNotificacionesActivity.this, pep, Toast.LENGTH_SHORT).show();
                        }
                    }
                    tagNames = partidos;
                    //Activamos o desactivamos los switches en caso de encontrar la tag correspondiente.
                    for (String tag: tagNames){
                        SwitchCompat sc = (SwitchCompat) findViewById(getResources().getIdentifier(tag.replace("-","_"),"id",getPackageName()));
                        if(sc != null){
                            sc.setChecked(true);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(PreferencesActivity.this, "No es posible contactar con el servicio de notificaciones, reinicie la aplicación", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                pd.dismiss();
            }
        });

    }

    //Comunicacion con PushWoosh SET
    private void sendTagsToPushWoosh(){
        Map<String,Object> tags = new HashMap<>();

        tags.put(NAME_TAGS, tagNames.toArray());
        pushManager.sendTags(getApplicationContext(), tags, new SendPushTagsCallBack() {
            @Override
            public void taskStarted() {
                //Task Start
            }

            @Override
            public void onSentTagsSuccess(Map<String, String> map) {
                //Task end
            }

            @Override
            public void onSentTagsError(Exception e) {

            }
        });
    }

    //Listener al cambiar el estado de un switch
    private class OnChangeSwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            String tagIdChanged = getResources().getResourceEntryName(compoundButton.getId()).replace("_","-");
            if(isChecked){
                //Añadirlo a la lista
                if(!tagNames.contains(tagIdChanged)){
                    tagNames.add(tagIdChanged);
                }
            }else{
                //Eliminarlo de la lista
                if(tagNames.contains(tagIdChanged)) {
                    tagNames.remove(tagIdChanged);
                }
            }
        }
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
