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
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEntidadPk;
    private String nombreEntidad;
    private String abreabiacionEntidad;
    private String idPais;
    private List<Municipios> municipiosList;

    public Entidad() {
    }

    public Entidad(BigDecimal idEntidadPk) {
        this.idEntidadPk = idEntidadPk;
    }

    public BigDecimal getIdEntidadPk() {
        return idEntidadPk;
    }

    public void setIdEntidadPk(BigDecimal idEntidadPk) {
        this.idEntidadPk = idEntidadPk;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getAbreabiacionEntidad() {
        return abreabiacionEntidad;
    }

    public void setAbreabiacionEntidad(String abreabiacionEntidad) {
        this.abreabiacionEntidad = abreabiacionEntidad;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public List<Municipios> getMunicipiosList() {
        return municipiosList;
    }

    public void setMunicipiosList(List<Municipios> municipiosList) {
        this.municipiosList = municipiosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidadPk != null ? idEntidadPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.idEntidadPk == null && other.idEntidadPk != null) || (this.idEntidadPk != null && !this.idEntidadPk.equals(other.idEntidadPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Entidad[ idEntidadPk=" + idEntidadPk + " ]";
    }
    
}
