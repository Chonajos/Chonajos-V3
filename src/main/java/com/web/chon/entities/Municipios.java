/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MUNICIPIOS", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m"),
    @NamedQuery(name = "Municipios.findByIdMunicipioPk", query = "SELECT m FROM Municipios m WHERE m.idMunicipioPk = :idMunicipioPk"),
    @NamedQuery(name = "Municipios.findByNombreMunicipio", query = "SELECT m FROM Municipios m WHERE m.nombreMunicipio = :nombreMunicipio")})
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MUNICIPIO_PK")
    private BigDecimal idMunicipioPk;
    @Size(max = 60)
    @Column(name = "NOMBRE_MUNICIPIO")
    private String nombreMunicipio;
    @OneToMany(mappedBy = "idMunicipioFk")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "ID_ENTIDAD_FK", referencedColumnName = "ID_ENTIDAD_PK")
    @ManyToOne
    private Entidad idEntidadFk;
    @OneToMany(mappedBy = "idMunicipioFk")
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

    @XmlTransient
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

    @XmlTransient
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
