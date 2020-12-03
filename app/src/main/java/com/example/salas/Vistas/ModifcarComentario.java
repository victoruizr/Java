package com.example.salas.Vistas;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Comments;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;


public class ModifcarComentario extends Fragment {

    Comentarios comentarios;
    Button bt_modificar;
    private EditText e;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comentarios = (Comentarios) getArguments().getSerializable("coment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifcar_comentario, container, false);
        // Inflate the layout for this fragment
        bt_modificar = view.findViewById(R.id.bt_modificar);
        e = view.findViewById(R.id.editTextTextPersonName24);
        bt_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(e.getText().toString()==""){
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
                    }else{
                        Ctrl_Comments cm = new Ctrl_Comments(getContext());
                        comentarios.setComentario(e.getText().toString());
                        if(cm.modificarComentarios(comentarios)!=-1){
                            Toast.makeText(getContext(),R.string.updateExito,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),R.string.updateFallido,Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Resources.NotFoundException notFoundException) {
                    Toast.makeText(getContext(),R.string.updateFallido,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),R.string.updateFallido,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}