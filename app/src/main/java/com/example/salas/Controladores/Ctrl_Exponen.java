package com.example.salas.Controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.salas.Controladores.BD_Salas;
import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Exponen;
import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_Exponen extends BD_Salas {

    public Ctrl_Exponen(@Nullable Context context) {
        super(context);
    }
    public long insertarExponen(Exponen exp){
        long nreg_afectados=-1;
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("idExposicion",exp.getIdExposicion());
            valores.put("dniPasaporte",exp.getDniPasaporte());
            nreg_afectados=db.insert("Exponen",null,valores);

        }
        db.close();
        return nreg_afectados;
    }
    public ArrayList obtenerArtistasExponen(Exposiciones e){
        SQLiteDatabase db=getReadableDatabase();
        List<String> listaArtistas=new ArrayList<>();
        if(db!=null){
            String[] campos = {"IdExposicion","dniPasaporte"};
            Cursor c = db.query("Exponen",campos,"IdExposicion="+e.getIdExposicion(),null,null,null,null);
            if(c.moveToFirst()){
                do{
                    listaArtistas.add(c.getString(1));
                }while(c.moveToNext());
            }
            c.close();
        }
        db.close();
        return (ArrayList) listaArtistas;
    }
    public long borrarExponen(String idExposicion, Context context){
        long nreg_afectados=-1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            if(db!=null){
                nreg_afectados=db.delete("Exponen","idExposicion='"+idExposicion+"'",null);
            }
            db.close();

        } catch (Exception e) {
            Toast.makeText(context, R.string.fail,Toast.LENGTH_LONG).show();
        }
        return nreg_afectados;
    }

    public long borrarExponenDni(String dni, Context context){
        long nreg_afectados=-1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            if(db!=null){
                nreg_afectados=db.delete("Exponen","dniPasaporte="+dni,null);
            }
            db.close();

        } catch (Exception e) {
            Toast.makeText(context, R.string.fail,Toast.LENGTH_LONG).show();
        }
        return nreg_afectados;
    }

}
