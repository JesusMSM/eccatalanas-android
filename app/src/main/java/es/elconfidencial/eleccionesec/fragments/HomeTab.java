package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
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
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Mensaje;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Partido;
import es.elconfidencial.eleccionesec.model.Politico;
import es.elconfidencial.eleccionesec.model.Title;
import es.elconfidencial.eleccionesec.rss.RssNoticiasParser;


public class HomeTab extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/elecciones-cataluna-2015-6160/";


    TextView tiempo;
    TextView label;
    PullRefreshLayout layout;


    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "HomeTab";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_home,container,false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recycler_view);
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

            //Primero insertamos el contador
            items.add("contador");

            //Título del grafico
            items.add(new Title(getString(R.string.titulo_resultados)));

            //Título de noticias
            items.add(new Title(getString(R.string.titulo_noticias)));

            //Después tratamos la lista de noticias, elegimos únicamente las 3 primeras (más recientes).
            //Si no tiene conexión mostramos en su lugar el mensaje de alerta
            if(haveNetworkConnection()) {
                int listLength = 3; //número de noticias que deben aparecer
                for (int i = 0; i < listLength; i++) {
                    items.add(noticias.get(i));
                }
            } else{
                items.add(new Mensaje(getString(R.string.alerta_conexion_noticias)));
            }

            //Títulos
            items.add(new Title(getString(R.string.titulo_fichas)));

            //Añadimos una ficha de un partido y de un político aleatoria
            int randomNumber = (int) (Math.random()*7+1);
            System.out.println(randomNumber);


            //Hacemos un switch para que se muestre el partido asociado al número aleatorio
            switch (randomNumber) {
                case 1:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 2:
                    items.add(new Partido( R.drawable.psc, getString(R.string.psc_nombre), getString(R.string.psc_representantes), getString(R.string.psc_fundacion), getString(R.string.psc_escanos), getString(R.string.psc_porcentajeVotos), getString(R.string.psc_ideologia), getString(R.string.psc_partidosRepresentados), getString(R.string.psc_perfil)));
                    break;
                case 3:
                    items.add(new Partido( R.drawable.jps, getString(R.string.cup_nombre), getString(R.string.cup_representantes), getString(R.string.cup_fundacion), getString(R.string.cup_escanos), getString(R.string.cup_porcentajeVotos), getString(R.string.cup_ideologia), getString(R.string.cup_partidosRepresentados), getString(R.string.cup_perfil)));
                    break;
                case 4:
                    items.add(new Partido( R.drawable.jps, getString(R.string.jps_nombre), getString(R.string.jps_representantes), getString(R.string.jps_fundacion), getString(R.string.jps_escanos), getString(R.string.jps_porcentajeVotos), getString(R.string.jps_ideologia), getString(R.string.jps_partidosRepresentados), getString(R.string.jps_perfil)));
                    break;
                case 5:
                    items.add(new Partido( R.drawable.pp, getString(R.string.pp_nombre), getString(R.string.pp_representantes), getString(R.string.pp_fundacion), getString(R.string.pp_escanos), getString(R.string.pp_porcentajeVotos), getString(R.string.pp_ideologia), getString(R.string.pp_partidosRepresentados), getString(R.string.pp_perfil)));
                    break;
                case 6:
                    items.add(new Partido( R.drawable.cs, getString(R.string.cs_nombre), getString(R.string.cs_representantes), getString(R.string.cs_fundacion), getString(R.string.cs_escanos), getString(R.string.cs_porcentajeVotos), getString(R.string.cs_ideologia), getString(R.string.cs_partidosRepresentados), getString(R.string.cs_perfil)));
                    break;
                case 7:
                    items.add(new Partido( R.drawable.udc, getString(R.string.udc_nombre), getString(R.string.udc_representantes), getString(R.string.udc_fundacion), getString(R.string.udc_escanos), getString(R.string.udc_porcentajeVotos), getString(R.string.udc_ideologia), getString(R.string.udc_partidosRepresentados), getString(R.string.udc_perfil)));
                    break;
                case 8:
                    items.add(new Partido( R.drawable.csqep, getString(R.string.csqep_nombre), getString(R.string.csqep_representantes), getString(R.string.csqep_fundacion), getString(R.string.csqep_escanos), getString(R.string.csqep_porcentajeVotos), getString(R.string.csqep_ideologia), getString(R.string.csqep_partidosRepresentados), getString(R.string.csqep_perfil)));
                    break;
                default:
                    break;

            }

            //Refrescamos el número aleatorio, para que cambie su valor
            randomNumber = (int) (Math.random()*7+1);
            System.out.println(randomNumber);


            //Hacemos un switch para que se muestre el político asociado al número aleatorio
            switch (randomNumber) {
                case 1:
                    items.add(new Politico(R.drawable.artur_mas, getString(R.string.artur_mas_nombre), getString(R.string.artur_mas_edad), getString(R.string.artur_mas_partido), getString(R.string.artur_mas_cargo), getString(R.string.artur_mas_perfil)));
                    break;
                case 2:
                    items.add(new Politico(R.drawable.miquel_iceta, getString(R.string.miquel_iceta_nombre), getString(R.string.miquel_iceta_edad), getString(R.string.miquel_iceta_partido), getString(R.string.miquel_iceta_cargo), getString(R.string.miquel_iceta_perfil)));
                    break;
                case 3:
                    items.add(new Politico(R.drawable.antonio_banos, getString(R.string.antonio_banos_nombre), getString(R.string.antonio_banos_edad), getString(R.string.antonio_banos_partido), getString(R.string.antonio_banos_cargo), getString(R.string.antonio_banos_perfil)));
                    break;
                case 4:
                    items.add(new Politico(R.drawable.raul_romeva, getString(R.string.raul_romeva_nombre), getString(R.string.raul_romeva_edad), getString(R.string.raul_romeva_partido), getString(R.string.raul_romeva_cargo), getString(R.string.raul_romeva_perfil)));
                    break;
                case 5:
                    items.add(new Politico(R.drawable.xavier_garcia_albiol, getString(R.string.xavier_garcia_albiol_nombre), getString(R.string.xavier_garcia_albiol_edad), getString(R.string.xavier_garcia_albiol_partido), getString(R.string.xavier_garcia_albiol_cargo), getString(R.string.xavier_garcia_albiol_perfil)));
                    break;
                case 6:
                    items.add(new Politico(R.drawable.ines_arrimadas, getString(R.string.ines_arrimadas_nombre), getString(R.string.ines_arrimadas_edad), getString(R.string.ines_arrimadas_partido), getString(R.string.ines_arrimadas_cargo), getString(R.string.ines_arrimadas_perfil)));
                    break;
                case 7:
                    items.add(new Politico(R.drawable.lluis_rabell, getString(R.string.lluis_rabell_nombre), getString(R.string.lluis_rabell_edad), getString(R.string.lluis_rabell_partido), getString(R.string.lluis_rabell_cargo), getString(R.string.lluis_rabell_perfil)));
                    break;
                default:
                    break;

            }

            mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
            mRecyclerView.setAdapter(mAdapter);
            if(layout!=null) layout.setRefreshing(false);
        }
    }
}
