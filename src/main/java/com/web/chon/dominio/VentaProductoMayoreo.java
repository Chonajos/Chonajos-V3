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
public class VentaProductoMayoreo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaMayProdPk;
    private BigDecimal idVentaMayoreoFk;
    private String idSubProductofk;
    private BigDecimal precioProducto;
    private BigDecimal kilosVendidos;
    private BigDecimal cantidadEmpaque;
    private BigDecimal totalVenta;
    private BigDecimal idTipoEmpaqueFk;
    private BigDecimal idTipoVentaFk;
    private BigDecimal idEntradaMercanciaFk;

    @Override
    public String toString() {
        return "VentaProductoMayoreo{" + "idVentaMayProdPk=" + idVentaMayProdPk + ", idVentaMayoreoFk=" + idVentaMayoreoFk + ", idSubProductofk=" + idSubProductofk + ", precioProducto=" + precioProducto + ", kilosVendidos=" + kilosVendidos + ", cantidadEmpaque=" + cantidadEmpaque + ", totalVenta=" + totalVenta + ", idTipoEmpaqueFk=" + idTipoEmpaqueFk + ", idTipoVentaFk=" + idTipoVentaFk + ", idEntradaMercanciaFk=" + idEntradaMercanciaFk + '}';
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

    public BigDecimal getIdTipoVentaFk() {
        return idTipoVentaFk;
    }

    public void setIdTipoVentaFk(BigDecimal idTipoVentaFk) {
        this.idTipoVentaFk = idTipoVentaFk;
    }

    public BigDecimal getIdEntradaMercanciaFk() {
        return idEntradaMercanciaFk;
    }

    public void setIdEntradaMercanciaFk(BigDecimal idEntradaMercanciaFk) {
        this.idEntradaMercanciaFk = idEntradaMercanciaFk;
    }
    
    
}
