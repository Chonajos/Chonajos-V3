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
 * @author JesusAlfredo
 */
public class TipoAbono extends ValueObject implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoAbono;
    private String nombreTipoAbono;
    private String descripcion;

    @Override
    public String toString() {
        return "TipoAbono{" + "idTipoAbono=" + idTipoAbono + ", nombreTipoAbono=" + nombreTipoAbono + ", descripcion=" + descripcion + '}';
    }

    public BigDecimal getIdTipoAbono() {
        return idTipoAbono;
    }

    public void setIdTipoAbono(BigDecimal idTipoAbono) {
        this.idTipoAbono = idTipoAbono;
    }

    public String getNombreTipoAbono() {
        return nombreTipoAbono;
    }

    public void setNombreTipoAbono(String nombreTipoAbono) {
        this.nombreTipoAbono = nombreTipoAbono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
