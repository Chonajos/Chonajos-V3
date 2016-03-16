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
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idSucursalPk;
    private String nombreSucursal;
    private String calleSucursal;
    private String coloniaSucursal;
    private Long telefonoSucursal;
    private List<ExistenciaProducto> existenciaProductoList;
    private Municipios idMunicipioFk;
    private Usuario idUsuarioSucursalFk;

    public Sucursal() {
    }

    public Sucursal(BigDecimal idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public BigDecimal getIdSucursalPk() {
        return idSucursalPk;
    }

    public void setIdSucursalPk(BigDecimal idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getCalleSucursal() {
        return calleSucursal;
    }

    public void setCalleSucursal(String calleSucursal) {
        this.calleSucursal = calleSucursal;
    }

    public String getColoniaSucursal() {
        return coloniaSucursal;
    }

    public void setColoniaSucursal(String coloniaSucursal) {
        this.coloniaSucursal = coloniaSucursal;
    }

    public Long getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(Long telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    public List<ExistenciaProducto> getExistenciaProductoList() {
        return existenciaProductoList;
    }

    public void setExistenciaProductoList(List<ExistenciaProducto> existenciaProductoList) {
        this.existenciaProductoList = existenciaProductoList;
    }

    public Municipios getIdMunicipioFk() {
        return idMunicipioFk;
    }

    public void setIdMunicipioFk(Municipios idMunicipioFk) {
        this.idMunicipioFk = idMunicipioFk;
    }

    public Usuario getIdUsuarioSucursalFk() {
        return idUsuarioSucursalFk;
    }

    public void setIdUsuarioSucursalFk(Usuario idUsuarioSucursalFk) {
        this.idUsuarioSucursalFk = idUsuarioSucursalFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursalPk != null ? idSucursalPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.idSucursalPk == null && other.idSucursalPk != null) || (this.idSucursalPk != null && !this.idSucursalPk.equals(other.idSucursalPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Sucursal[ idSucursalPk=" + idSucursalPk + " ]";
    }
    
}
