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
@Table(name = "TIPO_EMPAQUE", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEmpaque.findAll", query = "SELECT t FROM TipoEmpaque t"),
    @NamedQuery(name = "TipoEmpaque.findByIdTipoEmpaquePk", query = "SELECT t FROM TipoEmpaque t WHERE t.idTipoEmpaquePk = :idTipoEmpaquePk"),
    @NamedQuery(name = "TipoEmpaque.findByNombreEmpaque", query = "SELECT t FROM TipoEmpaque t WHERE t.nombreEmpaque = :nombreEmpaque")})
public class TipoEmpaque implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_EMPAQUE_PK")
    private BigDecimal idTipoEmpaquePk;
    @Size(max = 120)
    @Column(name = "NOMBRE_EMPAQUE")
    private String nombreEmpaque;
    @OneToMany(mappedBy = "idTipoEmpaqueFk")
    private List<VentaProducto> ventaProductoList;
    @OneToMany(mappedBy = "idTipoEmpaque")
    private List<ExistenciaProducto> existenciaProductoList;

    public TipoEmpaque() {
    }

    public TipoEmpaque(BigDecimal idTipoEmpaquePk) {
        this.idTipoEmpaquePk = idTipoEmpaquePk;
    }

    public BigDecimal getIdTipoEmpaquePk() {
        return idTipoEmpaquePk;
    }

    public void setIdTipoEmpaquePk(BigDecimal idTipoEmpaquePk) {
        this.idTipoEmpaquePk = idTipoEmpaquePk;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    @XmlTransient
    public List<VentaProducto> getVentaProductoList() {
        return ventaProductoList;
    }

    public void setVentaProductoList(List<VentaProducto> ventaProductoList) {
        this.ventaProductoList = ventaProductoList;
    }

    @XmlTransient
    public List<ExistenciaProducto> getExistenciaProductoList() {
        return existenciaProductoList;
    }

    public void setExistenciaProductoList(List<ExistenciaProducto> existenciaProductoList) {
        this.existenciaProductoList = existenciaProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEmpaquePk != null ? idTipoEmpaquePk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEmpaque)) {
            return false;
        }
        TipoEmpaque other = (TipoEmpaque) object;
        if ((this.idTipoEmpaquePk == null && other.idTipoEmpaquePk != null) || (this.idTipoEmpaquePk != null && !this.idTipoEmpaquePk.equals(other.idTipoEmpaquePk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoEmpaque[ idTipoEmpaquePk=" + idTipoEmpaquePk + " ]";
    }
    
}
