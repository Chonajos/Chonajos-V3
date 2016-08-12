package com.web.chon.dominio;

import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class ResultadoGestion extends ValueObject {

    private BigDecimal idResultadoGestion;
    private String descripcion;

    public BigDecimal getIdResultadoGestion() {
        return idResultadoGestion;
    }

    public void setIdResultadoGestion(BigDecimal idResultadoGestion) {
        this.idResultadoGestion = idResultadoGestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ResultadoGestion{" + "idResultadoGestion=" + idResultadoGestion + ", descripcion=" + descripcion + '}';
    }

    @Override
    public void reset() {
        idResultadoGestion = null;
        descripcion = null;
    }

}
