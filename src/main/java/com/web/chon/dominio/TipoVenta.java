/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author marcogante
 */
public class TipoVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoVentaPk;
    private String nombreTipoVenta;

    @Override
    public String toString() {
        return "TipoVenta{" + "idTipoVentaPk=" + idTipoVentaPk + ", nombreTipoVenta=" + nombreTipoVenta + '}';
    }
    
    
    public TipoVenta() {
    }

    public TipoVenta(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public BigDecimal getIdTipoVentaPk() {
        return idTipoVentaPk;
    }

    public void setIdTipoVentaPk(BigDecimal idTipoVentaPk) {
        this.idTipoVentaPk = idTipoVentaPk;
    }

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }


}
