package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class PartidoViewHolder extends RecyclerView.ViewHolder{

    public TextView nombre, presidente,secGeneral, portavozCongreso, portavozSenado, fundacion, ideologia, posicionEspecto, sede;
    public ImageView imagen;

    public PartidoViewHolder(View v) {
        super(v);
        nombre = (TextView) v.findViewById(R.id.nombre);
        presidente = (TextView) v.findViewById(R.id.presidente);
        secGeneral = (TextView) v.findViewById(R.id.secGeneral);
        portavozCongreso = (TextView) v.findViewById(R.id.portavozCongreso);
        portavozSenado = (TextView) v.findViewById(R.id.portavozSenado);
        fundacion = (TextView) v.findViewById(R.id.fundacion);
        ideologia = (TextView) v.findViewById(R.id.ideologia);
        posicionEspecto = (TextView) v.findViewById(R.id.posicionEspectro);
        sede = (TextView) v.findViewById(R.id.sede);
        imagen = (ImageView) v.findViewById(R.id.imagen);
    }
}
