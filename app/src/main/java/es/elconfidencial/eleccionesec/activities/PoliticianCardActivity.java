package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

public class PoliticianCardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_card);

        //Extraemos el intent para leer los par?metros y rellenar los campos
        Intent intent = getIntent();

        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView edad = (TextView) findViewById(R.id.edad);
        TextView partido = (TextView) findViewById(R.id.partido);
        TextView cargo = (TextView) findViewById(R.id.cargo);
        TextView perfil = (TextView) findViewById(R.id.perfil);

        nombre.setText(intent.getStringExtra("nombre"));
        edad.setText(intent.getStringExtra("edad"));
        partido.setText(intent.getStringExtra("partido"));
        cargo.setText(intent.getStringExtra("cargo"));
        perfil.setText(intent.getStringExtra("perfil"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_politician_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
