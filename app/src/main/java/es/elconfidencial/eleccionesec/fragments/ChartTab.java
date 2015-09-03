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
    private PartidoEstadisticas[] arrayPartidos;
    private PartidoEstadisticas[] arrayPartidos2011;
    private PartidoEstadisticas[] arrayPartidos2015;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_chart, container, false);
        downloadData();

        return v;
    }

    public void downloadData(){
        //Cargamos datos de 2011- Al terminar, cargamos los de 2015 - Al terminar dibujamos el chart
       new JSONParse2011().execute(url_2011);
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
            JSONObject json = jParser.getJSONFromUrl(args[0]);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            PartidoEstadisticas[] arrayPartidos;

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
                    partido.setElectedMembers(jsonEstadisticas.getString(TAG_ELECTED_MEMBERS));

                    JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                    partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                    partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                    partido.setColor(jsonPartido.getString(TAG_COLOR));

                    arrayPartidos[i]=partido;
                }

                //drawGraphics();
                arrayPartidos2011 = arrayPartidos;
                new JSONParse2015().execute(url_2015);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class JSONParse2015 extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
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
            JSONObject json = jParser.getJSONFromUrl(args[0]);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            PartidoEstadisticas[] arrayPartidos;

            //pDialog.dismiss();
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
                    partido.setElectedMembers(jsonEstadisticas.getString(TAG_ELECTED_MEMBERS));

                    JSONObject jsonPartido = jsonEstadisticas.getJSONObject(TAG_PARTIDO);
                    partido.setNombre(jsonPartido.getString(TAG_NOMBRE));
                    partido.setAlias(jsonPartido.getString(TAG_ALIAS));
                    partido.setColor(jsonPartido.getString(TAG_COLOR));

                    arrayPartidos[i]=partido;
                }

                //drawGraphics();
                arrayPartidos2015 = arrayPartidos;
             //   drawHemiciclo(getDataString(arrayPartidos2011,"res2011"),getDataString(arrayPartidos2015,"res2015"));
                drawHalfDonut();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private String getDataString(PartidoEstadisticas[] array,String title){
        String result = "";
        String [] resultArray = new String[array.length];
        for(int i=0; i<array.length; i++){
            result += array[i].getElectedMembers();
            if(i != array.length-1){//No es el ultimo item
                result += ",";
            }
        }
        return title + ": [" + result + "],";
    }
 /**   public void drawHemiciclo(String data2011,String data2015){
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
                + data2011
                + data2015
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
                + "    .data(function(d) { return pie(d); })"
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

        WebView webview2 = (WebView) v.findViewById(R.id.webView2);
    }

    public void drawBar(){
        //GRAFICO BARRAS

        WebView webview2 = (WebView) v.findViewById(R.id.webView2);
        String content2 = "<!DOCTYPE html>"
                + "<meta charset='utf-8'>"
                + "<style>"
                + ".chart rect {"
                + "}"
                + ".chart text{"
                + "  fill: black;"
                + "  background-color: #00ff00"
                + "  font: 10px sans-serif;"
                + "  text-anchor: end;"
                + "}"
                + ".chart text.name {"
                + "  fill: #000;"
                + "}"
                + "</style>"
                + "<svg class='chart'></svg>"
                + "<script src='http://d3js.org/d3.v3.min.js'></script>"
                + "<script>"
                + "var data = [42, 28, 16, 10, 8, 4];"
                + "var color = d3.scale.ordinal()"
                + "                   .range(['#0077a7', '#df2927', '#5e2b5e', '#df843d', '#24988b', '#e12893']);"
                + "var width = 860,"
                + "    barHeight = 60;"
                + "var x = d3.scale.linear()"
                + "    .domain([0, d3.max(data)])"
                + "    .range([50, width-50]);"
                + "var chart = d3.select('.chart')"
                + "    .attr('width', width)"
                + "    .attr('height', barHeight * data.length);"
                + "var bar = chart.selectAll('g')"
                + "    .data(data)"
                + "  .enter().append('g')"
                + "    .attr('transform', function(d, i) { return 'translate(0,' + i * barHeight + ')'; });"
                + "bar.append('rect')"
                + "    .attr('width', x)"
                + "    .attr('fill', function(d, i) { return color(i); })"
                + "    .attr('height', barHeight - 30);"
                + "bar.append('text')"
                + "    .attr('x', width)"
                + "    .attr('y', barHeight / 3)"
                + "    .text(function(d) { return d; });"
                + "</script>";


        WebSettings webSettings2 = webview2.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        //webview.requestFocusFromTouch();
        webview2.setInitialScale(1);
        webview2.getSettings().setJavaScriptEnabled(true);
        webview2.getSettings().setLoadWithOverviewMode(true);
        webview2.getSettings().setUseWideViewPort(true);
        webview2.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview2.setScrollbarFadingEnabled(false);
        webview2.getSettings().setLoadWithOverviewMode(true);
        webview2.getSettings().setUseWideViewPort(true);
        // disable scroll on touch

        webview2.loadDataWithBaseURL("", content2 , "text/html", "charset=UTF-8", null);

    } **/

    public void drawHalfDonut(){
        // Pie Chart Section Names
        String[] code = new String[] { "Froyo", "Gingerbread",
                "IceCream Sandwich", "Jelly Bean", "KitKat" };

        // Pie Chart Section Value
        double[] distribution = { 0.5, 9.1, 7.8, 45.5, 33.9 };

        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                Color.RED };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(
                " Android version distribution as on October 1, 2012");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
//Adding colors to the chart
            defaultRenderer.setBackgroundColor(getResources().getColor(R.color.white));
            defaultRenderer.setApplyBackgroundColor(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        //defaultRenderer.setChartTitle("Android version distribution as on December 1, 2014. ");
        //defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setPanEnabled(false);
        defaultRenderer.setZoomEnabled(false);

        // this part is used to display graph on the xml
        // Creating an intent to plot bar chart using dataset and
        // multipleRenderer
        // Intent intent = ChartFactory.getPieChartIntent(getBaseContext(),
        // distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        // startActivity(intent);

        LinearLayout chartContainer = (LinearLayout) v.findViewById(R.id.chart);
        // remove any views before u paint the chart
       // chartContainer.removeAllViews();
        // drawing pie chart
        mChart = ChartFactory.getPieChartView(v.getContext().getApplicationContext(),
                distributionSeries, defaultRenderer);
        // adding the view to the linearlayout
        chartContainer.addView(mChart);

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
            Log.d("GRAFICOS", "" + arrayPartidos[i].getPorcentajeObtenido());
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