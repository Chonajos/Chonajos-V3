/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author freddy
 */
public class RelacionOperacionesMayoreo implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaPk;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private BigDecimal idClienteFk;
    private BigDecimal idVendedorFk;
    private String nombreCliente;
    private String nombreVendedor;
    private String nombreTipoVenta;
    private String estatus;
    private String comentariosCancel;
    
    
    private Date fechaPago;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private Date fechaCancelacion;
     
    
    private BigDecimal idSucursal;
    private BigDecimal idStatus;
    private BigDecimal idTipoVenta;
    private BigDecimal ventaSucursal;
    private BigDecimal idCajero;
    private BigDecimal idCancelUser;
    private BigDecimal ganciaVenta;
    private BigDecimal totalVenta;

    @Override
    public String toString() {
        return "RelacionOperacionesMayoreo{" + "idVentaPk=" + idVentaPk + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", idClienteFk=" + idClienteFk + ", idVendedorFk=" + idVendedorFk + ", nombreCliente=" + nombreCliente + ", nombreVendedor=" + nombreVendedor + ", totalVenta=" + totalVenta + ", estatus=" + estatus + ", fechaPago=" + fechaPago + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", idSucursal=" + idSucursal + ", idStatus=" + idStatus + ", idTipoVenta=" + idTipoVenta + ", nombreTipoVenta=" + nombreTipoVenta + ", ventaSucursal=" + ventaSucursal + ", idCajero=" + idCajero + ", idCancelUser=" + idCancelUser + ", comentariosCancel=" + comentariosCancel + ", fechaCancelacion=" + fechaCancelacion + '}';
    }

    public BigDecimal getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(BigDecimal idCajero) {
        this.idCajero = idCajero;
    }

    public BigDecimal getIdCancelUser() {
        return idCancelUser;
    }

    public void setIdCancelUser(BigDecimal idCancelUser) {
        this.idCancelUser = idCancelUser;
    }

    public String getComentariosCancel() {
        return comentariosCancel;
    }

    public void setComentariosCancel(String comentariosCancel) {
        this.comentariosCancel = comentariosCancel;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    
    public BigDecimal getVentaSucursal() {
        return ventaSucursal;
    }

    public void setVentaSucursal(BigDecimal ventaSucursal) {
        this.ventaSucursal = ventaSucursal;
    }

    

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }
    

    
    public BigDecimal getIdVentaPk() {
        return idVentaPk;
    }

    public void setIdVentaPk(BigDecimal idVentaPk) {
        this.idVentaPk = idVentaPk;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaPromesaPago() {
        return fechaPromesaPago;
    }

    public void setFechaPromesaPago(Date fechaPromesaPago) {
        this.fechaPromesaPago = fechaPromesaPago;
    }

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdVendedorFk() {
        return idVendedorFk;
    }

    public void setIdVendedorFk(BigDecimal idVendedorFk) {
        this.idVendedorFk = idVendedorFk;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(BigDecimal idStatus) {
        this.idStatus = idStatus;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public BigDecimal getGanciaVenta() {
        return ganciaVenta;
    }

    public void setGanciaVenta(BigDecimal ganciaVenta) {
        this.ganciaVenta = ganciaVenta;
    }
    
    

    
}
