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
    public ImageView image;
    public TextView title;
    public TextView subtitle;

    public QuizViewHolder(View v) {
        super(v);
        image = (ImageView) v.findViewById(R.id.image);
        title = (TextView) v.findViewById(R.id.title);
        subtitle = (TextView) v.findViewById(R.id.subtitle);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }
}
