package com.example.salas.Vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static android.app.Activity.RESULT_OK;

public class addWork extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinerArtistas;
    private Button b1,b3;
    private EditText nombreImagen,e1,e2,e3,e4;
    private ArrayList<String> listaDniArtistas;
    private ArrayList<Artistas> listaArtistasExistentes;
    private ArrayAdapter<String> adaptadorSpinner;
    private String dniArtistaTrabajo;
    private ImageView imageView;
    private Bitmap bitmap;
    private Uri UriImage;
    private final int ACT_GALLERY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);

        spinerArtistas = view.findViewById(R.id.spinnerArtistas);
        spinerArtistas.setOnItemSelectedListener(this);
        e1 = view.findViewById(R.id.editTextTextPersonName18);
        e2 = view.findViewById(R.id.editTextTextPersonName19);
        e3 = view.findViewById(R.id.editTextTextPersonName20);
        e4 = view.findViewById(R.id.editTextTextPersonName21);

        nombreImagen=view.findViewById(R.id.editTextTextPersonName22);
        b1 = view.findViewById(R.id.bImageGallery);
        b3 = view.findViewById(R.id.button_crearExposicion);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,ACT_GALLERY);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File ruta = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                //Toast.makeText(getContext(), ruta.getAbsolutePath(), Toast.LENGTH_LONG).show();

                File fichero = new File(ruta + nombreImagen.getText().toString() + ".png");

                try {
                    FileOutputStream os = new FileOutputStream(fichero);
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG,50,os);
                    os.flush();
                    os.close();

                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(),R.string.fileNotFound,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(),R.string.failImage,Toast.LENGTH_SHORT).show();
                }catch (ClassCastException cle){
                    Toast.makeText(getContext(),R.string.failImage,Toast.LENGTH_SHORT).show();
                }
                try {
                    Ctrl_Trabajos ct= new Ctrl_Trabajos(getContext());

                    if(e1.getText().toString()==""|| e2.getText().toString()=="" ||e3.getText().toString()==""||e4.getText().toString()==""|| dniArtistaTrabajo==""||  nombreImagen.getText().toString()==""){
                        Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
                    }else {
                        Trabajos trabajo = new Trabajos(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), dniArtistaTrabajo, "Pictures" + nombreImagen.getText().toString() + ".png");
                        if (ct.insertarTrabajos(trabajo) != -1) {
                            Toast.makeText(getContext(), R.string.insertExito, Toast.LENGTH_SHORT).show();
                        }
                    }/*else{
                        Toast.makeText(getContext(),R.string.insertFallido,Toast.LENGTH_SHORT).show();
                    }*/
                } catch (Exception e) {
                    Toast.makeText(getContext(),R.string.existingWork,Toast.LENGTH_SHORT).show();

                }
            }
        });
        imageView=view.findViewById(R.id.imageView2);
        listaDniArtistas = new ArrayList<>();
        Ctrl_Artistas cta = new Ctrl_Artistas(getContext());
        listaArtistasExistentes = cta.listarArtistas();
        for (Iterator it = listaArtistasExistentes.iterator(); it.hasNext(); ) {
            Artistas art = (Artistas) it.next();
            listaDniArtistas.add(art.getDniPasaporte());
        }
        adaptadorSpinner= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,listaDniArtistas);
        spinerArtistas.setAdapter(adaptadorSpinner);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACT_GALLERY && resultCode == RESULT_OK){
            UriImage = data.getData();
            imageView.setImageURI(UriImage);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case  R.id.spinnerArtistas:
                dniArtistaTrabajo = listaDniArtistas.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}