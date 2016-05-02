package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Juan de la Cruz
 */
public class Rol extends ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idRolPk;
    private String nombreRol;
    private String descripcionRol;

    public Rol() {
    }

    public Rol(BigDecimal idRolPk) {
        this.idRolPk = idRolPk;
    }

    public Rol(BigDecimal idRolPk, String nombreRol) {
        this.idRolPk = idRolPk;
        this.nombreRol = nombreRol;
    }

    public BigDecimal getIdRolPk() {
        return idRolPk;
    }

    public void setIdRolPk(BigDecimal idRolPk) {
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
        return "Rol{" + "idRolPk=" + idRolPk + ", nombreRol=" + nombreRol + ", descripcionRol=" + descripcionRol + '}';
    }


    @Override
    public void reset() {

        idRolPk = null;
        nombreRol = null;
        descripcionRol = null;
    }

}
