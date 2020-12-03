package com.example.salas.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Adapters.AdapterWorks;
import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.util.ArrayList;

public class TrabajosFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Trabajos> listaTrabajos = new ArrayList<>();
    Button b1;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AdapterWorks(listaTrabajos,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trabajos, container, false);
        cargarDatos();
        b1=view.findViewById(R.id.b_addWork);
        recyclerView=view.findViewById(R.id.rv_works);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ctrl_Artistas cta = new Ctrl_Artistas(getContext());
                ArrayList<Artistas> art = cta.listarArtistas();
                if(art.size()>0) {
                    Navigation.findNavController(v).navigate(R.id.action_nav_works_to_addWork);
                }else{
                    Toast.makeText(getContext()
                            ,R.string.noArtist,Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    private void cargarDatos() {
        Ctrl_Trabajos ct = new Ctrl_Trabajos(getContext());
        listaTrabajos = ct.listarTrabajos();
    }


}