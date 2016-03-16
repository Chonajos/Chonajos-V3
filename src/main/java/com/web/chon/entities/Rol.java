/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "ROL", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdRolPk", query = "SELECT r FROM Rol r WHERE r.idRolPk = :idRolPk"),
    @NamedQuery(name = "Rol.findByNombreRol", query = "SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol"),
    @NamedQuery(name = "Rol.findByDescripcionRol", query = "SELECT r FROM Rol r WHERE r.descripcionRol = :descripcionRol")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROL_PK")
    private Short idRolPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "NOMBRE_ROL")
    private String nombreRol;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_ROL")
    private String descripcionRol;
    @OneToMany(mappedBy = "idRolFk")
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

    @XmlTransient
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
