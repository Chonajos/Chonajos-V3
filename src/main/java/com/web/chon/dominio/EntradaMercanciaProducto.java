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
    private BigDecimal idTipo;
    private BigDecimal valorPorcentaje;
    private BigDecimal valorPacto;
    private BigDecimal idBodegaFK;
    private String nombreTipoOrdenCompra;
    private String nombreBodega;

    @Override
    public String toString() {
        return "EntradaMercanciaProducto{" + "idEmpPK=" + idEmpPK + ", idEmFK=" + idEmFK + ", idSubProductoFK=" + idSubProductoFK + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", cantidadPaquetes=" + cantidadPaquetes + ", kilosTotalesProducto=" + kilosTotalesProducto + ", comentarios=" + comentarios + ", precio=" + precio + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + ", idTipo=" + idTipo + ", valorPorcentaje=" + valorPorcentaje + ", valorPacto=" + valorPacto + ", idBodegaFK=" + idBodegaFK + ", nombreTipoOrdenCompra=" + nombreTipoOrdenCompra + ", nombreBodega=" + nombreBodega + '}';
    }
    
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
       idTipo = null;
       
       idBodegaFK = null;
       nombreBodega  = null;
    }

    
    public BigDecimal getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(BigDecimal idTipo) {
        this.idTipo = idTipo;
    }

    public BigDecimal getKilosTotalesProducto() {
        return kilosTotalesProducto;
    }

    public void setKilosTotalesProducto(BigDecimal kilosTotalesProducto) {
        this.kilosTotalesProducto = kilosTotalesProducto;
    }

 

    public String getNombreTipoOrdenCompra() {
        return nombreTipoOrdenCompra;
    }

    public void setNombreTipoOrdenCompra(String nombreTipoOrdenCompra) {
        this.nombreTipoOrdenCompra = nombreTipoOrdenCompra;
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

    
    
}
