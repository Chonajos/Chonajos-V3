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
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idDocumentoPk;
    private BigDecimal idTipoDocumento;
    private BigDecimal idAbonoFk;
    private BigDecimal idClienteFk;
    private BigDecimal idStatusFk;
    private BigDecimal monto;
    private Date fechaCobro;
    private String comentario;
    private BigDecimal idAbonoDocumentoFk;
    private BigDecimal numeroCheque;
    private String factura;
    private String banco;
    private String librador;
    private BigDecimal idFormaCobroFk;
    private BigDecimal idDocumentoPadreFk;
    private String nombreStatus;
    private String nombreCliente;
    private String nombreFormaCobro;
    private BigDecimal totalAbonado;

    @Override
    public String toString() {
        return "Documento{" + "idDocumentoPk=" + idDocumentoPk + ", idTipoDocumento=" + idTipoDocumento + ", idAbonoFk=" + idAbonoFk + ", idClienteFk=" + idClienteFk + ", idStatusFk=" + idStatusFk + ", monto=" + monto + ", fechaCobro=" + fechaCobro + ", comentario=" + comentario + ", idAbonoDocumentoFk=" + idAbonoDocumentoFk + ", numeroCheque=" + numeroCheque + ", factura=" + factura + ", banco=" + banco + ", librador=" + librador + ", idFormaCobroFk=" + idFormaCobroFk + ", idDocumentoPadreFk=" + idDocumentoPadreFk + '}';
    }

    public void reset() {
        idDocumentoPk = null;
        idTipoDocumento = null;
        idAbonoFk = null;
        idClienteFk = null;
        idStatusFk = null;
        fechaCobro = null;
        monto = null;
        comentario = null;
        idAbonoDocumentoFk = null;
        numeroCheque = null;
        factura = null;
        banco = null;
        librador = null;
        idFormaCobroFk = null;
        idDocumentoPadreFk = null;
        totalAbonado=null;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    
    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreFormaCobro() {
        return nombreFormaCobro;
    }

    public void setNombreFormaCobro(String nombreFormaCobro) {
        this.nombreFormaCobro = nombreFormaCobro;
    }

    
    public BigDecimal getIdDocumentoPadreFk() {
        return idDocumentoPadreFk;
    }

    public void setIdDocumentoPadreFk(BigDecimal idDocumentoPadreFk) {
        this.idDocumentoPadreFk = idDocumentoPadreFk;
    }

    public BigDecimal getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(BigDecimal numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getLibrador() {
        return librador;
    }

    public void setLibrador(String librador) {
        this.librador = librador;
    }

    public BigDecimal getIdFormaCobroFk() {
        return idFormaCobroFk;
    }

    public void setIdFormaCobroFk(BigDecimal idFormaCobroFk) {
        this.idFormaCobroFk = idFormaCobroFk;
    }

    public BigDecimal getIdAbonoDocumentoFk() {
        return idAbonoDocumentoFk;
    }

    public void setIdAbonoDocumentoFk(BigDecimal idAbonoDocumentoFk) {
        this.idAbonoDocumentoFk = idAbonoDocumentoFk;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public BigDecimal getIdDocumentoPk() {
        return idDocumentoPk;
    }

    public void setIdDocumentoPk(BigDecimal idDocumentoPk) {
        this.idDocumentoPk = idDocumentoPk;
    }

    public BigDecimal getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(BigDecimal idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public BigDecimal getIdAbonoFk() {
        return idAbonoFk;
    }

    public void setIdAbonoFk(BigDecimal idAbonoFk) {
        this.idAbonoFk = idAbonoFk;
    }

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
