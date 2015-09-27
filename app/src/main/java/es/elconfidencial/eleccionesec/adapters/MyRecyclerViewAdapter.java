package es.elconfidencial.eleccionesec.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.fitness.data.DataSet;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.activities.NoticiaContentActivity;
import es.elconfidencial.eleccionesec.activities.PartyCardActivity;
import es.elconfidencial.eleccionesec.activities.PoliticianCardActivity;
import es.elconfidencial.eleccionesec.activities.QuizContentActivity;
import es.elconfidencial.eleccionesec.chart.HorizontalBarChartItem;
import es.elconfidencial.eleccionesec.chart.LineChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem;
import es.elconfidencial.eleccionesec.chart.PieChartItem2012;
import es.elconfidencial.eleccionesec.fragments.ChartTab;
import es.elconfidencial.eleccionesec.model.Mensaje;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Partido;
import es.elconfidencial.eleccionesec.model.Politico;
import es.elconfidencial.eleccionesec.model.Quiz;
import es.elconfidencial.eleccionesec.model.Title;
import es.elconfidencial.eleccionesec.viewholders.ContadorViewHolder;
import es.elconfidencial.eleccionesec.viewholders.EncuestaViewHolder;
import es.elconfidencial.eleccionesec.viewholders.Grafico2012ViewHolder;
import es.elconfidencial.eleccionesec.viewholders.Grafico2015ViewHolder;
import es.elconfidencial.eleccionesec.viewholders.GraficoHorizontalBarViewHolder;
import es.elconfidencial.eleccionesec.viewholders.GraficoLineasViewHolder;
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
    private String fechaElecciones = "27/09/2015";

    private final int NOTICIA = 0, QUIZ = 1, CONTADOR = 2, PARTIDO = 3, POLITICO = 4, TITULO = 5, MENSAJE = 6, GRAFICO2012 = 7, GRAFICO2015 = 8, GRAFICOLINEAS = 9, ENCUESTA = 10, GRAFICOHORIZONTALBAR =11;

    int partidoMarcado = -1;


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
        } else if (items.get(position).equals("contador")) {
            return CONTADOR;
        } else if (items.get(position) instanceof Partido) {
            return PARTIDO;
        } else if (items.get(position) instanceof Politico) {
            return POLITICO;
        } else if (items.get(position) instanceof Title) {
            return TITULO;
        } else if (items.get(position) instanceof Mensaje) {
            return MENSAJE;
        } else if (items.get(position) instanceof PieChartItem2012) {
            return GRAFICO2012;
        } else if (items.get(position) instanceof PieChartItem) {
            return GRAFICO2015;
        } else if (items.get(position) instanceof LineChartItem) {
            return GRAFICOLINEAS;
        }else if (items.get(position).equals("encuesta")) {
            return ENCUESTA;
        }else if (items.get(position) instanceof HorizontalBarChartItem) {
            return GRAFICOHORIZONTALBAR;
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
            case GRAFICO2012:
                View v8 = inflater.inflate(R.layout.chart_pie2012, viewGroup, false);
                viewHolder = new Grafico2012ViewHolder(v8);
                break;
            case GRAFICO2015:
                View v9 = inflater.inflate(R.layout.chart_pie, viewGroup, false);
                viewHolder = new Grafico2015ViewHolder(v9);
                break;
            case GRAFICOLINEAS:
                View v10 = inflater.inflate(R.layout.chart_line, viewGroup, false);
                viewHolder = new GraficoLineasViewHolder(v10);
                break;
            case ENCUESTA:
                View v11 = inflater.inflate(R.layout.recyclerview_item_encuesta, viewGroup, false);
                viewHolder = new EncuestaViewHolder(v11);
                break;
            case GRAFICOHORIZONTALBAR:
                View v12 = inflater.inflate(R.layout.chart_horizontal_bar, viewGroup, false);
                viewHolder = new GraficoHorizontalBarViewHolder(v12);
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
            case GRAFICO2012:
                Grafico2012ViewHolder vh8 = (Grafico2012ViewHolder) viewHolder;
                configureGrafico2012ViewHolder(vh8, position);
                break;
            case GRAFICO2015:
                Grafico2015ViewHolder vh9 = (Grafico2015ViewHolder) viewHolder;
                configureGrafico2015ViewHolder(vh9, position);
                break;
            case GRAFICOLINEAS:
                GraficoLineasViewHolder vh10 = (GraficoLineasViewHolder) viewHolder;
                configureGraficoLineasViewHolder(vh10, position);
                break;
            case ENCUESTA:
                EncuestaViewHolder vh11 = (EncuestaViewHolder) viewHolder;
                configureEncuestaViewHolder(vh11, position);
                break;
            case GRAFICOHORIZONTALBAR:
                GraficoHorizontalBarViewHolder vh12 = (GraficoHorizontalBarViewHolder) viewHolder;
                configureGraficoHorizontalBarViewHolder(vh12, position);
                break;
            default:
        }
    }

    /**
     * Funciones de configuracion de los ViewHolders **
     */
    private void configureNoticiaViewHolder(final NoticiaViewHolder vh1, int position) {
        final Noticia noticia = (Noticia) items.get(position);
        if (noticia != null) {
            vh1.titulo.setText(Html.fromHtml(noticia.getTitulo()));
            vh1.autor.setText(noticia.getAutor());
            try {
                System.gc();
                Glide.with(context).load(noticia.getImagenUrl()).placeholder(R.drawable.nopic).into(vh1.imagen);

            } catch (Exception e) {
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

    private void configureQuizViewHolder(QuizViewHolder vh2, int position) {
        final Quiz quiz = (Quiz) items.get(position);
        if (quiz != null) {
            vh2.titulo.setText(Html.fromHtml(quiz.getTitulo()));
            vh2.autor.setText(quiz.getAutor());
            try {
                System.gc();
                Glide.with(context).load(quiz.getImagen()).placeholder(R.drawable.nopic).into(vh2.imagen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //onClickListenerNoticia
        vh2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un intent para llamar a QuizContentActivity con los extras del quiz correspondiente
                Intent intent = new Intent(context, QuizContentActivity.class);
                intent.putExtra("link", quiz.getLink());
                intent.putExtra("title", quiz.getTitulo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //Fonts
        vh2.autor.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh2.titulo.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Heavy-Italic.ttf"));

    }

    private void configureContadorViewHolder(ContadorViewHolder vh3, int position) {
        vh3.showContador();
        vh3.contador.setTypeface(Typeface.createFromAsset(context.getAssets(), "Milio-Bold.ttf"));
        vh3.label.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
        vh3.powered.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
    }

    private void configurePartidoViewHolder(PartidoViewHolder vh4, int position) {
        final Partido partido = (Partido) items.get(position);
        if (partido != null) {
            try {
                System.gc();
                Glide.with(context).load(partido.getImagen()).placeholder(R.drawable.nopicpartidolow).into(vh4.imagen);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void configurePoliticoViewHolder(PoliticoViewHolder vh5, int position) {
        final Politico politico = (Politico) items.get(position);
        if (politico != null) {
            try {
                System.gc();
                Glide.with(context).load(politico.getImagen()).placeholder(R.drawable.nopicpoliticolow).into(vh5.imagen);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void configureTitleViewHolder(TitleViewHolder vh6, int position) {
        final Title title = (Title) items.get(position);
        if (title != null) {
            vh6.title.setText(title.getTitle());
        }
        vh6.link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String nombreTitulo = title.getTitle();
                    if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_resultados_2012))) {
                        HomeActivity.switchFragment(3);
                    } else if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_noticias))) {
                        HomeActivity.switchFragment(0);
                    } else if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_resultados_2015))) {
                        HomeActivity.switchFragment(3);
                    } else if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_evolucion))) {
                        HomeActivity.switchFragment(3);
                    } else if (nombreTitulo.equals(context.getResources().getString(R.string.titulo_encuesta))) {
                        HomeActivity.switchFragment(3);
                    } else {
                        HomeActivity.switchFragment(1);
                    }
                } catch (Exception e) {
                    Log.e("MyTag", "Object title not exists");
                }


            }
        });

        //Fonts
        vh6.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));


    }

    private void configureMensajeViewHolder(MensajeViewHolder vh7, int position) {
        final Mensaje mensaje = (Mensaje) items.get(position);
        if (mensaje != null) {
            vh7.mensaje.setText(mensaje.getMensaje());
        }
        //Fonts
        vh7.mensaje.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

    }

    private void configureGrafico2012ViewHolder(Grafico2012ViewHolder vh8, int position) {
        final PieChartItem2012 grafico = (PieChartItem2012) items.get(position);
        ChartData<?> mChartData = grafico.getItemData();

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

        if (getSizeName(context).equals("xlarge")) {
            mChartData.setValueTextSize(25f);
        } else if (getSizeName(context).equals("large")) {
            mChartData.setValueTextSize(17f);
        } else if (getSizeName(context).equals("normal")) {
            mChartData.setValueTextSize(15f);
        }else {
            mChartData.setValueTextSize(11f);
        }

        mChartData.setValueTextColor(Color.WHITE);

        if (grafico != null) {
            // apply styling

            if (getSizeName(context).equals("xlarge")) {
                vh8.grafico.setCenterTextSize(40f);
            } else if (getSizeName(context).equals("large")) {
                vh8.grafico.setCenterTextSize(29f);
            } else if (getSizeName(context).equals("normal")) {
                vh8.grafico.setCenterTextSize(26f);
            }else {
                vh8.grafico.setCenterTextSize(17f);
            }

            vh8.grafico.setDescription("");
            vh8.grafico.setHoleRadius(52f);
            vh8.grafico.setTransparentCircleRadius(57f);
            vh8.grafico.setCenterText("2012");
            vh8.grafico.setCenterTextTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
            vh8.grafico.setCenterTextColor(context.getResources().getColor(R.color.ColorAccent));
            vh8.grafico.setTouchEnabled(false);
            vh8.grafico.setDrawSliceText(false);
            vh8.grafico.setRotationAngle(180f);

            //Offset de top
            vh8.grafico.setExtraTopOffset(10f);


            vh8.grafico.setData((PieData) mChartData);

            Legend l = vh8.grafico.getLegend();

            if (getSizeName(context).equals("xlarge")) {
                l.setTextSize(30f);
                l.setFormSize(30f);
            } else if (getSizeName(context).equals("large")) {
                l.setTextSize(17f);
                l.setFormSize(17f);
            } else if (getSizeName(context).equals("normal")) {
                l.setTextSize(15f);
                l.setFormSize(15f);
            }else {
                l.setTextSize(11f);
                l.setFormSize(11f);
            }

            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setYEntrySpace(0f);
            l.setYOffset(10f);
            l.setWordWrapEnabled(true);
            l.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            l.setCustom(mChartData.getColors(), createLegend(mChartData));

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            vh8.grafico.animateXY(900, 900);

            vh8.link.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        HomeActivity.switchFragment(3);
                    } catch (Exception e) {
                        Log.e("MyTag", "Object grafico not exists");
                    }
                }
            });
        }

    }

    private void configureGrafico2015ViewHolder(Grafico2015ViewHolder vh9, int position) {
        final PieChartItem grafico = (PieChartItem) items.get(position);
        ChartData<?> mChartData = grafico.getItemData();

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
        if (getSizeName(context).equals("xlarge")) {
            mChartData.setValueTextSize(25f);
        } else if (getSizeName(context).equals("large")) {
            mChartData.setValueTextSize(17f);
        } else if (getSizeName(context).equals("normal")) {
            mChartData.setValueTextSize(15f);
        }else {
            mChartData.setValueTextSize(11f);
        }
        mChartData.setValueTextColor(Color.WHITE);

        if (grafico != null) {
            // apply styling

            if (getSizeName(context).equals("xlarge")) {
                vh9.grafico.setCenterTextSize(34f);
            } else if (getSizeName(context).equals("large")) {
                vh9.grafico.setCenterTextSize(22f);
            } else if (getSizeName(context).equals("normal")) {
                vh9.grafico.setCenterTextSize(19f);
            }else {
                vh9.grafico.setCenterTextSize(15f);
            }

            vh9.grafico.setDescription("");
            vh9.grafico.setHoleRadius(52f);
            vh9.grafico.setTransparentCircleRadius(57f);
            vh9.grafico.setCenterText(context.getResources().getString(R.string.porcentaje_escrutado) + "\n" + ChartTab.porcentajeEscrutado + " %");
            vh9.grafico.setCenterTextTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Regular.otf"));
            vh9.grafico.setCenterTextColor(context.getResources().getColor(R.color.ColorAccent));
            vh9.grafico.setTouchEnabled(false);
            vh9.grafico.setDrawSliceText(false);
            vh9.grafico.setRotationAngle(180f);

            vh9.grafico.setData((PieData) mChartData);

            Legend l = vh9.grafico.getLegend();

            if (getSizeName(context).equals("xlarge")) {
                l.setTextSize(30f);
                l.setFormSize(30f);
            } else if (getSizeName(context).equals("large")) {
                l.setTextSize(18f);
                l.setFormSize(18f);
            } else if (getSizeName(context).equals("normal")) {
                l.setTextSize(15f);
                l.setFormSize(15f);
            }else {
                l.setTextSize(11f);
                l.setFormSize(11f);
            }

            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setYEntrySpace(0f);
            l.setYOffset(10f);
            l.setWordWrapEnabled(true);
            l.setCustom(mChartData.getColors(), createLegend(mChartData));
            System.out.println("COLORS: " + mChartData.getColors().length);
            l.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            vh9.grafico.animateXY(900, 900);

            vh9.link.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        HomeActivity.switchFragment(3);
                    } catch (Exception e) {
                        Log.e("MyTag", "Object grafico not exists");
                    }
                }
            });
        }

    }

    private void configureGraficoLineasViewHolder(GraficoLineasViewHolder vh10, int position) {
        final LineChartItem grafico = (LineChartItem) items.get(position);
        ChartData<?> mChartData = grafico.getItemData();


        if (grafico != null) {
            // apply styling


            vh10.grafico.setDescription("");
            vh10.grafico.setDrawGridBackground(false);
            vh10.grafico.setTouchEnabled(false);

            XAxis xAxis = vh10.grafico.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(true);

            YAxis leftAxis = vh10.grafico.getAxisLeft();
            leftAxis.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            leftAxis.setLabelCount(5, false);

            YAxis rightAxis = vh10.grafico.getAxisRight();
            rightAxis.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            rightAxis.setLabelCount(5, false);
            rightAxis.setDrawGridLines(false);

            if (getSizeName(context).equals("xlarge")) {
                xAxis.setTextSize(20f);
                leftAxis.setTextSize(20f);
                rightAxis.setTextSize(20f);
            } else if (getSizeName(context).equals("large")) {
                xAxis.setTextSize(13f);
                leftAxis.setTextSize(13f);
                rightAxis.setTextSize(13f);
            } else if (getSizeName(context).equals("normal")) {
                xAxis.setTextSize(11f);
                leftAxis.setTextSize(11f);
                rightAxis.setTextSize(11f);
            }else {
                xAxis.setTextSize(8f);
                leftAxis.setTextSize(8f);
                rightAxis.setTextSize(8f);
            }

            //Set data
            vh10.grafico.setData((LineData) mChartData);

            vh10.grafico.animateX(750);

            //Legend
            Legend l = vh10.grafico.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setYEntrySpace(0f);
            l.setYOffset(10f);
            l.setWordWrapEnabled(true);
            // l.setCustom(mChartData.getColors(), createLegendLines(mChartData));
            l.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

            if (getSizeName(context).equals("xlarge")) {
                l.setTextSize(30f);
                l.setFormSize(30f);
            } else if (getSizeName(context).equals("large")) {
                l.setTextSize(18f);
                l.setFormSize(18f);
            } else if (getSizeName(context).equals("normal")) {
                l.setTextSize(15f);
                l.setFormSize(15f);
            }else {
                l.setTextSize(11f);
                l.setFormSize(11f);
            }

        }

    }



    private void configureGraficoHorizontalBarViewHolder(GraficoHorizontalBarViewHolder vh12, int position) {
        final HorizontalBarChartItem grafico = (HorizontalBarChartItem) items.get(position);
        ChartData<?> mChartData = grafico.getItemData();

        if (getSizeName(context).equals("xlarge")) {
            mChartData.setValueTextSize(25f);
        } else if (getSizeName(context).equals("large")) {
            mChartData.setValueTextSize(17f);
        } else if (getSizeName(context).equals("normal")) {
            mChartData.setValueTextSize(13f);
        }else {
            mChartData.setValueTextSize(11f);
        }        if (grafico != null) {

            vh12.grafico.getLegend().setEnabled(false);

            // apply styling
            vh12.grafico.setDrawBarShadow(false);
            vh12.grafico.setTouchEnabled(false);
            vh12.grafico.setDrawValueAboveBar(false);
            vh12.grafico.setBackgroundColor(Color.TRANSPARENT);
            vh12.grafico.setGridBackgroundColor(Color.TRANSPARENT);
            vh12.grafico.setDescription("");

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            vh12.grafico.setMaxVisibleValueCount(60);

            // scaling can now only be done on x- and y-axis separately
            vh12.grafico.setPinchZoom(false);

            // draw shadows for each bar that show the maximum value
            // mChart.setDrawBarShadow(true);

            // mChart.setDrawXLabels(false);

            vh12.grafico.setDrawGridBackground(false);

            // mChart.setDrawYLabels(false);

            mChartData.setValueTextColor(Color.WHITE);


            XAxis xl = vh12.grafico.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            xl.setDrawAxisLine(false);
            xl.setDrawGridLines(false);
            xl.setGridLineWidth(0.3f);
            if (getSizeName(context).equals("xlarge")) {
                xl.setTextSize(25f);
            } else if (getSizeName(context).equals("large")) {
                xl.setTextSize(14f);
            } else if (getSizeName(context).equals("normal")) {
                xl.setTextSize(11f);
            }else {
                xl.setTextSize(8f);
            }


            YAxis yl = vh12.grafico.getAxisLeft();
            yl.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            yl.setDrawAxisLine(false);
            yl.setDrawGridLines(false);
            yl.setTextColor(Color.TRANSPARENT);
            yl.setGridLineWidth(0.3f);

            //  yl.setInverted(true);

            YAxis yr = vh12.grafico.getAxisRight();
            yr.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            yr.setDrawAxisLine(false);
            yr.setDrawGridLines(false);
            yr.setTextColor(Color.TRANSPARENT);
//            yr.setInverted(true);

            vh12.grafico.setData((BarData) mChartData);
            vh12.grafico.animateY(2500);
        }

    }

    private void configureEncuestaViewHolder(final EncuestaViewHolder vh11, int position) {
        try {
            System.gc();

            //Imagenes
            Glide.with(context).load(R.drawable.psc).placeholder(R.drawable.nopicpartidolow).into(vh11.psc);
            Glide.with(context).load(R.drawable.cup).placeholder(R.drawable.nopicpartidolow).into(vh11.cup);
            Glide.with(context).load(R.drawable.jps).placeholder(R.drawable.nopicpartidolow).into(vh11.jps);
            Glide.with(context).load(R.drawable.pp).placeholder(R.drawable.nopicpartidolow).into(vh11.pp);
            Glide.with(context).load(R.drawable.udc).placeholder(R.drawable.nopicpartidolow).into(vh11.udc);
            Glide.with(context).load(R.drawable.cs).placeholder(R.drawable.nopicpartidolow).into(vh11.cs);
            Glide.with(context).load(R.drawable.csqep).placeholder(R.drawable.nopicpartidolow).into(vh11.csqep);

            //TextViews
            vh11.psc_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.cup_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.jps_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.pp_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.udc_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.cs_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.csqep_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.nsnc_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.otros_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.votar.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));
            vh11.verResultados.setTypeface(Typeface.createFromAsset(context.getAssets(), "Titillium-Light.otf"));

            //Checkbox
            vh11.psc_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.cup_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.jps_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.pp_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.udc_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.cs_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.csqep_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.nsnc_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));
            vh11.otros_cb.setOnCheckedChangeListener(new myCheckerListener(vh11));


            vh11.votar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (partidoMarcado != -1) {
                        //Marcar como que ha votado
                        //Cargamos las preferencias compartidas, es como la base de datos para guardarlas y que se recuerden mas tarde
                        Activity mAct = (Activity) (v.getContext());
                        SharedPreferences prefs = mAct.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("hasVoted", "true"); //Lo guardamos para recordarlo
                        editor.commit(); //Guardamos las SharedPreferences

                        //Mandar a parse
                        // Enviar voto a parse
                        try {
                            Parse.enableLocalDatastore(v.getContext());
                            //Autenticacion con Parse
                            Parse.initialize(v.getContext(), "7P82tODwUk7C6AZLyLSuKBvyjLZcdpNz80J6RT2Z", "3jhqLEIKUI7RknTCU8asoITvPC9PjHD5n2FDub4h");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Comunicacion con Parse.com
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Partido");
                        query.whereEqualTo("Name", "Votaciones");
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    try {
                                        switch (partidoMarcado) {
                                            case 0: //PSC
                                                object.getJSONArray("Valores").put(0, object.getJSONArray("Valores").getInt(0) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 1: //CUP
                                                object.getJSONArray("Valores").put(1, object.getJSONArray("Valores").getInt(1) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 2: //JUNTS
                                                object.getJSONArray("Valores").put(2, object.getJSONArray("Valores").getInt(2) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 3: //PP
                                                object.getJSONArray("Valores").put(3, object.getJSONArray("Valores").getInt(3) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 4: //UNIO
                                                object.getJSONArray("Valores").put(4, object.getJSONArray("Valores").getInt(4) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 5: //CS
                                                object.getJSONArray("Valores").put(5, object.getJSONArray("Valores").getInt(5) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 6: //CAT SI QUE ES POT
                                                object.getJSONArray("Valores").put(6, object.getJSONArray("Valores").getInt(6) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 7: //OTROS
                                                object.getJSONArray("Valores").put(7, object.getJSONArray("Valores").getInt(7) + 1);
                                                object.saveInBackground();
                                                break;
                                            case 8: //NSNC
                                                object.getJSONArray("Valores").put(8, object.getJSONArray("Valores").getInt(8) + 1);
                                                object.saveInBackground();
                                                break;
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    //something went wrong
                                }
                            }
                        });
                        int pos = 2;
                        if(isElectionDay()){
                            if(items.get(0) instanceof Mensaje){//Esperando datos de escrutinio
                                pos = 2;
                            }else{
                                pos = 3;
                            }
                        }
                        items.remove(pos);
                        notifyItemRemoved(pos);
                        items.add(pos, new Mensaje(context.getResources().getString(R.string.encuestagracias)));
                        notifyItemInserted(pos);
                    }else{
                        Toast.makeText(context,context.getResources().getString(R.string.encuestaerror),Toast.LENGTH_SHORT).show();

                    }
                }
            });

            vh11.verResultados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.switchFragment(3);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class myCheckerListener implements CompoundButton.OnCheckedChangeListener{
        private EncuestaViewHolder vh11;
        myCheckerListener(RecyclerView.ViewHolder vh11){
            this.vh11= (EncuestaViewHolder) vh11;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            System.out.println(partidoMarcado);
            if (isChecked){
                //Uncheck all
                vh11.psc_cb.setChecked(false);
                vh11.cup_cb.setChecked(false);
                vh11.jps_cb.setChecked(false);
                vh11.pp_cb.setChecked(false);
                vh11.udc_cb.setChecked(false);
                vh11.cs_cb.setChecked(false);
                vh11.csqep_cb.setChecked(false);
                vh11.nsnc_cb.setChecked(false);
                vh11.otros_cb.setChecked(false);

                //Check this button
                buttonView.setChecked(true);
                switch (buttonView.getId()){
                    case R.id.checkboxPSC: partidoMarcado= 0;break;
                    case R.id.checkboxCUP: partidoMarcado = 1;break;
                    case R.id.checkboxJPS: partidoMarcado = 2;break;
                    case R.id.checkboxPP: partidoMarcado = 3;break;
                    case R.id.checkboxUDC: partidoMarcado = 4;break;
                    case R.id.checkboxCS: partidoMarcado = 5;break;
                    case R.id.checkboxCSQEP: partidoMarcado = 6;break;
                    case R.id.checkboxOTROS: partidoMarcado = 7;break;
                    case R.id.checkboxNSNC: partidoMarcado = 8;break;
                    default: partidoMarcado = -1;break;
                }
            }else{
                partidoMarcado = -1;
            }
        }
    }


    //Metodos auxiliares


    private static String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }


    public String [] createLegend(ChartData<?> mChartData){
        //Datos de alias de partidos.
        List<String> alias = mChartData.getXVals();
        //Datos de porcentaje
        com.github.mikephil.charting.data.DataSet myDataSet = mChartData.getDataSetByLabel("",true);
        String element = "";
        List<String> elements = new ArrayList<String>(Arrays.asList(new String[] {}));
        //Creamos cada string
        for (int i = 0;i< myDataSet.getEntryCount(); i++){
            String nombre = alias.get(i);
            if(nombre.equals("PARTIDO ANIMALISTA CONTRA EL MALTRATO ANIMAL")){
                nombre = "PACMA";
            }
            element = nombre + " (" + (myDataSet.getEntryForXIndex(i).getVal()) + "%)";
            elements.add(element);
        }
        return elements.toArray(new String[elements.size()]);
    }
public boolean isElectionDay() {
    Date today = new Date();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date electionsDate;
    try {
        electionsDate = sdf.parse(fechaElecciones);
    } catch (Exception e) {
        e.printStackTrace();
        electionsDate = null;
    }

    int comparacion = today.compareTo(electionsDate);
    boolean isElectionDay = false;
    if (comparacion >= 0) isElectionDay = true;

    return isElectionDay;
}

    /** public String [] createLegendLines(ChartData<?> mChartData){

     //Datos de alias de partidos.
     com.github.mikephil.charting.data.DataSet myDataSet = mChartData.getDataSetByLabel("",true);

     String element = "";
     List<String> elements = new ArrayList<String>(Arrays.asList(new String[] {}));
     //Creamos cada string
     for (int i = 0;i<myDataSet.getEntryCount(); i++){
     String nombre =(String) myDataSet.getEntryForXIndex(i).getData();
     Log.i("MyTag", nombre);
     if(nombre.equals("PARTIDO ANIMALISTA CONTRA EL MALTRATO ANIMAL")){
     nombre = "PACMA";
     }
     element = nombre + "     ";
     elements.add(element);
     System.out.println(element);
     }
     return elements.toArray(new String[elements.size()]);
     }**/
}
