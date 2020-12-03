package com.example.salas.Vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;

public class actuzalizar_exposicion extends Fragment implements View.OnClickListener {
    Exposiciones expo;
    EditText text1, text2, text3, text4, text5;
    Button btUpdateExibition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expo = (Exposiciones) getArguments().getSerializable("expo");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actuzalizar_exposicion, container, false);
        text1 = view.findViewById(R.id.editTextTextPersonName2);
        text2 = view.findViewById(R.id.editTextTextPersonName5);
        text3 = view.findViewById(R.id.editTextTextPersonName6);
        text4 = view.findViewById(R.id.editTextTextPersonName7);
        text5 = view.findViewById(R.id.editTextTextPersonName8);
        btUpdateExibition = view.findViewById(R.id.btUpdateExibition);
        btUpdateExibition.setOnClickListener(this);
        text1.setText(String.valueOf(expo.getIdExposicion()));
        text2.setText(expo.getNombreExp());
        text3.setText(expo.getDescripcion());
        text4.setText(expo.getFechaInicio());
        text5.setText(expo.getFechaFin());

        // Inflate the layout for this fragment
        return view;
    }

    private boolean compruebaFecha(String fecha) {
        String[] fechaAux = fecha.split("/");
        int dia = Integer.parseInt(fechaAux[0]);
        int mes = Integer.parseInt(fechaAux[1]);
        if (dia >= 0 && dia <= 31 && mes >= 1 && mes <= 12 && fecha.length() == 10) {
            return true;
        } else {
            return false;

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btUpdateExibition:
                try {
                    Ctrl_Exposicion cte = new Ctrl_Exposicion(getContext());
                    if (!compruebaFecha(text4.getText().toString()) || !compruebaFecha(text5.getText().toString())) {
                        Toast.makeText(getContext(), R.string.date, Toast.LENGTH_SHORT).show();
                    } else if(text1.getText().toString()==""|| text2.getText().toString()==""|| text3.getText().toString()==""|| text4.getText().toString()==""|| text5.getText().toString()==""){
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_LONG).show();

                    }else {
                        Exposiciones ex = new Exposiciones(Integer.parseInt(text1.getText().toString()), text2.getText().toString(), text3.getText().toString(), text4.getText().toString(), text5.getText().toString());
                        if (cte.modificarExposicion(ex) != -1) {
                            Toast.makeText(getContext(), R.string.updateExito, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.updateFallido, Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), R.string.failUpdate, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.fail, Toast.LENGTH_LONG).show();
                }
        }
    }


}