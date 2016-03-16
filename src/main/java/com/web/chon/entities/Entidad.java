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
@Table(name = "ENTIDAD", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e"),
    @NamedQuery(name = "Entidad.findByIdEntidadPk", query = "SELECT e FROM Entidad e WHERE e.idEntidadPk = :idEntidadPk"),
    @NamedQuery(name = "Entidad.findByNombreEntidad", query = "SELECT e FROM Entidad e WHERE e.nombreEntidad = :nombreEntidad"),
    @NamedQuery(name = "Entidad.findByAbreabiacionEntidad", query = "SELECT e FROM Entidad e WHERE e.abreabiacionEntidad = :abreabiacionEntidad"),
    @NamedQuery(name = "Entidad.findByIdPais", query = "SELECT e FROM Entidad e WHERE e.idPais = :idPais")})
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ENTIDAD_PK")
    private BigDecimal idEntidadPk;
    @Size(max = 60)
    @Column(name = "NOMBRE_ENTIDAD")
    private String nombreEntidad;
    @Size(max = 10)
    @Column(name = "ABREABIACION_ENTIDAD")
    private String abreabiacionEntidad;
    @Size(max = 4)
    @Column(name = "ID_PAIS")
    private String idPais;
    @OneToMany(mappedBy = "idEntidadFk")
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

    @XmlTransient
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
