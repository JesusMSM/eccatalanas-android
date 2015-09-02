package es.elconfidencial.eleccionesec.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.WelcomeActivity;
import es.elconfidencial.eleccionesec.model.IdiomaSpinnerModel;

/**
 * Created by Afll on 02/09/2015.
 */
public class IdiomaSpinnerAdapter extends ArrayAdapter<String>{

        private Activity activity;
        private ArrayList data;
        IdiomaSpinnerModel idioma;
        LayoutInflater inflater;

        public IdiomaSpinnerAdapter(WelcomeActivity welcomeActivity,int textViewResourceId,ArrayList objects) {
            super(welcomeActivity, textViewResourceId, objects);

            this.activity = welcomeActivity;
            data     = objects;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            View row = inflater.inflate(R.layout.row_custom_spinner, parent, false);

            idioma = (IdiomaSpinnerModel) data.get(position);

            TextView nombre        = (TextView)row.findViewById(R.id.nombre);
            TextView sigla          = (TextView)row.findViewById(R.id.sigla);
            ImageView imagen = (ImageView)row.findViewById(R.id.imagen);



            // Set values for spinner each row
            nombre.setText(idioma.getNombre());
            sigla.setText(idioma.getSigla());
            imagen.setImageResource(idioma.getImagen());

            nombre.setTypeface(Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
            sigla.setTypeface(Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "Titillium-Light.otf"));
            return row;
        }
    }

