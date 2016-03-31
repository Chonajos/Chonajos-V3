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
public class Correos implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int idcorreo;
    private String correo;
    private String tipo;
    private int id_cliente_fk;

    @Override
    public String toString() {
        return "Correos{" + "idcorreo=" + idcorreo + ", correo=" + correo + ", tipo=" + tipo + ", id_cliente_fk=" + id_cliente_fk + '}';
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

    public int getId_cliente_fk() {
        return id_cliente_fk;
    }

    public void setId_cliente_fk(int id_cliente_fk) {
        this.id_cliente_fk = id_cliente_fk;
    }
    
    
}
