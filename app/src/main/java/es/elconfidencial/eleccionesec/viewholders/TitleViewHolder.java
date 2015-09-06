package es.elconfidencial.eleccionesec.viewholders;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by MOONFISH on 04/09/2015.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public LinearLayout link;
    public TitleViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.title);
        link = (LinearLayout) v.findViewById(R.id.title_layout);

    }

}