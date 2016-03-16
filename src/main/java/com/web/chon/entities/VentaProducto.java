/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "VENTA_PRODUCTO", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentaProducto.findAll", query = "SELECT v FROM VentaProducto v"),
    @NamedQuery(name = "VentaProducto.findByIdVentaProductoPk", query = "SELECT v FROM VentaProducto v WHERE v.idVentaProductoPk = :idVentaProductoPk"),
    @NamedQuery(name = "VentaProducto.findByPrecioProducto", query = "SELECT v FROM VentaProducto v WHERE v.precioProducto = :precioProducto"),
    @NamedQuery(name = "VentaProducto.findByKilosVendidos", query = "SELECT v FROM VentaProducto v WHERE v.kilosVendidos = :kilosVendidos")})
public class VentaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VENTA_PRODUCTO_PK")
    private BigDecimal idVentaProductoPk;
    @Column(name = "PRECIO_PRODUCTO")
    private BigInteger precioProducto;
    @Column(name = "KILOS_VENDIDOS")
    private BigInteger kilosVendidos;
    @JoinColumn(name = "ID_PRODUCTO_FK", referencedColumnName = "ID_PRODUCTO_PK")
    @ManyToOne
    private Producto idProductoFk;
    @JoinColumn(name = "ID_TIPO_EMPAQUE_FK", referencedColumnName = "ID_TIPO_EMPAQUE_PK")
    @ManyToOne
    private TipoEmpaque idTipoEmpaqueFk;
    @JoinColumn(name = "ID_TIPO_VENTA_FK", referencedColumnName = "ID_TIPO_VENTA_PK")
    @ManyToOne
    private TipoVenta idTipoVentaFk;

    public VentaProducto() {
    }

    public VentaProducto(BigDecimal idVentaProductoPk) {
        this.idVentaProductoPk = idVentaProductoPk;
    }

    public BigDecimal getIdVentaProductoPk() {
        return idVentaProductoPk;
    }

    public void setIdVentaProductoPk(BigDecimal idVentaProductoPk) {
        this.idVentaProductoPk = idVentaProductoPk;
    }

    public BigInteger getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigInteger precioProducto) {
        this.precioProducto = precioProducto;
    }

    public BigInteger getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigInteger kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public Producto getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(Producto idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    public TipoEmpaque getIdTipoEmpaqueFk() {
        return idTipoEmpaqueFk;
    }

    public void setIdTipoEmpaqueFk(TipoEmpaque idTipoEmpaqueFk) {
        this.idTipoEmpaqueFk = idTipoEmpaqueFk;
    }

    public TipoVenta getIdTipoVentaFk() {
        return idTipoVentaFk;
    }

    public void setIdTipoVentaFk(TipoVenta idTipoVentaFk) {
        this.idTipoVentaFk = idTipoVentaFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaProductoPk != null ? idVentaProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaProducto)) {
            return false;
        }
        VentaProducto other = (VentaProducto) object;
        if ((this.idVentaProductoPk == null && other.idVentaProductoPk != null) || (this.idVentaProductoPk != null && !this.idVentaProductoPk.equals(other.idVentaProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.VentaProducto[ idVentaProductoPk=" + idVentaProductoPk + " ]";
    }
    
}
