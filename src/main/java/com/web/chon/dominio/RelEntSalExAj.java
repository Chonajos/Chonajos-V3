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
public class RelEntSalExAj implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal kilosEntrada;
    private BigDecimal kilosVendidos;
    private BigDecimal kilosAjustados;
    private BigDecimal kilosExistencia;

    @Override
    public String toString() {
        return "RelEntSalExAj{" + "kilosEntrada=" + kilosEntrada + ", kilosVendidos=" + kilosVendidos + ", kilosAjustados=" + kilosAjustados + ", kilosExistencia=" + kilosExistencia + '}';
    }
    
    

    public BigDecimal getKilosEntrada() {
        return kilosEntrada;
    }

    public void setKilosEntrada(BigDecimal kilosEntrada) {
        this.kilosEntrada = kilosEntrada;
    }

    public BigDecimal getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigDecimal kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public BigDecimal getKilosAjustados() {
        return kilosAjustados;
    }

    public void setKilosAjustados(BigDecimal kilosAjustados) {
        this.kilosAjustados = kilosAjustados;
    }

    public BigDecimal getKilosExistencia() {
        return kilosExistencia;
    }

    public void setKilosExistencia(BigDecimal kilosExistencia) {
        this.kilosExistencia = kilosExistencia;
    }
    
    
    
}
