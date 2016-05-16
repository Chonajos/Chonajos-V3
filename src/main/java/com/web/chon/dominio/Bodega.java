/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;

/**
 *
 * @author marcogante
 */
public class Bodega extends ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal idBodegaPK;
    private String nombreBodega;
    private String descripcionBodega;
    private BigDecimal idSucursalFk;
    private String nombreSucursal;

    @Override
    public String toString() {
        return "Bodega{" + "idBodegaPK=" + idBodegaPK + ", nombreBodega=" + nombreBodega + ", descripcionBodega=" + descripcionBodega + ", idSucursalFk=" + idSucursalFk + '}';
    }


    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    
    public BigDecimal getIdBodegaPK() {
        return idBodegaPK;
    }

    public void setIdBodegaPK(BigDecimal idBodegaPK) {
        this.idBodegaPK = idBodegaPK;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getDescripcionBodega() {
        return descripcionBodega;
    }

    public void setDescripcionBodega(String descripcionBodega) {
        this.descripcionBodega = descripcionBodega;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    
    

    @Override
    public void reset() {
        idBodegaPK = null;
        nombreBodega = null;
        descripcionBodega = null;
    }

}
