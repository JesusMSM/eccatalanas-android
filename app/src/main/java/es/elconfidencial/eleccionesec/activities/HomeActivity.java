package es.elconfidencial.eleccionesec.activities;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.elconfidencial.eleccionesec.activities.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.activities.model.Noticia;
import es.elconfidencial.eleccionesec.activities.model.Quiz;


public class HomeActivity extends ActionBarActivity {

    public static Context context;
    ActionBar actionBar;
    TextView tiempo;
    TextView label;

    //RecyclerView atributtes
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.context = getApplicationContext();

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar_color))); //Para que el color quede uniforme
        actionBar.setDisplayShowTitleEnabled(false); //Sin titulo

        //Cargamos un customView para la action bar, es necesario si quieres distribuir uniformemente los botones
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        //Codigo para manejar los clicks en la action bar
        ImageButton homeButton = (ImageButton) mCustomView
                .findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Home Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        //Cargamos el customView
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);

        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(this, getSampleArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
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
