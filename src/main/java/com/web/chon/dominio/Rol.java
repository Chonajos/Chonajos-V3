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
 * @author marcogante
 */
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short idRolPk;
    private String nombreRol;
    private String descripcionRol;
    private List<Usuario> usuarioList;

    public Rol() {
    }

    public Rol(Short idRolPk) {
        this.idRolPk = idRolPk;
    }

    public Rol(Short idRolPk, String nombreRol) {
        this.idRolPk = idRolPk;
        this.nombreRol = nombreRol;
    }

    public Short getIdRolPk() {
        return idRolPk;
    }

    public void setIdRolPk(Short idRolPk) {
        this.idRolPk = idRolPk;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolPk != null ? idRolPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idRolPk == null && other.idRolPk != null) || (this.idRolPk != null && !this.idRolPk.equals(other.idRolPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Rol[ idRolPk=" + idRolPk + " ]";
    }
    
}
