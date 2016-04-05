/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;

/**
 *
 * @author marcogante
 */
public class StatusVenta implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id_status_pk;
    private String status;
    private String descripcion_status;

    @Override
    public String toString() {
        return "StatusVenta{" + "id_status_pk=" + id_status_pk + ", status=" + status + ", descripcion_status=" + descripcion_status + '}';
    }

    public int getId_status_pk() {
        return id_status_pk;
    }

    public void setId_status_pk(int id_status_pk) {
        this.id_status_pk = id_status_pk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion_status() {
        return descripcion_status;
    }

    public void setDescripcion_status(String descripcion_status) {
        this.descripcion_status = descripcion_status;
    }
    
    
}
