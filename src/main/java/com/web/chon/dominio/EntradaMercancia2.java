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
 * @author marcogante
 */
public class EntradaMercancia2 extends ValueObject implements Serializable {

    private BigDecimal idEmPK; 
    private BigDecimal idProvedorFK; 
    private int movimiento;
    private Date fecha;
    private String remision;
    private BigDecimal idSucursalFK;

    
    @Override
    public String toString() {
        return "EntradaMercancia2{" + "idEmPK=" + idEmPK + ", idProvedorFK=" + idProvedorFK + ", movimiento=" + movimiento + ", fecha=" + fecha + ", remision=" + remision + ", idSucursalFK=" + idSucursalFK + '}';
    }
    
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigDecimal getIdEmPK() {
        return idEmPK;
    }

    public void setIdEmPK(BigDecimal idEmPK) {
        this.idEmPK = idEmPK;
    }

    public BigDecimal getIdProvedorFK() {
        return idProvedorFK;
    }

    public void setIdProvedorFK(BigDecimal idProvedorFK) {
        this.idProvedorFK = idProvedorFK;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRemision() {
        return remision;
    }

    public void setRemision(String remision) {
        this.remision = remision;
    }

    public BigDecimal getIdSucursalFK() {
        return idSucursalFK;
    }

    public void setIdSucursalFK(BigDecimal idSucursalFK) {
        this.idSucursalFK = idSucursalFK;
    }
    
    
    
}
