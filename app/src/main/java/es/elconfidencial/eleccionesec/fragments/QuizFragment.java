package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.os.AsyncTask;
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
import es.elconfidencial.eleccionesec.rss.RssQuizsParser;


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
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);


        try {
            new CargarXmlTask().execute(rss_url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    /*Permite gestionar de forma asincrona el RSS */
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            RssQuizsParser saxparser =
                    new RssQuizsParser(params[0]);

            quizs = saxparser.parse();
            return true;
        }
        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de quizs.
            for (Quiz quiz : quizs){
                items.add(quiz);
            }
            mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
