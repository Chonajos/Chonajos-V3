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
public class Camion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCamionPk;
    private String descripcion;
    private Long numeroFactura;
    private TipoCompra idTipoCompraFk;
    private Usuario idUsuarioFk;
    private List<ExistenciaProducto> existenciaProductoList;

    public Camion() {
    }

    public Camion(BigDecimal idCamionPk) {
        this.idCamionPk = idCamionPk;
    }

    public BigDecimal getIdCamionPk() {
        return idCamionPk;
    }

    public void setIdCamionPk(BigDecimal idCamionPk) {
        this.idCamionPk = idCamionPk;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public TipoCompra getIdTipoCompraFk() {
        return idTipoCompraFk;
    }

    public void setIdTipoCompraFk(TipoCompra idTipoCompraFk) {
        this.idTipoCompraFk = idTipoCompraFk;
    }

    public Usuario getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(Usuario idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public List<ExistenciaProducto> getExistenciaProductoList() {
        return existenciaProductoList;
    }

    public void setExistenciaProductoList(List<ExistenciaProducto> existenciaProductoList) {
        this.existenciaProductoList = existenciaProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCamionPk != null ? idCamionPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Camion)) {
            return false;
        }
        Camion other = (Camion) object;
        if ((this.idCamionPk == null && other.idCamionPk != null) || (this.idCamionPk != null && !this.idCamionPk.equals(other.idCamionPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Camion[ idCamionPk=" + idCamionPk + " ]";
    }
    
}
