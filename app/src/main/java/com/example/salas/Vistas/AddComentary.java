package com.example.salas.Vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Comments;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.util.ArrayList;
import java.util.Iterator;


public class AddComentary extends Fragment implements AdapterView.OnItemSelectedListener {
    Button b1;
    EditText e1;
    Spinner spinner1, spinner2;
    private ArrayList<String> listaIdExposicion;
    private ArrayList<Exposiciones> listaIdExistentes;
    private ArrayList<String> listaNombre;
    private ArrayList<Trabajos> listaNomExistentes;
    private ArrayAdapter<String> adaptadorSpinner1;
    private ArrayAdapter<String> adaptadorSpinner2;
    private int idExposicion;
    private String nombreTrabajo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_comentary, container, false);

        spinner1 = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);
        b1 = view.findViewById(R.id.button_Comentary);
        e1 = view.findViewById(R.id.editTextTextPersonName23);
        listaIdExposicion = new ArrayList<>();
        listaNombre = new ArrayList<>();
        Ctrl_Exposicion cte = new Ctrl_Exposicion(getContext());
        listaIdExistentes = cte.listarExposiciones();

        for (Iterator it = listaIdExistentes.iterator(); it.hasNext(); ) {
            Exposiciones expo = (Exposiciones) it.next();
            listaIdExposicion.add(String.valueOf(expo.getIdExposicion()));
        }
        adaptadorSpinner1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaIdExposicion);
        spinner1.setAdapter(adaptadorSpinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        Ctrl_Trabajos ct = new Ctrl_Trabajos(getContext());
        listaNomExistentes = ct.listarTrabajos();

        for (Iterator it = listaNomExistentes.iterator(); it.hasNext(); ) {
            Trabajos tra = (Trabajos) it.next();
            listaNombre.add(tra.getNombreTrabajo());
        }
        adaptadorSpinner2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listaNombre);
        spinner2.setAdapter(adaptadorSpinner2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Ctrl_Comments cc = new Ctrl_Comments(getContext());

                    Comentarios comentarios = new Comentarios(idExposicion, nombreTrabajo, e1.getText().toString());
                    //Toast.makeText(getContext(), "El " + idExposicion + " El " + nombreTrabajo, Toast.LENGTH_SHORT).show();
                    if (String.valueOf(idExposicion)==""|| nombreTrabajo=="" || e1.getText().toString()=="") {
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
                    } else if (cc.insertarComentarios(comentarios) != -1) {
                        Toast.makeText(getContext(), R.string.insertExito, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.insertFallido, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.fail, Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner:
                try {
                    idExposicion = Integer.parseInt(listaIdExposicion.get(position));
                }catch(Exception e){

                }

            case R.id.spinner2:
                try {
                    nombreTrabajo = listaNombre.get(position);
                }catch(Exception e){}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}