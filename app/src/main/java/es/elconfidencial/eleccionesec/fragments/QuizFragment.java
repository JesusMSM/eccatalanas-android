package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.elconfidencial.eleccionesec.R;


public class QuizFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        return v;
    }
}
