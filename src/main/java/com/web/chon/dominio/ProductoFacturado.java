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
 * @author jramirez
 */
public class ProductoFacturado implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idProductoFacturadoPk;
    private BigDecimal idTipoLlaveFk;
    private BigDecimal idLlaveFk;
    private BigDecimal importe;
    private BigDecimal cantidad;
    private BigDecimal kilos;
    private BigDecimal idFacturaFk;

    @Override
    public String toString() {
        return "ProductoFacturado{" + "idProductoFacturadoPk=" + idProductoFacturadoPk + ", idTipoLlaveFk=" + idTipoLlaveFk + ", idLlaveFk=" + idLlaveFk + ", importe=" + importe + ", cantidad=" + cantidad + ", kilos=" + kilos + ", idFacturaFk=" + idFacturaFk + '}';
    }
    
    

    public BigDecimal getIdFacturaFk() {
        return idFacturaFk;
    }

    public void setIdFacturaFk(BigDecimal idFacturaFk) {
        this.idFacturaFk = idFacturaFk;
    }

    
    
    

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }
    

    public BigDecimal getIdProductoFacturadoPk() {
        return idProductoFacturadoPk;
    }

    public void setIdProductoFacturadoPk(BigDecimal idProductoFacturadoPk) {
        this.idProductoFacturadoPk = idProductoFacturadoPk;
    }

    public BigDecimal getIdTipoLlaveFk() {
        return idTipoLlaveFk;
    }

    public void setIdTipoLlaveFk(BigDecimal idTipoLlaveFk) {
        this.idTipoLlaveFk = idTipoLlaveFk;
    }

    public BigDecimal getIdLlaveFk() {
        return idLlaveFk;
    }

    public void setIdLlaveFk(BigDecimal idLlaveFk) {
        this.idLlaveFk = idLlaveFk;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
    
}
