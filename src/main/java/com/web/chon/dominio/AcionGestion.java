package com.web.chon.dominio;

import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class AcionGestion extends ValueObject {

    private BigDecimal idAcionGestion;
    private String descripcion;
    private BigDecimal idResultadoGestion;

    public BigDecimal getIdAcionGestion() {
        return idAcionGestion;
    }

    public void setIdAcionGestion(BigDecimal idAcionGestion) {
        this.idAcionGestion = idAcionGestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIdResultadoGestion() {
        return idResultadoGestion;
    }

    public void setIdResultadoGestion(BigDecimal idResultadoGestion) {
        this.idResultadoGestion = idResultadoGestion;
    }

    @Override
    public String toString() {
        return "AcionGestion{" + "idAcionGestion=" + idAcionGestion + ", descripcion=" + descripcion + ", idResultadoGestion=" + idResultadoGestion + '}';
    }


    @Override
    public void reset() {

        idAcionGestion = null;
        descripcion = null;
        idResultadoGestion = null;
    }

}
