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
 * @author jramirez
 */
public class CatalogoSat implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idCatalogoSatPk;
    private String codigo;
    private String descripcion;
    private BigDecimal tipo;
    private BigDecimal valor;

    @Override
    public String toString() {
        return "CatalogoSat{" + "idCatalogoSatPk=" + idCatalogoSatPk + ", codigo=" + codigo + ", descripcion=" + descripcion + ", tipo=" + tipo + '}';
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    
    
    

    public BigDecimal getIdCatalogoSatPk() {
        return idCatalogoSatPk;
    }

    public void setIdCatalogoSatPk(BigDecimal idCatalogoSatPk) {
        this.idCatalogoSatPk = idCatalogoSatPk;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }
    
    
    
}
