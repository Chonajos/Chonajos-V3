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
 * @author marcogante
 */
public class ExistenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idExistenciaProductoPk;
    private BigInteger kilosExistencia;
    private BigInteger cantidadEmpaque;
    private BigInteger kilosEmpaque;
    private Camion idCamionFk;
    private Producto idProductoFk;
    private Sucursal idSucursalFk;
    private TipoEmpaque idTipoEmpaque;

    public ExistenciaProducto() {
    }

    public ExistenciaProducto(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigDecimal getIdExistenciaProductoPk() {
        return idExistenciaProductoPk;
    }

    public void setIdExistenciaProductoPk(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigInteger getKilosExistencia() {
        return kilosExistencia;
    }

    public void setKilosExistencia(BigInteger kilosExistencia) {
        this.kilosExistencia = kilosExistencia;
    }

    public BigInteger getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigInteger cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigInteger getKilosEmpaque() {
        return kilosEmpaque;
    }

    public void setKilosEmpaque(BigInteger kilosEmpaque) {
        this.kilosEmpaque = kilosEmpaque;
    }

    public Camion getIdCamionFk() {
        return idCamionFk;
    }

    public void setIdCamionFk(Camion idCamionFk) {
        this.idCamionFk = idCamionFk;
    }

    public Producto getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(Producto idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    public Sucursal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(Sucursal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public TipoEmpaque getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(TipoEmpaque idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExistenciaProductoPk != null ? idExistenciaProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExistenciaProducto)) {
            return false;
        }
        ExistenciaProducto other = (ExistenciaProducto) object;
        if ((this.idExistenciaProductoPk == null && other.idExistenciaProductoPk != null) || (this.idExistenciaProductoPk != null && !this.idExistenciaProductoPk.equals(other.idExistenciaProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.ExistenciaProducto[ idExistenciaProductoPk=" + idExistenciaProductoPk + " ]";
    }
    
}
