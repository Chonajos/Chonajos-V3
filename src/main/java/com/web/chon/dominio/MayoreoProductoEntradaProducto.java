package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Juan de la Cruz
 */
public class MayoreoProductoEntradaProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSubProductofk;
    private BigDecimal precioProducto;
    private BigDecimal kilosVendidos;
    private BigDecimal empaquesVendidos;
    private BigDecimal totalVenta;
    private BigDecimal carroSucursal;
    private BigDecimal convenio;
    private BigDecimal idConvenio;
    private BigDecimal kilosEntrada;
    private BigDecimal empaqueEntrada;
    private BigDecimal idTipoEmpaqueFk;
    private BigDecimal comision;

    @Override
    public String toString() {
        return "MayoreoProductoEntradaProducto{" + "idSubProductofk=" + idSubProductofk + ", precioProducto=" + precioProducto + ", kilosVendidos=" + kilosVendidos + ", empaquesVendidos=" + empaquesVendidos + ", totalVenta=" + totalVenta + ", carroSucursal=" + carroSucursal + ", convenio=" + convenio + ", idConvenio=" + idConvenio + ", kilosEntrada=" + kilosEntrada + ", empaEntrada=" + empaqueEntrada + ", idTipoEmpaqueFk=" + idTipoEmpaqueFk + '}';
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

    public BigDecimal getEmpaquesVendidos() {
        return empaquesVendidos;
    }

    public void setEmpaquesVendidos(BigDecimal empaquesVendidos) {
        this.empaquesVendidos = empaquesVendidos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getCarroSucursal() {
        return carroSucursal;
    }

    public void setCarroSucursal(BigDecimal carroSucursal) {
        this.carroSucursal = carroSucursal;
    }

    public BigDecimal getConvenio() {
        return convenio;
    }

    public void setConvenio(BigDecimal convenio) {
        this.convenio = convenio;
    }

    public BigDecimal getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(BigDecimal idConvenio) {
        this.idConvenio = idConvenio;
    }

    public BigDecimal getKilosEntrada() {
        return kilosEntrada;
    }

    public void setKilosEntrada(BigDecimal kilosEntrada) {
        this.kilosEntrada = kilosEntrada;
    }

    public BigDecimal getEmpaqueEntrada() {
        return empaqueEntrada;
    }

    public void setEmpaqueEntrada(BigDecimal empaqueEntrada) {
        this.empaqueEntrada = empaqueEntrada;
    }

    public BigDecimal getIdTipoEmpaqueFk() {
        return idTipoEmpaqueFk;
    }

    public void setIdTipoEmpaqueFk(BigDecimal idTipoEmpaqueFk) {
        this.idTipoEmpaqueFk = idTipoEmpaqueFk;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    
    public void reset() {
        idSubProductofk = null;
        precioProducto = null;
        kilosVendidos = null;
        kilosEntrada = null;
        empaquesVendidos = null;
        carroSucursal = null;
        convenio = null;
        idConvenio = null;
        totalVenta = null;
        idTipoEmpaqueFk = null;
        empaqueEntrada = null;
        comision = null;

    }

}
