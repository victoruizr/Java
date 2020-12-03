package com.example.salas.Vistas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Adapters.AdapterArtist;
import com.example.salas.Controladores.Adapters.AdapterExhibition;
import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;

public class ArtistasFragment extends Fragment implements View.OnClickListener {
    Button b1;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Artistas> listArtistas = new ArrayList<>();
    private Artistas artista;
    private boolean estado=false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artistas, container, false);

        if(!estado){
            cargarDatosArtistas();
        }

        rv = view.findViewById(R.id.rvArtista);
        b1 = view.findViewById(R.id.bAddArtist);
        b1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listArtistas=new ArrayList<>();

        if(getArguments()!=null){
            listArtistas= (ArrayList<Artistas>) getArguments().getSerializable("listaArtistasExponen");
            if(listArtistas==null){
                artista = (Artistas) getArguments().getSerializable("artista");
                if(artista!=null){
                    listArtistas=new ArrayList<>();
                    listArtistas.add(artista);
                }
            }
            estado=true;
        }else{
           cargarDatosArtistas();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        adapter = new AdapterArtist(listArtistas, getActivity());
        rv.setAdapter(adapter);
    }

    private void cargarDatosArtistas() {
        Ctrl_Artistas list = new Ctrl_Artistas(getContext());
        listArtistas = list.listarArtistas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAddArtist:
                Navigation.findNavController(v).navigate(R.id.action_nav_artists_to_addArtistas);
        }
    }
}