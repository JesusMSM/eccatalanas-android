package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 09/08/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.elconfidencial.eleccionesec.R;


public class QuizTab extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_quiz,container,false);
        return v;
    }


}
