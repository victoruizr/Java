package com.example.salas.Preferencia;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.salas.R;


public class Preferencias extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias_de_usuario);
    }
}
