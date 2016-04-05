/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;

/**
 *
 * @author freddy
 */
public class Motivos implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id_motivo;
    private String nombre_motivo;
    private String comentarios;

    
    public int getId_motivo() {
        return id_motivo;
    }

    public void setId_motivo(int id_motivo) {
        this.id_motivo = id_motivo;
    }

    public String getNombre_motivo() {
        return nombre_motivo;
    }

    public void setNombre_motivo(String nombre_motivo) {
        this.nombre_motivo = nombre_motivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Motivos{" + "id_motivo=" + id_motivo + ", nombre_motivo=" + nombre_motivo + ", comentarios=" + comentarios + '}';
    }
   
    
}
