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


import com.baoyz.widget.PullRefreshLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.chart.HorizontalBarChartItem;
import es.elconfidencial.eleccionesec.chart.ChartItem;
import es.elconfidencial.eleccionesec.chart.LineChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem2012;
import es.elconfidencial.eleccionesec.json.JSONParser;
import es.elconfidencial.eleccionesec.model.Mensaje;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.PartidoEstadisticas;
import es.elconfidencial.eleccionesec.model.Title;


public class ChartTab extends Fragment {

    BarChart barChart;
    ChartItem pie;
    View v;
    private View mChart;
    PullRefreshLayout layout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Object> items = new ArrayList<>();
    List<Noticia> noticias = new ArrayList<>();

    private static String url_2015 = "";
    private static String url_2012 = "http://api.elconfidencial.com/service/elections/place/1/7/99/9/";

    private static final String TAG_DATA = "data";
    private static final String TAG_RESULTS = "results";
    private static final String TAG_PARTICIPATION = "participation";
    private static final String TAG_ESCRUTADO = "par_percent_census_counted";
    private static final String TAG_COM_AUT = "res_name";
    private static final String TAG_PORCENTAJE = "res_percent_votes_candidacy";
    private static final String TAG_PARTIDO = "res_party";
    private static final String TAG_NOMBRE = "par_name";
    private static final String TAG_ALIAS = "par_alias";
    private static final String TAG_COLOR = "par_color";
    private static final String TAG_ELECTED_MEMBERS = "res_elected_members";

    private int numPartidos;
    private int numPartidos2012;
    private PartidoEstadisticas[] arrayPartidos;
    private PartidoEstadisticas[] arrayPartidos2012;
    public static double porcentajeEscrutado = 0.0;

    int numPartidosCat = 8;
    int[] valores = new int[numPartidosCat];
    int contadorAux=0;
    JSONArray jsonArrayVotaciones; //JSON que se descarga de Parse con las votaciones de los usuarios

    private String fechaElecciones = "27/09/2015";


    private String[] partidos2012 = {"CiU", "PSC", "PP", "ERC", "ICV", "Ciudadanos", "CUP", "Otros"};
    private double[] porcentajes2012 = {30.68,14.44,13,13.69,9.9,7.58,3.48,7.23};
    private String[] colores2012 = {"#002060", "#DF2927","#0077A7" ,"#FFC53F" ,"#C0D52E" ,"#DF843D" ,"#DFD717","#C7C7C7" };

    private int numConvocatorias = 10;
    private String[] partidosHistoricos = {"CiU", "Ciudadanos", "CUP", "ERC", "ICV", "PP", "PSC"};
    private double[][] historicoCataluña = {
            {27.83, 46.8, 45.72, 46.19, 40.95, 37.7, 30.94, 31.52, 38.43, 30.68},
            {0, 0, 0, 0, 0, 0, 0, 3.03, 3.39, 7.58},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 3.48},
            {8.9, 4.41, 4.14, 7.96, 9.49, 8.67, 16.44, 14.03, 7, 13.69},
            {0, 0, 7.76, 6.5, 9.71, 2.51, 7.28, 9.52, 7.37, 10},
            {0, 7.7, 5.31, 5.97, 13.08, 9.51, 11.89, 10.65, 12.37, 13},
            {22.43, 30.11, 30, 27.55, 24.8, 30.33, 31.16, 26.82, 18.38, 14.44}
    };
    private String[] historicoColors = {"#002060", "#DF843D", "#DFD717", "#FFC53F", "#C0D52E", "#0077A7", "#DF2927"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_chart, container, false);




        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.chart_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //Parse url_2015
        try {
            Parse.enableLocalDatastore(getActivity());
            //Autenticacion con Parse
            Parse.initialize(getActivity(), "7P82tODwUk7C6AZLyLSuKBvyjLZcdpNz80J6RT2Z", "3jhqLEIKUI7RknTCU8asoITvPC9PjHD5n2FDub4h");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comunicacion con Parse.com
        ParseQuery<ParseObject> queryURL = ParseQuery.getQuery("URL");
        queryURL.whereEqualTo("Name", "prueba_url_2015");
        queryURL.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    url_2015 = object.getString("Link");
                    object.saveInBackground();
                    downloadData();

                } else {
                    //something went wrong
                }
            }
        });


        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayoutChart);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadData();

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

    public void downloadData() {
        //Descargamos valores de Parse
        try {
            Parse.enableLocalDatastore(getActivity());
            //Autenticacion con Parse
            Parse.initialize(getActivity(), "7P82tODwUk7C6AZLyLSuKBvyjLZcdpNz80J6RT2Z", "3jhqLEIKUI7RknTCU8asoITvPC9PjHD5n2FDub4h");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Comunicacion con Parse.com
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Partido");


        query.whereEqualTo("Name", "Votaciones");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    jsonArrayVotaciones = object.getJSONArray("Valores");
                    Log.d("VOTACIONES", jsonArrayVotaciones.toString());
                    object.saveInBackground();

                } else {
                    //something went wrong
                }
            }
        });

         new JSONParse2015().execute();

    }


    private class JSONParse2015 extends AsyncTask<String, String, JSONObject> {


        private JSONObject data;
        private JSONObject participation;
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
                    participation = data.getJSONObject(TAG_PARTICIPATION);
                    porcentajeEscrutado = participation.getDouble(TAG_ESCRUTADO);
                    results = data.getJSONArray(TAG_RESULTS);
                    numPartidos = results.length();
                    arrayPartidos = new PartidoEstadisticas[numPartidos];

                    for (int i = 0; i < numPartidos; i++) {

                        PartidoEstadisticas partido = new PartidoEstadisticas();

                        JSONObject jsonEstadisticas = results.getJSONObject(i); //Vamos cargando cada partido

                        partido.setComunidadAutonoma(jsonEstadisticas.getString(TAG_COM_AUT));

                        partido.setPorcentajeObtenido(jsonEstadisticas.getDouble(TAG_PORCENTAJE));

                        JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                        partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                        partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                        partido.setColor(jsonPartido.getString(TAG_COLOR));

                        arrayPartidos[i] = partido;

                    }
                    if (layout != null) layout.setRefreshing(false);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
                addItems();

        }
    }


    private void addItems() {

        if(items.size()>0) items.clear();

        Date today = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date electionsDate;
        try {
            electionsDate = sdf.parse(fechaElecciones);
        }catch (Exception e){
            e.printStackTrace();
            electionsDate =null;
        }

        int comparacion = today.compareTo(electionsDate);
        boolean isElectionDay = false;
        if(comparacion>=0) isElectionDay = true;

        if(isElectionDay){
            if(numPartidos>0){
                items.add(new Title(getString(R.string.titulo_resultados_2015)));
                items.add(new PieChartItem(generateDataPie2015("2015"), HomeActivity.context));
            }else{
                items.add(new Mensaje(getString(R.string.alerta_espera_datos)));
            }
        }


        //Grafico de 2012
        items.add(new Title(getString(R.string.titulo_resultados_2012)));
        items.add(new PieChartItem2012(generateDataPie2012(), HomeActivity.context));

        //Grafico de líneas
        items.add(new Title(getString(R.string.titulo_evolucion)));
        items.add(new LineChartItem(generateDataLine(), HomeActivity.context));

        //Grafico de votacion usuarios
        items.add(new Title(getString(R.string.encuesta_ec)));
        items.add(new HorizontalBarChartItem(generateDataBar(), HomeActivity.context));

        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
        mRecyclerView.setAdapter(mAdapter);
        if(layout!=null) layout.setRefreshing(false);

    }

    private BarData generateDataBar(){

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("PSC");
        xVals.add("CUP");
        xVals.add("Junts Pel Sí");
        xVals.add("PP");
        xVals.add("UDC");
        xVals.add("Ciudadanos");
        xVals.add("Cat Si que es Pot");
        xVals.add("NS/NC");




        for(contadorAux=0; contadorAux< numPartidosCat; contadorAux++) {
            Log.d("VOTACIONES", "SE HA METIDO EN EL BUCLE");
            try {
                Log.d("VOTACIONESVALOR", jsonArrayVotaciones.getInt(contadorAux)+" indice: " +contadorAux);
                valores[contadorAux] = jsonArrayVotaciones.getInt(contadorAux);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        for (int i = 0; i < numPartidosCat; i++) {
            yVals1.add(new BarEntry((float) valores[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Valores");

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        //data.setValueTypeface(mTf);


        return data;
    }

    private ArrayList<String> getQuarters(String ano) {

            ArrayList<String> q = new ArrayList<String>();


            for (int i = 0; i < numPartidos; i++) {
                if(arrayPartidos[i].getPorcentajeObtenido() >=3){
                    q.add(arrayPartidos[i].getAlias());
                }else{//Otros
                    if(i == numPartidos-1){//Ultimo
                        q.add("Otros");
                    }
                }
            }
            //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());

            return q;

    }





    private PieData generateDataPie2015(String ano) {
            float porcentajeOtros = 0;
            ArrayList<Entry> entries = new ArrayList<>();
            List<Integer> coloresList = new ArrayList<>();
            for (int i = 0; i < numPartidos; i++) {
                float porcentaje = arrayPartidos[i].getPorcentajeObtenido().floatValue();

                if(porcentaje>=3){
                    entries.add(new Entry(porcentaje, i));
                    coloresList.add(Color.parseColor(colorNotNull(arrayPartidos[i].getColor())));
                }else{//Otros
                    porcentajeOtros += porcentaje;
                    if (i == numPartidos-1){//Ultimo elemento
                        entries.add(new Entry(porcentajeOtros, i));
                        coloresList.add(Color.parseColor(colorNotNull("#c7c7c7")));
                    }
                }
            }

            PieDataSet d = new PieDataSet(entries, "");

        // space between slices
            d.setSliceSpace(0.5f);
            d.setColors(coloresList);
            d.setValueTextColor(Color.BLACK);

            PieData cd = new PieData(getQuarters("2015"), d);
            return cd;
    }

    private String colorNotNull(String color) {
        if (color == null || color.equals("")) return "#9b9999";
        else return color;
    }

    private PieData generateDataPie2012 () {

        ArrayList<Entry> entries = new ArrayList<>();

        int[] colores = new int[partidos2012.length];
        for (int i = 0; i < partidos2012.length; i++) {
            int porcentaje = (int) porcentajes2012[i];
            entries.add(new Entry(porcentaje, i));
            colores[i] = Color.parseColor(colorNotNull(colores2012[i]));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(0.5f);
        d.setColors(colores);
        d.setValueTextColor(Color.BLACK);

        ArrayList<String> q = new ArrayList<String>();

        for (int i = 0; i < partidos2012.length; i++) {
            q.add(partidos2012[i]);
        }

        PieData cd = new PieData(q, d);
        return cd;
    }


    private LineData generateDataLine() {
        ArrayList<LineDataSet> sets = new ArrayList<>();

        for (int i = 0; i < partidosHistoricos.length; i++) {

            ArrayList<Entry> e1 = new ArrayList<>();
            for (int j = 0; j < numConvocatorias; j++) {
                int valor = (int) historicoCataluña[i][j];
                e1.add(new Entry(valor, j));
            }

            LineDataSet d1 = new LineDataSet(e1, partidosHistoricos[i]);
            d1.setLineWidth(2.5f);
            d1.setCircleSize(4.0f);
            d1.setColor(Color.parseColor(historicoColors[i]));
            d1.setCircleColor(Color.parseColor(historicoColors[i]));
            d1.setCircleColorHole(Color.parseColor(historicoColors[i]));
            d1.setDrawValues(false);
            sets.add(d1);
        }


        LineData cd = new LineData(getYears(), sets);
        return cd;
    }

    private ArrayList<String> getYears() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("1980");
        m.add("1984");
        m.add("1988");
        m.add("1992");
        m.add("1995");
        m.add("1999");
        m.add("2003");
        m.add("2006");
        m.add("2010");
        m.add("2012");

        return m;
    }
}
