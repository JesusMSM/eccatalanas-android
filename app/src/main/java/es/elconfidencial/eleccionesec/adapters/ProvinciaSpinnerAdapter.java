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
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.WelcomeActivity;
import es.elconfidencial.eleccionesec.model.IdiomaSpinnerModel;

/**
 * Created by Afll on 08/09/2015.
 */
public class ProvinciaSpinnerAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private List<String> data;
    LayoutInflater inflater;

    public ProvinciaSpinnerAdapter(WelcomeActivity welcomeActivity,int textViewResourceId,List<String> objects) {
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

        View row = inflater.inflate(R.layout.row_custom_spinner_provincia, parent, false);

        TextView nombre        = (TextView)row.findViewById(R.id.nombreProvincia);

        // Set values for spinner each row
        nombre.setText(data.get(position));

        nombre.setTypeface(Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "Titillium-Semibold.otf"));
        return row;
    }
}
