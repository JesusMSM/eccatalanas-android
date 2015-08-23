package es.elconfidencial.eleccionesec.viewholders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class PartidoViewHolder extends RecyclerView.ViewHolder{

    public TextView nombre, representantes,fundacion, escanos, porcentajeVotos, ideologia, partidosRepresentados;
    public ImageView imagen;
    public FloatingActionButton fab;

    public PartidoViewHolder(View v) {
        super(v);
        nombre = (TextView) v.findViewById(R.id.nombre);
        representantes = (TextView) v.findViewById(R.id.representantes);
        fundacion = (TextView) v.findViewById(R.id.fundacion);
        escanos = (TextView) v.findViewById(R.id.escanos);
        porcentajeVotos = (TextView) v.findViewById(R.id.porcentajeVotos);
        ideologia = (TextView) v.findViewById(R.id.ideologia);
        partidosRepresentados = (TextView) v.findViewById(R.id.partidosRepresentados);
        imagen = (ImageView) v.findViewById(R.id.imagen);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
    }
}
