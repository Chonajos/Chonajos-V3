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
public class TipoOperacion implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoOperacionPk;
    private int numero;
    private String nombre;
    private String descripcion;
    private BigDecimal montoTotal;

    @Override
    public String toString() {
        return "TipoOperacion{" + "idTipoOperacionPk=" + idTipoOperacionPk + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getIdTipoOperacionPk() {
        return idTipoOperacionPk;
    }

    public void setIdTipoOperacionPk(BigDecimal idTipoOperacionPk) {
        this.idTipoOperacionPk = idTipoOperacionPk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
