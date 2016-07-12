package com.web.chon.dominio;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class AbonoCredito extends ValueObject {

    private BigDecimal idAbonoCreditoPk;
    private BigDecimal idCreditoFk;
    private BigDecimal montoAbono;
    private Date fechaAbono;

    private BigDecimal idUsuarioFk;

    public BigDecimal getIdAbonoCreditoPk() {
        return idAbonoCreditoPk;
    }

    public void setIdAbonoCreditoPk(BigDecimal idAbonoCreditoPk) {
        this.idAbonoCreditoPk = idAbonoCreditoPk;
    }

    public BigDecimal getIdCreditoFk() {
        return idCreditoFk;
    }

    public void setIdCreditoFk(BigDecimal idCreditoFk) {
        this.idCreditoFk = idCreditoFk;
    }

    public BigDecimal getMontoAbono() {
        return montoAbono;
    }

    public void setMontoAbono(BigDecimal montoAbono) {
        this.montoAbono = montoAbono;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    @Override
    public String toString() {
        return "AbonoCredito{" + "idAbonoCreditoPk=" + idAbonoCreditoPk + ", idCreditoFk=" + idCreditoFk + ", montoAbono=" + montoAbono + ", fechaAbono=" + fechaAbono + ", idUsuarioFk=" + idUsuarioFk + '}';
    }

    @Override
    public void reset() {
        idAbonoCreditoPk = null;
        idCreditoFk = null;
        montoAbono = null;
        fechaAbono = null;
    }

}
