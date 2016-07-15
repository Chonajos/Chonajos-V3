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
    private String periodosAtraso;
    private String diasAtraso;
    private BigDecimal saldoDeudor;
    private BigDecimal saldoAtrasado;
    private BigDecimal idEstatus;
    private String nombreStatus;
    private BigDecimal saldoTotal;
    private BigDecimal tipoCredito;
    private BigDecimal saldoLiquidar;

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
        saldoLiquidar=null;
    }

    @Override
    public String toString() {
        return "SaldosDeudas{" + "folioCredito=" + folioCredito + ", folioVenta=" + folioVenta + ", montoTotal=" + montoTotal + ", totalAbonado=" + totalAbonado + ", fechaVenta=" + fechaVenta + ", montoAbonar=" + montoAbonar + ", fechaProximaAbonar=" + fechaProximaAbonar + ", periodo=" + periodo + ", plazo=" + plazo + ", periodosAtraso=" + periodosAtraso + ", diasAtraso=" + diasAtraso + ", saldoDeudor=" + saldoDeudor + ", saldoAtrasado=" + saldoAtrasado + ", idEstatus=" + idEstatus + ", nombreStatus=" + nombreStatus + ", saldoTotal=" + saldoTotal + ", tipoCredito=" + tipoCredito + ", saldoLiquidar=" + saldoLiquidar + '}';
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

    public String getPeriodosAtraso() {
        return periodosAtraso;
    }

    public void setPeriodosAtraso(String periodosAtraso) {
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
