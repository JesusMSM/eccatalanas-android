package es.elconfidencial.eleccionesec.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.rss.RssParser;

/**
 * Created by MOONFISH on 03/08/2015.
 */
public class RSSTab extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/elecciones-municipales-y-autonomicas-2015-11575/";
    List<Object> items = new ArrayList<>();
    List<Noticia> noticias = new ArrayList<>();

    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RssTab";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_rss,container,false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rss_recycler_view);
        mRecyclerView.setHasFixedSize(true);
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
            RssParser saxparser =
                    new RssParser(params[0]);

            noticias = saxparser.parse();
            return true;
        }
        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de noticias.
            for (Noticia noticia : noticias){
                items.add(noticia);
            }
            mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
