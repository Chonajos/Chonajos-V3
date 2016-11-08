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
 * @author freddy
 */
public class PagosBancarios implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTransBancariasPk;
    private BigDecimal idCajaFk;
    private BigDecimal idConceptoFk;
    private BigDecimal idTipoFk;
    private String comentarios;
    private BigDecimal idUserFk;
    private BigDecimal monto;
    private Date fecha;
    private BigDecimal idStatusFk;
    private Date fechaTranferencia;
    private BigDecimal folioElectronico;
    private Date fechaDeposito;
    private BigDecimal idCuentaFk;
    private String concepto;
    private String referencia;
    private String nombreCaja;
    private String nombreConcepto;
    private String nombreTipoAbono;
    private String nombreUsuario;
    private String nombreBanco;
    private BigDecimal numero;
    private String nombreStatus;
    private BigDecimal idOperacionCajaFk;

    @Override
    public String toString() {
        return "PagosBancarios{" + "idTransBancariasPk=" + idTransBancariasPk + ", idCajaFk=" + idCajaFk + ", idConceptoFk=" + idConceptoFk + ", idTipoFk=" + idTipoFk + ", comentarios=" + comentarios + ", idUserFk=" + idUserFk + ", monto=" + monto + ", fecha=" + fecha + ", idStatusFk=" + idStatusFk + ", fechaTranferencia=" + fechaTranferencia + ", folioElectronico=" + folioElectronico + ", fechaDeposito=" + fechaDeposito + ", idCuentaFk=" + idCuentaFk + ", concepto=" + concepto + ", referencia=" + referencia + ", nombreCaja=" + nombreCaja + ", nombreConcepto=" + nombreConcepto + ", nombreTipoAbono=" + nombreTipoAbono + ", nombreUsuario=" + nombreUsuario + ", nombreBanco=" + nombreBanco + ", numero=" + numero + ", nombreStatus=" + nombreStatus + ", idOperacionCajaFk=" + idOperacionCajaFk + '}';
    }

    public void reset() {
        idTransBancariasPk = null;
        idCajaFk = null;
        idConceptoFk = null;
        idTipoFk = null;
        comentarios = null;
        idUserFk = null;
        monto = null;
        fecha = null;
        idStatusFk = null;
        fechaTranferencia = null;
        folioElectronico = null;
        fechaDeposito = null;
        idCuentaFk = null;
        concepto = null;
        referencia = null;
        idOperacionCajaFk=null;
    }
    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }


    
    public BigDecimal getIdOperacionCajaFk() {
        return idOperacionCajaFk;
    }

    public void setIdOperacionCajaFk(BigDecimal idOperacionCajaFk) {
        this.idOperacionCajaFk = idOperacionCajaFk;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    public String getNombreTipoAbono() {
        return nombreTipoAbono;
    }

    public void setNombreTipoAbono(String nombreTipoAbono) {
        this.nombreTipoAbono = nombreTipoAbono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public BigDecimal getIdTransBancariasPk() {
        return idTransBancariasPk;
    }

    public void setIdTransBancariasPk(BigDecimal idTransBancariasPk) {
        this.idTransBancariasPk = idTransBancariasPk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public BigDecimal getIdConceptoFk() {
        return idConceptoFk;
    }

    public void setIdConceptoFk(BigDecimal idConceptoFk) {
        this.idConceptoFk = idConceptoFk;
    }

    public BigDecimal getIdTipoFk() {
        return idTipoFk;
    }

    public void setIdTipoFk(BigDecimal idTipoFk) {
        this.idTipoFk = idTipoFk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigDecimal idUserFk) {
        this.idUserFk = idUserFk;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public Date getFechaTranferencia() {
        return fechaTranferencia;
    }

    public void setFechaTranferencia(Date fechaTranferencia) {
        this.fechaTranferencia = fechaTranferencia;
    }

    public BigDecimal getFolioElectronico() {
        return folioElectronico;
    }

    public void setFolioElectronico(BigDecimal folioElectronico) {
        this.folioElectronico = folioElectronico;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public BigDecimal getIdCuentaFk() {
        return idCuentaFk;
    }

    public void setIdCuentaFk(BigDecimal idCuentaFk) {
        this.idCuentaFk = idCuentaFk;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

}
