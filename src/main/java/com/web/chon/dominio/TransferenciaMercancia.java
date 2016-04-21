/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author marcogante
 */
public class TransferenciaMercancia extends ValueObject implements Serializable {

    private BigDecimal idTransferenciaPK;
    private BigDecimal idExistenciaProductoFK;
    private BigDecimal cantidad;
    private BigDecimal kilos;
    private BigDecimal cantidadMovida;
    private BigDecimal kilosMovios;
    private BigDecimal idBodegaNueva;
    private BigDecimal idSucursalNuevaFK;
    private Date fechaTransferencia;
    private BigDecimal idUsuarioFK;
    private String nombreSucursalNueva;
    private String nombreBodegaNueva;

    @Override
    public void reset() {

        idTransferenciaPK = null;
        idExistenciaProductoFK = null;
        cantidad = null;
        kilos = null;
        cantidadMovida = null;
        kilosMovios = null;
        idBodegaNueva = null;
        idSucursalNuevaFK = null;
        fechaTransferencia = null;
        idUsuarioFK = null;
        nombreBodegaNueva=null;
        nombreSucursalNueva=null;

    }

    @Override
    public String toString() {
        return "TransferenciaMercancia{" + "idTransferenciaPK=" + idTransferenciaPK + ", idExistenciaProductoFK=" + idExistenciaProductoFK + ", cantidad=" + cantidad + ", kilos=" + kilos + ", cantidadMovida=" + cantidadMovida + ", kilosMovios=" + kilosMovios + ", idBodegaNueva=" + idBodegaNueva + ", idSucursalNuevaFK=" + idSucursalNuevaFK + ", fechaTransferencia=" + fechaTransferencia + ", idUsuarioFK=" + idUsuarioFK + ", nombreSucursalNueva=" + nombreSucursalNueva + ", nombreBodegaNueva=" + nombreBodegaNueva + '}';
    }
    

    public BigDecimal getIdSucursalNuevaFK() {
        return idSucursalNuevaFK;
    }

    public void setIdSucursalNuevaFK(BigDecimal idSucursalNuevaFK) {
        this.idSucursalNuevaFK = idSucursalNuevaFK;
    }

    
    
    
    public String getNombreSucursalNueva() {
        return nombreSucursalNueva;
    }

    public void setNombreSucursalNueva(String nombreSucursalNueva) {
        this.nombreSucursalNueva = nombreSucursalNueva;
    }

    public String getNombreBodegaNueva() {
        return nombreBodegaNueva;
    }

    public void setNombreBodegaNueva(String nombreBodegaNueva) {
        this.nombreBodegaNueva = nombreBodegaNueva;
    }

    

    public BigDecimal getIdTransferenciaPK() {
        return idTransferenciaPK;
    }

    public void setIdTransferenciaPK(BigDecimal idTransferenciaPK) {
        this.idTransferenciaPK = idTransferenciaPK;
    }

    public BigDecimal getIdExistenciaProductoFK() {
        return idExistenciaProductoFK;
    }

    public void setIdExistenciaProductoFK(BigDecimal idExistenciaProductoFK) {
        this.idExistenciaProductoFK = idExistenciaProductoFK;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public BigDecimal getCantidadMovida() {
        return cantidadMovida;
    }

    public void setCantidadMovida(BigDecimal cantidadMovida) {
        this.cantidadMovida = cantidadMovida;
    }

    public BigDecimal getKilosMovios() {
        return kilosMovios;
    }

    public void setKilosMovios(BigDecimal kilosMovios) {
        this.kilosMovios = kilosMovios;
    }

    public BigDecimal getIdBodegaNueva() {
        return idBodegaNueva;
    }

    public void setIdBodegaNueva(BigDecimal idBodegaNueva) {
        this.idBodegaNueva = idBodegaNueva;
    }

   
    public Date getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(Date fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

    public BigDecimal getIdUsuarioFK() {
        return idUsuarioFK;
    }

    public void setIdUsuarioFK(BigDecimal idUsuarioFK) {
        this.idUsuarioFK = idUsuarioFK;
    }

   

    
}
