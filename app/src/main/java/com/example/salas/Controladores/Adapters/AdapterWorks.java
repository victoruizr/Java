package com.example.salas.Controladores.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Comments;
import com.example.salas.Controladores.Ctrl_Exponen;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exponen;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AdapterWorks extends RecyclerView.Adapter<AdapterWorks.AdaptadorViewHolder> {

    private ArrayList<Trabajos> listTrabajos = new ArrayList<>();
    private Context context;

    public AdapterWorks(ArrayList<Trabajos> listTrabajos, Context context) {
        this.listTrabajos = listTrabajos;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trabajos, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(item);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWorks.AdaptadorViewHolder holder, int position) {
        Trabajos trabajos = listTrabajos.get(position);
        holder.textView18.setText(trabajos.getNombreTrabajo());
        holder.textView19.setText(trabajos.getDescripcion());
        holder.textView20.setText(trabajos.getTamanio());
        holder.textView21.setText(trabajos.getPeso());
        Ctrl_Artistas cta = new Ctrl_Artistas(context);
        Artistas art = cta.buscarArtista(trabajos.getDniPasaporte());
        if (art != null) {
            holder.textView22.setText(art.getNombre());
        }

        File path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String[] stringAuxilar = path.getAbsolutePath().split("/");
        String aux = "/" + stringAuxilar[1] + "/" + stringAuxilar[2] + "/" + stringAuxilar[3] + "/" + stringAuxilar[4] + "/" + stringAuxilar[5] + "/" + stringAuxilar[6] + "/" + stringAuxilar[7] + "/";
        String foto = aux + trabajos.getFoto();
        Bitmap bitmap = BitmapFactory.decodeFile(foto);
        holder.img_work.setImageBitmap(bitmap);

        holder.bt_delWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String mensaje = context.getString(R.string.confirmDelete);
                    builder.setMessage(mensaje + trabajos.getNombreTrabajo() + " ?").setTitle(R.string.deleteWork);
                    builder.setPositiveButton(context.getString(R.string.acept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Ctrl_Trabajos ct = new Ctrl_Trabajos(context);
                                Ctrl_Comments cte = new Ctrl_Comments(context);
                                cte.borrarComentariosIdTrabajo(trabajos);
                                if (ct.borrarTrabajoArtistas(trabajos.getNombreTrabajo()) != -1) {
                                    Toast.makeText(context, R.string.delExito, Toast.LENGTH_LONG).show();
                                    listTrabajos.remove(position);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, R.string.delFallido, Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(context, R.string.delfailComentary, Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception e) {
                    //Toast.makeText()
                }
            }
        });
        holder.bt_informationArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ctrl_Artistas ca = new Ctrl_Artistas(context);
                Artistas art = ca.buscarArtista(trabajos.getDniPasaporte());
                if (art != null) {
                    Bundle b = new Bundle();
                    b.putSerializable("artista", art);
                    Navigation.findNavController(v).navigate(R.id.action_nav_works_to_nav_artists,b);

                }else{
                    Toast.makeText(context, R.string.deldatos, Toast.LENGTH_LONG).show();

                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return listTrabajos.size();
    }


    public static class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView textView18, textView19, textView20, textView21, textView22;
        private Button bt_delWork, bt_informationArtist;
        private ImageView img_work;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textView18 = itemView.findViewById(R.id.textView18);
            textView19 = itemView.findViewById(R.id.textView19);
            textView20 = itemView.findViewById(R.id.textView20);
            textView21 = itemView.findViewById(R.id.textView21);
            textView22 = itemView.findViewById(R.id.textView22);
            img_work = itemView.findViewById(R.id.img_work);
            bt_delWork = itemView.findViewById(R.id.bt_delWork);
            bt_informationArtist = itemView.findViewById(R.id.bt_informationArtist);

        }
    }
}
