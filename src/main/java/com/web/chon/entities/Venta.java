/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marcogante
 */
@Entity
@Table(name = "VENTA", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdVentaPk", query = "SELECT v FROM Venta v WHERE v.idVentaPk = :idVentaPk"),
    @NamedQuery(name = "Venta.findByFechaVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "Venta.findByFechaPromesaPago", query = "SELECT v FROM Venta v WHERE v.fechaPromesaPago = :fechaPromesaPago")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VENTA_PK")
    private BigDecimal idVentaPk;
    @Column(name = "FECHA_VENTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    @Column(name = "FECHA_PROMESA_PAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPromesaPago;
    @JoinColumn(name = "ID_CLIENTE_FK", referencedColumnName = "ID_USUARIO_PK")
    @ManyToOne
    private Usuario idClienteFk;
    @JoinColumn(name = "ID_VENDEDOR_FK", referencedColumnName = "ID_USUARIO_PK")
    @ManyToOne
    private Usuario idVendedorFk;

    public Venta() {
    }

    public Venta(BigDecimal idVentaPk) {
        this.idVentaPk = idVentaPk;
    }

    public BigDecimal getIdVentaPk() {
        return idVentaPk;
    }

    public void setIdVentaPk(BigDecimal idVentaPk) {
        this.idVentaPk = idVentaPk;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaPromesaPago() {
        return fechaPromesaPago;
    }

    public void setFechaPromesaPago(Date fechaPromesaPago) {
        this.fechaPromesaPago = fechaPromesaPago;
    }

    public Usuario getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(Usuario idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public Usuario getIdVendedorFk() {
        return idVendedorFk;
    }

    public void setIdVendedorFk(Usuario idVendedorFk) {
        this.idVendedorFk = idVendedorFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaPk != null ? idVentaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVentaPk == null && other.idVentaPk != null) || (this.idVentaPk != null && !this.idVentaPk.equals(other.idVentaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.Venta[ idVentaPk=" + idVentaPk + " ]";
    }
    
}
