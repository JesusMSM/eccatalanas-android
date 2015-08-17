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
 * Created by JesúsManuel on 16/08/2015.
 */
public class PreferencesTab extends Fragment {

    ImageView header;
    ListView lvIdioma;
    ListView lvPartidos;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_preferences,container,false);
        header = (ImageView) v.findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

        lvIdioma = (ListView) v.findViewById(R.id.listViewIdiomas);
        lvPartidos = (ListView) v.findViewById(R.id.listViewPartidos);

        List<String> idioma = new ArrayList<>();
        idioma.add(getResources().getString(R.string.seleccion_idioma));

        List<String> partidos = new ArrayList<>();
        //idioma.add(getResources().getString(R.string.seleccion_idioma));
        for(int i=0; i<10; i++){
            partidos.add("Partido "+i);
        }

        ArrayAdapter<String> arrayAdapterIdioma = new ArrayAdapter<>(
                HomeActivity.context,
                R.layout.list_seleccion_idioma,
                idioma );

        ArrayAdapter<String> arrayAdapterPartidos = new ArrayAdapter<>(
                HomeActivity.context,
                R.layout.list_seleccion_idioma,
                partidos );

        lvIdioma.setAdapter(arrayAdapterIdioma);
        lvPartidos.setAdapter(arrayAdapterPartidos);

        lvIdioma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

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

        Typeface tf = Typeface.createFromAsset(HomeActivity.context.getAssets(),
                "Roboto-Medium.ttf");
        TextView tv = (TextView) v.findViewById(R.id.textView4);
        TextView tv1 = (TextView) v.findViewById(R.id.textView5);
        tv.setTypeface(tf);
        tv1.setTypeface(tf);



        return v;
    }


}

