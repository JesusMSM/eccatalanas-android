package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.activities.PoliticianCardActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Politico;


public class PoliticianFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_politician, container, false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.politicians_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context, getSampleArrayList());
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Politico(R.drawable.artur_mas, getString(R.string.artur_mas_nombre), getString(R.string.artur_mas_edad), getString(R.string.artur_mas_partido), getString(R.string.artur_mas_cargo), getString(R.string.artur_mas_perfil)));
        items.add(new Politico(R.drawable.miquel_iceta, getString(R.string.miquel_iceta_nombre), getString(R.string.miquel_iceta_edad), getString(R.string.miquel_iceta_partido), getString(R.string.miquel_iceta_cargo), getString(R.string.miquel_iceta_perfil)));
        items.add(new Politico(R.drawable.antonio_banos, getString(R.string.antonio_banos_nombre), getString(R.string.antonio_banos_edad), getString(R.string.antonio_banos_partido), getString(R.string.antonio_banos_cargo), getString(R.string.antonio_banos_perfil)));
        items.add(new Politico(R.drawable.raul_romeva, getString(R.string.raul_romeva_nombre), getString(R.string.raul_romeva_edad), getString(R.string.raul_romeva_partido), getString(R.string.raul_romeva_cargo), getString(R.string.raul_romeva_perfil)));
        items.add(new Politico(R.drawable.xavier_garcia_albiol, getString(R.string.xavier_garcia_albiol_nombre), getString(R.string.xavier_garcia_albiol_edad), getString(R.string.xavier_garcia_albiol_partido), getString(R.string.xavier_garcia_albiol_cargo), getString(R.string.xavier_garcia_albiol_perfil)));
        items.add(new Politico(R.drawable.ines_arrimadas, getString(R.string.ines_arrimadas_nombre), getString(R.string.ines_arrimadas_edad), getString(R.string.ines_arrimadas_partido), getString(R.string.ines_arrimadas_cargo), getString(R.string.ines_arrimadas_perfil)));
        items.add(new Politico(R.drawable.lluis_rabell, getString(R.string.lluis_rabell_nombre), getString(R.string.lluis_rabell_edad), getString(R.string.lluis_rabell_partido), getString(R.string.lluis_rabell_cargo), getString(R.string.lluis_rabell_perfil)));
        return items;
    }

}
