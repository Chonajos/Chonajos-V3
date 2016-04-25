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
 * @author freddy
 */
public class TipoConvenio implements Serializable
{
    private static final long serialVersionUID = 1L;
    private BigDecimal idTcPK;
    private String nombreTipoConvenio;
    private String descripcionTipoConvenio;

    @Override
    public String toString() {
        return "TipoConvenio{" + "idTcPK=" + idTcPK + ", nombreTipoConvenio=" + nombreTipoConvenio + ", descripcionTipoConvenio=" + descripcionTipoConvenio + '}';
    }

    
    
    public BigDecimal getIdTcPK() {
        return idTcPK;
    }

    public void setIdTcPK(BigDecimal idTcPK) {
        this.idTcPK = idTcPK;
    }

    public String getNombreTipoConvenio() {
        return nombreTipoConvenio;
    }

    public void setNombreTipoConvenio(String nombreTipoConvenio) {
        this.nombreTipoConvenio = nombreTipoConvenio;
    }

    public String getDescripcionTipoConvenio() {
        return descripcionTipoConvenio;
    }

    public void setDescripcionTipoConvenio(String descripcionTipoConvenio) {
        this.descripcionTipoConvenio = descripcionTipoConvenio;
    }

    
    
    
    
}
