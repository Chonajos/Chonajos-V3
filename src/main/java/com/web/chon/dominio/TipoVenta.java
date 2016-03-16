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
public class TipoVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoVentaPk;
    private String nombreTipoVenta;
    private List<VentaProducto> ventaProductoList;

    public TipoVenta() {
    }

    public TipoVenta(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public BigDecimal getIdTipoVentaPk() {
        return idTipoVentaPk;
    }

    public void setIdTipoVentaPk(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }

    public List<VentaProducto> getVentaProductoList() {
        return ventaProductoList;
    }

    public void setVentaProductoList(List<VentaProducto> ventaProductoList) {
        this.ventaProductoList = ventaProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoVentaPk != null ? idTipoVentaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVenta)) {
            return false;
        }
        TipoVenta other = (TipoVenta) object;
        if ((this.idTipoVentaPk == null && other.idTipoVentaPk != null) || (this.idTipoVentaPk != null && !this.idTipoVentaPk.equals(other.idTipoVentaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoVenta[ idTipoVentaPk=" + idTipoVentaPk + " ]";
    }
    
}
