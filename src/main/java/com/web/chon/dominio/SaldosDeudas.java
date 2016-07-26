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
    private String diasAtraso;
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
        diasAtraso = null;
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
        statusFechaProxima=null;
        chequesPorCobrar=null;

    }

    @Override
    public String toString() {
        return "SaldosDeudas{" + "folioCredito=" + folioCredito + ", folioVenta=" + folioVenta + ", montoTotal=" + montoTotal + ", totalAbonado=" + totalAbonado + ", fechaVenta=" + fechaVenta + ", montoAbonar=" + montoAbonar + ", fechaProximaAbonar=" + fechaProximaAbonar + ", periodo=" + periodo + ", plazo=" + plazo + ", periodosAtraso=" + periodosAtraso + ", diasAtraso=" + diasAtraso + ", saldoDeudor=" + saldoDeudor + ", saldoAtrasado=" + saldoAtrasado + ", idEstatus=" + idEstatus + ", nombreStatus=" + nombreStatus + ", saldoTotal=" + saldoTotal + ", tipoCredito=" + tipoCredito + ", saldoLiquidar=" + saldoLiquidar + ", saldoACuenta=" + saldoACuenta + ", statusAcuenta=" + statusAcuenta + ", minimoPago=" + minimoPago + ", numeroPagos=" + numeroPagos + '}';
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

    public String getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso) {
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

}
