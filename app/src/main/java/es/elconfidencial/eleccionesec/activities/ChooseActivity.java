package es.elconfidencial.eleccionesec.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by JesúsManuel on 03/09/2015.
 */
public class ChooseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Comprueba si es la primera vez
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        boolean firstTime = prefs.getBoolean("firstTime", true); //Si no existe, devuelve el segundo parametro

        if(firstTime){
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
