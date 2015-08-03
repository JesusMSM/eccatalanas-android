package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;

import es.elconfidencial.eleccionesec.adapters.MyRecyclerViewAdapter;
import es.elconfidencial.eleccionesec.R;
import es.elconfidencial.eleccionesec.adapters.ViewPagerAdapter;
import es.elconfidencial.eleccionesec.model.Noticia;
import es.elconfidencial.eleccionesec.model.Quiz;
import es.elconfidencial.eleccionesec.slidingtabfiles.SlidingTabLayout;


public class HomeActivity extends ActionBarActivity {

    public static Context context;
    ActionBar actionBar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    int numbOfTabs =4;
    CharSequence[] Titles = new CharSequence[numbOfTabs];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.context = getApplicationContext();

      /*  actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar_color))); //Para que el color quede uniforme
        actionBar.setDisplayShowTitleEnabled(false); //Sin titulo*

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
        actionBar.setDisplayShowCustomEnabled(true);*/


        //Titulos, da igual lo que se ponga pero tienen que existir aunque no se vayan a ver despues
        Titles[0] = "HOME";
        Titles[1] = "CHART";
        Titles[2] = "DOC";
        Titles[3] = "RSS";

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,numbOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.actionBar_color);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setCustomTabView(R.layout.custom_actionbar, 0);
        tabs.setViewPager(pager);
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


}
