package es.elconfidencial.eleccionesec.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;

/**
 * Created by MOONFISH on 16/08/2015.
 */
public class PreferencesTab extends Fragment {

    ImageView header;
    Button buttonIdiomas;
    Context context;
    Activity activity;
    View v;

    private PushManager pushManager ;
    public final String NAME_TAGS = "PARTIDOS_TAGS";
    public String[] empty = {};
    public String[] tagPartidos = {"convergencia-democratica-de-catalunya-cdc-6665","xavier-garcia-albiol-14516","ines-arrimadas-15503","psc-6043","unio-democratica-de-catalunya-udc-7522","cataluna-si-que-es-pot-15843","junts-pel-si-15903","cup-15022"};
    public ArrayList<String> tagNames = new ArrayList<String>(Arrays.asList(empty));
    public ArrayList<String> switchTags = new ArrayList<>(Arrays.asList(tagPartidos));

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tab_preferences, container, false);
        context = getActivity().getApplicationContext();
        activity = getActivity();

       // header = (ImageView) v.findViewById(R.id.imageView);
       // header.setImageResource(R.drawable.preferencias);
        pushManager = PushManager.getInstance(getActivity());

        //Escondemos el boton guardar (Tab)
        Button save = (Button) v.findViewById(R.id.guardar);
        save.setVisibility(View.GONE);

        buttonIdiomas = (Button) v.findViewById(R.id.idiomaButton);
        buttonIdiomas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.seleccion_idioma)
                        .items(R.array.idioma)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                //Cargamos la configuracion para cambiarla despues
                                Resources standardResources = getActivity().getResources();
                                DisplayMetrics metrics = standardResources.getDisplayMetrics();
                                Configuration config = new Configuration(standardResources.getConfiguration());

                                //Cargamos las preferencias compartidas, es como la base de datos para guardarlas y que se recuerden mas tarde
                                SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
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
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                                //Intent refresh = new Intent(HomeActivity.context, HomeActivity.class);
                                // startActivity(refresh);
                                return true;
                            }
                        })
                        .positiveText(R.string.cambiar)
                        .show();
            }
        });


        // --------- Ajuste de Fonts-----------
        insertFonts(v);

        //Obtenemos las Tags de partidos que estan almacenadas en PW, almacenandolas en el ArrayList tagNames
        getTagsFromPushWoosh();

        //Listeners al modificar el estado de un switch
        SwitchCompat cdc = (SwitchCompat) v.findViewById(R.id.convergencia_democratica_de_catalunya_cdc_6665);
        SwitchCompat pp = (SwitchCompat) v.findViewById(R.id.xavier_garcia_albiol_14516);
        SwitchCompat cs = (SwitchCompat) v.findViewById(R.id.ines_arrimadas_15503);
        SwitchCompat psc = (SwitchCompat) v.findViewById(R.id.psc_6043);
        SwitchCompat udc = (SwitchCompat) v.findViewById(R.id.unio_democratica_de_catalunya_udc_7522);
        SwitchCompat catsiqueespot = (SwitchCompat) v.findViewById(R.id.cataluna_si_que_es_pot_15843);
        SwitchCompat junts = (SwitchCompat) v.findViewById(R.id.junts_pel_si_15903);
        SwitchCompat cup = (SwitchCompat) v.findViewById(R.id.cup_15022);

        cdc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        pp.setOnCheckedChangeListener(new OnChangeSwitchListener());
        cs.setOnCheckedChangeListener(new OnChangeSwitchListener());
        psc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        udc.setOnCheckedChangeListener(new OnChangeSwitchListener());
        catsiqueespot.setOnCheckedChangeListener(new OnChangeSwitchListener());
        junts.setOnCheckedChangeListener(new OnChangeSwitchListener());
        cup.setOnCheckedChangeListener(new OnChangeSwitchListener());

        return v;
    }
    //Comunicacion con Pushwoosh GET
    private void getTagsFromPushWoosh(){
        if(isAdded()) {
            pushManager.getTagsAsync(context, new PushManager.GetTagsListener() {
                @Override
                public void onTagsReceived(Map<String, Object> map) {
                    //Primera vez. No exista la TAG. Mandamos a PW un array vacío sin tags
                    if (!map.containsKey(NAME_TAGS)) {
                        Log.i("elecciones", "EsNull");
                        Object obj = empty;
                        Map<String, Object> tags = new HashMap<>();
                        tags.put(NAME_TAGS, obj);
                        pushManager.sendTags(context, tags, new SendPushTagsCallBack() {
                            @Override
                            public void taskStarted() {

                            }

                            @Override
                            public void onSentTagsSuccess(Map<String, String> map) {
                                tagNames = new ArrayList<String>(Arrays.asList(empty));
                            }

                            @Override
                            public void onSentTagsError(Exception e) {
                                Toast.makeText(activity, "No es posible contactar con el servicio de notificaciones, intentelo de nuevo en unos minutos", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Existe la TAG. Descargamos el contenido del array de PW y rellenamos los switchs
                    } else {
                        if(isAdded()) {
                            Log.i("elecciones", "NotNull");
                            ArrayList<String> partidos = new ArrayList<String>();
                            String[] arrayTags = map.get(NAME_TAGS).toString().split("\"");
                            for (String partidoTag : arrayTags) {
                                if (!(partidoTag.contains("[") || partidoTag.contains("]") || partidoTag.contains(","))) {
                                    partidos.add(partidoTag);
                                    //Toast.makeText(ControlNotificacionesActivity.this, pep, Toast.LENGTH_SHORT).show();
                                }
                            }
                            tagNames = partidos;
                            //Activamos o desactivamos los switches en caso de encontrar la tag correspondiente.
                            for (String tag : tagNames) {
                                SwitchCompat sc = (SwitchCompat) v.findViewById(getResources().getIdentifier(tag.replace("-", "_"), "id", activity.getPackageName()));
                                if (sc != null) {
                                    sc.setChecked(true);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(activity, "No es posible contactar con el servicio de notificaciones, reinicie la aplicación", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        }
    }

    //Comunicacion con PushWoosh SET
    private void sendTagsToPushWoosh(){
        Map<String,Object> tags = new HashMap<>();
        if(isAdded()){
        tags.put(NAME_TAGS, tagNames.toArray());
        pushManager.sendTags(context, tags, new SendPushTagsCallBack() {
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
    }}

    //Listener al cambiar el estado de un switch
    private class OnChangeSwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isAdded()) {
                String tagIdChanged = getResources().getResourceEntryName(compoundButton.getId()).replace("_", "-");
                if (isChecked) {
                    //Añadirlo a la lista
                    if (!tagNames.contains(tagIdChanged)) {
                        tagNames.add(tagIdChanged);
                    }
                } else {
                    //Eliminarlo de la lista
                    if (tagNames.contains(tagIdChanged)) {
                        tagNames.remove(tagIdChanged);
                    }
                }
                sendTagsToPushWoosh();
            }
        }
    }
    private void insertFonts(View v){
        // --------- Ajuste de Fonts-----------

        //Título
        Typeface mb = Typeface.createFromAsset(context.getAssets(),
                "Milio-Bold.ttf");
        TextView preferencias = (TextView) v.findViewById(R.id.preferencias);
        preferencias.setTypeface(mb);

        //Semibold(títulos)
        Typeface ts = Typeface.createFromAsset(HomeActivity.context.getAssets(),
                "Titillium-Semibold.otf");
        TextView suscripcion = (TextView) v.findViewById(R.id.suscripcion);
        TextView idioma = (TextView) v.findViewById(R.id.idioma);

        suscripcion.setTypeface(ts);
        idioma.setTypeface(ts);

        //Regular(resto del contenido)
        Typeface tr = Typeface.createFromAsset(HomeActivity.context.getAssets(),
                "Titillium-Regular.otf");
        TextView suscripcion_descripcion = (TextView) v.findViewById(R.id.suscripcion_descripcion);
        TextView cdc = (TextView) v.findViewById(R.id.cdc);
        TextView pp = (TextView) v.findViewById(R.id.pp);
        TextView cs = (TextView) v.findViewById(R.id.cs);
        TextView psc = (TextView) v.findViewById(R.id.psc);
        TextView udc = (TextView) v.findViewById(R.id.udc);
        TextView csqep = (TextView) v.findViewById(R.id.csqep);
        TextView jps = (TextView) v.findViewById(R.id.jps);
        TextView cup = (TextView) v.findViewById(R.id.cup);
        TextView notificaciones_descripcion = (TextView) v.findViewById(R.id.notificaciones_descripcion);

        suscripcion_descripcion.setTypeface(tr);
        cdc.setTypeface(tr);
        pp.setTypeface(tr);
        cs.setTypeface(tr);
        psc.setTypeface(tr);
        udc.setTypeface(tr);
        csqep.setTypeface(tr);
        jps.setTypeface(tr);
        cup.setTypeface(tr);
        //notificaciones_descripcion.setTypeface(tr);
        buttonIdiomas.setTypeface(tr);
    }


}

