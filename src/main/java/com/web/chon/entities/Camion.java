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
@Table(name = "CAMION", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Camion.findAll", query = "SELECT c FROM Camion c"),
    @NamedQuery(name = "Camion.findByIdCamionPk", query = "SELECT c FROM Camion c WHERE c.idCamionPk = :idCamionPk"),
    @NamedQuery(name = "Camion.findByDescripcion", query = "SELECT c FROM Camion c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Camion.findByNumeroFactura", query = "SELECT c FROM Camion c WHERE c.numeroFactura = :numeroFactura")})
public class Camion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CAMION_PK")
    private BigDecimal idCamionPk;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "NUMERO_FACTURA")
    private Long numeroFactura;
    @JoinColumn(name = "ID_TIPO_COMPRA_FK", referencedColumnName = "ID_TIPO_COMPRA_PK")
    @ManyToOne
    private TipoCompra idTipoCompraFk;
    @JoinColumn(name = "ID_USUARIO_FK", referencedColumnName = "ID_USUARIO_PK")
    @ManyToOne
    private Usuario idUsuarioFk;
    @OneToMany(mappedBy = "idCamionFk")
    private List<ExistenciaProducto> existenciaProductoList;

    public Camion() {
    }

    public Camion(BigDecimal idCamionPk) {
        this.idCamionPk = idCamionPk;
    }

    public BigDecimal getIdCamionPk() {
        return idCamionPk;
    }

    public void setIdCamionPk(BigDecimal idCamionPk) {
        this.idCamionPk = idCamionPk;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public TipoCompra getIdTipoCompraFk() {
        return idTipoCompraFk;
    }

    public void setIdTipoCompraFk(TipoCompra idTipoCompraFk) {
        this.idTipoCompraFk = idTipoCompraFk;
    }

    public Usuario getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(Usuario idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
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
        hash += (idCamionPk != null ? idCamionPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Camion)) {
            return false;
        }
        Camion other = (Camion) object;
        if ((this.idCamionPk == null && other.idCamionPk != null) || (this.idCamionPk != null && !this.idCamionPk.equals(other.idCamionPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Camion[ idCamionPk=" + idCamionPk + " ]";
    }
    
}
