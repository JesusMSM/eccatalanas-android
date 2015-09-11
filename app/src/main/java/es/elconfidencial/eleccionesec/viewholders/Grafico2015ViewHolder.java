package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.chart.PieChartEC;

/**
 * Created by MOONFISH on 06/09/2015.
 */
public class Grafico2015ViewHolder extends RecyclerView.ViewHolder {
    public PieChartEC grafico;
    public LinearLayout link;

    public Grafico2015ViewHolder(View v) {
        super(v);
        grafico = (PieChartEC) v.findViewById(R.id.chart);
        link = (LinearLayout) v.findViewById(R.id.llayout);
    }
}
