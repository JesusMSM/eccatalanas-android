package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.github.mikephil.charting.charts.LineChart;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MMOONFISH on 06/09/2015.
 */
public class GraficoLineasViewHolder extends RecyclerView.ViewHolder {
    public LineChart grafico;

    public GraficoLineasViewHolder(View v) {
        super(v);
        grafico = (LineChart) v.findViewById(R.id.chartLine);
    }
}
