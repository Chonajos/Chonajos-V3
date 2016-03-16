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
@Table(name = "TIPO_VENTA", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoVenta.findAll", query = "SELECT t FROM TipoVenta t"),
    @NamedQuery(name = "TipoVenta.findByIdTipoVentaPk", query = "SELECT t FROM TipoVenta t WHERE t.idTipoVentaPk = :idTipoVentaPk"),
    @NamedQuery(name = "TipoVenta.findByNombreTipoVenta", query = "SELECT t FROM TipoVenta t WHERE t.nombreTipoVenta = :nombreTipoVenta")})
public class TipoVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_VENTA_PK")
    private BigDecimal idTipoVentaPk;
    @Size(max = 120)
    @Column(name = "NOMBRE_TIPO_VENTA")
    private String nombreTipoVenta;
    @OneToMany(mappedBy = "idTipoVentaFk")
    private List<VentaProducto> ventaProductoList;

    public TipoVenta() {
    }

    public TipoVenta(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public BigDecimal getIdTipoVentaPk() {
        return idTipoVentaPk;
    }

    public void setIdTipoVentaPk(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }

    @XmlTransient
    public List<VentaProducto> getVentaProductoList() {
        return ventaProductoList;
    }

    public void setVentaProductoList(List<VentaProducto> ventaProductoList) {
        this.ventaProductoList = ventaProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoVentaPk != null ? idTipoVentaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVenta)) {
            return false;
        }
        TipoVenta other = (TipoVenta) object;
        if ((this.idTipoVentaPk == null && other.idTipoVentaPk != null) || (this.idTipoVentaPk != null && !this.idTipoVentaPk.equals(other.idTipoVentaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.TipoVenta[ idTipoVentaPk=" + idTipoVentaPk + " ]";
    }
    
}
