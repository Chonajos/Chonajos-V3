package com.web.chon.dominio;

import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class GestionCredito extends ValueObject {

    private BigDecimal idGestionCredito;
    private BigDecimal idAcionGestion;
    private BigDecimal idUsario;
    private BigDecimal idCredito;
    private String observaciones;

    public BigDecimal getIdGestionCredito() {
        return idGestionCredito;
    }

    public void setIdGestionCredito(BigDecimal idGestionCredito) {
        this.idGestionCredito = idGestionCredito;
    }

    public BigDecimal getIdAcionGestion() {
        return idAcionGestion;
    }

    public void setIdAcionGestion(BigDecimal idAcionGestion) {
        this.idAcionGestion = idAcionGestion;
    }

    public BigDecimal getIdUsario() {
        return idUsario;
    }

    public void setIdUsario(BigDecimal idUsario) {
        this.idUsario = idUsario;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "GestionCredito{" + "idGestionCredito=" + idGestionCredito + ", idAcionGestion=" + idAcionGestion + ", idUsario=" + idUsario + ", idCredito=" + idCredito + ", observaciones=" + observaciones + '}';
    }

    @Override
    public void reset() {
        idGestionCredito = null;
        idAcionGestion = null;
        idUsario = null;
        idCredito = null;
        observaciones = null;
    }

}
