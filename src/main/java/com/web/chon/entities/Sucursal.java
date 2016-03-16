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
@Table(name = "SUCURSAL", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
    @NamedQuery(name = "Sucursal.findByIdSucursalPk", query = "SELECT s FROM Sucursal s WHERE s.idSucursalPk = :idSucursalPk"),
    @NamedQuery(name = "Sucursal.findByNombreSucursal", query = "SELECT s FROM Sucursal s WHERE s.nombreSucursal = :nombreSucursal"),
    @NamedQuery(name = "Sucursal.findByCalleSucursal", query = "SELECT s FROM Sucursal s WHERE s.calleSucursal = :calleSucursal"),
    @NamedQuery(name = "Sucursal.findByColoniaSucursal", query = "SELECT s FROM Sucursal s WHERE s.coloniaSucursal = :coloniaSucursal"),
    @NamedQuery(name = "Sucursal.findByTelefonoSucursal", query = "SELECT s FROM Sucursal s WHERE s.telefonoSucursal = :telefonoSucursal")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SUCURSAL_PK")
    private BigDecimal idSucursalPk;
    @Size(max = 255)
    @Column(name = "NOMBRE_SUCURSAL")
    private String nombreSucursal;
    @Size(max = 255)
    @Column(name = "CALLE_SUCURSAL")
    private String calleSucursal;
    @Size(max = 255)
    @Column(name = "COLONIA_SUCURSAL")
    private String coloniaSucursal;
    @Column(name = "TELEFONO_SUCURSAL")
    private Long telefonoSucursal;
    @OneToMany(mappedBy = "idSucursalFk")
    private List<ExistenciaProducto> existenciaProductoList;
    @JoinColumn(name = "ID_MUNICIPIO_FK", referencedColumnName = "ID_MUNICIPIO_PK")
    @ManyToOne
    private Municipios idMunicipioFk;
    @JoinColumn(name = "ID_USUARIO_SUCURSAL_FK", referencedColumnName = "ID_USUARIO_PK")
    @ManyToOne
    private Usuario idUsuarioSucursalFk;

    public Sucursal() {
    }

    public Sucursal(BigDecimal idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public BigDecimal getIdSucursalPk() {
        return idSucursalPk;
    }

    public void setIdSucursalPk(BigDecimal idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getCalleSucursal() {
        return calleSucursal;
    }

    public void setCalleSucursal(String calleSucursal) {
        this.calleSucursal = calleSucursal;
    }

    public String getColoniaSucursal() {
        return coloniaSucursal;
    }

    public void setColoniaSucursal(String coloniaSucursal) {
        this.coloniaSucursal = coloniaSucursal;
    }

    public Long getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(Long telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    @XmlTransient
    public List<ExistenciaProducto> getExistenciaProductoList() {
        return existenciaProductoList;
    }

    public void setExistenciaProductoList(List<ExistenciaProducto> existenciaProductoList) {
        this.existenciaProductoList = existenciaProductoList;
    }

    public Municipios getIdMunicipioFk() {
        return idMunicipioFk;
    }

    public void setIdMunicipioFk(Municipios idMunicipioFk) {
        this.idMunicipioFk = idMunicipioFk;
    }

    public Usuario getIdUsuarioSucursalFk() {
        return idUsuarioSucursalFk;
    }

    public void setIdUsuarioSucursalFk(Usuario idUsuarioSucursalFk) {
        this.idUsuarioSucursalFk = idUsuarioSucursalFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursalPk != null ? idSucursalPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.idSucursalPk == null && other.idSucursalPk != null) || (this.idSucursalPk != null && !this.idSucursalPk.equals(other.idSucursalPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Sucursal[ idSucursalPk=" + idSucursalPk + " ]";
    }
    
}
