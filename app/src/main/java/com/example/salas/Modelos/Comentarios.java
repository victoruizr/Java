package com.example.salas.Modelos;

import java.io.Serializable;

public class Comentarios implements Serializable {
    private int idExposicion;
    private String nombreTrabajo;
    private String comentario;

    public Comentarios(int idExposicion, String nombreTrabajo, String comentario) {
        this.idExposicion = idExposicion;
        this.nombreTrabajo = nombreTrabajo;
        this.comentario = comentario;
    }

    public int getIdExposicion() {
        return idExposicion;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setIdExposicion(int idExposicion) {
        this.idExposicion = idExposicion;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
