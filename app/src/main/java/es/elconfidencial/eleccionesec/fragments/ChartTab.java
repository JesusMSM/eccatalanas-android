package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import es.elconfidencial.eleccionesec.json.JSONParser;
import es.elconfidencial.eleccionesec.model.PartidoEstadisticas;


public class ChartTab extends Fragment {

    BarChart barChart;
    ChartItem pie;
    ListView lv;
    View v;
    PullRefreshLayout layout;
    private static String url = "http://api.elconfidencial.com/service/elections/place/3/7/99/9/";
    private static final String TAG_DATA = "data";
    private static final String TAG_RESULTS = "results";
    private static final String TAG_COM_AUT = "res_name";
    private static final String TAG_PORCENTAJE = "res_percent_votes_candidacy";
    private static final String TAG_PARTIDO = "res_party";
    private static final String TAG_NOMBRE = "par_name";
    private static final String TAG_ALIAS = "par_alias";
    private static final String TAG_COLOR = "par_color";
    private int numPartidos;
    private PartidoEstadisticas[] arrayPartidos;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_chart, container, false);
        //downloadData();



        //HEMICICLO

        WebView webview1 = (WebView) v.findViewById(R.id.webView1);
        String content1 = "<html>"
                + "<meta charset='utf-8'>"
                + "<script src='http://d3js.org/d3.v3.min.js'></script>"
                + "<head>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1'>"
                + "<style> "
                + "body {"
                + "  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;"
                + "  margin: auto;"
                + "  position: relative;"
                + "}"
                + "form {"
                + "  position: absolute;"
                + "  right: 10px;"
                + "  top: 10px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<script>"
                + "  var dataset = {"
                + "  apples: [53245, 28479, 19697, 24037, 40245],"
                + "  oranges: [53245, 28479, 19697, 24037, 40245],"
                + "};"
                + "var width = 460,"
                + "    height = 300,"
                + "    cwidth = 80;"
                + "    r = 10;"
                + "var color = d3.scale.ordinal()"
                + "                   .range(['#0077a7', '#df2927', '#5e2b5e', '#df843d', '#24988b', '#e12893']);"
                + "var pie = d3.layout.pie()"
                + "    .sort(null)"
                + "    .startAngle(3/2 * Math.PI)"
                + "    .endAngle(5/2 * Math.PI);"
                + "var arc = d3.svg.arc();"
                + "var svg = d3.select('body').append('svg')"
                + "    .attr('width', width)"
                + "    .attr('height', height)"
                + "    .append('g')"
                + "    .attr('transform', 'translate(' + width / 2 + ',' + height + ')');"
                + "var gs = svg.selectAll('g').data(d3.values(dataset)).enter().append('g');"
                + "var path = gs.selectAll('path')"
                + "    .data(pie(dataset.apples))"
                + "  .enter().append('path')"
                + "    .attr('fill', function(d, i) { return color(i); })"
                + "    .attr('d', function(d, i, j) { return arc.innerRadius(30+cwidth*j).outerRadius(cwidth*(j+1))(d); });"
                + "</script>"
                + "</body>"
                + "</html";


        WebSettings webSettings1 = webview1.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        //webview.requestFocusFromTouch();
        webview1.setInitialScale(1);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setUseWideViewPort(true);
        webview1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview1.setScrollbarFadingEnabled(false);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setUseWideViewPort(true);
        // disable scroll on touch

        webview1.loadDataWithBaseURL("", content1 , "text/html", "charset=UTF-8", null);


        return v;
    }

    public void downloadData(){

        new JSONParse().execute();

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private JSONObject data;
        private JSONArray results;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            //  pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
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

                drawGraphics();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }
    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        Log.d("GRAFICOS", ""+numPartidos);


           /* q.add("PP");
            q.add("PSOE");
            q.add("ASD");
            q.add("SA");*/

        //q.add(arrayPartidos[i].getAlias());
               /* q.add("PP");
                q.add("PSOE");
                q.add("ASD");
                q.add("SA");*/
        for(int i=0; i<numPartidos; i++){
            q.add(arrayPartidos[i].getAlias());
            //if(arrayPartidos[i].getAlias()!=null &&arrayPartidos[i]!= null && arrayPartidos!=null) Log.d("GRAFICOS", arrayPartidos[i].getAlias());
        }



        return q;
    }
    private void drawGraphics(){
        ArrayList<ChartItem> list = new ArrayList<>();
        list.add(new BarChartItem(generateDataBar(3), HomeActivity.context));
        list.add(new LineChartItem(generateDataLine(4), HomeActivity.context));
        pie = new PieChartItem(generateDataPie(), HomeActivity.context);
        list.add(pie);
        list.add(new BarChartItem(generateDataBar(6), HomeActivity.context));

        ChartDataAdapter cda = new ChartDataAdapter(HomeActivity.context, list);
        lv.setAdapter(cda);

    }
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < numPartidos; i++) {
            float porcentaje = arrayPartidos[i].getPorcentajeObtenido().floatValue();
            Log.d("GRAFICOS", ""+porcentaje);
            Log.d("GRAFICOS", ""+arrayPartidos[i].getPorcentajeObtenido());
            entries.add(new Entry(porcentaje, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setValueTextColor(Color.BLACK);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

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