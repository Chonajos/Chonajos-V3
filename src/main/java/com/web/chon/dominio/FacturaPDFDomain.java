/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author jramirez
 */
public class FacturaPDFDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private BigDecimal idFacturaPk;
    private BigDecimal numeroFactura;
    private BigDecimal idUsuarioFk;
    private BigDecimal idClienteFk;
    private BigDecimal idLlaveFk;
    private BigDecimal idTipoLlaveFk;
    
    private String nombreEstatus;
    private BigDecimal idStatusFk;
    private String comentarios;
    private String nombreSucursal;
    private BigDecimal idSucursalFk;
    private String nombreCliente;
    
    
    //========Datos para PDF======//
    //detail 1
    private String razonSocialEmpresa;
    private String rfcEmisor;
    private String domiciloFiscalEmisor;
    private String coloniaEmisor;
    private String ciudadEmisor;
    private String codigoPostalE;
    
    private String parametroA;
    private String parametroB;
    private String fechaEmision;
    private String lugarEmision;
    
    
    //detail 2
    //Datos de Cliente:
    private String rfcCliente;
    private String domicilioFiscalCliente;
    private String coloniaCliente;
    private String ciudadCliente;
    private String codigoPostalCliente;
    private String telefonoCliente;
    
    //detail 3
    private ArrayList<VentaProductoMayoreo> listaProductosMayoreo;
    private ArrayList<VentaProducto> listaProductosMenudeo;
    
    private Venta ventaMenudeo;
    private VentaMayoreo ventaMayoreo;
    
    //Detail 4
    private String importeLetra;
    private String importe;
    private String descuento;
    private String iva1;
    private String iva2;
    private String total;
    
    //Detail 5
    private String selloDigital;
    private String cadena;
    private String selloSAT;
    
    //Detail 6
    private String cadenaQR;
    private String formaPago;
    private String nCuenta;
    private String metodoPago;
    private String nSerieCertificadoCSD;
    private String folioFiscal;
    private String nSerieCertificadoSAT;
    private String fechaCertificacion;
    
    //========Datos para PDF======//

    public BigDecimal getIdLlaveFk() {
        return idLlaveFk;
    }

    public void setIdLlaveFk(BigDecimal idLlaveFk) {
        this.idLlaveFk = idLlaveFk;
    }

    public BigDecimal getIdTipoLlaveFk() {
        return idTipoLlaveFk;
    }

    public void setIdTipoLlaveFk(BigDecimal idTipoLlaveFk) {
        this.idTipoLlaveFk = idTipoLlaveFk;
    }

    
    
    
    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

   

    public String getColoniaEmisor() {
        return coloniaEmisor;
    }

    public void setColoniaEmisor(String coloniaEmisor) {
        this.coloniaEmisor = coloniaEmisor;
    }

    public String getCiudadEmisor() {
        return ciudadEmisor;
    }

    public void setCiudadEmisor(String ciudadEmisor) {
        this.ciudadEmisor = ciudadEmisor;
    }

    public String getCodigoPostalE() {
        return codigoPostalE;
    }

    public void setCodigoPostalE(String codigoPostalE) {
        this.codigoPostalE = codigoPostalE;
    }
    
    

    public String getRazonSocialEmpresa() {
        return razonSocialEmpresa;
    }

    public void setRazonSocialEmpresa(String razonSocialEmpresa) {
        this.razonSocialEmpresa = razonSocialEmpresa;
    }

    public String getRfcEmisor() {
        return rfcEmisor;
    }

    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    public String getDomiciloFiscalEmisor() {
        return domiciloFiscalEmisor;
    }

    public void setDomiciloFiscalEmisor(String domiciloFiscalEmisor) {
        this.domiciloFiscalEmisor = domiciloFiscalEmisor;
    }

    public String getParametroA() {
        return parametroA;
    }

    public void setParametroA(String parametroA) {
        this.parametroA = parametroA;
    }

    public String getParametroB() {
        return parametroB;
    }

    public void setParametroB(String parametroB) {
        this.parametroB = parametroB;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getLugarEmision() {
        return lugarEmision;
    }

    public void setLugarEmision(String lugarEmision) {
        this.lugarEmision = lugarEmision;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public String getDomicilioFiscalCliente() {
        return domicilioFiscalCliente;
    }

    public void setDomicilioFiscalCliente(String domicilioFiscalCliente) {
        this.domicilioFiscalCliente = domicilioFiscalCliente;
    }

    public String getColoniaCliente() {
        return coloniaCliente;
    }

    public void setColoniaCliente(String coloniaCliente) {
        this.coloniaCliente = coloniaCliente;
    }

    public String getCiudadCliente() {
        return ciudadCliente;
    }

    public void setCiudadCliente(String ciudadCliente) {
        this.ciudadCliente = ciudadCliente;
    }

    public String getCodigoPostalCliente() {
        return codigoPostalCliente;
    }

    public void setCodigoPostalCliente(String codigoPostalCliente) {
        this.codigoPostalCliente = codigoPostalCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    

    public ArrayList<VentaProductoMayoreo> getListaProductosMayoreo() {
        return listaProductosMayoreo;
    }

    public void setListaProductosMayoreo(ArrayList<VentaProductoMayoreo> listaProductosMayoreo) {
        this.listaProductosMayoreo = listaProductosMayoreo;
    }

    public ArrayList<VentaProducto> getListaProductosMenudeo() {
        return listaProductosMenudeo;
    }

    public void setListaProductosMenudeo(ArrayList<VentaProducto> listaProductosMenudeo) {
        this.listaProductosMenudeo = listaProductosMenudeo;
    }

    public Venta getVentaMenudeo() {
        return ventaMenudeo;
    }

    public void setVentaMenudeo(Venta ventaMenudeo) {
        this.ventaMenudeo = ventaMenudeo;
    }

    public VentaMayoreo getVentaMayoreo() {
        return ventaMayoreo;
    }

    public void setVentaMayoreo(VentaMayoreo ventaMayoreo) {
        this.ventaMayoreo = ventaMayoreo;
    }

    public String getImporteLetra() {
        return importeLetra;
    }

    public void setImporteLetra(String importeLetra) {
        this.importeLetra = importeLetra;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getIva1() {
        return iva1;
    }

    public void setIva1(String iva1) {
        this.iva1 = iva1;
    }

    public String getIva2() {
        return iva2;
    }

    public void setIva2(String iva2) {
        this.iva2 = iva2;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getSelloSAT() {
        return selloSAT;
    }

    public void setSelloSAT(String selloSAT) {
        this.selloSAT = selloSAT;
    }

    public String getCadenaQR() {
        return cadenaQR;
    }

    public void setCadenaQR(String cadenaQR) {
        this.cadenaQR = cadenaQR;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getnCuenta() {
        return nCuenta;
    }

    public void setnCuenta(String nCuenta) {
        this.nCuenta = nCuenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getnSerieCertificadoCSD() {
        return nSerieCertificadoCSD;
    }

    public void setnSerieCertificadoCSD(String nSerieCertificadoCSD) {
        this.nSerieCertificadoCSD = nSerieCertificadoCSD;
    }

    public String getFolioFiscal() {
        return folioFiscal;
    }

    public void setFolioFiscal(String folioFiscal) {
        this.folioFiscal = folioFiscal;
    }

    public String getnSerieCertificadoSAT() {
        return nSerieCertificadoSAT;
    }

    public void setnSerieCertificadoSAT(String nSerieCertificadoSAT) {
        this.nSerieCertificadoSAT = nSerieCertificadoSAT;
    }

    public String getFechaCertificacion() {
        return fechaCertificacion;
    }

    public void setFechaCertificacion(String fechaCertificacion) {
        this.fechaCertificacion = fechaCertificacion;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public BigDecimal getIdFacturaPk() {
        return idFacturaPk;
    }

    public void setIdFacturaPk(BigDecimal idFacturaPk) {
        this.idFacturaPk = idFacturaPk;
    }

    public BigDecimal getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(BigDecimal numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }
    
    
    
    
    
}
