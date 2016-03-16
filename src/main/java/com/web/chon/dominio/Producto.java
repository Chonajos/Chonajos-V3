/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Juan de la Cruz
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idProductoPk;
    private String nombreProducto;
    private String descripcionProducto;
    private List<VentaProducto> ventaProductoList;
    private List<ExistenciaProducto> existenciaProductoList;
    private List<Subproducto> subproductoList;

    public Producto() {
    }

    public Producto(String idProductoPk) {
        this.idProductoPk = idProductoPk;
    }

    public Producto(String idProductoPk, String nombreProducto) {
        this.idProductoPk = idProductoPk;
        this.nombreProducto = nombreProducto;
    }

    public String getIdProductoPk() {
        return idProductoPk;
    }

    public void setIdProductoPk(String idProductoPk) {
        this.idProductoPk = idProductoPk;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
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

    public List<Subproducto> getSubproductoList() {
        return subproductoList;
    }

    public void setSubproductoList(List<Subproducto> subproductoList) {
        this.subproductoList = subproductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductoPk != null ? idProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProductoPk == null && other.idProductoPk != null) || (this.idProductoPk != null && !this.idProductoPk.equals(other.idProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProductoPk=" + idProductoPk + ", nombreProducto=" + nombreProducto + ", descripcionProducto=" + descripcionProducto + ", ventaProductoList=" + ventaProductoList + ", existenciaProductoList=" + existenciaProductoList + ", subproductoList=" + subproductoList + '}';
    }
  
    
}
