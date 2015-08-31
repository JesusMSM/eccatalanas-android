package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
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

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Partido;
import es.elconfidencial.eleccionesec.model.Politico;
import es.elconfidencial.eleccionesec.rss.RssNoticiasParser;


public class HomeTab extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/elecciones-cataluna-2015-6160/";
    List<Object> items = new ArrayList<>();
    List<Noticia> noticias = new ArrayList<>();
    TextView tiempo;
    TextView label;


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

        try {
            new CargarXmlTask().execute(rss_url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            RssNoticiasParser saxparser =
                    new RssNoticiasParser(params[0]);

            noticias = saxparser.parse();
            return true;
        }
        protected void onPostExecute(Boolean result) {

            //Primero insertamos el contador
            items.add("contador");

            //Después tratamos la lista de noticias, elegimos únicamente las 3 primeras (más recientes).
            int listLength = 3; //número de noticias que deben aparecer
            for (int i = 0; i< listLength; i++){
                items.add(noticias.get(i));
            }

            //Añadimos una ficha de un partido y de un político aleatoria
            int randomNumber = (int) (Math.random()*7+1);
            System.out.println(randomNumber);

            //Hacemos un switch para que se muestre el partido asociado al número aleatorio
            switch (randomNumber) {
                case 1:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 2:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 3:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 4:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 5:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 6:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
                    break;
                case 7:
                    items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
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
        }
    }
}
