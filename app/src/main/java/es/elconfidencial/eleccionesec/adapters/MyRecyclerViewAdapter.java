package es.elconfidencial.eleccionesec.adapters;

import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Point;
        import android.graphics.Typeface;
        import android.graphics.drawable.Drawable;
        import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
        import android.text.Html;
        import android.util.Log;
        import android.view.Display;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.ImageView;

        import com.squareup.picasso.Picasso;

        import org.apache.http.HttpStatus;

        import java.io.InputStream;
        import java.lang.ref.WeakReference;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.List;

        import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.activities.NoticiaContentActivity;
        import es.elconfidencial.eleccionesec.activities.PartyCardActivity;
        import es.elconfidencial.eleccionesec.activities.PoliticianCardActivity;
        import es.elconfidencial.eleccionesec.model.Mensaje;
        import es.elconfidencial.eleccionesec.model.Noticia;
        import es.elconfidencial.eleccionesec.model.Partido;
        import es.elconfidencial.eleccionesec.model.Politico;
        import es.elconfidencial.eleccionesec.model.Quiz;
        import es.elconfidencial.eleccionesec.model.Title;
        import es.elconfidencial.eleccionesec.viewholders.ContadorViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.MensajeViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.NoticiaViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.PartidoViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.PoliticoViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.QuizViewHolder;
        import es.elconfidencial.eleccionesec.viewholders.TitleViewHolder;

/**
 * Created by MOONFISH on 01/08/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;
    Context context;
    private final int NOTICIA = 0, QUIZ = 1, CONTADOR = 2, PARTIDO = 3, POLITICO = 4, TITULO = 5, MENSAJE = 6;

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
        } else if (items.get(position) instanceof Title) {
            return TITULO;
        }else if (items.get(position) instanceof Mensaje) {
            return MENSAJE;
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
            case TITULO:
                View v6 = inflater.inflate(R.layout.recyclerview_item_title, viewGroup, false);
                viewHolder = new TitleViewHolder(v6);
                break;
            case MENSAJE:
                View v7 = inflater.inflate(R.layout.recyclerview_item_mensaje, viewGroup, false);
                viewHolder = new MensajeViewHolder(v7);
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
            case TITULO:
                TitleViewHolder vh6 = (TitleViewHolder) viewHolder;
                configureTitleViewHolder(vh6, position);
                break;
            case MENSAJE:
                MensajeViewHolder vh7 = (MensajeViewHolder) viewHolder;
                configureMensajeViewHolder(vh7, position);
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
            try {
                System.gc();
                Picasso.with(context).load(noticia.getImagenUrl()).placeholder(R.drawable.nopic).fit().into(vh1.imagen);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        vh1.autor.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh1.titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy-Italic.ttf"));

        //onClickListenerNoticia
        vh1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un intent para llamar a NoticiasContentActivity con los extras de la noticia correspondiente
                Intent intent = new Intent(context, NoticiaContentActivity.class);
                intent.putExtra("titulo", noticia.getTitulo());
                System.out.print("DESC" + noticia.getDescripcion());
                intent.putExtra("descripcion", noticia.getDescripcion());
                intent.putExtra("autor", noticia.getAutor());
                intent.putExtra("fecha", noticia.getFecha());
                intent.putExtra("link", noticia.getLink());
                intent.putExtra("imagenUrl", noticia.getImagenUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void configureQuizViewHolder(QuizViewHolder vh2,int position) {
        Quiz quiz = (Quiz) items.get(position);
        if(quiz != null){
            vh2.titulo.setText(Html.fromHtml(quiz.getTitulo()));
            vh2.autor.setText(quiz.getAutor());
            try {
                System.gc();
                Picasso.with(context).load(quiz.getImagenUrl()).placeholder(R.drawable.nopic).into(vh2.imagen);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //Fonts
        vh2.autor.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh2.titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy-Italic.ttf"));

    }

    private void configureContadorViewHolder(ContadorViewHolder vh3,int position) {
        vh3.showContador();
        vh3.contador.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh3.label.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
    }

    private void configurePartidoViewHolder(PartidoViewHolder vh4,int position) {
        final Partido partido = (Partido) items.get(position);
        if(partido != null){
            try {
                System.gc();
                Picasso.with(context).load(partido.getImagen()).resize(450, 450).placeholder(R.drawable.nopicpartidolow).into(vh4.imagen);
            }catch (Exception e){e.printStackTrace();}
            vh4.nombre.setText(partido.getNombre());
            vh4.representantes.setText(partido.getRepresentantes());
            vh4.fundacion.setText(partido.getFundacion());
            vh4.escanos.setText(partido.getEscanos());
            vh4.porcentajeVotos.setText(partido.getPorcentajeVotos());
            vh4.ideologia.setText(partido.getIdeologia());
            vh4.partidosRepresentados.setText(partido.getPartidosRepresentados());
        }

        vh4.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, PartyCardActivity.class);
                intent.putExtra("imagen", partido.getImagen());
                intent.putExtra("nombre", partido.getNombre());
                intent.putExtra("representantes", partido.getRepresentantes());
                intent.putExtra("fundacion", partido.getFundacion());
                intent.putExtra("escanos", partido.getEscanos());
                intent.putExtra("porcentajeVotos", partido.getPorcentajeVotos());
                intent.putExtra("ideologia", partido.getIdeologia());
                intent.putExtra("partidosRepresentados", partido.getPartidosRepresentados());
                intent.putExtra("perfil", partido.getPerfil());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //Fonts
        vh4.nombre.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh4.representantes.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh4.fundacion.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh4.escanos.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh4.porcentajeVotos.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh4.ideologia.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh4.partidosRepresentados.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
    }

    private void configurePoliticoViewHolder(PoliticoViewHolder vh5,int position) {
        final Politico politico = (Politico) items.get(position);
        if(politico != null) {
            try {
                System.gc();
                Picasso.with(context).load(politico.getImagen()).resize(450, 450).placeholder(R.drawable.nopicpoliticolow).centerInside().into(vh5.imagen);
            }catch (Exception e){e.printStackTrace();}
            vh5.nombre.setText(politico.getNombre());
            vh5.edad.setText(politico.getEdad());
            vh5.partido.setText(politico.getPartido());
            vh5.cargo.setText(politico.getCargo());
        }
        vh5.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, PoliticianCardActivity.class);
                intent.putExtra("imagen", politico.getImagen());
                intent.putExtra("nombre", politico.getNombre());
                intent.putExtra("edad", politico.getEdad());
                intent.putExtra("partido", politico.getPartido());
                intent.putExtra("cargo", politico.getCargo());
                intent.putExtra("perfil", politico.getPerfil());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //Fonts
        vh5.nombre.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh5.cargo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh5.partido.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        vh5.edad.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));


    }

    private void configureTitleViewHolder(TitleViewHolder vh6,int position) {
        final Title title = (Title) items.get(position);
        if(title != null) {
            vh6.title.setText(title.getTitle());
        }
        vh6.link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    String nombreTitulo = title.getTitle();
                    if(nombreTitulo.equals(context.getResources().getString(R.string.titulo_resultados))){
                        HomeActivity.switchFragment(3);
                    } else if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_noticias))){
                        HomeActivity.switchFragment(0);
                    } else {
                        HomeActivity.switchFragment(1);
                    }
                } catch (Exception e){
                    Log.e("MyTag", "Object title not exists");
                }


            }
        });

        //Fonts
        vh6.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));


    }

    private void configureMensajeViewHolder(MensajeViewHolder vh7,int position) {
        final Mensaje mensaje = (Mensaje) items.get(position);
        if(mensaje != null) {
            vh7.mensaje.setText(mensaje.getMensaje());
        }
        //Fonts
        vh7.mensaje.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

    }

}
