/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan de la Cruz
 */
public class TipoEmpaque implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoEmpaquePk;
    private String nombreEmpaque;
    private boolean estatus;
    private List<VentaProducto> ventaProductoList;
    private List<ExistenciaProducto> existenciaProductoList;

    public TipoEmpaque() {
    }

    public TipoEmpaque(BigDecimal idTipoEmpaquePk) {
        this.idTipoEmpaquePk = idTipoEmpaquePk;
    }

    public BigDecimal getIdTipoEmpaquePk() {
        return idTipoEmpaquePk;
    }

    public void setIdTipoEmpaquePk(BigDecimal idTipoEmpaquePk) {
        this.idTipoEmpaquePk = idTipoEmpaquePk;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public List<VentaProducto> getVentaProductoList() {
        return ventaProductoList;
    }

    public void setVentaProductoList(List<VentaProducto> ventaProductoList) {
        this.ventaProductoList = ventaProductoList;
    }

    public List<ExistenciaProducto> getExistenciaProductoList() {
        return existenciaProductoList;
    }

    public void setExistenciaProductoList(List<ExistenciaProducto> existenciaProductoList) {
        this.existenciaProductoList = existenciaProductoList;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEmpaquePk != null ? idTipoEmpaquePk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEmpaque)) {
            return false;
        }
        TipoEmpaque other = (TipoEmpaque) object;
        if ((this.idTipoEmpaquePk == null && other.idTipoEmpaquePk != null) || (this.idTipoEmpaquePk != null && !this.idTipoEmpaquePk.equals(other.idTipoEmpaquePk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoEmpaque[ idTipoEmpaquePk=" + idTipoEmpaquePk + " ]";
    }
    
}
