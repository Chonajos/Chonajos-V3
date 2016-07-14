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
    private BigDecimal idtipoAbonoFk;
    private BigDecimal idUsuarioFk;
    private BigDecimal numeroCheque;
    private String librador;
    private Date fechaCobro;
    private String banco;
    private String factura;
    private String referencia;

    @Override
    public String toString() {
        return "AbonoCredito{" + "idAbonoCreditoPk=" + idAbonoCreditoPk + ", idCreditoFk=" + idCreditoFk + ", montoAbono=" + montoAbono + ", fechaAbono=" + fechaAbono + ", idtipoAbonoFk=" + idtipoAbonoFk + ", idUsuarioFk=" + idUsuarioFk + ", numeroCheque=" + numeroCheque + ", librador=" + librador + ", fechaCobro=" + fechaCobro + ", banco=" + banco + ", factura=" + factura + ", referencia=" + referencia + '}';
    }

    
    
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

    public BigDecimal getIdtipoAbonoFk() {
        return idtipoAbonoFk;
    }

    public void setIdtipoAbonoFk(BigDecimal idtipoAbonoFk) {
        this.idtipoAbonoFk = idtipoAbonoFk;
    }

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public BigDecimal getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(BigDecimal numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getLibrador() {
        return librador;
    }

    public void setLibrador(String librador) {
        this.librador = librador;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
   
}
