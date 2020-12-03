package com.example.salas.Controladores.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Exponen;
import com.example.salas.Controladores.Ctrl_Exposicion;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exponen;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.R;

import java.util.ArrayList;
import java.util.Iterator;

public class AdapterExhibition extends RecyclerView.Adapter<AdapterExhibition.AdaptadorViewHolder> {

    private ArrayList<Exposiciones> listExpo = new ArrayList<>();
    private Context context;
    private Artistas artistas;
    private boolean estado;
    ArrayList<Artistas> listaArtistasExponen = new ArrayList<>();
    public AdapterExhibition(ArrayList<Exposiciones> listExpo, Context context,Artistas artis, boolean estado) {
        this.listExpo = listExpo;
        this.context = context;
        this.artistas=artis;
        this.estado=estado;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exibiciones, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(item);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExhibition.AdaptadorViewHolder holder, int position) {
        Exposiciones expo = listExpo.get(position);

        holder.tx_idExpo.setText(String.valueOf(expo.getIdExposicion()));
        holder.tx_nombreExpo.setText(expo.getNombreExp());
        holder.tx_descripcion.setText(expo.getDescripcion());
        holder.tx_fechaIncio.setText(expo.getFechaInicio());
        holder.tx_fechaFin.setText(expo.getFechaFin());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String mensaje = context.getString(R.string.confirmDelete);
                    builder.setMessage(mensaje + expo.getNombreExp()+" ?").setTitle(R.string.bt_delExpo);
                    builder.setPositiveButton(context.getString(R.string.acept), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Ctrl_Exposicion f = new Ctrl_Exposicion(context);
                                    Ctrl_Exponen z = new Ctrl_Exponen(context);
                                    z.borrarExponen(String.valueOf(expo.getIdExposicion()),context);

                                    if (f.borrarExposicion(expo,context) != -1 ) {
                                        Toast.makeText(context,R.string.delExito,Toast.LENGTH_LONG).show();
                                        listExpo.remove(position);
                                        notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(context,R.string.delFallido,Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show();

                }
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("expo",expo);
                Navigation.findNavController(v).navigate(R.id.action_nav_exhibitions_to_actuzalizar_exposicion,b);
            }
        });
        holder.bt_participarArtista.setEnabled(estado);
        holder.bt_participarArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                        Ctrl_Exponen ctex=new Ctrl_Exponen(context);
                        Exponen ex = new Exponen(expo.getIdExposicion(),artistas.getDniPasaporte());
                        if(ctex.insertarExponen(ex)!=-1){
                            Toast.makeText(context,R.string.insertExito,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,R.string.insertFallido,Toast.LENGTH_LONG).show();
                        }
               } catch (Exception e) {
                    Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show();
               }
            }
        });
        holder.bt_viewArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ctrl_Exponen cte = new Ctrl_Exponen(context);
                Ctrl_Artistas cta = new Ctrl_Artistas(context);
                ArrayList<String> dni=cte.obtenerArtistasExponen(expo);
                for(Iterator it=dni.iterator();it.hasNext();){
                    String idArtista = (String) it.next();
                    Artistas arti = cta.buscarArtista(idArtista);

                    listaArtistasExponen.add(arti);
                }
                if(listaArtistasExponen.size()>0){
                    Bundle b = new Bundle();
                    b.putSerializable("listaArtistasExponen",listaArtistasExponen);
                    Navigation.findNavController(v).navigate(R.id.action_nav_exhibitions_to_nav_artists,b);
                }else{
                    Toast.makeText(context,R.string.array,Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.buttonComentaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("Exposicion",expo);

                Ctrl_Trabajos ct = new Ctrl_Trabajos(context);
                Ctrl_Artistas ca = new Ctrl_Artistas(context);
                Ctrl_Exponen ce = new Ctrl_Exponen(context);
                Ctrl_Exposicion cex = new Ctrl_Exposicion(context);
                Exposiciones expos = cex.obtenerExposicionId(expo.getIdExposicion());
                ArrayList<String> listEx = ce.obtenerArtistasExponen(expos);
                ArrayList<Trabajos> listaWorks = new ArrayList<>();
                for(Iterator it = listEx.iterator();it.hasNext();){
                    String dniPas = (String) it.next();
                    Trabajos work = ct.obtenerTrabajos(dniPas);
                    listaWorks.add(work);
                }

                if(listaWorks.size()>0){
                    b.putSerializable("listaWorks",listaWorks);
                    Navigation.findNavController(v).navigate(R.id.action_nav_exhibitions_to_comentarios,b);
                }else{
                    Toast.makeText(context,R.string.noWork,Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


    @Override
    public int getItemCount() {
        return listExpo.size();
    }


    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView tx_idExpo, tx_nombreExpo,  tx_descripcion, tx_fechaIncio, tx_fechaFin;
        private Button del, update,bt_participarArtista,bt_viewArtists,buttonComentaries;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_idExpo = itemView.findViewById(R.id.tx_idExposicion);
            tx_nombreExpo = itemView.findViewById(R.id.tx_nombreExpo);
            tx_descripcion = itemView.findViewById(R.id.tx_descripcion);
            tx_fechaIncio = itemView.findViewById(R.id.tx_fecInicio);
            tx_fechaFin = itemView.findViewById(R.id.tx_populationArtista);
            del = itemView.findViewById(R.id.bt_borrarExpo);
            update = itemView.findViewById(R.id.bt_modExpo);
            bt_participarArtista=itemView.findViewById(R.id.bt_participarArtista);
            bt_viewArtists=itemView.findViewById(R.id.bt_viewArtists);
            buttonComentaries = itemView.findViewById(R.id.buttonComentaries);

        }
    }
}
