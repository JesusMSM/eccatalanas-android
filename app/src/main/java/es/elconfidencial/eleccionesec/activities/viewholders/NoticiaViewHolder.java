package es.elconfidencial.eleccionesec.activities.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by Afll on 01/08/2015.
 */
public class NoticiaViewHolder extends RecyclerView.ViewHolder{

    public TextView titulo,decripcion,autor;
    public ImageView imagen;

    public NoticiaViewHolder(View v) {
        super(v);
        titulo = (TextView) v.findViewById(R.id.titulo);
        decripcion = (TextView) v.findViewById(R.id.descripcion);
        autor = (TextView) v.findViewById(R.id.autor);
        imagen = (ImageView) v.findViewById(R.id.imagen);
    }
}
