package es.elconfidencial.eleccionesec.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Quiz;
import es.elconfidencial.eleccionesec.viewholders.ContadorViewHolder;
import es.elconfidencial.eleccionesec.viewholders.NoticiaViewHolder;
import es.elconfidencial.eleccionesec.viewholders.QuizViewHolder;

/**
 * Created by Afll on 01/08/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;
    Context context;
    private final int NOTICIA = 0, QUIZ = 1, CONTADOR = 2;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        //Depediendo del tipo de objeto (Noticia, Quiz, Integer...) devolvemos el tipo de celda del RecyclerView.
        if (items.get(position) instanceof Noticia) {
            return NOTICIA;
        } else if (items.get(position) instanceof Quiz) {
            return QUIZ;
        }else if (items.get(position).equals("contador")) {
            return CONTADOR;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //Dependiendo del tipo de view, cargamos el archivo layout XML correspondiente
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case NOTICIA:
                View v1 = inflater.inflate(R.layout.recyclerview_item_noticias, viewGroup, false);
                viewHolder = new NoticiaViewHolder(v1);
                break;
            case QUIZ:
                View v2 = inflater.inflate(R.layout.recyclerview_item_quiz, viewGroup, false);
                viewHolder = new QuizViewHolder(v2);
                break;
            case CONTADOR:
                View v3 = inflater.inflate(R.layout.recyclerview_item_contador, viewGroup, false);
                viewHolder = new ContadorViewHolder(v3);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //Identificamos el tipo de ViewHolder y creamos el correspondiente para dicha posicion.
        switch (viewHolder.getItemViewType()) {
            case NOTICIA:
                NoticiaViewHolder vh1 = (NoticiaViewHolder) viewHolder;
                configureNoticiaViewHolder(vh1, position);
                break;
            case QUIZ:
                QuizViewHolder vh2 = (QuizViewHolder) viewHolder;
                configureQuizViewHolder(vh2, position);
                break;
            case CONTADOR:
                ContadorViewHolder vh3 = (ContadorViewHolder) viewHolder;
                configureContadorViewHolder(vh3, position);
                break;
            default:
        }
    }

    /*** Funciones de configuracion de los ViewHolders ***/

    private void configureNoticiaViewHolder(NoticiaViewHolder vh1, int position) {
        Noticia noticia = (Noticia) items.get(position);
        if (noticia != null) {
            vh1.titulo.setText(noticia.getTitulo());
            vh1.decripcion.setText(noticia.getDescripcion());
            vh1.autor.setText(noticia.getAutor());
            vh1.imagen.setImageResource(noticia.getImagen());
        }
        vh1.autor.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));
        vh1.titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));
    }

    private void configureQuizViewHolder(QuizViewHolder vh2,int position) {
        Quiz quiz = (Quiz) items.get(position);
        if(quiz != null){
            vh2.image.setImageResource(quiz.getImage());
            vh2.title.setText(quiz.getTitle());
            vh2.subtitle.setText(quiz.getSubtitle());
        }
    }

    private void configureContadorViewHolder(ContadorViewHolder vh3,int position) {
        vh3.showContador();
        vh3.contador.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));
        vh3.label.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));
    }
}
