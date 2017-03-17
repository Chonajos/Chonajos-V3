/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author jramirez
 */
public class DatosFacturacion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idDatosFacturacionPk;
    private BigDecimal idClienteFk;
    private BigDecimal idSucursalFk;
    private String razonSocial;
    private String rfc;
    private String calle;
    private String numInt;
    private String numExt;
    private String pais;
    private String localidad;
    private BigDecimal idCodigoPostalFk;
    private String telefono;
    private String correo;
    private String regimen;
    private String field;
    private String ruta_llave_privada;
    private String ruta_certificado;
    private String ruta_llave_privada_cancel;
    private String ruta_certificado_cancel;
    private String municipio;
    private String colonia;
    private String estado;
    private String codigoPostal;
    private String nombre;
    private String clavePublica;
    
    private BigDecimal idEntidad;
    private BigDecimal idMunicipio;

    public String getClavePublica() {
        return clavePublica;
    }

    public void setClavePublica(String clavePublica) {
        this.clavePublica = clavePublica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getIdDatosFacturacionPk() {
        return idDatosFacturacionPk;
    }

    public void setIdDatosFacturacionPk(BigDecimal idDatosFacturacionPk) {
        this.idDatosFacturacionPk = idDatosFacturacionPk;
    }

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumInt() {
        return numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public BigDecimal getIdCodigoPostalFk() {
        return idCodigoPostalFk;
    }

    public void setIdCodigoPostalFk(BigDecimal idCodigoPostalFk) {
        this.idCodigoPostalFk = idCodigoPostalFk;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRuta_llave_privada() {
        return ruta_llave_privada;
    }

    public void setRuta_llave_privada(String ruta_llave_privada) {
        this.ruta_llave_privada = ruta_llave_privada;
    }

    public String getRuta_certificado() {
        return ruta_certificado;
    }

    public void setRuta_certificado(String ruta_certificado) {
        this.ruta_certificado = ruta_certificado;
    }

    public String getRuta_llave_privada_cancel() {
        return ruta_llave_privada_cancel;
    }

    public void setRuta_llave_privada_cancel(String ruta_llave_privada_cancel) {
        this.ruta_llave_privada_cancel = ruta_llave_privada_cancel;
    }

    public String getRuta_certificado_cancel() {
        return ruta_certificado_cancel;
    }

    public void setRuta_certificado_cancel(String ruta_certificado_cancel) {
        this.ruta_certificado_cancel = ruta_certificado_cancel;
    }

    public BigDecimal getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(BigDecimal idEntidad) {
        this.idEntidad = idEntidad;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    
    

    public void reset() {

        idDatosFacturacionPk = null;
        idClienteFk = null;
        idSucursalFk = null;
        razonSocial = null;
        rfc = null;
        calle = null;
        numInt = null;
        numExt = null;
        pais = null;
        localidad = null;
        idCodigoPostalFk = null;
        telefono = null;
        correo = null;
        regimen = null;
        field = null;
        ruta_llave_privada = null;
        ruta_certificado = null;
        ruta_llave_privada_cancel = null;
        ruta_certificado_cancel = null;
        municipio = null;
        colonia = null;
        estado = null;
        codigoPostal = null;
        nombre = null;
        clavePublica = null;

    }

}
