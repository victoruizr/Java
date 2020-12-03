package com.example.salas.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Adapters.AdapterExhibition;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;

public class ExposicionesFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    Artistas artistas;
    Boolean estado;
    private ArrayList<Exposiciones> listExposiciones = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artistas = (Artistas) getArguments().getSerializable("artis");
            estado = (Boolean) getArguments().getSerializable("estado");
        }else{
            artistas = new Artistas();
            estado =false;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exposiciones, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        cargarExpo();
        rv = root.findViewById(R.id.recicleViewExibiciones);
        Button bAd = root.findViewById(R.id.bAnadirExposicion);
        bAd.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        adapter = new AdapterExhibition(listExposiciones, getActivity(),artistas,estado);
        rv.setAdapter(adapter);
    }

    public void cargarExpo() {
        Ctrl_Exposicion list = new Ctrl_Exposicion(getContext());
        listExposiciones = list.listarExposiciones();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAnadirExposicion:
                Navigation.findNavController(v).navigate(R.id.action_nav_exhibitions_to_addExhibitions);
                break;
        }
    }
}