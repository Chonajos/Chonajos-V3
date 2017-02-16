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
 * @author JesusAlfredo
 */
public class SaldosDeudas implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal folioCredito;
    private BigDecimal folioVenta;
    private BigDecimal montoTotal;
    private BigDecimal totalAbonado;
    private Date fechaVenta;
    private BigDecimal montoAbonar;
    private Date fechaProximaAbonar;
    private String periodo;
    private BigDecimal plazo;
    private BigDecimal periodosAtraso;
    private int diasAtraso;
    private BigDecimal saldoDeudor;
    private BigDecimal saldoAtrasado;
    private BigDecimal idEstatus;
    private String nombreStatus;
    private BigDecimal saldoTotal;
    private BigDecimal tipoCredito;
    private BigDecimal saldoLiquidar;
    private BigDecimal saldoACuenta;
    private BigDecimal statusAcuenta;
    private BigDecimal minimoPago;
    private BigDecimal numeroPagos;
    private BigDecimal statusFechaProxima;
    private BigDecimal chequesPorCobrar;

    //Datos para consula de Creditos
    private BigDecimal numeroTelefono;
    private String nombreCompleto;
    private String correo;
    private int dias;
    private int filtro;
    private Date fechaPromesaFinPago;
    private String nombreSucursal;
    private BigDecimal idSucursal;
    private BigDecimal idCliente;
    private BigDecimal abonarTemporal;
    private ArrayList<SaldosDeudas> lstSaldosDeudas;
    private BigDecimal idVentaMayoreoFk;
    private BigDecimal idVentaMenudeoFk;
    private byte[] fichero;

    public void reset() {
        folioCredito = null;
        folioVenta = null;
        montoTotal = null;
        totalAbonado = null;
        fechaVenta = null;
        montoAbonar = null;
        fechaProximaAbonar = null;
        periodo = null;
        plazo = null;
        periodosAtraso = null;
        diasAtraso = 0;
        saldoDeudor = null;
        saldoAtrasado = null;
        idEstatus = null;
        nombreStatus = null;
        saldoTotal = null;
        tipoCredito = null;
        saldoLiquidar = null;
        saldoACuenta = null;
        statusAcuenta = null;
        minimoPago = null;
        numeroPagos = null;
        statusFechaProxima = null;
        chequesPorCobrar = null;
        numeroTelefono = new BigDecimal(0);
        nombreCompleto = null;
        correo = null;
        dias = 0;
        filtro = 0;
        fechaPromesaFinPago = null;
        nombreSucursal = null;
        idSucursal = null;
        abonarTemporal = null;

    }

    @Override
    public String toString() {
        return "SaldosDeudas{" + "folioCredito=" + folioCredito + ", folioVenta=" + folioVenta + ", montoTotal=" + montoTotal + ", totalAbonado=" + totalAbonado + ", fechaVenta=" + fechaVenta + ", montoAbonar=" + montoAbonar + ", fechaProximaAbonar=" + fechaProximaAbonar + ", periodo=" + periodo + ", plazo=" + plazo + ", periodosAtraso=" + periodosAtraso + ", diasAtraso=" + diasAtraso + ", saldoDeudor=" + saldoDeudor + ", saldoAtrasado=" + saldoAtrasado + ", idEstatus=" + idEstatus + ", nombreStatus=" + nombreStatus + ", saldoTotal=" + saldoTotal + ", tipoCredito=" + tipoCredito + ", saldoLiquidar=" + saldoLiquidar + ", saldoACuenta=" + saldoACuenta + ", statusAcuenta=" + statusAcuenta + ", minimoPago=" + minimoPago + ", numeroPagos=" + numeroPagos + ", statusFechaProxima=" + statusFechaProxima + ", chequesPorCobrar=" + chequesPorCobrar + ", numeroTelefono=" + numeroTelefono + ", nombreCompleto=" + nombreCompleto + ", correo=" + correo + '}';
    }

    public BigDecimal getAbonarTemporal() {
        return abonarTemporal;
    }

    public void setAbonarTemporal(BigDecimal abonarTemporal) {
        this.abonarTemporal = abonarTemporal;
    }

    public BigDecimal getChequesPorCobrar() {
        return chequesPorCobrar;
    }

    public void setChequesPorCobrar(BigDecimal chequesPorCobrar) {
        this.chequesPorCobrar = chequesPorCobrar;
    }

    public BigDecimal getNumeroPagos() {
        return numeroPagos;
    }

    public BigDecimal getStatusFechaProxima() {
        return statusFechaProxima;
    }

    public void setStatusFechaProxima(BigDecimal statusFechaProxima) {
        this.statusFechaProxima = statusFechaProxima;
    }

    public void setNumeroPagos(BigDecimal numeroPagos) {
        this.numeroPagos = numeroPagos;
    }

    public BigDecimal getMinimoPago() {
        return minimoPago;
    }

    public void setMinimoPago(BigDecimal minimoPago) {
        this.minimoPago = minimoPago;
    }

    public BigDecimal getSaldoACuenta() {
        return saldoACuenta;
    }

    public void setSaldoACuenta(BigDecimal saldoACuenta) {
        this.saldoACuenta = saldoACuenta;
    }

    public BigDecimal getStatusAcuenta() {
        return statusAcuenta;
    }

    public void setStatusAcuenta(BigDecimal statusAcuenta) {
        this.statusAcuenta = statusAcuenta;
    }

    public BigDecimal getSaldoLiquidar() {
        return saldoLiquidar;
    }

    public void setSaldoLiquidar(BigDecimal saldoLiquidar) {
        this.saldoLiquidar = saldoLiquidar;
    }

    public BigDecimal getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(BigDecimal idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public BigDecimal getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(BigDecimal tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public BigDecimal getFolioCredito() {
        return folioCredito;
    }

    public void setFolioCredito(BigDecimal folioCredito) {
        this.folioCredito = folioCredito;
    }

    public BigDecimal getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(BigDecimal folioVenta) {
        this.folioVenta = folioVenta;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getMontoAbonar() {
        return montoAbonar;
    }

    public void setMontoAbonar(BigDecimal montoAbonar) {
        this.montoAbonar = montoAbonar;
    }

    public Date getFechaProximaAbonar() {
        return fechaProximaAbonar;
    }

    public void setFechaProximaAbonar(Date fechaProximaAbonar) {
        this.fechaProximaAbonar = fechaProximaAbonar;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getPeriodosAtraso() {
        return periodosAtraso;
    }

    public void setPeriodosAtraso(BigDecimal periodosAtraso) {
        this.periodosAtraso = periodosAtraso;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public BigDecimal getSaldoDeudor() {
        return saldoDeudor;
    }

    public void setSaldoDeudor(BigDecimal saldoDeudor) {
        this.saldoDeudor = saldoDeudor;
    }

    public BigDecimal getSaldoAtrasado() {
        return saldoAtrasado;
    }

    public void setSaldoAtrasado(BigDecimal saldoAtrasado) {
        this.saldoAtrasado = saldoAtrasado;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public BigDecimal getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(BigDecimal numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public Date getFechaPromesaFinPago() {
        return fechaPromesaFinPago;
    }

    public void setFechaPromesaFinPago(Date fechaPromesaFinPago) {
        this.fechaPromesaFinPago = fechaPromesaFinPago;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public ArrayList<SaldosDeudas> getLstSaldosDeudas() {
        return lstSaldosDeudas;
    }

    public void setLstSaldosDeudas(ArrayList<SaldosDeudas> lstSaldosDeudas) {
        this.lstSaldosDeudas = lstSaldosDeudas;
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
    }

    public BigDecimal getIdVentaMayoreoFk() {
        return idVentaMayoreoFk;
    }

    public void setIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk) {
        this.idVentaMayoreoFk = idVentaMayoreoFk;
    }

    public BigDecimal getIdVentaMenudeoFk() {
        return idVentaMenudeoFk;
    }

    public void setIdVentaMenudeoFk(BigDecimal idVentaMenudeoFk) {
        this.idVentaMenudeoFk = idVentaMenudeoFk;
    }
    
    
    
    
    
    

}
