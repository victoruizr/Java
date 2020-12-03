package com.example.salas.Vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;


public class addExhibitions extends Fragment implements View.OnClickListener {
    Button bAnadir;
    EditText ID,nombre,descripcion,fIni,fFin;


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exhibitions, container, false);
        ID=view.findViewById(R.id.editTextNumberID);
        nombre=view.findViewById(R.id.editTextTextPersonName5);
        descripcion=view.findViewById(R.id.editTextTextPersonName6);
        fIni=view.findViewById(R.id.editTextDate);
        fFin=view.findViewById(R.id.editTextDate2);
        bAnadir=view.findViewById(R.id.btAddExibition);

        bAnadir.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.btAddExibition:
                try {
                    Ctrl_Exposicion cte = new Ctrl_Exposicion(getContext());
                    if(!compruebaFecha(fIni.getText().toString())||!compruebaFecha(fFin.getText().toString())){
                        Toast.makeText(getContext(), R.string.date, Toast.LENGTH_SHORT).show();
                    }else if(ID.getText().toString()=="" || nombre.getText().toString()==""|| descripcion.getText().toString()=="" || fIni.getText().toString()==""|| fFin.getText().toString()==""){
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_LONG).show();

                    }else {
                        Exposiciones ex = new Exposiciones(Integer.parseInt(ID.getText().toString()), nombre.getText().toString(), descripcion.getText().toString(), fIni.getText().toString(), fFin.getText().toString());
                        long estado = cte.insertarExposicion(ex);
                        if (estado != -1) {
                            Toast.makeText(getContext(), R.string.insertExito, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.insertFallido, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(),R.string.empty,Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),R.string.fail,Toast.LENGTH_LONG).show();
                }
        }
    }
}