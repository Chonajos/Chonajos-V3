/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author freddy
 */
public class BajaClientes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private int id_baja_cliente;
    private int id_motivo_fk;
    private String comentarios;
    private Date fechaBaja;

    public int getId_baja_cliente() {
        return id_baja_cliente;
    }

    public void setId_baja_cliente(int id_baja_cliente) {
        this.id_baja_cliente = id_baja_cliente;
    }

    public int getId_motivo_fk() {
        return id_motivo_fk;
    }

    public void setId_motivo_fk(int id_motivo_fk) {
        this.id_motivo_fk = id_motivo_fk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        return "BajaClientes{" + "id_baja_cliente=" + id_baja_cliente + ", id_motivo_fk=" + id_motivo_fk + ", comentarios=" + comentarios + ", fechaBaja=" + fechaBaja + '}';
    }
    
}
