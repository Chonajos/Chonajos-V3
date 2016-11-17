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
    private BigDecimal idtipoAbonoFk;
    private BigDecimal estatusAbono;
    private BigDecimal numeroCheque;
    private String librador;
    private Date fechaCobro;
    private String banco;
    private String factura;
    private String referencia;
    private String concepto;
    private Date fechaTransferencia;
    private BigDecimal datoACuenta;
    private BigDecimal idDocumentoPk;
    private String nombreCliente;
    private String nombreStatus;
    private BigDecimal idStatusDocumentoFk;
    private BigDecimal idClienteFk;
    private BigDecimal folioElectronico;
    private String nombreAbono;
    private String nombreCajero;

    @Override
    public String toString() {
        return "AbonoCredito{" + "idAbonoCreditoPk=" + idAbonoCreditoPk + ", idCreditoFk=" + idCreditoFk + ", montoAbono=" + montoAbono + ", fechaAbono=" + fechaAbono + ", idUsuarioFk=" + idUsuarioFk + ", idtipoAbonoFk=" + idtipoAbonoFk + ", estatusAbono=" + estatusAbono + ", numeroCheque=" + numeroCheque + ", librador=" + librador + ", fechaCobro=" + fechaCobro + ", banco=" + banco + ", factura=" + factura + ", referencia=" + referencia + ", concepto=" + concepto + ", fechaTransferencia=" + fechaTransferencia + ", datoACuenta=" + datoACuenta + ", idDocumentoPk=" + idDocumentoPk + ", nombreCliente=" + nombreCliente + ", nombreStatus=" + nombreStatus + ", idStatusDocumentoFk=" + idStatusDocumentoFk + ", idClienteFk=" + idClienteFk + ", folioElectronico=" + folioElectronico + '}';
    }

   

    @Override
    public void reset() {
        idAbonoCreditoPk = null;
        idCreditoFk = null;
        montoAbono = null;
        fechaAbono = null;
        idUsuarioFk = null;
        idtipoAbonoFk = null;
        estatusAbono = null;
        numeroCheque = null;
        librador = null;
        fechaCobro = null;
        banco = null;
        factura = null;
        referencia = null;
        concepto = null;
        datoACuenta=null;
        fechaTransferencia = null;
        idDocumentoPk=null;
        nombreCliente=null;
        nombreStatus=null;
        idClienteFk=null;
        folioElectronico=null; 
        nombreAbono = null;
        nombreCajero=null;
    }

    public BigDecimal getFolioElectronico() {
        return folioElectronico;
    }

    public void setFolioElectronico(BigDecimal folioElectronico) {
        this.folioElectronico = folioElectronico;
    }

    
    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    
    public BigDecimal getIdStatusDocumentoFk() {
        return idStatusDocumentoFk;
    }

    public void setIdStatusDocumentoFk(BigDecimal idStatusDocumentoFk) {
        this.idStatusDocumentoFk = idStatusDocumentoFk;
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
    

    public BigDecimal getIdDocumentoPk() {
        return idDocumentoPk;
    }

    public void setIdDocumentoPk(BigDecimal idDocumentoPk) {
        this.idDocumentoPk = idDocumentoPk;
    }
    

    public BigDecimal getDatoACuenta() {
        return datoACuenta;
    }

    public void setDatoACuenta(BigDecimal datoACuenta) {
        this.datoACuenta = datoACuenta;
    }
   
    public BigDecimal getEstatusAbono() {
        return estatusAbono;
    }

    public void setEstatusAbono(BigDecimal estatusAbono) {
        this.estatusAbono = estatusAbono;
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

    public String getNombreAbono() {
        return nombreAbono;
    }

    public void setNombreAbono(String nombreAbono) {
        this.nombreAbono = nombreAbono;
    }

    public String getNombreCajero() {
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }
    
    

    
}
