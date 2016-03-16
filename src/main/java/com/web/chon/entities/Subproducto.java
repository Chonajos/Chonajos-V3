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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "SUBPRODUCTO", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subproducto.findAll", query = "SELECT s FROM Subproducto s"),
    @NamedQuery(name = "Subproducto.findByIdSubproductoPk", query = "SELECT s FROM Subproducto s WHERE s.idSubproductoPk = :idSubproductoPk"),
    @NamedQuery(name = "Subproducto.findByNombreSubproducto", query = "SELECT s FROM Subproducto s WHERE s.nombreSubproducto = :nombreSubproducto"),
    @NamedQuery(name = "Subproducto.findByDescripcionSubproducto", query = "SELECT s FROM Subproducto s WHERE s.descripcionSubproducto = :descripcionSubproducto"),
    @NamedQuery(name = "Subproducto.findByUrlImagenSubproducto", query = "SELECT s FROM Subproducto s WHERE s.urlImagenSubproducto = :urlImagenSubproducto"),
    @NamedQuery(name = "Subproducto.findByPrecioMinimo", query = "SELECT s FROM Subproducto s WHERE s.precioMinimo = :precioMinimo"),
    @NamedQuery(name = "Subproducto.findByPrecioMaximo", query = "SELECT s FROM Subproducto s WHERE s.precioMaximo = :precioMaximo")})
public class Subproducto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SUBPRODUCTO_PK")
    private BigDecimal idSubproductoPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "NOMBRE_SUBPRODUCTO")
    private String nombreSubproducto;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_SUBPRODUCTO")
    private String descripcionSubproducto;
    @Size(max = 255)
    @Column(name = "URL_IMAGEN_SUBPRODUCTO")
    private String urlImagenSubproducto;
    @Column(name = "PRECIO_MINIMO")
    private BigInteger precioMinimo;
    @Column(name = "PRECIO_MAXIMO")
    private BigInteger precioMaximo;
    @JoinColumn(name = "ID_PRODUCTO_FK", referencedColumnName = "ID_PRODUCTO_PK")
    @ManyToOne
    private Producto idProductoFk;

    public Subproducto() {
    }

    public Subproducto(BigDecimal idSubproductoPk) {
        this.idSubproductoPk = idSubproductoPk;
    }

    public Subproducto(BigDecimal idSubproductoPk, String nombreSubproducto) {
        this.idSubproductoPk = idSubproductoPk;
        this.nombreSubproducto = nombreSubproducto;
    }

    public BigDecimal getIdSubproductoPk() {
        return idSubproductoPk;
    }

    public void setIdSubproductoPk(BigDecimal idSubproductoPk) {
        this.idSubproductoPk = idSubproductoPk;
    }

    public String getNombreSubproducto() {
        return nombreSubproducto;
    }

    public void setNombreSubproducto(String nombreSubproducto) {
        this.nombreSubproducto = nombreSubproducto;
    }

    public String getDescripcionSubproducto() {
        return descripcionSubproducto;
    }

    public void setDescripcionSubproducto(String descripcionSubproducto) {
        this.descripcionSubproducto = descripcionSubproducto;
    }

    public String getUrlImagenSubproducto() {
        return urlImagenSubproducto;
    }

    public void setUrlImagenSubproducto(String urlImagenSubproducto) {
        this.urlImagenSubproducto = urlImagenSubproducto;
    }

    public BigInteger getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(BigInteger precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public BigInteger getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(BigInteger precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Producto getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(Producto idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubproductoPk != null ? idSubproductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subproducto)) {
            return false;
        }
        Subproducto other = (Subproducto) object;
        if ((this.idSubproductoPk == null && other.idSubproductoPk != null) || (this.idSubproductoPk != null && !this.idSubproductoPk.equals(other.idSubproductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Subproducto[ idSubproductoPk=" + idSubproductoPk + " ]";
    }
    
}
