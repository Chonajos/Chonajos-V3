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
 * @author Juan
 */
public class OperacionesVentasMayoreo implements Serializable {
    
    private BigDecimal carroSucursal;
    private String idSubproducto;
    private String nombreSubProducto;
    private BigDecimal idTipoEmpaque;
    private String nombreEmpaque;
    private BigDecimal empaquesEntrada;
    private BigDecimal kiloEntrada;
    private BigDecimal empaqueVendidos;
    private BigDecimal kiloVendidos;
    private BigDecimal precioVenta;
    private BigDecimal convenio;
    private BigDecimal idTipoConvenio;
    private Date fechaVenta;
    private String strFechaVenta;
    private BigDecimal idCliente;
    private String nombreCliente;
    private BigDecimal idEmpFk;
    private BigDecimal precioMinimo;
    
    private BigDecimal totalVenta;
    
    private ArrayList lstOperacionesVentasMayoreo;

    @Override
    public String toString() {
        return "RelacionOperacionesVentasMayoreo{" + "carroSucursal=" + carroSucursal + ", idSubproducto=" + idSubproducto + ", nombreSubProducto=" + nombreSubProducto + ", idTipoEmpaque=" + idTipoEmpaque + ", nombreEmpaque=" + nombreEmpaque + ", empaquesEntrada=" + empaquesEntrada + ", kiloEntrada=" + kiloEntrada + ", empaqueVendidos=" + empaqueVendidos + ", kiloVendidos=" + kiloVendidos + ", precioVenta=" + precioVenta + ", convenio=" + convenio + ", idTipoConvenio=" + idTipoConvenio + ", fechaVenta=" + fechaVenta + ", strFechaVenta=" + strFechaVenta + ", idCliente=" + idCliente + ", nombreCliente=" + nombreCliente + '}';
    }

    public BigDecimal getCarroSucursal() {
        return carroSucursal;
    }

    public void setCarroSucursal(BigDecimal carroSucursal) {
        this.carroSucursal = carroSucursal;
    }

    public String getIdSubproducto() {
        return idSubproducto;
    }

    public void setIdSubproducto(String idSubproducto) {
        this.idSubproducto = idSubproducto;
    }

    public String getNombreSubProducto() {
        return nombreSubProducto;
    }

    public void setNombreSubProducto(String nombreSubProducto) {
        this.nombreSubProducto = nombreSubProducto;
    }

    public BigDecimal getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(BigDecimal idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public BigDecimal getEmpaquesEntrada() {
        return empaquesEntrada;
    }

    public void setEmpaquesEntrada(BigDecimal empaquesEntrada) {
        this.empaquesEntrada = empaquesEntrada;
    }

    public BigDecimal getKiloEntrada() {
        return kiloEntrada;
    }

    public void setKiloEntrada(BigDecimal kiloEntrada) {
        this.kiloEntrada = kiloEntrada;
    }

    public BigDecimal getEmpaqueVendidos() {
        return empaqueVendidos;
    }

    public void setEmpaqueVendidos(BigDecimal empaqueVendidos) {
        this.empaqueVendidos = empaqueVendidos;
    }

    public BigDecimal getKiloVendidos() {
        return kiloVendidos;
    }

    public void setKiloVendidos(BigDecimal kiloVendidos) {
        this.kiloVendidos = kiloVendidos;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getConvenio() {
        return convenio;
    }

    public void setConvenio(BigDecimal convenio) {
        this.convenio = convenio;
    }

    public BigDecimal getIdTipoConvenio() {
        return idTipoConvenio;
    }

    public void setIdTipoConvenio(BigDecimal idTipoConvenio) {
        this.idTipoConvenio = idTipoConvenio;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getStrFechaVenta() {
        return strFechaVenta;
    }

    public void setStrFechaVenta(String strFechaVenta) {
        this.strFechaVenta = strFechaVenta;
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public ArrayList getLstOperacionesVentasMayoreo() {
        return lstOperacionesVentasMayoreo;
    }

    public void setLstOperacionesVentasMayoreo(ArrayList lstOperacionesVentasMayoreo) {
        this.lstOperacionesVentasMayoreo = lstOperacionesVentasMayoreo;
    }

    public BigDecimal getIdEmpFk() {
        return idEmpFk;
    }

    public void setIdEmpFk(BigDecimal idEmpFk) {
        this.idEmpFk = idEmpFk;
    }

    public BigDecimal getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(BigDecimal precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public BigDecimal getTotalVenta() {
        return this.kiloVendidos.multiply(this.precioVenta);
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    
    
    
}
