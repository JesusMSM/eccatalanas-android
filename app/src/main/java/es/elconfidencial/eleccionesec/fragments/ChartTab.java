package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


import com.baoyz.widget.PullRefreshLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.chart.BarChartItem;
import es.elconfidencial.eleccionesec.chart.ChartItem;
import es.elconfidencial.eleccionesec.chart.LineChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem2011;
import es.elconfidencial.eleccionesec.json.JSONParser;
import es.elconfidencial.eleccionesec.model.PartidoEstadisticas;




public class ChartTab extends Fragment {

    BarChart barChart;
    ChartItem pie;
    ListView lv;
    View v;
    private View mChart;
    PullRefreshLayout layout;
    private static String url_2015 = "http://api.elconfidencial.com/service/elections/place/3/7/99/9/";
    private static String url_2011="http://api.elconfidencial.com/service/elections/place/1/7/99/9/";

    private static final String TAG_DATA = "data";
    private static final String TAG_RESULTS = "results";
    private static final String TAG_COM_AUT = "res_name";
    private static final String TAG_PORCENTAJE = "res_percent_votes_candidacy";
    private static final String TAG_PARTIDO = "res_party";
    private static final String TAG_NOMBRE = "par_name";
    private static final String TAG_ALIAS = "par_alias";
    private static final String TAG_COLOR = "par_color";
    private static final String TAG_ELECTED_MEMBERS = "res_elected_members";

    private int numPartidos;
    private int numPartidos2011;
    private PartidoEstadisticas[] arrayPartidos;
    private PartidoEstadisticas[] arrayPartidos2011;

    private int numConvocatorias = 10;
    private String[] partidosHistoricos = { "CiU", "Ciudadanos", "CUP", "ERC", "ICV" , "PP", "PSC"};
    private double[][] historicoCataluña = {
            {27.83,46.8,45.72,46.19,40.95,37.7,30.94,31.52,38.43,30.68},
            {0,0,0,0,0,0,0,3.03,3.39,7.58},
            {0,0,0,0,0,0,0,0,0,3.48},
            {8.9,4.41,4.14,7.96,9.49,8.67,16.44,14.03,7,13.69},
            {0,0,7.76,6.5,9.71,2.51,7.28,9.52,7.37,10},
            {0,7.7,5.31,5.97,13.08,9.51,11.89,10.65,12.37,13},
            {22.43,30.11,30,27.55,24.8,30.33,31.16,26.82,18.38,14.44}
    };
    private String[] historicoColors = {"#18307B","#EF7A36", "#FFED00", "#FFB232", "#80A233", "#0BB2FF", "#E20A16"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_chart, container, false);
        downloadData();
        lv = (ListView) v.findViewById(R.id.listView1);


        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

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

    public void downloadData(){
        //Cargamos datos de 2011- Al terminar, cargamos los de 2015 - Al terminar dibujamos el chart
        if(haveNetworkConnection()) new JSONParse2015().execute();

    }


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

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url_2015);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                // Getting JSON Array
                data = json.getJSONObject(TAG_DATA);
                results = data.getJSONArray(TAG_RESULTS);
                numPartidos = results.length();
                arrayPartidos = new PartidoEstadisticas[numPartidos];

                for(int i=0; i<numPartidos; i++){

                    PartidoEstadisticas partido = new PartidoEstadisticas();

                    JSONObject jsonEstadisticas = results.getJSONObject(i); //Vamos cargando cada partido

                    partido.setComunidadAutonoma(jsonEstadisticas.getString(TAG_COM_AUT));

                    partido.setPorcentajeObtenido(jsonEstadisticas.getDouble(TAG_PORCENTAJE));

                    JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                    partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                    partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                    partido.setColor(jsonPartido.getString(TAG_COLOR));

                    arrayPartidos[i]=partido;

                }
                new JSONParse2011().execute();

               // drawGraphics();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class JSONParse2011 extends AsyncTask<String, String, JSONObject> {

        private JSONObject data;
        private JSONArray results;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url_2011);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                // Getting JSON Array
                data = json.getJSONObject(TAG_DATA);
                results = data.getJSONArray(TAG_RESULTS);
                numPartidos2011 = results.length();
                arrayPartidos2011 = new PartidoEstadisticas[numPartidos2011];

                for(int i=0; i<numPartidos2011; i++){

                    PartidoEstadisticas partido = new PartidoEstadisticas();

                    JSONObject jsonEstadisticas = results.getJSONObject(i); //Vamos cargando cada partido

                    partido.setComunidadAutonoma(jsonEstadisticas.getString(TAG_COM_AUT));

                    partido.setPorcentajeObtenido(jsonEstadisticas.getDouble(TAG_PORCENTAJE));

                    JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                    partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                    partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                    partido.setColor(jsonPartido.getString(TAG_COLOR));

                    arrayPartidos2011[i]=partido;

                }
                if (layout != null) layout.setRefreshing(false);
                 drawGraphics();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private ArrayList<String> getQuarters(String ano) {

        if(ano.equals("2015")) {
            ArrayList<String> q = new ArrayList<String>();


            for (int i = 0; i < numPartidos - 6; i++) {   //OJOOOOOOOO HAY QUE BORRAR EL 6
                q.add(arrayPartidos[i].getAlias());
                //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());
            }

            return q;
        }else{
            ArrayList<String> q = new ArrayList<String>();
            Log.d("GRAFICOS", "" + numPartidos2011);

            for (int i = 0; i < numPartidos2011 - 6; i++) {   //OJOOOOOOOO HAY QUE BORRAR EL 6
                q.add(arrayPartidos2011[i].getAlias());
                //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());
            }
            return q;
        }
    }
    private void drawGraphics(){
        ArrayList<ChartItem> list = new ArrayList<>();

        list.add(new PieChartItem(generateDataPie("2015"), HomeActivity.context));
        list.add(new PieChartItem2011(generateDataPie("2011"), HomeActivity.context));
        //list.add(new BarChartItem(generateDataBar(3), HomeActivity.context));
        list.add(new LineChartItem(generateDataLine(), HomeActivity.context));

       // list.add(new BarChartItem(generateDataBar(6), HomeActivity.context));

        ChartDataAdapter cda = new ChartDataAdapter(HomeActivity.context, list);
        lv.setAdapter(cda);

    }
    private PieData generateDataPie(String ano) {

        if(ano.equals("2015")) {
            ArrayList<Entry> entries = new ArrayList<>();
            int[] colores = new int[numPartidos - 6]; //OJOOOOOOOOOO HAY QUE BORRAR EL -6, es para que aparezcan menos

            for (int i = 0; i < numPartidos - 6; i++) {
                float porcentaje = arrayPartidos[i].getPorcentajeObtenido().floatValue();
                entries.add(new Entry(porcentaje, i));
                colores[i] = Color.parseColor(colorNotNull(arrayPartidos[i].getColor()));
            }

            PieDataSet d = new PieDataSet(entries, "");

            // space between slices
            d.setSliceSpace(2f);
            d.setColors(colores);
            d.setValueTextColor(Color.BLACK);

            PieData cd = new PieData(getQuarters("2015"), d);
            return cd;
        }else{
            ArrayList<Entry> entries = new ArrayList<>();
            int[] colores = new int[numPartidos2011 - 6]; //OJOOOOOOOOOO HAY QUE BORRAR EL -6, es para que aparezcan menos

            for (int i = 0; i < numPartidos2011 - 6; i++) {
                float porcentaje = arrayPartidos2011[i].getPorcentajeObtenido().floatValue();
                Log.d("GRAFICOS", "" + porcentaje);
                Log.d("GRAFICOS", "" + arrayPartidos2011[i].getPorcentajeObtenido());
                entries.add(new Entry(porcentaje, i));
                colores[i] = Color.parseColor(colorNotNull(arrayPartidos2011[i].getColor()));
            }

            PieDataSet d = new PieDataSet(entries, "");

            // space between slices
            d.setSliceSpace(2f);
            d.setColors(colores);
            d.setValueTextColor(Color.BLACK);

            PieData cd = new PieData(getQuarters("2011"), d);
            return cd;
        }
    }

    private String colorNotNull(String color){
        if (color==null || color.equals("")) return "#9b9999";
        else return color;
    }
    private LineData generateDataLine() {
        ArrayList<LineDataSet> sets = new ArrayList<>();

        for (int i= 0; i< partidosHistoricos.length; i++) {

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
    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }
}
