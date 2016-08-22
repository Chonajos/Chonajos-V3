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
public class AbonoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idAbonoDocumentoPk;
    private BigDecimal idDocumentoFk;
    private BigDecimal montoAbono;
    private Date fechaAbono;
    private BigDecimal idTipoAbonoFk;
    private BigDecimal estatus;
    private BigDecimal numeroCheque;
    private String librador;
    private Date fechaCobro;
    private String banco;
    private String numeroFactura;
    private BigDecimal referencia;
    private String concepto;
    private Date fechaTransferencia;
    private BigDecimal idUsuarioFk;
    private String nombreCliente;
    private String nombreStatus;
    private BigDecimal idStatusDocumento;
    private BigDecimal idClienteFk;

    public void reset() {
        idAbonoDocumentoPk = null;
        idDocumentoFk = null;
        montoAbono = null;
        fechaAbono = null;
        idTipoAbonoFk = null;
        estatus = null;
        numeroCheque = null;
        librador = null;
        fechaCobro = null;
        banco = null;
        numeroFactura = null;
        referencia = null;
        concepto = null;
        idUsuarioFk = null;
        fechaTransferencia = null;
        nombreCliente = null;
        nombreStatus = null;
        idStatusDocumento = null;
        idClienteFk=null;
    }

    @Override
    public String toString() {
        return "AbonoDocumentos{" + "idAbonoDocumentoPk=" + idAbonoDocumentoPk + ", idDocumentoFk=" + idDocumentoFk + ", montoAbono=" + montoAbono + ", fechaAbono=" + fechaAbono + ", idTipoAbonoFk=" + idTipoAbonoFk + ", estatus=" + estatus + ", numeroCheque=" + numeroCheque + ", librador=" + librador + ", fechaCobro=" + fechaCobro + ", banco=" + banco + ", numeroFactura=" + numeroFactura + ", referencia=" + referencia + ", concepto=" + concepto + ", fechaTransferencia=" + fechaTransferencia + ", idUsuarioFk=" + idUsuarioFk + ", nombreCliente=" + nombreCliente + ", nombreStatus=" + nombreStatus + ", idStatusDocumento=" + idStatusDocumento + ", idClienteFk=" + idClienteFk + '}';
    }

    
    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public BigDecimal getIdStatusDocumento() {
        return idStatusDocumento;
    }

    public void setIdStatusDocumento(BigDecimal idStatusDocumento) {
        this.idStatusDocumento = idStatusDocumento;
    }

  

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public BigDecimal getIdAbonoDocumentoPk() {
        return idAbonoDocumentoPk;
    }

    public void setIdAbonoDocumentoPk(BigDecimal idAbonoDocumentoPk) {
        this.idAbonoDocumentoPk = idAbonoDocumentoPk;
    }

    public BigDecimal getIdDocumentoFk() {
        return idDocumentoFk;
    }

    public void setIdDocumentoFk(BigDecimal idDocumentoFk) {
        this.idDocumentoFk = idDocumentoFk;
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

    public BigDecimal getIdTipoAbonoFk() {
        return idTipoAbonoFk;
    }

    public void setIdTipoAbonoFk(BigDecimal idTipoAbonoFk) {
        this.idTipoAbonoFk = idTipoAbonoFk;
    }

    public BigDecimal getEstatus() {
        return estatus;
    }

    public void setEstatus(BigDecimal estatus) {
        this.estatus = estatus;
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

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public BigDecimal getReferencia() {
        return referencia;
    }

    public void setReferencia(BigDecimal referencia) {
        this.referencia = referencia;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(Date fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

}
