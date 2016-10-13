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
 * @author freddy
 */
public class VentaMayoreo implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaMayoreoPk;
    private BigDecimal idClienteFk;
    private BigDecimal idVendedorFK;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private BigDecimal idStatusFk;
    private Date fechaPago;
    private BigDecimal idSucursalFk;
    private BigDecimal idtipoVentaFk;
    private BigDecimal ventaSucursal;
    private BigDecimal idCajeroFk;
    private Date fechaCancelacion;
    private BigDecimal idCancelUser;
    private BigDecimal totalVenta;
    ;
    
    private String nombreCliente;
    private String nombreVendedor;
    private String nombreTipoVenta;
    private String nombreEstatus;
    private String comentariosCancel;
    private ArrayList<VentaProductoMayoreo> listaProductos;
    private BigDecimal ganciaVenta;

    @Override
    public String toString() {
        return "VentaMayoreo{" + "idVentaMayoreoPk=" + idVentaMayoreoPk + ", idClienteFk=" + idClienteFk + ", idVendedorFK=" + idVendedorFK + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", idStatusFk=" + idStatusFk + ", fechaPago=" + fechaPago + ", idSucursalFk=" + idSucursalFk + ", idtipoVentaFk=" + idtipoVentaFk + '}';
    }

    public void reset() {
        idVentaMayoreoPk = null;
        idClienteFk = null;
        idVendedorFK = null;
        fechaVenta = null;
        fechaPromesaPago = null;
        idStatusFk = null;
        fechaPago = null;
        idSucursalFk = null;
        idtipoVentaFk = null;
        ventaSucursal = null;
        idCajeroFk = null;
        fechaCancelacion = null;
        idCancelUser = null;
        totalVenta = null;
        nombreCliente = null;
        nombreVendedor = null;
        nombreTipoVenta = null;
        nombreEstatus = null;
        comentariosCancel = null;
        listaProductos = null;
        ganciaVenta = null;
    }

    public BigDecimal getVentaSucursal() {
        return ventaSucursal;
    }

    public void setVentaSucursal(BigDecimal ventaSucursal) {
        this.ventaSucursal = ventaSucursal;
    }

    public BigDecimal getIdtipoVentaFk() {
        return idtipoVentaFk;
    }

    public void setIdtipoVentaFk(BigDecimal idtipoVentaFk) {
        this.idtipoVentaFk = idtipoVentaFk;
    }

    public BigDecimal getIdVentaMayoreoPk() {
        return idVentaMayoreoPk;
    }

    public void setIdVentaMayoreoPk(BigDecimal idVentaMayoreoPk) {
        this.idVentaMayoreoPk = idVentaMayoreoPk;
    }

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdVendedorFK() {
        return idVendedorFK;
    }

    public void setIdVendedorFK(BigDecimal idVendedorFK) {
        this.idVendedorFK = idVendedorFK;
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

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
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

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public String getComentariosCancel() {
        return comentariosCancel;
    }

    public void setComentariosCancel(String comentariosCancel) {
        this.comentariosCancel = comentariosCancel;
    }

    public BigDecimal getIdCajeroFk() {
        return idCajeroFk;
    }

    public void setIdCajeroFk(BigDecimal idCajeroFk) {
        this.idCajeroFk = idCajeroFk;
    }

    public BigDecimal getIdCancelUser() {
        return idCancelUser;
    }

    public void setIdCancelUser(BigDecimal idCancelUser) {
        this.idCancelUser = idCancelUser;
    }

    public ArrayList<VentaProductoMayoreo> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<VentaProductoMayoreo> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getGanciaVenta() {
        return ganciaVenta;
    }

    public void setGanciaVenta(BigDecimal ganciaVenta) {
        this.ganciaVenta = ganciaVenta;
    }

}
