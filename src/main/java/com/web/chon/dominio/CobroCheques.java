/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author JesusAlfredo
 */
public class CobroCheques implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idCobroChequePk;
    private BigDecimal idDocumentoFk;
    private BigDecimal idTipoCobro;
    private Date fechaDeposito;
    private String bancoDeposito;
    private BigDecimal cuentaDeposito;
    private BigDecimal importeDeposito;
    private String observaciones;

    public void reset() {
        idCobroChequePk = null;
        idDocumentoFk = null;
        idTipoCobro = null;
        fechaDeposito = null;
        bancoDeposito = null;
        cuentaDeposito = null;
        importeDeposito = null;
        observaciones = null;
    }

    @Override
    public String toString() {
        return "CobroCheques{" + "idCobroChequePk=" + idCobroChequePk + ", idDocumentoFk=" + idDocumentoFk + ", idTipoCobro=" + idTipoCobro + ", fechaDeposito=" + fechaDeposito + ", bancoDeposito=" + bancoDeposito + ", cuentaDeposito=" + cuentaDeposito + ", importeDeposito=" + importeDeposito + ", observaciones=" + observaciones + '}';
    }

    public BigDecimal getIdCobroChequePk() {
        return idCobroChequePk;
    }

    public void setIdCobroChequePk(BigDecimal idCobroChequePk) {
        this.idCobroChequePk = idCobroChequePk;
    }

    public BigDecimal getIdDocumentoFk() {
        return idDocumentoFk;
    }

    public void setIdDocumentoFk(BigDecimal idDocumentoFk) {
        this.idDocumentoFk = idDocumentoFk;
    }

    public BigDecimal getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(BigDecimal idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public String getBancoDeposito() {
        return bancoDeposito;
    }

    public void setBancoDeposito(String bancoDeposito) {
        this.bancoDeposito = bancoDeposito;
    }

    public BigDecimal getCuentaDeposito() {
        return cuentaDeposito;
    }

    public void setCuentaDeposito(BigDecimal cuentaDeposito) {
        this.cuentaDeposito = cuentaDeposito;
    }

    public BigDecimal getImporteDeposito() {
        return importeDeposito;
    }

    public void setImporteDeposito(BigDecimal importeDeposito) {
        this.importeDeposito = importeDeposito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
