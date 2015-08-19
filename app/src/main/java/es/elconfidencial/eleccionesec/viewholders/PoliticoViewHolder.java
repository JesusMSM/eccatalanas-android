package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class PoliticoViewHolder extends RecyclerView.ViewHolder{

    public TextView nombre,edad, partido, cargo, perfil;
    public ImageView imagen;

    public PoliticoViewHolder(View v) {
        super(v);
        nombre = (TextView) v.findViewById(R.id.nombre);
        edad = (TextView) v.findViewById(R.id.edad);
        partido = (TextView) v.findViewById(R.id.partido);
        cargo = (TextView) v.findViewById(R.id.cargo);
        perfil = (TextView) v.findViewById(R.id.perfil);
        imagen = (ImageView) v.findViewById(R.id.imagen);
    }
}