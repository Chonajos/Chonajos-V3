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
    private BigDecimal cantidad;
    private BigDecimal kilos;
    private BigDecimal cantidadMovida;
    private BigDecimal kilosMovios;
    private BigDecimal idBodegaOrigen;
    private BigDecimal idSucursalOrigen;
    private Date fechaTransferencia;
    private BigDecimal idUsuarioFK;
    private String nombreSucursalNueva;
    private String nombreBodegaNueva;
    private BigDecimal idCarro;
    private BigDecimal idBodegaDestino;
    private BigDecimal idSucursalDestino;
    private String comentarios;

    @Override
    public void reset() {

        idTransferenciaPK = null;
        idExistenciaProductoFK = null;
        cantidad = null;
        kilos = null;
        cantidadMovida = null;
        kilosMovios = null;
        idBodegaOrigen = null;
        idSucursalOrigen = null;
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
        return "TransferenciaMercancia{" + "idTransferenciaPK=" + idTransferenciaPK + ", idExistenciaProductoFK=" + idExistenciaProductoFK + ", cantidad=" + cantidad + ", kilos=" + kilos + ", cantidadMovida=" + cantidadMovida + ", kilosMovios=" + kilosMovios + ", idBodegaOrigen=" + idBodegaOrigen + ", idSucursalOrigen=" + idSucursalOrigen + ", fechaTransferencia=" + fechaTransferencia + ", idUsuarioFK=" + idUsuarioFK + ", nombreSucursalNueva=" + nombreSucursalNueva + ", nombreBodegaNueva=" + nombreBodegaNueva + ", idCarro=" + idCarro + ", idBodegaDestino=" + idBodegaDestino + ", idSucursalDestino=" + idSucursalDestino + '}';
    }

    public BigDecimal getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(BigDecimal idCarro) {
        this.idCarro = idCarro;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getIdBodegaOrigen() {
        return idBodegaOrigen;
    }

    public void setIdBodegaOrigen(BigDecimal idBodegaOrigen) {
        this.idBodegaOrigen = idBodegaOrigen;
    }

    public BigDecimal getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public void setIdSucursalOrigen(BigDecimal idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
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
