package es.elconfidencial.eleccionesec.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.NoticiaContentActivity;
import es.elconfidencial.eleccionesec.activities.PoliticianCardActivity;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class PoliticoViewHolder extends RecyclerView.ViewHolder{

    public TextView nombre,edad, partido, cargo;
    public ImageView imagen;
    public FloatingActionButton fab;
    Context context;

    public PoliticoViewHolder(View v) {
        super(v);
        nombre = (TextView) v.findViewById(R.id.nombre);
        edad = (TextView) v.findViewById(R.id.edad);
        partido = (TextView) v.findViewById(R.id.partido);
        cargo = (TextView) v.findViewById(R.id.cargo);
        imagen = (ImageView) v.findViewById(R.id.imagen);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
    }
}