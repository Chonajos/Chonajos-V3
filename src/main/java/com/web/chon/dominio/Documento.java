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

    @Override
    public String toString() {
        return "Documento{" + "idDocumentoPk=" + idDocumentoPk + ", idTipoDocumento=" + idTipoDocumento + ", idAbonoFk=" + idAbonoFk + ", idClienteFk=" + idClienteFk + ", idStatusFk=" + idStatusFk + ", monto=" + monto + ", fechaCobro=" + fechaCobro + ", comentario=" + comentario + '}';
    }

    
    public void reset() {
        idDocumentoPk = null;
        idTipoDocumento = null;
        idAbonoFk = null;
        idClienteFk = null;
        idStatusFk = null;
        fechaCobro=null;
        monto = null;
        comentario = null;
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
