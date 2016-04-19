/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author freddy
 */
public class ExistenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idExistenciaProductoPk;
    private BigDecimal idSucursalFk;
    private String idSubProductoFk;
    private BigDecimal kilosExistencia;
    private BigDecimal cantidadEmpaque;
    private BigDecimal idTipoEmpaque;
    private BigDecimal kilosEmpaque;
    private BigDecimal idBodegaFk;
    private BigDecimal idProvedorFk;

    @Override
    public String toString() {
        return "ExistenciaProducto{" + "idExistenciaProductoPk=" + idExistenciaProductoPk + ", idSucursalFk=" + idSucursalFk + ", idSubProductoFk=" + idSubProductoFk + ", kilosExistencia=" + kilosExistencia + ", cantidadEmpaque=" + cantidadEmpaque + ", idTipoEmpaque=" + idTipoEmpaque + ", kilosEmpaque=" + kilosEmpaque + ", idBodegaFk=" + idBodegaFk + ", idProvedorFk=" + idProvedorFk + '}';
    }
    
    

    public BigDecimal getIdProvedorFk() {
        return idProvedorFk;
    }

    public void setIdProvedorFk(BigDecimal idProvedorFk) {
        this.idProvedorFk = idProvedorFk;
    }

    
    
    
    public BigDecimal getIdExistenciaProductoPk() {
        return idExistenciaProductoPk;
    }

    public void setIdExistenciaProductoPk(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public String getIdSubProductoFk() {
        return idSubProductoFk;
    }

    public void setIdSubProductoFk(String idSubProductoFk) {
        this.idSubProductoFk = idSubProductoFk;
    }

   

    public BigDecimal getKilosExistencia() {
        return kilosExistencia;
    }

    public void setKilosExistencia(BigDecimal kilosExistencia) {
        this.kilosExistencia = kilosExistencia;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(BigDecimal idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    public BigDecimal getKilosEmpaque() {
        return kilosEmpaque;
    }

    public void setKilosEmpaque(BigDecimal kilosEmpaque) {
        this.kilosEmpaque = kilosEmpaque;
    }

    public BigDecimal getIdBodegaFk() {
        return idBodegaFk;
    }

    public void setIdBodegaFk(BigDecimal idBodegaFk) {
        this.idBodegaFk = idBodegaFk;
    }

    
}
