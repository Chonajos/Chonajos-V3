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
 * @author Juan de la Cruz
 */
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaPk;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private BigDecimal idClienteFk;
    private int idSucursal;
    private BigDecimal idVendedorFk;
    private String nombrestatus;
    private Date fechaPago;
    private String nombreCliente;
    private String nombreVendedor;
    private BigDecimal totalVenta;
    private BigDecimal folio;
    private BigDecimal idStatusVenta;
    private BigDecimal count;
    private String nombreSucursal;
    private String nombreEstatus;
    private ArrayList<VentaProducto> lstVentaProducto;
    private BigDecimal tipoVenta;

    @Override
    public String toString() {
        return "Venta{" + "idVentaPk=" + idVentaPk + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", idClienteFk=" + idClienteFk + ", idSucursal=" + idSucursal + ", idVendedorFk=" + idVendedorFk + ", nombrestatus=" + nombrestatus + ", fechaPago=" + fechaPago + ", nombreCliente=" + nombreCliente + ", nombreVendedor=" + nombreVendedor + ", totalVenta=" + totalVenta + ", folio=" + folio + ", idStatusVenta=" + idStatusVenta + ", count=" + count + ", nombreSucursal=" + nombreSucursal + ", nombreEstatus=" + nombreEstatus + ", lstVentaProducto=" + lstVentaProducto + '}';
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

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdVendedorFk() {
        return idVendedorFk;
    }

    public void setIdVendedorFk(BigDecimal idVendedorFk) {
        this.idVendedorFk = idVendedorFk;
    }

    public String getNombrestatus() {
        return nombrestatus;
    }

    public void setNombrestatus(String nombrestatus) {
        this.nombrestatus = nombrestatus;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    public BigDecimal getFolio() {
        return folio;
    }

    public void setFolio(BigDecimal folio) {
        this.folio = folio;
    }

    public BigDecimal getIdStatusVenta() {
        return idStatusVenta;
    }

    public void setIdStatusVenta(BigDecimal idStatusVenta) {
        this.idStatusVenta = idStatusVenta;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public ArrayList<VentaProducto> getLstVentaProducto() {
        return lstVentaProducto;
    }

    public void setLstVentaProducto(ArrayList<VentaProducto> lstVentaProducto) {
        this.lstVentaProducto = lstVentaProducto;
    }

    public BigDecimal getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(BigDecimal tipoVenta) {
        this.tipoVenta = tipoVenta;
    }
    
    

}