package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.chart.PieChartEC;

/**
 * Created by MOONFISH on 06/09/2015.
 */
public class EncuestaViewHolder extends RecyclerView.ViewHolder{
    public ImageView psc,cup,jps,pp,udc,cs,csqep;
    public TextView psc_tv,cup_tv,jps_tv,pp_tv,udc_tv,cs_tv,csqep_tv,nsnc_tv,otros_tv;
    public CheckBox psc_cb,cup_cb,jps_cb,pp_cb,udc_cb,cs_cb,csqep_cb,nsnc_cb,otros_cb;
    public Button votar,verResultados;

    public EncuestaViewHolder(View v) {
        super(v);
        psc = (ImageView) v.findViewById(R.id.imagenPSC);
        cup = (ImageView) v.findViewById(R.id.imagenCUP);
        jps = (ImageView) v.findViewById(R.id.imagenJPS);
        pp = (ImageView) v.findViewById(R.id.imagenPP);
        udc = (ImageView) v.findViewById(R.id.imagenUDC);
        cs = (ImageView) v.findViewById(R.id.imagenCS);
        csqep = (ImageView) v.findViewById(R.id.imagenCSQEP);

        psc_tv = (TextView) v.findViewById(R.id.psc_enc);
        cup_tv = (TextView) v.findViewById(R.id.cup_enc);
        jps_tv = (TextView) v.findViewById(R.id.jps_enc);
        pp_tv = (TextView) v.findViewById(R.id.pp_enc);
        udc_tv = (TextView) v.findViewById(R.id.udc_enc);
        cs_tv = (TextView) v.findViewById(R.id.cs_enc);
        csqep_tv = (TextView) v.findViewById(R.id.csqep_enc);
        nsnc_tv = (TextView) v.findViewById(R.id.nsnc_enc);
        otros_tv = (TextView) v.findViewById(R.id.otros_enc);

        psc_cb = (CheckBox) v.findViewById(R.id.checkboxPSC);
        cup_cb = (CheckBox) v.findViewById(R.id.checkboxCUP);
        jps_cb = (CheckBox) v.findViewById(R.id.checkboxJPS);
        pp_cb = (CheckBox) v.findViewById(R.id.checkboxPP);
        udc_cb = (CheckBox) v.findViewById(R.id.checkboxUDC);
        cs_cb = (CheckBox) v.findViewById(R.id.checkboxCS);
        csqep_cb = (CheckBox) v.findViewById(R.id.checkboxCSQEP);
        nsnc_cb = (CheckBox) v.findViewById(R.id.checkboxNSNC);
        otros_cb = (CheckBox) v.findViewById(R.id.checkboxOTROS);

        votar = (Button) v.findViewById(R.id.votar);
        verResultados = (Button) v.findViewById(R.id.verResultados);

    }

}
