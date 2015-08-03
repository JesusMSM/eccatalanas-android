package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by JesúsManuel on 14/07/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import es.elconfidencial.eleccionesec.R;


public class DocumentTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_document,container,false);
        return v;
    }
}
