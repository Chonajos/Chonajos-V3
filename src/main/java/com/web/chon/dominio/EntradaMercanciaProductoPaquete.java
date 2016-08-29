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
 * @author JesusAlfredo
 */
public class EntradaMercanciaProductoPaquete implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEmPP;
    private BigDecimal kilos;
    private BigDecimal paquetes;
    private BigDecimal tara;

    public void reset() {
        idEmPP = null;
        kilos = null;
        paquetes = null;
        tara = null;
    }

    @Override
    public String toString() {
        return "EntradaMercanciaProductoPaquete{" + "idEmPP=" + idEmPP + ", kilos=" + kilos + ", paquetes=" + paquetes + ", tara=" + tara + '}';
    }

    public BigDecimal getIdEmPP() {
        return idEmPP;
    }

    public void setIdEmPP(BigDecimal idEmPP) {
        this.idEmPP = idEmPP;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public BigDecimal getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(BigDecimal paquetes) {
        this.paquetes = paquetes;
    }

    public BigDecimal getTara() {
        return tara;
    }

    public void setTara(BigDecimal tara) {
        this.tara = tara;
    }

}
