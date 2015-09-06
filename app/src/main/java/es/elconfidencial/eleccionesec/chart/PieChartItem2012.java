package es.elconfidencial.eleccionesec.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.PercentFormatter;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 04/09/2015.
 */
public class PieChartItem2012 extends ChartItem {

    private Typeface mTf;
    private ChartData<?> mChartData1;

    public PieChartItem2012(ChartData<?> cd, Context c) {
        super(cd);
        this.mChartData1 = cd;
        mTf = Typeface.createFromAsset(c.getAssets(), "Titillium-Light.otf");
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    public ChartData<?> getItemData() {
        return mChartData1;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.chart_pie2012, null);
            holder.chart = (PieChartEC) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setDescription("");
        holder.chart.setHoleRadius(52f);
        holder.chart.setTransparentCircleRadius(57f);
        holder.chart.setCenterText("Distribución\nhemiciclo 2011");
        holder.chart.setCenterTextTypeface(mTf);
        holder.chart.setCenterTextSize(16f);
        holder.chart.setTouchEnabled(false);
        holder.chart.setDrawSliceText(false);
        holder.chart.setRotationAngle(180f);

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.BLACK);
        // set data
        holder.chart.setData((PieData) mChartData);

        Legend l = holder.chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setWordWrapEnabled(true);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateXY(900, 900);

        return convertView;
    }

    private static class ViewHolder {
        PieChartEC chart;
    }
}
