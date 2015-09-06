package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.chart.PieChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem2012;
import es.elconfidencial.eleccionesec.json.JSONParser;
import es.elconfidencial.eleccionesec.model.Mensaje;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Partido;
import es.elconfidencial.eleccionesec.model.PartidoEstadisticas;
import es.elconfidencial.eleccionesec.model.Politico;
import es.elconfidencial.eleccionesec.model.Title;
import es.elconfidencial.eleccionesec.rss.RssNoticiasParser;


public class HomeTab extends Fragment {

    private String rss_url = "http://rss.elconfidencial.com/tags/temas/elecciones-cataluna-2015-6160/";


    TextView tiempo;
    TextView label;
    PullRefreshLayout layout;
    LinearLayout ll;
    RecyclerView.ViewHolder viewHolder;


    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "HomeTab";


    //CONSTANTES GRAFICOS
    private static String url_2015="http://api.elconfidencial.com/service/elections/place/3/7/99/9/";

    private static final String TAG_DATA = "data";
    private static final String TAG_RESULTS = "results";
    private static final String TAG_COM_AUT = "res_name";
    private static final String TAG_PORCENTAJE = "res_percent_votes_candidacy";
    private static final String TAG_PARTIDO = "res_party";
    private static final String TAG_NOMBRE = "par_name";
    private static final String TAG_ALIAS = "par_alias";
    private static final String TAG_COLOR = "par_color";
    private static final String TAG_ELECTED_MEMBERS = "res_elected_members";

    private int numPartidos2015;
    private PartidoEstadisticas[] arrayPartidos2015;

    private String[] partidos2012 = {"CiU", "PSC", "PP", "ERC", "ICV", "Ciudadanos", "CUP"};
    private double[] porcentajes2012 = {30.68,14.44,13,13.69,9.9,7.58,3.48};
    private String[] colores2012 = {"#18307B","#EF7A36", "#FFED00", "#FFB232", "#80A233", "#0BB2FF", "#E20A16"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_home,container,false);


        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);


        downloadDataCharts();




        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                downloadDataCharts();

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

    public void downloadDataCharts(){
         //Cargamos datos de 2015 del grafico
         new JSONParse2015().execute();

    }








    //------------------TAREA-------------BAJAR DATOS DE GRAFICOS---------------------------------

    private class JSONParse2015 extends AsyncTask<String, String, JSONObject> {

        private JSONObject data;
        private JSONArray results;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            JSONObject json;
            try {
                if(haveNetworkConnection()) {
                    // Getting JSON from URL
                    json = jParser.getJSONFromUrl(url_2015);
                }else{
                    json = null;
                }
            }catch (Exception e){
                e.printStackTrace();
                json = null;
            }
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                try {
                    // Getting JSON Array
                    data = json.getJSONObject(TAG_DATA);
                    results = data.getJSONArray(TAG_RESULTS);
                    numPartidos2015 = results.length();
                    arrayPartidos2015 = new PartidoEstadisticas[numPartidos2015];

                    for (int i = 0; i < numPartidos2015; i++) {

                        PartidoEstadisticas partido = new PartidoEstadisticas();

                        JSONObject jsonEstadisticas = results.getJSONObject(i); //Vamos cargando cada partido

                        partido.setComunidadAutonoma(jsonEstadisticas.getString(TAG_COM_AUT));

                        partido.setPorcentajeObtenido(jsonEstadisticas.getDouble(TAG_PORCENTAJE));

                        JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                        partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                        partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                        partido.setColor(jsonPartido.getString(TAG_COLOR));

                        arrayPartidos2015[i] = partido;

                    }
                    if (layout != null) layout.setRefreshing(false);
                    new CargarXmlTask().execute(rss_url); //Se han terminado de cargar gráficos, ahora empezamos a bajar las noticias

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String colorNotNull(String color){
        if (color==null || color.equals("")) return "#9b9999";
        else return color;
    }

    private ArrayList<String> getQuarters(String ano) {

        ArrayList<String> q = new ArrayList<String>();

        for (int i = 0; i < numPartidos2015 - 6; i++) {   //OJOOOOOOOO HAY QUE BORRAR EL 6
            q.add(arrayPartidos2015[i].getAlias());
            //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());
        }
        return q;
    }







    //------------------TAREA-------------BAJAR DATOS DE NOTICIAS---------------------------------

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        List<Object> items = new ArrayList<>();
        List<Noticia> noticias = new ArrayList<>();
        ViewGroup container;
        LayoutInflater inflater;

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

            //Insertamos grafico PIECHART 2012
            items.add(new PieChartItem2012(cargarDatos2012(), HomeActivity.context));

            //Insertamos gráfico PIE CHART 2015
            items.add(new PieChartItem2012(generateDataPie("2012"), HomeActivity.context));


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
            Title titulo =new Title(getString(R.string.titulo_fichas));
            items.add(titulo);


            //Añadimos una ficha de un partido y de un político aleatoria
            int randomNumber = (int) (Math.random()*7+1);


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

    private PieData cargarDatos2012 (){

        ArrayList<Entry> entries = new ArrayList<>();

        int[] colores = new int[partidos2012.length];
        for (int i = 0; i < partidos2012.length; i++) {
            int porcentaje = (int)porcentajes2012[i];
            entries.add(new Entry(porcentaje, i));
            colores[i] = Color.parseColor(colorNotNull(colores2012[i]));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(0.5f);
        d.setColors(colores);
        d.setValueTextColor(Color.BLACK);

        ArrayList<String> q = new ArrayList<String>();

        for(int i=0; i<partidos2012.length; i++){
            q.add(partidos2012[i]);
        }

        PieData cd = new PieData(q, d);
        return cd;


    }

    private PieData generateDataPie(String ano) {

            ArrayList<Entry> entries = new ArrayList<>();
            int[] colores = new int[numPartidos2015 - 6]; //OJOOOOOOOOOO HAY QUE BORRAR EL -6, es para que aparezcan menos

            for (int i = 0; i < numPartidos2015 - 6; i++) {
                float porcentaje = arrayPartidos2015[i].getPorcentajeObtenido().floatValue();
                Log.d("GRAFICOS", "" + porcentaje);
                Log.d("GRAFICOS", "" + arrayPartidos2015[i].getPorcentajeObtenido());
                entries.add(new Entry(porcentaje, i));
                colores[i] = Color.parseColor(colorNotNull(arrayPartidos2015[i].getColor()));
            }

            PieDataSet d = new PieDataSet(entries, "");

            // space between slices
            d.setSliceSpace(0.5f);
            d.setColors(colores);
            d.setValueTextColor(Color.BLACK);

            PieData cd = new PieData(getQuarters("2015"), d);
            return cd;
    }


}
