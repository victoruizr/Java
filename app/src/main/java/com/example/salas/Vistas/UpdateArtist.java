package com.example.salas.Vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

public class UpdateArtist extends Fragment implements View.OnClickListener {
    Artistas art;
    private Button b1;
    private EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_artist, container, false);


        e1 = view.findViewById(R.id.editTextTextPersonName30);
        e2 = view.findViewById(R.id.editTextTextPersonName31);
        e3 = view.findViewById(R.id.editTextTextPersonName32);
        e4 = view.findViewById(R.id.editTextTextPersonName33);
        e5 = view.findViewById(R.id.editTextTextPersonName34);
        e6 = view.findViewById(R.id.editTextTextPersonName35);
        e7 = view.findViewById(R.id.editTextTextPersonName36);
        e8 = view.findViewById(R.id.editTextTextPersonName37);
        e9 = view.findViewById(R.id.editTextTextPersonName38);
        e10 = view.findViewById(R.id.editTextTextPersonName39);
        e11 = view.findViewById(R.id.editTextTextPersonName40);
        e12 = view.findViewById(R.id.editTextDate4);
        // Inflate the layout for this fragment
        b1 = view.findViewById(R.id.bUpdateArtista);
        b1.setOnClickListener(this);
        if (getArguments() != null) {
            art = (Artistas) getArguments().getSerializable("arti");
        }

        e1.setText(art.getDniPasaporte());
        e2.setText(art.getNombre());
        e3.setText(art.getDireccion());
        e4.setText(art.getPoblacion());
        e5.setText(art.getProvincia());
        e6.setText(art.getPais());
        e7.setText(art.getMovilTrabajo());
        e8.setText(art.getMovilPersonal());
        e9.setText(art.getTelefonoFijo());
        e10.setText(art.getEmail());
        e11.setText(art.getWebBlog());
        e12.setText(art.getFechaNacimiento());


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


    private boolean compruebaTelefono(String text) {
        String number;
        boolean estado = false;
        if (text.matches("[0-9]+") && text.length() == 9) {
            estado = true;
        } else {
            estado = false;
        }
        return estado;
    }

    private boolean compruebaEmail(String email) {
        boolean estado = false;
        if (email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
            estado = true;
        }
        return estado;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUpdateArtista:
                try {
                    Ctrl_Artistas cte = new Ctrl_Artistas(getContext());
                    if (e1.getText().toString() == "" || e2.getText().toString() == "" || e3.getText().toString() == "" || e4.getText().toString() == "" || e5.getText().toString() == "" || e6.getText().toString() == "" || e7.getText().toString() == "" || e8.getText().toString() == "" || e9.getText().toString() == "" || e10.getText().toString() == "" || e11.getText().toString() == "" || e12.getText().toString() == "") {
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_LONG).show();
                    } else if (!compruebaFecha(e12.getText().toString())) {
                        Toast.makeText(getContext(), R.string.date, Toast.LENGTH_SHORT).show();
                    } else if (!compruebaTelefono(e7.getText().toString()) || !compruebaTelefono(e8.getText().toString()) || !compruebaTelefono(e9.getText().toString())) {
                        Toast.makeText(getContext(), R.string.phonenovaild, Toast.LENGTH_LONG).show();
                    } else if (!compruebaEmail(e10.getText().toString())) {
                        Toast.makeText(getContext(), R.string.emailnotvalid, Toast.LENGTH_SHORT).show();
                    } else {
                        Artistas art = new Artistas(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), e6.getText().toString(), e7.getText().toString(), e8.getText().toString(), e9.getText().toString(), e10.getText().toString(), e11.getText().toString(), e12.getText().toString());
                        if (cte.modificarArtistas(art) != -1) {
                            Toast.makeText(getContext(), R.string.updateExito, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.updateFallido, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), R.string.empty, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.fail, Toast.LENGTH_LONG).show();
                }
        }
    }
}