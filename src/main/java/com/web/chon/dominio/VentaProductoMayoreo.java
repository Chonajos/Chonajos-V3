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
public class VentaProductoMayoreo implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaMayProdPk;
    private BigDecimal idVentaMayoreoFk;
    private String idSubProductofk;
    private BigDecimal precioProducto;
    private BigDecimal kilosVendidos;
    private BigDecimal cantidadEmpaque;
    private BigDecimal totalVenta;
    private BigDecimal idTipoEmpaqueFk;
    private BigDecimal idEntradaMercanciaFk;
    private String clave;
    private String nombreProducto;
    private String nombreEmpaque;
    private BigDecimal idExistenciaFk;
    private BigDecimal idBodegaFk;
    private BigDecimal folioVenta;

    //Variables para credito
    private BigDecimal idTipoVenta;
    private BigDecimal tipoPago;
    private BigDecimal numeroPagos;
    private BigDecimal precioSinInteres;
    private BigDecimal folioCarro;
    private BigDecimal totalVentaSinIva;
    private BigDecimal precioProductoSinIva;


    public VentaProductoMayoreo()
    {
        
    }

    public BigDecimal getPrecioProductoSinIva() {
        return precioProductoSinIva;
    }

    public void setPrecioProductoSinIva(BigDecimal precioProductoSinIva) {
        this.precioProductoSinIva = precioProductoSinIva;
    }
    
    

    
    public void reset() {
        idVentaMayProdPk = null;
        idVentaMayoreoFk = null;
        idSubProductofk = null;
        precioProducto = null;
        kilosVendidos = null;
        cantidadEmpaque = null;
        totalVenta = null;
        idTipoEmpaqueFk = null;
        idEntradaMercanciaFk = null;
        clave = null;
        nombreProducto = null;
        nombreEmpaque = null;
        idExistenciaFk=null;
        folioCarro=null;
        idBodegaFk=null;
        

    }

    public BigDecimal getIdBodegaFk() {
        return idBodegaFk;
    }

    public void setIdBodegaFk(BigDecimal idBodegaFk) {
        this.idBodegaFk = idBodegaFk;
    }
    

    @Override
    public String toString() {
        return "VentaProductoMayoreo{" + "idVentaMayProdPk=" + idVentaMayProdPk + ", idVentaMayoreoFk=" + idVentaMayoreoFk + ", idSubProductofk=" + idSubProductofk + ", precioProducto=" + precioProducto + ", kilosVendidos=" + kilosVendidos + ", cantidadEmpaque=" + cantidadEmpaque + ", totalVenta=" + totalVenta + ", idTipoEmpaqueFk=" + idTipoEmpaqueFk + ", idEntradaMercanciaFk=" + idEntradaMercanciaFk + ", clave=" + clave + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + ", idExistenciaFk=" + idExistenciaFk + ", idTipoVenta=" + idTipoVenta + '}';
    }

    public BigDecimal getFolioCarro() {
        return folioCarro;
    }

    public void setFolioCarro(BigDecimal folioCarro) {
        this.folioCarro = folioCarro;
    }

    
    public BigDecimal getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(BigDecimal tipoPago) {
        this.tipoPago = tipoPago;
    }

    public BigDecimal getPrecioSinInteres() {
        return precioSinInteres;
    }

    public void setPrecioSinInteres(BigDecimal precioSinInteres) {
        this.precioSinInteres = precioSinInteres;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }
    
    
    public BigDecimal getIdExistenciaFk() {
        return idExistenciaFk;
    }

    public void setIdExistenciaFk(BigDecimal idExistenciaFk) {
        this.idExistenciaFk = idExistenciaFk;
    }

    public BigDecimal getNumeroPagos() {
        return numeroPagos;
    }

    public void setNumeroPagos(BigDecimal numeroPagos) {
        this.numeroPagos = numeroPagos;
    }

    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public BigDecimal getIdVentaMayProdPk() {
        return idVentaMayProdPk;
    }

    public void setIdVentaMayProdPk(BigDecimal idVentaMayProdPk) {
        this.idVentaMayProdPk = idVentaMayProdPk;
    }

    public BigDecimal getIdVentaMayoreoFk() {
        return idVentaMayoreoFk;
    }

    public void setIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk) {
        this.idVentaMayoreoFk = idVentaMayoreoFk;
    }

    public String getIdSubProductofk() {
        return idSubProductofk;
    }

    public void setIdSubProductofk(String idSubProductofk) {
        this.idSubProductofk = idSubProductofk;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public BigDecimal getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigDecimal kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getIdTipoEmpaqueFk() {
        return idTipoEmpaqueFk;
    }

    public void setIdTipoEmpaqueFk(BigDecimal idTipoEmpaqueFk) {
        this.idTipoEmpaqueFk = idTipoEmpaqueFk;
    }

    public BigDecimal getIdEntradaMercanciaFk() {
        return idEntradaMercanciaFk;
    }

    public void setIdEntradaMercanciaFk(BigDecimal idEntradaMercanciaFk) {
        this.idEntradaMercanciaFk = idEntradaMercanciaFk;
    }

    public BigDecimal getTotalVentaSinIva() {
        return totalVentaSinIva;
    }

    public void setTotalVentaSinIva(BigDecimal totalVentaSinIva) {
        this.totalVentaSinIva = totalVentaSinIva;
    }

    public BigDecimal getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(BigDecimal folioVenta) {
        this.folioVenta = folioVenta;
    }
    

   
}
