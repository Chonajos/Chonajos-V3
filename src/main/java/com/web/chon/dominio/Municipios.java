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
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idMunicipioPk;
    private String nombreMunicipio;
    private List<Usuario> usuarioList;
    private Entidad idEntidadFk;
    private List<Sucursal> sucursalList;

    public Municipios() {
    }

    public Municipios(BigDecimal idMunicipioPk) {
        this.idMunicipioPk = idMunicipioPk;
    }

    public BigDecimal getIdMunicipioPk() {
        return idMunicipioPk;
    }

    public void setIdMunicipioPk(BigDecimal idMunicipioPk) {
        this.idMunicipioPk = idMunicipioPk;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Entidad getIdEntidadFk() {
        return idEntidadFk;
    }

    public void setIdEntidadFk(Entidad idEntidadFk) {
        this.idEntidadFk = idEntidadFk;
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {
        this.sucursalList = sucursalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMunicipioPk != null ? idMunicipioPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.idMunicipioPk == null && other.idMunicipioPk != null) || (this.idMunicipioPk != null && !this.idMunicipioPk.equals(other.idMunicipioPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Municipios[ idMunicipioPk=" + idMunicipioPk + " ]";
    }
    
}
