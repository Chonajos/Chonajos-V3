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
 * @author fredy
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idClientePk;
    private String nombre;
    private String paterno;
    private String materno;
    private String nombreCompleto;
    private Character sexo;
    
    private BigDecimal idStatusFk;
    private Date fechaAlta;
    
    private String empresa;
    private String rfc;
    private String razonSocial;
    
    //Datos de Domicilio
    private String calle;
    private BigDecimal idCodigoPostalFk;
    private String nombreEstado;
    private String nombreMunicipio;
    private String nombreColonia;
    private String numInterior;
    private String numExterior;
    private String localidad;
    private String pais;
    private String codigoPostal;
    //llaves de tablas necesarias
    private BigDecimal idMunicipioFk;
    private BigDecimal idEntidadFk;
    private BigDecimal idColoniaFk;
    
    //Datos de Contaco
    
    private BigDecimal telefonoCelular;
    private BigDecimal nextel;
    private BigDecimal telefonoOficina;
    private BigDecimal ext;
    private String correo;
    
    //Datos de Crédito
    private String tipoPersona;
    private BigDecimal diasCredito;
    private BigDecimal limiteCredito;
    private BigDecimal utilizadoMenudeo;
    private BigDecimal utilizadoMayoreo;
    private BigDecimal utilizadoTotal;
    private BigDecimal creditoDisponible;
    private BigDecimal utilizadoDocumentos;
    
    private BigDecimal promedioRecuperacionTres;
    private BigDecimal promedioRecuperacion;
    
   
    
   
    public String getNombreCompleto() {
        String nombre = this.nombre == null ? "" : this.nombre;
        String paterno = this.paterno == null ? "" : this.paterno;
        String materno = this.materno == null ? "" : this.materno;
        return nombreCompleto == null? nombre.trim()+" "+paterno.trim()+" "+materno.trim():nombreCompleto.trim();

    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    
    

    public BigDecimal getIdClientePk() {
        return idClientePk;
    }

    public void setIdClientePk(BigDecimal idClientePk) {
        this.idClientePk = idClientePk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

    public BigDecimal getIdCodigoPostalFk() {
        return idCodigoPostalFk;
    }

    public void setIdCodigoPostalFk(BigDecimal idCodigoPostalFk) {
        this.idCodigoPostalFk = idCodigoPostalFk;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getNombreColonia() {
        return nombreColonia;
    }

    public void setNombreColonia(String nombreColonia) {
        this.nombreColonia = nombreColonia;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public BigDecimal getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(BigDecimal telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public BigDecimal getNextel() {
        return nextel;
    }

    public void setNextel(BigDecimal nextel) {
        this.nextel = nextel;
    }

    public BigDecimal getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(BigDecimal telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public BigDecimal getExt() {
        return ext;
    }

    public void setExt(BigDecimal ext) {
        this.ext = ext;
    }
    

    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public BigDecimal getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(BigDecimal diasCredito) {
        this.diasCredito = diasCredito;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public BigDecimal getUtilizadoMenudeo() {
        return utilizadoMenudeo;
    }

    public void setUtilizadoMenudeo(BigDecimal utilizadoMenudeo) {
        this.utilizadoMenudeo = utilizadoMenudeo;
    }

    public BigDecimal getUtilizadoMayoreo() {
        return utilizadoMayoreo;
    }

    public void setUtilizadoMayoreo(BigDecimal utilizadoMayoreo) {
        this.utilizadoMayoreo = utilizadoMayoreo;
    }

    public BigDecimal getUtilizadoTotal() {
        return utilizadoTotal;
    }

    public void setUtilizadoTotal(BigDecimal utilizadoTotal) {
        this.utilizadoTotal = utilizadoTotal;
    }

    public BigDecimal getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(BigDecimal creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    public BigDecimal getUtilizadoDocumentos() {
        return utilizadoDocumentos;
    }

    public void setUtilizadoDocumentos(BigDecimal utilizadoDocumentos) {
        this.utilizadoDocumentos = utilizadoDocumentos;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getIdMunicipioFk() {
        return idMunicipioFk;
    }

    public void setIdMunicipioFk(BigDecimal idMunicipioFk) {
        this.idMunicipioFk = idMunicipioFk;
    }

    public BigDecimal getIdEntidadFk() {
        return idEntidadFk;
    }

    public void setIdEntidadFk(BigDecimal idEntidadFk) {
        this.idEntidadFk = idEntidadFk;
    }

    public BigDecimal getIdColoniaFk() {
        return idColoniaFk;
    }

    public void setIdColoniaFk(BigDecimal idColoniaFk) {
        this.idColoniaFk = idColoniaFk;
    }

    public BigDecimal getPromedioRecuperacionTres() {
        return promedioRecuperacionTres;
    }

    public void setPromedioRecuperacionTres(BigDecimal promedioRecuperacionTres) {
        this.promedioRecuperacionTres = promedioRecuperacionTres;
    }

    public BigDecimal getPromedioRecuperacion() {
        return promedioRecuperacion;
    }

    public void setPromedioRecuperacion(BigDecimal promedioRecuperacion) {
        this.promedioRecuperacion = promedioRecuperacion;
    }

}
