package es.elconfidencial.eleccionesec.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.NoticiaContentActivity;
import es.elconfidencial.eleccionesec.activities.PoliticianCardActivity;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Partido;
import es.elconfidencial.eleccionesec.model.Politico;
import es.elconfidencial.eleccionesec.model.Quiz;
import es.elconfidencial.eleccionesec.viewholders.ContadorViewHolder;
import es.elconfidencial.eleccionesec.viewholders.NoticiaViewHolder;
import es.elconfidencial.eleccionesec.viewholders.PartidoViewHolder;
import es.elconfidencial.eleccionesec.viewholders.PoliticoViewHolder;
import es.elconfidencial.eleccionesec.viewholders.QuizViewHolder;

/**
 * Created by MOONFISH on 01/08/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;
    Context context;
    private final int NOTICIA = 0, QUIZ = 1, CONTADOR = 2, PARTIDO = 3, POLITICO = 4;

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
        }else if (items.get(position) instanceof Partido) {
            return PARTIDO;
        }else if (items.get(position) instanceof Politico) {
            return POLITICO;
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
            case PARTIDO:
                View v4 = inflater.inflate(R.layout.recyclerview_item_partido, viewGroup, false);
                viewHolder = new PartidoViewHolder(v4);
                break;
            case POLITICO:
                View v5 = inflater.inflate(R.layout.recyclerview_item_politico, viewGroup, false);
                viewHolder = new PoliticoViewHolder(v5);
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
            case PARTIDO:
                PartidoViewHolder vh4 = (PartidoViewHolder) viewHolder;
                configurePartidoViewHolder(vh4, position);
                break;
            case POLITICO:
                PoliticoViewHolder vh5 = (PoliticoViewHolder) viewHolder;
                configurePoliticoViewHolder(vh5, position);
                break;
            default:
        }
    }

    /*** Funciones de configuracion de los ViewHolders ***/
    private void configureNoticiaViewHolder(final NoticiaViewHolder vh1, int position) {
        final Noticia noticia = (Noticia) items.get(position);
        if (noticia != null) {
            vh1.titulo.setText(Html.fromHtml(noticia.getTitulo()));
            vh1.autor.setText(noticia.getAutor());
            Picasso.with(context).load(noticia.getImagenUrl()).into(vh1.imagen);
        }
        vh1.autor.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));
        vh1.titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy.ttf"));

        //onClickListenerNoticia
        vh1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un intent para llamar a NoticiasContentActivity con los extras de la noticia correspondiente
                Intent intent = new Intent(context, NoticiaContentActivity.class);
                intent.putExtra("titulo",noticia.getTitulo());
                System.out.print("DESC" + noticia.getDescripcion());
                intent.putExtra("descripcion", noticia.getDescripcion());
                intent.putExtra("autor",noticia.getAutor());
                intent.putExtra("fecha",noticia.getFecha());
                intent.putExtra("link",noticia.getLink());
                intent.putExtra("imagenUrl",noticia.getImagenUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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

    private void configurePartidoViewHolder(PartidoViewHolder vh4,int position) {
        Partido partido = (Partido) items.get(position);
        if(partido != null){
            vh4.nombre.setText(partido.getNombre());
            vh4.presidente.setText(partido.getPresidente());
            vh4.secGeneral.setText(partido.getSecGeneral());
            vh4.portavozCongreso.setText(partido.getPortavozCongreso());
            vh4.portavozSenado.setText(partido.getPortavozSenado());
            vh4.fundacion.setText(partido.getFundacion());
            vh4.ideologia.setText(partido.getIdeologia());
            vh4.posicionEspecto.setText(partido.getPosicionEspectro());
            vh4.sede.setText(partido.getSede());
        }
    }

    private void configurePoliticoViewHolder(PoliticoViewHolder vh5,int position) {
       final Politico politico = (Politico) items.get(position);
        if(politico != null) {
            vh5.imagen.setImageResource(politico.getImagen());
            vh5.nombre.setText(politico.getNombre());
            vh5.edad.setText(politico.getEdad());
            vh5.partido.setText(politico.getPartido());
            vh5.cargo.setText(politico.getCargo());
        }
            vh5.fab.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(context, PoliticianCardActivity.class);
                    intent.putExtra("imagen",politico.getImagen());
                    intent.putExtra("nombre", politico.getNombre());
                    intent.putExtra("edad", politico.getEdad());
                    intent.putExtra("partido", politico.getPartido());
                    intent.putExtra("cargo", politico.getCargo());
                    intent.putExtra("perfil", politico.getPerfil());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

    }

}
