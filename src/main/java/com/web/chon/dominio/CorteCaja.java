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
public class CorteCaja implements Serializable{
    private BigDecimal idCorteCajaPk;
    private BigDecimal idCajaFk;
    private BigDecimal ventasMayoreo;
    private BigDecimal ventasMenudeo;
    private BigDecimal abonosCreditos;
    private BigDecimal anticipos;
    private BigDecimal cantCheques;
    private BigDecimal montoCheques;
    private BigDecimal transferenciasIN;
    private BigDecimal transferenciasOUT;
    private BigDecimal servicios;
    private BigDecimal prestamos;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoNuevo;
    private BigDecimal idUserFk;
    private String comentarios;
    private Date fecha;

    @Override
    public String toString() {
        return "CorteCaja{" + "idCorteCajaPk=" + idCorteCajaPk + ", idCajaFk=" + idCajaFk + ", ventasMayoreo=" + ventasMayoreo + ", ventasMenudeo=" + ventasMenudeo + ", abonosCreditos=" + abonosCreditos + ", anticipos=" + anticipos + ", cantCheques=" + cantCheques + ", montoCheques=" + montoCheques + ", transferenciasIN=" + transferenciasIN + ", transferenciasOUT=" + transferenciasOUT + ", servicios=" + servicios + ", prestamos=" + prestamos + ", saldoAnterior=" + saldoAnterior + ", saldoNuevo=" + saldoNuevo + ", idUserFk=" + idUserFk + ", comentarios=" + comentarios + '}';
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
   

    
    
    public BigDecimal getIdCorteCajaPk() {
        return idCorteCajaPk;
    }

    public void setIdCorteCajaPk(BigDecimal idCorteCajaPk) {
        this.idCorteCajaPk = idCorteCajaPk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public BigDecimal getVentasMayoreo() {
        return ventasMayoreo;
    }

    public void setVentasMayoreo(BigDecimal ventasMayoreo) {
        this.ventasMayoreo = ventasMayoreo;
    }

    public BigDecimal getVentasMenudeo() {
        return ventasMenudeo;
    }

    public void setVentasMenudeo(BigDecimal ventasMenudeo) {
        this.ventasMenudeo = ventasMenudeo;
    }

    public BigDecimal getAbonosCreditos() {
        return abonosCreditos;
    }

    public void setAbonosCreditos(BigDecimal abonosCreditos) {
        this.abonosCreditos = abonosCreditos;
    }

    public BigDecimal getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(BigDecimal anticipos) {
        this.anticipos = anticipos;
    }

    public BigDecimal getCantCheques() {
        return cantCheques;
    }

    public void setCantCheques(BigDecimal cantCheques) {
        this.cantCheques = cantCheques;
    }

    public BigDecimal getMontoCheques() {
        return montoCheques;
    }

    public void setMontoCheques(BigDecimal montoCheques) {
        this.montoCheques = montoCheques;
    }

    public BigDecimal getTransferenciasIN() {
        return transferenciasIN;
    }

    public void setTransferenciasIN(BigDecimal transferenciasIN) {
        this.transferenciasIN = transferenciasIN;
    }

    public BigDecimal getTransferenciasOUT() {
        return transferenciasOUT;
    }

    public void setTransferenciasOUT(BigDecimal transferenciasOUT) {
        this.transferenciasOUT = transferenciasOUT;
    }

    public BigDecimal getServicios() {
        return servicios;
    }

    public void setServicios(BigDecimal servicios) {
        this.servicios = servicios;
    }

    public BigDecimal getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(BigDecimal prestamos) {
        this.prestamos = prestamos;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public BigDecimal getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigDecimal idUserFk) {
        this.idUserFk = idUserFk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
