package com.example.salas.Controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.salas.Modelos.Artistas;
import com.example.salas.Modelos.Comentarios;
import com.example.salas.Modelos.Trabajos;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_Comments extends BD_Salas {
    public Ctrl_Comments(@Nullable Context context) {
        super(context);
    }

    public long insertarComentarios(Comentarios com) {
        long nreg_afectados = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("idExposicion", com.getIdExposicion());
            valores.put("NombreTrab", com.getNombreTrabajo());
            valores.put("Comentario", com.getComentario());
            nreg_afectados = db.insert("Comentarios", null, valores);
        }
        db.close();
        return nreg_afectados;
    }

    public ArrayList<Comentarios> listarComentarios() {
        SQLiteDatabase db = getReadableDatabase();
        Comentarios coment;
        List<Comentarios> listaComentarios = new ArrayList<>();
        if (db != null) {
            String[] campos = {"idExposicion", "NombreTrab", "Comentario"};
            Cursor c = db.query("Comentarios", campos, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    coment = new Comentarios(c.getInt(0),
                            c.getString(1),
                            c.getString(2));

                    listaComentarios.add(coment);
                } while (c.moveToNext());
            }
            c.close();
        }
        db.close();
        return (ArrayList<Comentarios>) listaComentarios;
    }

    public long modificarComentarios(Comentarios comentarios) {
        long nreg_afectados = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("Comentario", comentarios.getComentario());
            nreg_afectados = db.update("Comentarios", valores, "idExposicion=" + comentarios.getIdExposicion(), null);
        }
        db.close();
        return nreg_afectados;
    }

    public long borrarComentarios(Comentarios coment) {
        long nreg_afectados = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            nreg_afectados = db.delete("Comentarios", "idExposicion=" + coment.getIdExposicion(), null);
        }
        db.close();
        return nreg_afectados;
    }

    public ArrayList<Comentarios> listarComentariosId(int idComentarios) {
        SQLiteDatabase db = getReadableDatabase();
        Comentarios coment;
        List<Comentarios> listaComentarios = new ArrayList<>();
        if (db != null) {
            String[] campos = {"idExposicion", "NombreTrab", "Comentario"};
            Cursor c = db.query("Comentarios", campos, "idExposicion="+idComentarios, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    coment = new Comentarios(c.getInt(0),
                            c.getString(1),
                            c.getString(2));

                    listaComentarios.add(coment);
                } while (c.moveToNext());
            }
            c.close();
        }
        db.close();
        return (ArrayList<Comentarios>) listaComentarios;
    }

    public long borrarComentariosIdTrabajo(Trabajos trabajos){
        long nreg_afectados = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            nreg_afectados = db.delete("Comentarios", "NombreTrab='"+trabajos.getNombreTrabajo()+"'", null);

        }
        db.close();
        return nreg_afectados;
    }


}
