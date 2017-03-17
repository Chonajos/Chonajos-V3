/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jramirez
 */
public class FacturaPDFDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private BigDecimal idFacturaPk;
    private String numeroFactura;
    private BigDecimal idUsuarioFk;
    private BigDecimal idClienteFk;
    private BigDecimal idLlaveFk;
    private BigDecimal idTipoLlaveFk;
    
    private byte[] fichero;
    private String nombreArchivoTimbrado;
    
    
    private String nombreEstatus;
    private BigDecimal idStatusFk;
    private String comentarios;
    private String nombreSucursal;
    private BigDecimal idSucursalFk;
    
    
    //Datos de Emisor:
    private String nombreEmisor;
    private String rfcEmisor;
    private String calleEmisor;
    private String noExteriorEmisor;
    private String coloniaEmisor;
    private String localidadEmisor;
    private String municipioEmisor;
    private String estadoEmisor;
    private String paisEmisor;
    private String codigoPostalEmisor;
    private String domicilioFiscalEmisor;
    private String noInteriorEmisor;
    
    
    //========Datos para PDF======//
    //detail 1
    private String razonSocialEmpresa;
   // private String rfcEmisor;
    private String domiciloFiscalEmisor;
   // private String coloniaEmisor;
    private String ciudadEmisor;
    private String codigoPostalE;
    
    private String parametroA;
    private String parametroB;
    private Date fechaEmision;
    private String lugarEmision;
    
    
    //detail 2
    //Datos de Cliente:
    private String nombreCliente;
    private String rfcCliente;
    private String calleFiscalCliente;
    private String coloniaCliente;
    private String domicilioFiscalCliente;
    private String ciudadCliente;
    private String codigoPostalCliente;
    private String telefonoCliente;
    private String numeroExteriorCliente;
    private String numeroInteriorCliente;
    private String municipioCliente;
    private String estadoCliente;
    private String localidadCliente;
    private String paisCliente;
    
    //detail 3
    private ArrayList<VentaProductoMayoreo> listaProductosMayoreo;
    private ArrayList<VentaProducto> listaProductosMenudeo;
    
    private Venta ventaMenudeo;
    private VentaMayoreo ventaMayoreo;
    
    //Detail 4
    private String importeLetra;
    private BigDecimal importe;
    private BigDecimal descuento;
    private BigDecimal iva1;
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
    private Date fechaCertificacion;
    private StreamedContent file;

    @Override
    public String toString() {
        return "FacturaPDFDomain{" + "idFacturaPk=" + idFacturaPk + ", numeroFactura=" + numeroFactura + ", idUsuarioFk=" + idUsuarioFk + ", idClienteFk=" + idClienteFk + ", idLlaveFk=" + idLlaveFk + ", idTipoLlaveFk=" + idTipoLlaveFk + ", fichero=" + fichero + ", nombreArchivoTimbrado=" + nombreArchivoTimbrado + ", nombreEstatus=" + nombreEstatus + ", idStatusFk=" + idStatusFk + ", comentarios=" + comentarios + ", nombreSucursal=" + nombreSucursal + ", idSucursalFk=" + idSucursalFk + ", nombreEmisor=" + nombreEmisor + ", rfcEmisor=" + rfcEmisor + ", calleEmisor=" + calleEmisor + ", noExteriorEmisor=" + noExteriorEmisor + ", coloniaEmisor=" + coloniaEmisor + ", localidadEmisor=" + localidadEmisor + ", municipioEmisor=" + municipioEmisor + ", estadoEmisor=" + estadoEmisor + ", paisEmisor=" + paisEmisor + ", codigoPostalEmisor=" + codigoPostalEmisor + ", domicilioFiscalEmisor=" + domicilioFiscalEmisor + ", noInteriorEmisor=" + noInteriorEmisor + ", razonSocialEmpresa=" + razonSocialEmpresa + ", domiciloFiscalEmisor=" + domiciloFiscalEmisor + ", ciudadEmisor=" + ciudadEmisor + ", codigoPostalE=" + codigoPostalE + ", parametroA=" + parametroA + ", parametroB=" + parametroB + ", fechaEmision=" + fechaEmision + ", lugarEmision=" + lugarEmision + ", nombreCliente=" + nombreCliente + ", rfcCliente=" + rfcCliente + ", calleFiscalCliente=" + calleFiscalCliente + ", coloniaCliente=" + coloniaCliente + ", domicilioFiscalCliente=" + domicilioFiscalCliente + ", ciudadCliente=" + ciudadCliente + ", codigoPostalCliente=" + codigoPostalCliente + ", telefonoCliente=" + telefonoCliente + ", numeroExteriorCliente=" + numeroExteriorCliente + ", numeroInteriorCliente=" + numeroInteriorCliente + ", municipioCliente=" + municipioCliente + ", estadoCliente=" + estadoCliente + ", localidadCliente=" + localidadCliente + ", paisCliente=" + paisCliente + ", listaProductosMayoreo=" + listaProductosMayoreo + ", listaProductosMenudeo=" + listaProductosMenudeo + ", ventaMenudeo=" + ventaMenudeo + ", ventaMayoreo=" + ventaMayoreo + ", importeLetra=" + importeLetra + ", importe=" + importe + ", descuento=" + descuento + ", iva1=" + iva1 + ", iva2=" + iva2 + ", total=" + total + ", selloDigital=" + selloDigital + ", cadena=" + cadena + ", selloSAT=" + selloSAT + ", cadenaQR=" + cadenaQR + ", formaPago=" + formaPago + ", nCuenta=" + nCuenta + ", metodoPago=" + metodoPago + ", nSerieCertificadoCSD=" + nSerieCertificadoCSD + ", folioFiscal=" + folioFiscal + ", nSerieCertificadoSAT=" + nSerieCertificadoSAT + ", fechaCertificacion=" + fechaCertificacion + ", file=" + file + '}';
    }

    
    
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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIva1() {
        return iva1;
    }

    public void setIva1(BigDecimal iva1) {
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

    

    public BigDecimal getIdFacturaPk() {
        return idFacturaPk;
    }

    public void setIdFacturaPk(BigDecimal idFacturaPk) {
        this.idFacturaPk = idFacturaPk;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getCalleEmisor() {
        return calleEmisor;
    }

    public void setCalleEmisor(String calleEmisor) {
        this.calleEmisor = calleEmisor;
    }

    public String getNoExteriorEmisor() {
        return noExteriorEmisor;
    }

    public void setNoExteriorEmisor(String noExteriorEmisor) {
        this.noExteriorEmisor = noExteriorEmisor;
    }

    public String getLocalidadEmisor() {
        return localidadEmisor;
    }

    public void setLocalidadEmisor(String localidadEmisor) {
        this.localidadEmisor = localidadEmisor;
    }

    public String getMunicipioEmisor() {
        return municipioEmisor;
    }

    public void setMunicipioEmisor(String municipioEmisor) {
        this.municipioEmisor = municipioEmisor;
    }

    public String getEstadoEmisor() {
        return estadoEmisor;
    }

    public void setEstadoEmisor(String estadoEmisor) {
        this.estadoEmisor = estadoEmisor;
    }

    public String getPaisEmisor() {
        return paisEmisor;
    }

    public void setPaisEmisor(String paisEmisor) {
        this.paisEmisor = paisEmisor;
    }

    public String getCodigoPostalEmisor() {
        return codigoPostalEmisor;
    }

    public void setCodigoPostalEmisor(String codigoPostalEmisor) {
        this.codigoPostalEmisor = codigoPostalEmisor;
    }

    public String getDomicilioFiscalEmisor() {
        return domicilioFiscalEmisor;
    }

    public void setDomicilioFiscalEmisor(String domicilioFiscalEmisor) {
        this.domicilioFiscalEmisor = domicilioFiscalEmisor;
    }

    public String getNoInteriorEmisor() {
        return noInteriorEmisor;
    }

    public void setNoInteriorEmisor(String noInteriorEmisor) {
        this.noInteriorEmisor = noInteriorEmisor;
    }

    public String getCalleFiscalCliente() {
        return calleFiscalCliente;
    }

    public void setCalleFiscalCliente(String calleFiscalCliente) {
        this.calleFiscalCliente = calleFiscalCliente;
    }

    public String getNumeroExteriorCliente() {
        return numeroExteriorCliente;
    }

    public void setNumeroExteriorCliente(String numeroExteriorCliente) {
        this.numeroExteriorCliente = numeroExteriorCliente;
    }

    public String getNumeroInteriorCliente() {
        return numeroInteriorCliente;
    }

    public void setNumeroInteriorCliente(String numeroInteriorCliente) {
        this.numeroInteriorCliente = numeroInteriorCliente;
    }

    public String getMunicipioCliente() {
        return municipioCliente;
    }

    public void setMunicipioCliente(String municipioCliente) {
        this.municipioCliente = municipioCliente;
    }

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getLocalidadCliente() {
        return localidadCliente;
    }

    public void setLocalidadCliente(String localidadCliente) {
        this.localidadCliente = localidadCliente;
    }

    public String getPaisCliente() {
        return paisCliente;
    }

    public void setPaisCliente(String paisCliente) {
        this.paisCliente = paisCliente;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaCertificacion() {
        return fechaCertificacion;
    }

    public void setFechaCertificacion(Date fechaCertificacion) {
        this.fechaCertificacion = fechaCertificacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getNombreArchivoTimbrado() {
        return nombreArchivoTimbrado;
    }

    public void setNombreArchivoTimbrado(String nombreArchivoTimbrado) {
        this.nombreArchivoTimbrado = nombreArchivoTimbrado;
    }
    
    
    
    
    
    
    
}
