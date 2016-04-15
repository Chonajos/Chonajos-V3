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
public class TipoOrdenCompra implements Serializable
{
    private static final long serialVersionUID = 1L;
    private BigDecimal idTocPK;
    private String nombreTipoOrdenCompra;
    private String descripcionTipoOrden;

    @Override
    public String toString() {
        return "TipoOrdenCompra{" + "idTocPK=" + idTocPK + ", nombreTipoOrdenCompra=" + nombreTipoOrdenCompra + ", descripcionTipoOrden=" + descripcionTipoOrden + '}';
    }

    
    public BigDecimal getIdTocPK() {
        return idTocPK;
    }

    public void setIdTocPK(BigDecimal idTocPK) {
        this.idTocPK = idTocPK;
    }

    public String getNombreTipoOrdenCompra() {
        return nombreTipoOrdenCompra;
    }

    public void setNombreTipoOrdenCompra(String nombreTipoOrdenCompra) {
        this.nombreTipoOrdenCompra = nombreTipoOrdenCompra;
    }

    public String getDescripcionTipoOrden() {
        return descripcionTipoOrden;
    }

    public void setDescripcionTipoOrden(String descripcionTipoOrden) {
        this.descripcionTipoOrden = descripcionTipoOrden;
    }
    
    
    
}
