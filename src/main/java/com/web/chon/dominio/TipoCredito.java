package com.web.chon.dominio;

import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class TipoCredito extends ValueObject {

    private BigDecimal idTipoCredito;
    private BigDecimal valorCredito;
    private BigDecimal descripcion;

    public BigDecimal getIdTipoCredito() {
        return idTipoCredito;
    }

    public void setIdTipoCredito(BigDecimal idTipoCredito) {
        this.idTipoCredito = idTipoCredito;
    }

    public BigDecimal getValorCredito() {
        return valorCredito;
    }

    public void setValorCredito(BigDecimal valorCredito) {
        this.valorCredito = valorCredito;
    }

    public BigDecimal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(BigDecimal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoCredito{" + "idTipoCredito=" + idTipoCredito + ", valorCredito=" + valorCredito + ", descripcion=" + descripcion + '}';
    }

    @Override
    public void reset() {
        
     idTipoCredito = null;
     valorCredito = null;
     descripcion = null;
}

}
