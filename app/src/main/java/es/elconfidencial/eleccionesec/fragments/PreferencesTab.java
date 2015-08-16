package es.elconfidencial.eleccionesec.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 16/08/2015.
 */
public class PreferencesTab extends Fragment {

    ImageView header;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_preferences,container,false);
        header = (ImageView) v.findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

        return v;
    }


}

