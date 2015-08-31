package es.elconfidencial.eleccionesec.fragments;

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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;

/**
 * Created by MOONFISH on 16/08/2015.
 */
public class PreferencesTab extends Fragment {

    ImageView header;
    Button buttonIdiomas;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_preferences,container,false);
        header = (ImageView) v.findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

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

        return v;
    }

    private void insertFonts(View v){
        // --------- Ajuste de Fonts-----------

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
        notificaciones_descripcion.setTypeface(tr);
        buttonIdiomas.setTypeface(tr);
    }


}

