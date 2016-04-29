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
public class EntradaMercanciaProducto extends ValueObject implements Serializable
{
    private BigDecimal idEmpPK;
    
    private BigDecimal idEmFK;
    private String idSubProductoFK;
    private BigDecimal idTipoEmpaqueFK;
    private BigDecimal cantidadPaquetes;
    private BigDecimal kilosTotalesProducto;
    private String comentarios;
    private BigDecimal precio;
    private String nombreProducto;
    private String nombreEmpaque;
    private BigDecimal idTipoConvenio;
    private BigDecimal valorPorcentaje;
    private BigDecimal valorPacto;
    private BigDecimal idBodegaFK;
    private String nombreTipoConvenio;
    private String nombreBodega;
    private BigDecimal kilospromprod;
    private int numeroMovimiento;
    

    
    @Override
    public void reset() {
       idEmFK = null;
       idSubProductoFK = null;
       idTipoEmpaqueFK = null;
       cantidadPaquetes  = null;
       kilosTotalesProducto = null;
       comentarios = null;
       precio = null;
       nombreProducto = null;
       nombreEmpaque = null;
       idTipoConvenio = null;
       valorPorcentaje=null;
       valorPacto=null;
       idBodegaFK = null;
       nombreTipoConvenio=null;
       nombreBodega  = null;
    }

    @Override
    public String toString() {
        return "EntradaMercanciaProducto{" + "idEmpPK=" + idEmpPK + ", idEmFK=" + idEmFK + ", idSubProductoFK=" + idSubProductoFK + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", cantidadPaquetes=" + cantidadPaquetes + ", kilosTotalesProducto=" + kilosTotalesProducto + ", comentarios=" + comentarios + ", precio=" + precio + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + ", idTipoConvenio=" + idTipoConvenio + ", valorPorcentaje=" + valorPorcentaje + ", valorPacto=" + valorPacto + ", idBodegaFK=" + idBodegaFK + ", nombreTipoConvenio=" + nombreTipoConvenio + ", nombreBodega=" + nombreBodega + '}';
    }

    public BigDecimal getKilospromprod() {
        return kilospromprod;
    }

    public void setKilospromprod(BigDecimal kilospromprod) {
        this.kilospromprod = kilospromprod;
    }
    
    
    

    public BigDecimal getIdTipoConvenio() {
        return idTipoConvenio;
    }

    public void setIdTipoConvenio(BigDecimal idTipoConvenio) {
        this.idTipoConvenio = idTipoConvenio;
    }

    public String getNombreTipoConvenio() {
        return nombreTipoConvenio;
    }

    public void setNombreTipoConvenio(String nombreTipoConvenio) {
        this.nombreTipoConvenio = nombreTipoConvenio;
    }

  
    
    
    

    public BigDecimal getKilosTotalesProducto() {
        return kilosTotalesProducto;
    }

    public void setKilosTotalesProducto(BigDecimal kilosTotalesProducto) {
        this.kilosTotalesProducto = kilosTotalesProducto;
    }

 

    
    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public String getIdSubProductoFK() {
        return idSubProductoFK;
    }

    public void setIdSubProductoFK(String idSubProductoFK) {
        this.idSubProductoFK = idSubProductoFK;
    }


    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    

    public BigDecimal getIdEmpPK() {
        return idEmpPK;
    }

    public void setIdEmpPK(BigDecimal idEmpPK) {
        this.idEmpPK = idEmpPK;
    }

    public BigDecimal getIdEmFK() {
        return idEmFK;
    }

    public void setIdEmFK(BigDecimal idEmFK) {
        this.idEmFK = idEmFK;
    }

   

    public BigDecimal getIdTipoEmpaqueFK() {
        return idTipoEmpaqueFK;
    }

    public void setIdTipoEmpaqueFK(BigDecimal idTipoEmpaqueFK) {
        this.idTipoEmpaqueFK = idTipoEmpaqueFK;
    }

    public BigDecimal getCantidadPaquetes() {
        return cantidadPaquetes;
    }

    public void setCantidadPaquetes(BigDecimal cantidadPaquetes) {
        this.cantidadPaquetes = cantidadPaquetes;
    }

    


    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getIdBodegaFK() {
        return idBodegaFK;
    }

    public void setIdBodegaFK(BigDecimal idBodegaFK) {
        this.idBodegaFK = idBodegaFK;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    
    
}
