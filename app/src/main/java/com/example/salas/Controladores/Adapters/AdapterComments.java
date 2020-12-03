package com.example.salas.Controladores.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.example.salas.Controladores.Ctrl_Comments;
import com.example.salas.Controladores.Ctrl_Trabajos;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.Modelos.Trabajos;
import com.example.salas.R;

import java.util.ArrayList;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.AdaptadorViewHolder> {
    private ArrayList<Comentarios> listComentarios = new ArrayList<>();
    private Context context;


    public AdapterComments(ArrayList<Comentarios> listaCome, Context context) {
        this.listComentarios = listaCome;
        this.context = context;
    }


    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentary, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(item);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        Comentarios comentarios = listComentarios.get(position);
        holder.textView26.setText(String.valueOf(comentarios.getIdExposicion()));
        holder.textView27.setText(comentarios.getNombreTrabajo());
        holder.textView28.setText(comentarios.getComentario());
        holder.buttondelcomentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //String mensaje=context.getString(R.string.confirmacionDelExpo);
                builder.setMessage(R.string.confirmDelete)
                        .setTitle(R.string.delcom);
                builder.setPositiveButton(context.getString(R.string.acept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Ctrl_Comments cc = new Ctrl_Comments(context);
                        if (cc.borrarComentarios(comentarios) != -1) {
                            Toast.makeText(context, R.string.delExito, Toast.LENGTH_LONG).show();
                            listComentarios.remove(position);
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(context, R.string.delExito, Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.bt_updateComentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("coment",comentarios );
                Navigation.findNavController(v).navigate(R.id.action_comentarios_to_modifcarComentario,b);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listComentarios.size();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView textView26, textView27, textView28;
        private Button buttondelcomentary,bt_updateComentary;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textView26 = itemView.findViewById(R.id.textView26);
            textView27 = itemView.findViewById(R.id.textView27);
            textView28 = itemView.findViewById(R.id.textView28);
            buttondelcomentary = itemView.findViewById(R.id.buttondelcomentary);
            bt_updateComentary = itemView.findViewById(R.id.bt_updateComentary);

        }
    }

}
