/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CUENTA_BANCARIA", catalog = "", schema = "CHONAJOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBancaria.findAll", query = "SELECT c FROM CuentaBancaria c"),
    @NamedQuery(name = "CuentaBancaria.findByIdCuentaBancariaPk", query = "SELECT c FROM CuentaBancaria c WHERE c.idCuentaBancariaPk = :idCuentaBancariaPk"),
    @NamedQuery(name = "CuentaBancaria.findByNombreBanco", query = "SELECT c FROM CuentaBancaria c WHERE c.nombreBanco = :nombreBanco"),
    @NamedQuery(name = "CuentaBancaria.findByBenificiario", query = "SELECT c FROM CuentaBancaria c WHERE c.benificiario = :benificiario"),
    @NamedQuery(name = "CuentaBancaria.findByClaveInterbancaria", query = "SELECT c FROM CuentaBancaria c WHERE c.claveInterbancaria = :claveInterbancaria")})
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUENTA_BANCARIA_PK")
    private BigDecimal idCuentaBancariaPk;
    @Size(max = 120)
    @Column(name = "NOMBRE_BANCO")
    private String nombreBanco;
    @Size(max = 120)
    @Column(name = "BENIFICIARIO")
    private String benificiario;
    @Size(max = 60)
    @Column(name = "CLAVE_INTERBANCARIA")
    private String claveInterbancaria;
    @JoinColumn(name = "ID_USUARIO_FK", referencedColumnName = "ID_USUARIO_PK")
    @ManyToOne
    private Usuario idUsuarioFk;

    public CuentaBancaria() {
    }

    public CuentaBancaria(BigDecimal idCuentaBancariaPk) {
        this.idCuentaBancariaPk = idCuentaBancariaPk;
    }

    public BigDecimal getIdCuentaBancariaPk() {
        return idCuentaBancariaPk;
    }

    public void setIdCuentaBancariaPk(BigDecimal idCuentaBancariaPk) {
        this.idCuentaBancariaPk = idCuentaBancariaPk;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getBenificiario() {
        return benificiario;
    }

    public void setBenificiario(String benificiario) {
        this.benificiario = benificiario;
    }

    public String getClaveInterbancaria() {
        return claveInterbancaria;
    }

    public void setClaveInterbancaria(String claveInterbancaria) {
        this.claveInterbancaria = claveInterbancaria;
    }

    public Usuario getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(Usuario idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuentaBancariaPk != null ? idCuentaBancariaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaBancaria)) {
            return false;
        }
        CuentaBancaria other = (CuentaBancaria) object;
        if ((this.idCuentaBancariaPk == null && other.idCuentaBancariaPk != null) || (this.idCuentaBancariaPk != null && !this.idCuentaBancariaPk.equals(other.idCuentaBancariaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.chon.entities.CuentaBancaria[ idCuentaBancariaPk=" + idCuentaBancariaPk + " ]";
    }
    
}
