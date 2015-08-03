package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by JesúsManuel on 14/07/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Quiz;


public class HomeTab extends Fragment {

    TextView tiempo;
    TextView label;

    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "HomeTab";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_home,container,false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context, getSampleArrayList());
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add("contador");
        items.add(new Noticia("¿Qué pasará en Grecia el día después del referendum?", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. ", "AUTOR", R.drawable.imagequiz));
        items.add(new Noticia("¿Qué pasará en Grecia el día después del referendum?", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", "AUTOR", R.drawable.imagequiz));
        items.add(new Noticia("¿Qué pasará en Grecia el día después del referendum?", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", "AUTOR", R.drawable.imagequiz));
        items.add(new Quiz(R.drawable.imagequiz,"Quiz 1","Descripcion 1"));
        items.add(new Quiz(R.drawable.imagequiz,"Quiz 2","Descripcion 2"));
        return items;
    }
}
