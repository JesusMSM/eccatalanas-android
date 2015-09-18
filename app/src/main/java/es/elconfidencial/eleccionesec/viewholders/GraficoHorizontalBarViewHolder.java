package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 18/09/2015.
 */
public class GraficoHorizontalBarViewHolder extends RecyclerView.ViewHolder {
    public HorizontalBarChart grafico;

    public GraficoHorizontalBarViewHolder(View v) {
        super(v);
        grafico = (HorizontalBarChart) v.findViewById(R.id.chartBar);
    }
}