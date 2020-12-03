package com.example.salas.Controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.salas.Modelos.Exposiciones;
import com.example.salas.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_Exposicion extends BD_Salas {

    public Ctrl_Exposicion(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Exposiciones> listarExposiciones(){
        SQLiteDatabase db=getReadableDatabase();
        Exposiciones exposiciones;
        List<Exposiciones> listaExposiciones=new ArrayList<>();
        if(db!=null){
            String[] campos = {"IdExposicion","NombreExp","Descripcion","FechaInicio","FechaFin"};
            Cursor c = db.query("Exposiciones",campos,null,null,null,null,null);
            if(c.moveToFirst()){
                do{
                    exposiciones=new Exposiciones(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4));
                    listaExposiciones.add(exposiciones);
                }while(c.moveToNext());
            }
            c.close();
        }
        db.close();
        return (ArrayList) listaExposiciones;
    }

    public long insertarExposicion(Exposiciones e){
        long nreg_afectados=-1;
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("IdExposicion",e.getIdExposicion());
            valores.put("NombreExp",e.getNombreExp());
            valores.put("Descripcion",e.getDescripcion());
            valores.put("FechaInicio",e.getFechaInicio());
            valores.put("FechaFin",e.getFechaFin());
            nreg_afectados=db.insert("Exposiciones",null,valores);

        }
        db.close();
        return nreg_afectados;
    }
    public long modificarExposicion(Exposiciones e){
        long nreg_afectados=-1;
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("NombreExp",e.getNombreExp());
            valores.put("Descripcion",e.getDescripcion());
            valores.put("FechaInicio",e.getFechaInicio());
            valores.put("FechaFin",e.getFechaFin());
            nreg_afectados=db.update("Exposiciones",valores,"IdExposicion="+e.getIdExposicion(),null);

        }
        db.close();
        return nreg_afectados;
    }
    public long borrarExposicion(Exposiciones e,Context context){
        long nreg_afectados=-1;
        try {

            SQLiteDatabase db = getWritableDatabase();
            if(db!=null){
                nreg_afectados=db.delete("Exposiciones","IdExposicion="+e.getIdExposicion(),null);
            }
            db.close();

        } catch (Exception exception) {
            Toast.makeText(context, R.string.fail,Toast.LENGTH_LONG).show();

        }
        return nreg_afectados;
    }

    public Exposiciones obtenerExposicionId(int idExpo) {
        SQLiteDatabase db = getReadableDatabase();
        Exposiciones exposiciones = null;

        if (db != null) {
            String[] campos = {"IdExposicion", "NombreExp", "Descripcion", "FechaInicio", "FechaFin"};
            Cursor c = db.query("Exposiciones", campos, "IdExposicion=" + idExpo, null, null, null, null);
            if (c.getCount() != 0) {
                c.moveToFirst();
                exposiciones = new Exposiciones(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));


            }
            c.close();
        }
        db.close();
        return exposiciones;
    }

}
