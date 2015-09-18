package es.elconfidencial.eleccionesec.chart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 14/08/2015.
 */
public class HorizontalBarChartItem extends ChartItem {

    private Typeface mTf;
    private ChartData<?> mChartData1;

    public HorizontalBarChartItem(ChartData<?> cd, Context c) {
        super(cd);
        this.mChartData1 = cd;
        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    public ChartData<?> getItemData() {
        return mChartData1;
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.chart_horizontal_bar, null);
            holder.chart = (HorizontalBarChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.chart.setDrawBarShadow(false);

        holder.chart.setDrawValueAboveBar(true);

        holder.chart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        holder.chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        holder.chart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        holder.chart.setDrawGridBackground(false);
        holder.chart.setTouchEnabled(false);

        // mChart.setDrawYLabels(false);


        XAxis xl = holder.chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = holder.chart.getAxisLeft();
        yl.setTypeface(mTf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        yl.setInverted(true);

        YAxis yr = holder.chart.getAxisRight();
        yr.setTypeface(mTf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setInverted(true);

        holder.chart.setData((BarData) mChartData);
        holder.chart.animateY(2500);


        Legend l = holder.chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);


        return convertView;
    }

    private static class ViewHolder {
        HorizontalBarChart chart;
    }
}
