package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 04/09/2015.
 */
public class MensajeViewHolder extends RecyclerView.ViewHolder{
    public TextView mensaje;

    public MensajeViewHolder(View v) {
        super(v);
        mensaje = (TextView) v.findViewById(R.id.mensaje);
    }
}