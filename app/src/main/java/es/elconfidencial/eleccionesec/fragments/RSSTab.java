package es.elconfidencial.eleccionesec.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Mensaje;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Title;
import es.elconfidencial.eleccionesec.rss.RssNoticiasParser;

/**
 * Created by MOONFISH on 03/08/2015.
 */
public class RSSTab extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/elecciones-cataluna-2015-6160/";

    PullRefreshLayout layout;

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

        new CargarXmlTask().execute(rss_url);

        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CargarXmlTask().execute(rss_url);

            }
        });

        return v;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    /*Permite gestionar de forma asincrona el RSS */
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        List<Object> items = new ArrayList<>();
        List<Noticia> noticias = new ArrayList<>();

        protected Boolean doInBackground(String... params) {
            try {
                if(haveNetworkConnection()) {
                    RssNoticiasParser saxparser =
                            new RssNoticiasParser(params[0]);

                    noticias = saxparser.parse();

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        protected void onPostExecute(Boolean result) {

            if(isAdded()){
            //Insertamos t�tulo del tab
            items.add(new Title(getString(R.string.titulo_noticias)));


            //Tratamos la lista de noticias.
            //En caso de sin conexi�n, se muestra mensaje de alerta
            if(haveNetworkConnection()) {
                for (Noticia noticia : noticias){
                    items.add(noticia);
                }
            } else{
                items.add(new Mensaje(getString(R.string.alerta_conexion_noticias)));
            }

            mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
            mRecyclerView.setAdapter(mAdapter);
            if(layout!=null) layout.setRefreshing(false);
        }
    }
    }
}
