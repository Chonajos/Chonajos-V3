package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la cruz
 */
public class TransferenciaMercancia extends ValueObject implements Serializable {

    private BigDecimal idTransferenciaPK;
    private BigDecimal idExistenciaProductoFK;
    private BigDecimal cantidadAnterior;
    private BigDecimal kilosAnterior;
    private BigDecimal cantidadMovida;
    private BigDecimal kilosMovios;
    private BigDecimal idBodegaOrigen;
    private Date fechaTransferencia;
    private BigDecimal idUsuarioFK;
    private String comentarios;

    //Datos obcionales
    private BigDecimal idSucursalOrigen;
    private String nombreSucursalNueva;
    private String nombreBodegaNueva;
    private BigDecimal idCarro;
    private BigDecimal idBodegaDestino;
    private BigDecimal idSucursalDestino;

    @Override
    public void reset() {

        idTransferenciaPK = null;
        idExistenciaProductoFK = null;
        cantidadAnterior = null;
        kilosAnterior = null;
        cantidadMovida = null;
        kilosMovios = null;
        idBodegaDestino = null;
        idSucursalDestino = null;
        fechaTransferencia = null;
        idUsuarioFK = null;
        nombreBodegaNueva = null;
        nombreSucursalNueva = null;
        comentarios = null;

    }

    @Override
    public String toString() {
        return "TransferenciaMercancia{" + "idTransferenciaPK=" + idTransferenciaPK + ", idExistenciaProductoFK=" + idExistenciaProductoFK + ", cantidadAnterior=" + cantidadAnterior + ", kilosAnterior=" + kilosAnterior + ", cantidadMovida=" + cantidadMovida + ", kilosMovios=" + kilosMovios + ", idBodegaOrigen=" + idBodegaOrigen + ", fechaTransferencia=" + fechaTransferencia + ", idUsuarioFK=" + idUsuarioFK + ", comentarios=" + comentarios + ", nombreSucursalNueva=" + nombreSucursalNueva + ", nombreBodegaNueva=" + nombreBodegaNueva + ", idCarro=" + idCarro + ", idBodegaDestino=" + idBodegaDestino + ", idSucursalDestino=" + idSucursalDestino + '}';
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

    public BigDecimal getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(BigDecimal cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }

    public BigDecimal getKilosAnterior() {
        return kilosAnterior;
    }

    public void setKilosAnterior(BigDecimal kilosAnterior) {
        this.kilosAnterior = kilosAnterior;
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

    public BigDecimal getIdBodegaOrigen() {
        return idBodegaOrigen;
    }

    public void setIdBodegaOrigen(BigDecimal idBodegaOrigen) {
        this.idBodegaOrigen = idBodegaOrigen;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public BigDecimal getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(BigDecimal idCarro) {
        this.idCarro = idCarro;
    }

    public BigDecimal getIdBodegaDestino() {
        return idBodegaDestino;
    }

    public void setIdBodegaDestino(BigDecimal idBodegaDestino) {
        this.idBodegaDestino = idBodegaDestino;
    }

    public BigDecimal getIdSucursalDestino() {
        return idSucursalDestino;
    }

    public void setIdSucursalDestino(BigDecimal idSucursalDestino) {
        this.idSucursalDestino = idSucursalDestino;
    }

    public BigDecimal getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public void setIdSucursalOrigen(BigDecimal idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

}
