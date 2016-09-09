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
 * @author JesusAlfredo
 */
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCajaPk;
    private BigDecimal idSucursalFk;
    private String nombre;
    private BigDecimal tipo;
    private BigDecimal cuenta;
    private BigDecimal monto;
    private BigDecimal montoMenudeo;
    private BigDecimal montoMayoreo;
    private BigDecimal montoCredito;
    private BigDecimal idUsuarioFK;
    private BigDecimal cantCheques;
    private BigDecimal montoCheques;
    private BigDecimal montoAnticipos;
    private BigDecimal transferencias_IN;
    private BigDecimal transferencias_OUT;
    private BigDecimal servicios;
    private BigDecimal provedores;
    private BigDecimal apertura;
    private BigDecimal prestamos;
    private BigDecimal faltante;
    private BigDecimal sobrante;
    private BigDecimal saldoAnterior;

    @Override
    public String toString() {
        return "Caja{" + "idCajaPk=" + idCajaPk + ", idSucursalFk=" + idSucursalFk + ", nombre=" + nombre + ", tipo=" + tipo + ", cuenta=" + cuenta + ", monto=" + monto + '}';
    }

    public void reset() 
    {
        idCajaPk = null;
        faltante=null;
        sobrante=null;
        idSucursalFk = null;
        nombre = null;
        tipo = null;
        cuenta = null;
        monto = null;
        montoCheques = null;
        montoAnticipos = null;
        transferencias_IN = null;
        transferencias_OUT = null;
        servicios = null;
        provedores = null;
        cantCheques=null;
        apertura=null;
        prestamos=null;
        saldoAnterior=null;
    }

    public BigDecimal getFaltante() {
        return faltante;
    }

    public void setFaltante(BigDecimal faltante) {
        this.faltante = faltante;
    }

    public BigDecimal getSobrante() {
        return sobrante;
    }

    public void setSobrante(BigDecimal sobrante) {
        this.sobrante = sobrante;
    }

    
    public BigDecimal getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(BigDecimal prestamos) {
        this.prestamos = prestamos;
    }

    public BigDecimal getApertura() {
        return apertura;
    }

    public void setApertura(BigDecimal apertura) {
        this.apertura = apertura;
    }

    public BigDecimal getCantCheques() {
        return cantCheques;
    }

    public void setCantCheques(BigDecimal cantCheques) {
        this.cantCheques = cantCheques;
    }
    

    public BigDecimal getIdUsuarioFK() {
        return idUsuarioFK;
    }

    public void setIdUsuarioFK(BigDecimal idUsuarioFK) {
        this.idUsuarioFK = idUsuarioFK;
    }

    public BigDecimal getIdCajaPk() {
        return idCajaPk;
    }

    public void setIdCajaPk(BigDecimal idCajaPk) {
        this.idCajaPk = idCajaPk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCuenta() {
        return cuenta;
    }

    public void setCuenta(BigDecimal cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoMenudeo() {
        return montoMenudeo;
    }

    public void setMontoMenudeo(BigDecimal montoMenudeo) {
        this.montoMenudeo = montoMenudeo;
    }

    public BigDecimal getMontoMayoreo() {
        return montoMayoreo;
    }

    public void setMontoMayoreo(BigDecimal montoMayoreo) {
        this.montoMayoreo = montoMayoreo;
    }

    public BigDecimal getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(BigDecimal montoCredito) {
        this.montoCredito = montoCredito;
    }

    public BigDecimal getMontoCheques() {
        return montoCheques;
    }

    public void setMontoCheques(BigDecimal montoCheques) {
        this.montoCheques = montoCheques;
    }

    public BigDecimal getMontoAnticipos() {
        return montoAnticipos;
    }

    public void setMontoAnticipos(BigDecimal montoAnticipos) {
        this.montoAnticipos = montoAnticipos;
    }

    public BigDecimal getTransferencias_IN() {
        return transferencias_IN;
    }

    public void setTransferencias_IN(BigDecimal transferencias_IN) {
        this.transferencias_IN = transferencias_IN;
    }

    public BigDecimal getTransferencias_OUT() {
        return transferencias_OUT;
    }

    public void setTransferencias_OUT(BigDecimal transferencias_OUT) {
        this.transferencias_OUT = transferencias_OUT;
    }

    public BigDecimal getServicios() {
        return servicios;
    }

    public void setServicios(BigDecimal servicios) {
        this.servicios = servicios;
    }

    public BigDecimal getProvedores() {
        return provedores;
    }

    public void setProvedores(BigDecimal provedores) {
        this.provedores = provedores;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }
    

     
}
