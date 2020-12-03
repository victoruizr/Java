package com.example.salas.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Adapters.AdapterComments;
import com.example.salas.Controladores.Ctrl_Comments;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.util.ArrayList;

public class ComentariosFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Exposiciones expos;
    Button b1;
    private ArrayList<Comentarios> listaCome= new ArrayList<>();
    private ArrayList<Trabajos> listaTrabajos = new ArrayList<>();
    private  Exposiciones expo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listaCome=new ArrayList<>();
        if(getArguments()!=null){
            listaTrabajos=(ArrayList) getArguments().getSerializable("listaWorks");
            expos = (Exposiciones) getArguments().getSerializable("Exposicion");
            cargarDatosComentarios();
        }else {
            cargarDatosComentarios();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AdapterComments(listaCome,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_comentarios, container, false);
        recyclerView = view.findViewById(R.id.rv_comentarios);
        b1 = view.findViewById(R.id.buttonAddComentary);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_comentarios_to_addComentary);
            }
        });

        return view;

    }

    private void cargarDatosComentarios() {
        Ctrl_Comments list = new Ctrl_Comments(getContext());
        if(expos!=null){
            listaCome = list.listarComentariosId(expos.getIdExposicion());
        }else{
            listaCome = list.listarComentarios();
        }

    }
}
