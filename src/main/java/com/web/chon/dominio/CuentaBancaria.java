/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author marcogante
 */
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCuentaBancariaPk;
    private String nombreBanco;
    private String benificiario;
    private String claveInterbancaria;
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
