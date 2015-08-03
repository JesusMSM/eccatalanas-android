package es.elconfidencial.eleccionesec.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 03/08/2015.
 */
public class RSSTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_rss,container,false);
        return v;
    }
}
