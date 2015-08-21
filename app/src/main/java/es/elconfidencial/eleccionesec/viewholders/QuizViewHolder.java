package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by Afll on 01/08/2015.
 */
public class QuizViewHolder extends RecyclerView.ViewHolder{
    public ImageView imagen;
    public TextView titulo;
    public TextView autor;

    public QuizViewHolder(View v) {
        super(v);
        imagen = (ImageView) v.findViewById(R.id.imagen_quiz);
        titulo = (TextView) v.findViewById(R.id.titulo_quiz);
        autor = (TextView) v.findViewById(R.id.autor_quiz);
    }
}