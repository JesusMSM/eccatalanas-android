package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Quiz;


public class QuizFragment extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/test-15238/";

    List<Object> items = new ArrayList<>();
    List<Quiz> quizs = new ArrayList<>();

    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "QuizFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.quiz_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context, getSampleArrayList());
        mRecyclerView.setAdapter(mAdapter);

        

        return v;
    }


    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Quiz(R.drawable.nopic, getString(R.string.alerta_conexion_noticias), getString(R.string.autor_quiz), "http://datos.elconfidencial.com/quizcat1/"));
        items.add(new Quiz(R.drawable.nopic, getString(R.string.alerta_conexion_noticias), getString(R.string.autor_quiz), "http://datos.elconfidencial.com/quizcat2/"));
        items.add(new Quiz(R.drawable.nopic, getString(R.string.alerta_conexion_noticias), getString(R.string.autor_quiz), "http://datos.elconfidencial.com/quizcat3/"));
        items.add(new Quiz(R.drawable.nopic, getString(R.string.alerta_conexion_noticias), getString(R.string.autor_quiz), "http://datos.elconfidencial.com/quizcat4/"));
        items.add(new Quiz(R.drawable.nopic, getString(R.string.alerta_conexion_noticias), getString(R.string.autor_quiz), "http://datos.elconfidencial.com/quizcat5/"));
        return items;
    }
}
