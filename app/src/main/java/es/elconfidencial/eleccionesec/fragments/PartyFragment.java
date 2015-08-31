package es.elconfidencial.eleccionesec.fragments;

/**
 * Created by MOONFISH on 14/07/2015.
 */
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.HomeActivity;
import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.model.Partido;


public class PartyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_party, container, false);

        //RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.parties_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(HomeActivity.context, getSampleArrayList());
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Partido( R.drawable.cdc, getString(R.string.cdc_nombre), getString(R.string.cdc_representantes), getString(R.string.cdc_fundacion), getString(R.string.cdc_escanos), getString(R.string.cdc_porcentajeVotos), getString(R.string.cdc_ideologia), getString(R.string.cdc_partidosRepresentados), getString(R.string.cdc_perfil)));
        items.add(new Partido( R.drawable.psc, getString(R.string.psc_nombre), getString(R.string.psc_representantes), getString(R.string.psc_fundacion), getString(R.string.psc_escanos), getString(R.string.psc_porcentajeVotos), getString(R.string.psc_ideologia), getString(R.string.psc_partidosRepresentados), getString(R.string.psc_perfil)));
        items.add(new Partido( R.drawable.jps, getString(R.string.cup_nombre), getString(R.string.cup_representantes), getString(R.string.cup_fundacion), getString(R.string.cup_escanos), getString(R.string.cup_porcentajeVotos), getString(R.string.cup_ideologia), getString(R.string.cup_partidosRepresentados), getString(R.string.cup_perfil)));
        items.add(new Partido( R.drawable.jps, getString(R.string.jps_nombre), getString(R.string.jps_representantes), getString(R.string.jps_fundacion), getString(R.string.jps_escanos), getString(R.string.jps_porcentajeVotos), getString(R.string.jps_ideologia), getString(R.string.jps_partidosRepresentados), getString(R.string.jps_perfil)));
        items.add(new Partido( R.drawable.pp, getString(R.string.pp_nombre), getString(R.string.pp_representantes), getString(R.string.pp_fundacion), getString(R.string.pp_escanos), getString(R.string.pp_porcentajeVotos), getString(R.string.pp_ideologia), getString(R.string.pp_partidosRepresentados), getString(R.string.pp_perfil)));
        items.add(new Partido( R.drawable.udc, getString(R.string.udc_nombre), getString(R.string.udc_representantes), getString(R.string.udc_fundacion), getString(R.string.udc_escanos), getString(R.string.udc_porcentajeVotos), getString(R.string.udc_ideologia), getString(R.string.udc_partidosRepresentados), getString(R.string.udc_perfil)));
        items.add(new Partido( R.drawable.cs, getString(R.string.cs_nombre), getString(R.string.cs_representantes), getString(R.string.cs_fundacion), getString(R.string.cs_escanos), getString(R.string.cs_porcentajeVotos), getString(R.string.cs_ideologia), getString(R.string.cs_partidosRepresentados), getString(R.string.cs_perfil)));
        items.add(new Partido( R.drawable.csqep, getString(R.string.csqep_nombre), getString(R.string.csqep_representantes), getString(R.string.csqep_fundacion), getString(R.string.csqep_escanos), getString(R.string.csqep_porcentajeVotos), getString(R.string.csqep_ideologia), getString(R.string.csqep_partidosRepresentados), getString(R.string.csqep_perfil)));
        return items;
    }
}
