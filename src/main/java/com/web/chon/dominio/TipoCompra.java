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
 * @author marcogante
 */
public class TipoCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoCompraPk;
    private String descripcionTipoCompra;
    private String nombreTipoCompra;
    private List<Camion> camionList;

    public TipoCompra() {
    }

    public TipoCompra(BigDecimal idTipoCompraPk) {
        this.idTipoCompraPk = idTipoCompraPk;
    }

    public BigDecimal getIdTipoCompraPk() {
        return idTipoCompraPk;
    }

    public void setIdTipoCompraPk(BigDecimal idTipoCompraPk) {
        this.idTipoCompraPk = idTipoCompraPk;
    }

    public String getDescripcionTipoCompra() {
        return descripcionTipoCompra;
    }

    public void setDescripcionTipoCompra(String descripcionTipoCompra) {
        this.descripcionTipoCompra = descripcionTipoCompra;
    }

    public String getNombreTipoCompra() {
        return nombreTipoCompra;
    }

    public void setNombreTipoCompra(String nombreTipoCompra) {
        this.nombreTipoCompra = nombreTipoCompra;
    }

    public List<Camion> getCamionList() {
        return camionList;
    }

    public void setCamionList(List<Camion> camionList) {
        this.camionList = camionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCompraPk != null ? idTipoCompraPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCompra)) {
            return false;
        }
        TipoCompra other = (TipoCompra) object;
        if ((this.idTipoCompraPk == null && other.idTipoCompraPk != null) || (this.idTipoCompraPk != null && !this.idTipoCompraPk.equals(other.idTipoCompraPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoCompra[ idTipoCompraPk=" + idTipoCompraPk + " ]";
    }
    
}
