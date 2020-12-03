package com.example.salas.Controladores.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salas.Controladores.Ctrl_Artistas;
import com.example.salas.Controladores.Ctrl_Exponen;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exponen;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterArtist extends RecyclerView.Adapter<AdapterArtist.AdaptadorViewHolder> {


    private ArrayList<Artistas> listArtis = new ArrayList<>();
    private Context context;

    public AdapterArtist(ArrayList<Artistas> listArtis, Context context) {
        this.listArtis = listArtis;
        this.context = context;

    }


    @NonNull
    @Override
    public AdapterArtist.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artistas, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(itemView);

        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        Artistas artist = listArtis.get(position);
        holder.tx_dniPasaporte.setText(artist.getDniPasaporte());
        holder.tx_nameArtista.setText(artist.getNombre());
        holder.tx_adressArtista.setText(artist.getDireccion());
        holder.tx_populationArtista.setText(artist.getPoblacion());
        holder.tx_provinceArtista.setText(artist.getProvincia());
        holder.tx_CountryArtista.setText(artist.getPais());
        holder.tx_mobileWorkArtista.setText(artist.getMovilTrabajo());
        holder.tx_PersonalMobileArtista.setText(artist.getMovilPersonal());
        holder.tx_fijoArtista.setText(artist.getTelefonoFijo());
        holder.tx_WebBlogArtista.setText(artist.getWebBlog());
        holder.tx_CumpleFecha.setText(artist.getFechaNacimiento());
        holder.bt_borrarArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String mensaje = context.getString(R.string.confirmDelete);
                builder.setMessage(mensaje + artist.getNombre() + " ?").setTitle(R.string.bDeleteArtist);
                builder.setPositiveButton(context.getString(R.string.acept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Ctrl_Artistas cart = new Ctrl_Artistas(context);
                        Ctrl_Exponen z = new Ctrl_Exponen(context);

                        z.borrarExponenDni(artist.getDniPasaporte(),context);
                        if (cart.borrarArtistas(artist,context) != -1) {
                            Toast.makeText(context, R.string.delExito, Toast.LENGTH_LONG).show();
                            listArtis.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, R.string.delFallido, Toast.LENGTH_LONG).show();
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
            }
        });
        holder.bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("arti", artist);
                Navigation.findNavController(v).navigate(R.id.action_nav_artists_to_updateArtist, b);

            }
        });
        holder.bt_participar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("artis", artist);
                b.putSerializable("estado", true);
                Navigation.findNavController(v).navigate(R.id.action_nav_artists_to_nav_exhibitions, b);
            }
        });
        holder.b_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("artis", artist);
                Navigation.findNavController(v).navigate(R.id.action_nav_artists_to_email, b);

            }
        });
        holder.b_PhonePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.setData(Uri.parse("tel:" + artist.getMovilPersonal()));
                    context.startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(context,R.string.PohoneFail,Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.b_phoneWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.setData(Uri.parse("tel:" + artist.getMovilTrabajo()));
                    context.startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(context,R.string.PohoneFail,Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.b_landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.setData(Uri.parse("tel:" + artist.getTelefonoFijo()));
                    context.startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(context,R.string.PohoneFail,Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.b_PhonePersonal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formato2=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date hoy = new Date();

                    String fecha1;
                    String[] auxFecha1=artist.getFechaNacimiento().split("\\/");
                    fecha1=auxFecha1[2]+"-"+auxFecha1[1]+"-"+auxFecha1[0];

                    String fecha2=formato.format(hoy);
                    String[] auxFecha2 =fecha2.split("\\/");
                    fecha2=auxFecha2[2]+"-"+auxFecha2[1]+"-"+auxFecha2[0];

                    Date fechaDate=formato2.parse(fecha1);
                    Date fechaActu=formato2.parse(fecha2);

                    if(fechaDate.equals(fechaActu)){
                        String correo=artist.getEmail();
                        String [] arrayEmail=correo.split(",");
                        String titulo="Feliz cumpleaños "+artist.getNombre();
                        String mensaje="Cumpleaños feliz "+artist.getNombre();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL,arrayEmail);
                        intent.putExtra(Intent.EXTRA_SUBJECT,titulo);
                        intent.putExtra(Intent.EXTRA_TEXT,mensaje);
                        intent.setType("message/rfc822");
                        context.startActivity(Intent.createChooser(intent,"Eligue el cliente de email"));
                    }else{
                        Toast.makeText(context,"No es su cumpleaños",Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException e) {

                    Toast.makeText(context,"Error al felicitar al artista",Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return listArtis.size();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView tx_dniPasaporte, tx_nameArtista, tx_adressArtista,
                tx_populationArtista, tx_provinceArtista, tx_CountryArtista, tx_mobileWorkArtista,
                tx_PersonalMobileArtista, tx_fijoArtista, tx_WebBlogArtista, tx_CumpleFecha;
        private Button bt_borrarArtist, bt_update, bt_participar,b_email,b_PhonePersonal,b_phoneWork,b_landline,b_PhonePersonal2;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_dniPasaporte = itemView.findViewById(R.id.tx_dniPasaporte);
            tx_nameArtista = itemView.findViewById(R.id.tx_nameArtista);
            tx_adressArtista = itemView.findViewById(R.id.tx_adressArtista);
            tx_populationArtista = itemView.findViewById(R.id.tx_populationArtista);
            tx_provinceArtista = itemView.findViewById(R.id.tx_provinceArtista);
            tx_CountryArtista = itemView.findViewById(R.id.tx_CountryArtista);
            tx_mobileWorkArtista = itemView.findViewById(R.id.tx_mobileWorkArtista);
            tx_PersonalMobileArtista = itemView.findViewById(R.id.tx_PersonalMobileArtista);
            tx_WebBlogArtista = itemView.findViewById(R.id.tx_WebBlogArtista);
            tx_fijoArtista = itemView.findViewById(R.id.tx_fijoArtista);
            tx_CumpleFecha = itemView.findViewById(R.id.tx_CumpleFecha);

            bt_borrarArtist = itemView.findViewById(R.id.bt_borrarArtist);
            bt_update = itemView.findViewById(R.id.bt_update);
            bt_participar = itemView.findViewById(R.id.bt_participar);
            b_email=itemView.findViewById(R.id.b_email);
            b_PhonePersonal=itemView.findViewById(R.id.b_PhonePersonal);
            b_phoneWork=itemView.findViewById(R.id.b_phoneWork);
            b_landline=itemView.findViewById(R.id.b_landline);
            b_PhonePersonal2=itemView.findViewById(R.id.b_PhonePersonal2);
        }
    }

}
