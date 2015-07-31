package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class PreferencesActivity extends PreferenceActivity {

    ImageView header;
    Context context;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.activity_preferences);

        header = (ImageView) findViewById(R.id.imageView);
        header.setImageResource(R.drawable.preferencias);

        //Creo el boton programaticamente, porque en el layout da problemas con la lista
        button = new Button(this);
        button.setText("Guardar");
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
            }});


        addPreferencesFromResource(R.xml.prefs);

        ListView v = getListView();
        v.addFooterView(button);
    }

}
