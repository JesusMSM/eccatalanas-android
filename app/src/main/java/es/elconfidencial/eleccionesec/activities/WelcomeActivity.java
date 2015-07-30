package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.elconfidencial.eleccionesec.R;

/**
 * Created by JesúsManuel on 30/07/2015.
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /** Called when the user clicks the Start button */
    public void start(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }
}
