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
    private BigDecimal pesoNeto;
    private BigDecimal idEmpFK;
    private BigDecimal folio;
    private BigDecimal idStatusFk;

    @Override
    public String toString() {
        return "EntradaMercanciaProductoPaquete{" + "idEmPP=" + idEmPP + ", kilos=" + kilos + ", paquetes=" + paquetes + ", tara=" + tara + ", pesoNeto=" + pesoNeto + ", idEmpFK=" + idEmpFK + ", folio=" + folio + ", idStatusFk=" + idStatusFk + '}';
    }

    
    
    public void reset() {
        idEmPP = null;
        kilos = null;
        paquetes = null;
        tara = null;
        pesoNeto=null;
        idEmpFK=null;
        folio = null;
        idStatusFk=null;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }


    
    public BigDecimal getFolio() {
        return folio;
    }

    public void setFolio(BigDecimal folio) {
        this.folio = folio;
    }

    
    public BigDecimal getIdEmpFK() {
        return idEmpFK;
    }

    public void setIdEmpFK(BigDecimal idEmpFK) {
        this.idEmpFK = idEmpFK;
    }
    
    
    public BigDecimal getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(BigDecimal pesoNeto) {
        this.pesoNeto = pesoNeto;
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
