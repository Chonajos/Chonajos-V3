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
@Table(name = "PRODUCTO", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProductoPk", query = "SELECT p FROM Producto p WHERE p.idProductoPk = :idProductoPk"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByDescripcionProducto", query = "SELECT p FROM Producto p WHERE p.descripcionProducto = :descripcionProducto")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PRODUCTO_PK")
    private BigDecimal idProductoPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_PRODUCTO")
    private String descripcionProducto;
    @OneToMany(mappedBy = "idProductoFk")
    private List<VentaProducto> ventaProductoList;
    @OneToMany(mappedBy = "idProductoFk")
    private List<ExistenciaProducto> existenciaProductoList;
    @OneToMany(mappedBy = "idProductoFk")
    private List<Subproducto> subproductoList;

    public Producto() {
    }

    public Producto(BigDecimal idProductoPk) {
        this.idProductoPk = idProductoPk;
    }

    public Producto(BigDecimal idProductoPk, String nombreProducto) {
        this.idProductoPk = idProductoPk;
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getIdProductoPk() {
        return idProductoPk;
    }

    public void setIdProductoPk(BigDecimal idProductoPk) {
        this.idProductoPk = idProductoPk;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
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

    @XmlTransient
    public List<Subproducto> getSubproductoList() {
        return subproductoList;
    }

    public void setSubproductoList(List<Subproducto> subproductoList) {
        this.subproductoList = subproductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductoPk != null ? idProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProductoPk == null && other.idProductoPk != null) || (this.idProductoPk != null && !this.idProductoPk.equals(other.idProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Producto[ idProductoPk=" + idProductoPk + " ]";
    }
    
}
