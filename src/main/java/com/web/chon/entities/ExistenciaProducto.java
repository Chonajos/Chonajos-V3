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
@Table(name = "EXISTENCIA_PRODUCTO", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExistenciaProducto.findAll", query = "SELECT e FROM ExistenciaProducto e"),
    @NamedQuery(name = "ExistenciaProducto.findByIdExistenciaProductoPk", query = "SELECT e FROM ExistenciaProducto e WHERE e.idExistenciaProductoPk = :idExistenciaProductoPk"),
    @NamedQuery(name = "ExistenciaProducto.findByKilosExistencia", query = "SELECT e FROM ExistenciaProducto e WHERE e.kilosExistencia = :kilosExistencia"),
    @NamedQuery(name = "ExistenciaProducto.findByCantidadEmpaque", query = "SELECT e FROM ExistenciaProducto e WHERE e.cantidadEmpaque = :cantidadEmpaque"),
    @NamedQuery(name = "ExistenciaProducto.findByKilosEmpaque", query = "SELECT e FROM ExistenciaProducto e WHERE e.kilosEmpaque = :kilosEmpaque")})
public class ExistenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EXISTENCIA_PRODUCTO_PK")
    private BigDecimal idExistenciaProductoPk;
    @Column(name = "KILOS_EXISTENCIA")
    private BigInteger kilosExistencia;
    @Column(name = "CANTIDAD_EMPAQUE")
    private BigInteger cantidadEmpaque;
    @Column(name = "KILOS_EMPAQUE")
    private BigInteger kilosEmpaque;
    @JoinColumn(name = "ID_CAMION_FK", referencedColumnName = "ID_CAMION_PK")
    @ManyToOne
    private Camion idCamionFk;
    @JoinColumn(name = "ID_PRODUCTO_FK", referencedColumnName = "ID_PRODUCTO_PK")
    @ManyToOne
    private Producto idProductoFk;
    @JoinColumn(name = "ID_SUCURSAL_FK", referencedColumnName = "ID_SUCURSAL_PK")
    @ManyToOne
    private Sucursal idSucursalFk;
    @JoinColumn(name = "ID_TIPO_EMPAQUE", referencedColumnName = "ID_TIPO_EMPAQUE_PK")
    @ManyToOne
    private TipoEmpaque idTipoEmpaque;

    public ExistenciaProducto() {
    }

    public ExistenciaProducto(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigDecimal getIdExistenciaProductoPk() {
        return idExistenciaProductoPk;
    }

    public void setIdExistenciaProductoPk(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigInteger getKilosExistencia() {
        return kilosExistencia;
    }

    public void setKilosExistencia(BigInteger kilosExistencia) {
        this.kilosExistencia = kilosExistencia;
    }

    public BigInteger getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigInteger cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigInteger getKilosEmpaque() {
        return kilosEmpaque;
    }

    public void setKilosEmpaque(BigInteger kilosEmpaque) {
        this.kilosEmpaque = kilosEmpaque;
    }

    public Camion getIdCamionFk() {
        return idCamionFk;
    }

    public void setIdCamionFk(Camion idCamionFk) {
        this.idCamionFk = idCamionFk;
    }

    public Producto getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(Producto idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    public Sucursal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(Sucursal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public TipoEmpaque getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(TipoEmpaque idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExistenciaProductoPk != null ? idExistenciaProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExistenciaProducto)) {
            return false;
        }
        ExistenciaProducto other = (ExistenciaProducto) object;
        if ((this.idExistenciaProductoPk == null && other.idExistenciaProductoPk != null) || (this.idExistenciaProductoPk != null && !this.idExistenciaProductoPk.equals(other.idExistenciaProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.ExistenciaProducto[ idExistenciaProductoPk=" + idExistenciaProductoPk + " ]";
    }
    
}
