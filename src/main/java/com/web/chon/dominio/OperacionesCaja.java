/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author JesusAlfredo
 */
public class OperacionesCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idOperacionesCajaPk;
    private BigDecimal idCorteCajaFk;
    private BigDecimal idCajaFk;
    private BigDecimal idCajaDestinoFk;
    private BigDecimal idConceptoFk;
    private Date fecha;
    private BigDecimal idStatusFk;
    private BigDecimal idUserFk;
    private String comentarios;
    private BigDecimal monto;
    private String nombreUsuario;
    private String nombreCaja;
    private String nombreCajaDestino;
    private String nombreStatus;
    private String nombreConcepto;
    private String nombreOperacion;
    private BigDecimal idTipoOperacionFk;
    private BigDecimal entradaSalida;
    private String nombreEntradaSalida;
    private int numero;
    private BigDecimal idCuentaDestinoFk;
    private String nombreBanco;
    private BigDecimal cuentaBanco;
    private BigDecimal idSucursalFk;
    private String nombreSucursal;
    private BigDecimal idFormaPago;
    private String nombrePago;
    private StreamedContent productImage;
    
    
    private byte[] fichero;

    @Override
    public String toString() {
        return "OperacionesCaja{" + "idOperacionesCajaPk=" + idOperacionesCajaPk + ", idCorteCajaFk=" + idCorteCajaFk + ", idCajaFk=" + idCajaFk + ", idCajaDestinoFk=" + idCajaDestinoFk + ", idConceptoFk=" + idConceptoFk + ", fecha=" + fecha + ", idStatusFk=" + idStatusFk + ", idUserFk=" + idUserFk + ", comentarios=" + comentarios + ", monto=" + monto + ", nombreUsuario=" + nombreUsuario + ", nombreCaja=" + nombreCaja + ", nombreCajaDestino=" + nombreCajaDestino + ", nombreStatus=" + nombreStatus + ", nombreConcepto=" + nombreConcepto + ", nombreOperacion=" + nombreOperacion + ", idTipoOperacionFk=" + idTipoOperacionFk + ", entradaSalida=" + entradaSalida + ", nombreEntradaSalida=" + nombreEntradaSalida + ", numero=" + numero + ", idCuentaDestinoFk=" + idCuentaDestinoFk + ", nombreBanco=" + nombreBanco + ", cuentaBanco=" + cuentaBanco + ", idSucursalFk=" + idSucursalFk + '}';
    }
    
    public void reset() {
        idFormaPago=null;
        nombrePago=null;
        idOperacionesCajaPk = null;
        idCorteCajaFk = null;
        idCajaFk = null;
        idCajaDestinoFk = null;
        idConceptoFk = null;
        fecha = null;
        idStatusFk = null;
        idUserFk = null;
        comentarios = null;
        monto = null;
        nombreUsuario = null;
        nombreCaja = null;
        nombreCajaDestino = null;
        nombreStatus = null;
        entradaSalida=null;
        nombreConcepto=null;
        nombreOperacion=null;
        idTipoOperacionFk=null;
        nombreEntradaSalida=null;
        idCuentaDestinoFk=null;
        idSucursalFk = null;
        nombreSucursal = null;
       
    }

    public StreamedContent getProductImage() {
        return productImage;
    }

    public void setProductImage(StreamedContent productImage) {
        this.productImage = productImage;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
    }

    
    public BigDecimal getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(BigDecimal idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getNombrePago() {
        return nombrePago;
    }

    public void setNombrePago(String nombrePago) {
        this.nombrePago = nombrePago;
    }

    
    

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public BigDecimal getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(BigDecimal cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }
    

    public BigDecimal getIdCuentaDestinoFk() {
        return idCuentaDestinoFk;
    }

    public void setIdCuentaDestinoFk(BigDecimal idCuentaDestinoFk) {
        this.idCuentaDestinoFk = idCuentaDestinoFk;
    }

    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    
    public String getNombreEntradaSalida() {
        return nombreEntradaSalida;
    }

    public void setNombreEntradaSalida(String nombreEntradaSalida) {
        this.nombreEntradaSalida = nombreEntradaSalida;
    }

    
    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public BigDecimal getIdTipoOperacionFk() {
        return idTipoOperacionFk;
    }

    public void setIdTipoOperacionFk(BigDecimal idTipoOperacionFk) {
        this.idTipoOperacionFk = idTipoOperacionFk;
    }


    
    
    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    
    public BigDecimal getEntradaSalida() {
        return entradaSalida;
    }

    public void setEntradaSalida(BigDecimal entradaSalida) {
        this.entradaSalida = entradaSalida;
    }

    
    public BigDecimal getIdConceptoFk() {
        return idConceptoFk;
    }

    public void setIdConceptoFk(BigDecimal idConceptoFk) {
        this.idConceptoFk = idConceptoFk;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombreCajaDestino() {
        return nombreCajaDestino;
    }

    public void setNombreCajaDestino(String nombreCajaDestino) {
        this.nombreCajaDestino = nombreCajaDestino;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public BigDecimal getIdOperacionesCajaPk() {
        return idOperacionesCajaPk;
    }

    public void setIdOperacionesCajaPk(BigDecimal idOperacionesCajaPk) {
        this.idOperacionesCajaPk = idOperacionesCajaPk;
    }

    public BigDecimal getIdCorteCajaFk() {
        return idCorteCajaFk;
    }

    public void setIdCorteCajaFk(BigDecimal idCorteCajaFk) {
        this.idCorteCajaFk = idCorteCajaFk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public BigDecimal getIdCajaDestinoFk() {
        return idCajaDestinoFk;
    }

    public void setIdCajaDestinoFk(BigDecimal idCajaDestinoFk) {
        this.idCajaDestinoFk = idCajaDestinoFk;
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

    public BigDecimal getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigDecimal idUserFk) {
        this.idUserFk = idUserFk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    
}
