package es.elconfidencial.eleccionesec.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import es.elconfidencial.eleccionesec.R;

public class PartyCardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_card);

        //Extraemos el intent para leer los par?metros y rellenar los campos
        Intent intent = getIntent();

        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView representantes = (TextView) findViewById(R.id.representantes);
        TextView fundacion = (TextView) findViewById(R.id.fundacion);
        TextView escanos = (TextView) findViewById(R.id.escanos);
        TextView porcentajeVotos = (TextView) findViewById(R.id.porcentajeVotos);
        TextView ideologia = (TextView) findViewById(R.id.ideologia);
        TextView partidosRepresentados = (TextView) findViewById(R.id.partidosRepresentados);
        TextView perfil = (TextView) findViewById(R.id.perfil);

        imagen.setImageResource(intent.getIntExtra("imagen", 0));
        nombre.setText(intent.getStringExtra("nombre"));
        representantes.setText(intent.getStringExtra("representantes"));
        fundacion.setText(intent.getStringExtra("fundacion"));
        escanos.setText(intent.getStringExtra("escanos"));
        porcentajeVotos.setText(intent.getStringExtra("porcentajeVotos"));
        ideologia.setText(intent.getStringExtra("ideologia"));
        partidosRepresentados.setText(intent.getStringExtra("partidosRepresentados"));
        perfil.setText(intent.getStringExtra("perfil"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_party_card, menu);
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
