/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author freddy
 */
public class Correos implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idcorreo;
    private String correo;
    private String tipo;
    private BigDecimal id_cliente_fk;

    @Override
    public String toString() {
        return "Correos{" + "idcorreo=" + idcorreo + ", correo=" + correo + ", tipo=" + tipo + ", id_cliente_fk=" + id_cliente_fk + '}';
    }

    
    
    
    public BigDecimal getId_cliente_fk() {
        return id_cliente_fk;
    }

    public void setId_cliente_fk(BigDecimal id_cliente_fk) {
        this.id_cliente_fk = id_cliente_fk;
    }
    
     public int getIdcorreo() {
        return idcorreo;
    }

    public void setIdcorreo(int idcorreo) 
    {
        this.idcorreo = idcorreo;
    }

    public String getCorreo() 
    {
        return correo;
    }

    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

 
    
    
}
