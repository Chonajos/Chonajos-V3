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
@Table(name = "TIPO_COMPRA", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCompra.findAll", query = "SELECT t FROM TipoCompra t"),
    @NamedQuery(name = "TipoCompra.findByIdTipoCompraPk", query = "SELECT t FROM TipoCompra t WHERE t.idTipoCompraPk = :idTipoCompraPk"),
    @NamedQuery(name = "TipoCompra.findByDescripcionTipoCompra", query = "SELECT t FROM TipoCompra t WHERE t.descripcionTipoCompra = :descripcionTipoCompra"),
    @NamedQuery(name = "TipoCompra.findByNombreTipoCompra", query = "SELECT t FROM TipoCompra t WHERE t.nombreTipoCompra = :nombreTipoCompra")})
public class TipoCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_COMPRA_PK")
    private BigDecimal idTipoCompraPk;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_TIPO_COMPRA")
    private String descripcionTipoCompra;
    @Size(max = 90)
    @Column(name = "NOMBRE_TIPO_COMPRA")
    private String nombreTipoCompra;
    @OneToMany(mappedBy = "idTipoCompraFk")
    private List<Camion> camionList;

    public TipoCompra() {
    }

    public TipoCompra(BigDecimal idTipoCompraPk) {
        this.idTipoCompraPk = idTipoCompraPk;
    }

    public BigDecimal getIdTipoCompraPk() {
        return idTipoCompraPk;
    }

    public void setIdTipoCompraPk(BigDecimal idTipoCompraPk) {
        this.idTipoCompraPk = idTipoCompraPk;
    }

    public String getDescripcionTipoCompra() {
        return descripcionTipoCompra;
    }

    public void setDescripcionTipoCompra(String descripcionTipoCompra) {
        this.descripcionTipoCompra = descripcionTipoCompra;
    }

    public String getNombreTipoCompra() {
        return nombreTipoCompra;
    }

    public void setNombreTipoCompra(String nombreTipoCompra) {
        this.nombreTipoCompra = nombreTipoCompra;
    }

    @XmlTransient
    public List<Camion> getCamionList() {
        return camionList;
    }

    public void setCamionList(List<Camion> camionList) {
        this.camionList = camionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCompraPk != null ? idTipoCompraPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCompra)) {
            return false;
        }
        TipoCompra other = (TipoCompra) object;
        if ((this.idTipoCompraPk == null && other.idTipoCompraPk != null) || (this.idTipoCompraPk != null && !this.idTipoCompraPk.equals(other.idTipoCompraPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoCompra[ idTipoCompraPk=" + idTipoCompraPk + " ]";
    }
    
}
