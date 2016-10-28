package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class CarroDetalle extends ValueObject implements Serializable {

    private BigDecimal idVenta;
    private Date fecha;
    private BigDecimal folioSucursal;
    private String tipoVenta;
    private BigDecimal estatusCredito;
    private BigDecimal paquetesVendidos;
    private BigDecimal kilosVendidos;
    private BigDecimal totalVenta;
    private String nombreCliente;
    private BigDecimal paquetesEntrada;
    private BigDecimal kilosEntrada;
    private BigDecimal idConvenio;
    private BigDecimal convenio;
    private BigDecimal comisio;
    private BigDecimal saldoPorCobrar;
    private String strFecha;

    public BigDecimal getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(BigDecimal idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getFolioSucursal() {
        return folioSucursal;
    }

    public void setFolioSucursal(BigDecimal folioSucursal) {
        this.folioSucursal = folioSucursal;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public BigDecimal getEstatusCredito() {
        return estatusCredito;
    }

    public void setEstatusCredito(BigDecimal estatusCredito) {
        this.estatusCredito = estatusCredito;
    }

    public BigDecimal getPaquetesVendidos() {
        return paquetesVendidos;
    }

    public void setPaquetesVendidos(BigDecimal paquetesVendidos) {
        this.paquetesVendidos = paquetesVendidos;
    }

    public BigDecimal getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigDecimal kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public BigDecimal getPaquetesEntrada() {
        return paquetesEntrada;
    }

    public void setPaquetesEntrada(BigDecimal paquetesEntrada) {
        this.paquetesEntrada = paquetesEntrada;
    }

    public BigDecimal getKilosEntrada() {
        return kilosEntrada;
    }

    public void setKilosEntrada(BigDecimal kilosEntrada) {
        this.kilosEntrada = kilosEntrada;
    }

    public BigDecimal getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(BigDecimal idConvenio) {
        this.idConvenio = idConvenio;
    }

    public BigDecimal getConvenio() {
        return convenio;
    }

    public void setConvenio(BigDecimal convenio) {
        this.convenio = convenio;
    }

    public BigDecimal getComisio() {
        return comisio;
    }

    public void setComisio(BigDecimal comisio) {
        this.comisio = comisio;
    }

    public BigDecimal getSaldoPorCobrar() {
        return saldoPorCobrar;
    }

    public void setSaldoPorCobrar(BigDecimal saldoPorCobrar) {
        this.saldoPorCobrar = saldoPorCobrar;
    }

    public String getStrFecha() {
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }
    
    

    @Override
    public String toString() {
        return "CarroDetalle{" + "idVenta=" + idVenta + ", fecha=" + fecha + ", folioSucursal=" + folioSucursal + ", tipoVenta=" + tipoVenta + ", estatusCredito=" + estatusCredito + ", paquetesVendidos=" + paquetesVendidos + ", kilosVendidos=" + kilosVendidos + ", totalVenta=" + totalVenta + ", nombreCliente=" + nombreCliente + ", paquetesEntrada=" + paquetesEntrada + ", kilosEntrada=" + kilosEntrada + ", idConvenio=" + idConvenio + ", convenio=" + convenio + '}';
    }


    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
