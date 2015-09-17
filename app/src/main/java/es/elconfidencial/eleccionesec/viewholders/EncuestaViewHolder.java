package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.chart.PieChartEC;

/**
 * Created by MOONFISH on 06/09/2015.
 */
public class EncuestaViewHolder extends RecyclerView.ViewHolder{
    public ImageView psc;
    public ImageView cup;
    public ImageView jps;
    public ImageView pp;
    public ImageView udc;
    public ImageView cs;
    public ImageView csqep;


    public EncuestaViewHolder(View v) {
        super(v);
        psc = (ImageView) v.findViewById(R.id.psc_enc);
        cup = (ImageView) v.findViewById(R.id.cup_enc);
        jps = (ImageView) v.findViewById(R.id.jps_enc);
        pp = (ImageView) v.findViewById(R.id.pp_enc);
        udc = (ImageView) v.findViewById(R.id.udc_enc);
        cs = (ImageView) v.findViewById(R.id.cs_enc);
        csqep = (ImageView) v.findViewById(R.id.csqep_enc);

    }
}
