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
public class SaldosDeudas implements Serializable
{
    private static final long serialVersionUID = 1L;
    private BigDecimal folio;
    private String periodo;
    private String plazo;
    private String periodosAtraso;
    private String diasAtraso;
    private String saldoDeudor;
    private String saldoAtrasado;

    @Override
    public String toString() {
        return "SaldosDeudas{" + "folio=" + folio + ", periodo=" + periodo + ", plazo=" + plazo + ", periodosAtraso=" + periodosAtraso + ", diasAtraso=" + diasAtraso + ", saldoDeudor=" + saldoDeudor + ", saldoAtrasado=" + saldoAtrasado + '}';
    }

    
    
    public BigDecimal getFolio() {
        return folio;
    }

    public void setFolio(BigDecimal folio) {
        this.folio = folio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getPeriodosAtraso() {
        return periodosAtraso;
    }

    public void setPeriodosAtraso(String periodosAtraso) {
        this.periodosAtraso = periodosAtraso;
    }

    public String getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public String getSaldoDeudor() {
        return saldoDeudor;
    }

    public void setSaldoDeudor(String saldoDeudor) {
        this.saldoDeudor = saldoDeudor;
    }

    public String getSaldoAtrasado() {
        return saldoAtrasado;
    }

    public void setSaldoAtrasado(String saldoAtrasado) {
        this.saldoAtrasado = saldoAtrasado;
    }
    
    
    
}
