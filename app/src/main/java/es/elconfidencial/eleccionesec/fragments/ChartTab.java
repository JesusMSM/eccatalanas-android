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
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.baoyz.widget.PullRefreshLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
import es.elconfidencial.eleccionesec.chart.ChartItem;
import es.elconfidencial.eleccionesec.chart.LineChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem2012;
import es.elconfidencial.eleccionesec.json.JSONParser;
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

    private static String url_2015 = "http://api.elconfidencial.com/service/elections/place/3/7/99/9/";
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


    private String[] partidos2012 = {"CiU", "PSC", "PP", "ERC", "ICV", "Ciudadanos", "CUP"};
    private double[] porcentajes2012 = {30.68,14.44,13,13.69,9.9,7.58,3.48};
    private String[] colores2012 = {"#002060", "#DF2927","#0077A7" ,"#FFC53F" ,"#C0D52E" ,"#DF843D" ,"#DFD717" };

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

        downloadData();

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

        if(arrayPartidos!=null) {
            //Grafico de 2015
            items.add(new Title(getString(R.string.titulo_resultados_2015)));
            items.add(new PieChartItem(generateDataPie2015("2015"), HomeActivity.context));
        }

        //Grafico de 2012
        items.add(new Title(getString(R.string.titulo_resultados_2012)));
        items.add(new PieChartItem2012(generateDataPie2012(), HomeActivity.context));

        //Grafico de líneas
        items.add(new Title(getString(R.string.titulo_evolucion)));
        items.add(new LineChartItem(generateDataLine(), HomeActivity.context));

        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context,items);
        mRecyclerView.setAdapter(mAdapter);
        if(layout!=null) layout.setRefreshing(false);

    }



    private ArrayList<String> getQuarters(String ano) {

            ArrayList<String> q = new ArrayList<String>();


            for (int i = 0; i < numPartidos; i++) {   //OJOOOOOOOO HAY QUE BORRAR EL 6
                q.add(arrayPartidos[i].getAlias());
                //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());
            }

            return q;

    }



    private PieData generateDataPie2015(String ano) {

            ArrayList<Entry> entries = new ArrayList<>();
            int[] colores = new int[numPartidos]; //OJOOOOOOOOOO HAY QUE BORRAR EL -6, es para que aparezcan menos
            for (int i = 0; i < numPartidos; i++) {
                float porcentaje = arrayPartidos[i].getPorcentajeObtenido().floatValue();
                entries.add(new Entry(porcentaje, i));
                colores[i] = Color.parseColor(colorNotNull(arrayPartidos[i].getColor()));
            }

            PieDataSet d = new PieDataSet(entries, "");

        // space between slices
            d.setSliceSpace(0.5f);
            d.setColors(colores);
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
