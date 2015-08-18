package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by JesúsManuel on 14/07/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;


public class DocumentTab extends Fragment {

    private FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View v =inflater.inflate(R.layout.tab_document, container, false);


/**
        try{
            //Inicializamos Parse
            Parse.initialize(v.getContext(), "5jjYeXiPV5uB7hnZvDT5kfLqkspzYQQCvrAtRyEL", "jvaAGBVLtLIMlWpmLdR8UbRARaw9OO4UyNKyPFbL");
        }catch (Exception e){
            e.printStackTrace();
        }

        //Prueba de comunicacion con Parse.com . Ignorar por el momento
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PartidoPolitico");
        query.whereEqualTo("Nombre","PODEMOS");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    System.out.println(object.getString("Nombre"));
                    object.increment("Valor");
                    object.saveInBackground();
                } else {
                    // something went wrong
                }
            }
        }); **/

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.tab_document);

        Bundle partyTab = new Bundle();
        partyTab.putInt("Arg for PartyTab", 1);
        mTabHost.addTab(mTabHost.newTabSpec("PartyTab").setIndicator("Partidos"),
                PartyFragment.class, partyTab);

        Bundle politicianTab = new Bundle();
        politicianTab.putInt("Arg for politicianTab", 2);
        mTabHost.addTab(mTabHost.newTabSpec("PoliticianTab").setIndicator("Políticos"),
                PoliticianFragment.class, politicianTab);

        Bundle quizTab = new Bundle();
        quizTab.putInt("Arg for quizTab", 2);
        mTabHost.addTab(mTabHost.newTabSpec("QuizTab").setIndicator("Quizs"),
                QuizFragment.class, quizTab);

        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}
