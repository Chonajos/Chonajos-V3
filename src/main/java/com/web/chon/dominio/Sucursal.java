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
 * @author marcogante
 */
public class Sucursal extends ValueObject implements Serializable {

   private static final long serialVersionUID = 1L;
    private BigDecimal idSucursalPk;
    private String nombreSucursal;
    private String calleSucursal;
    private BigDecimal cpSucursal;
    private BigDecimal telefonoSucursal;
    private BigDecimal numInt;
    private BigDecimal numExt;
    private BigDecimal idStatusSucursalfk;
    private String nombreStatus;
    private BigDecimal idEntidadFk;
    private String nombreEntidad;
    private BigDecimal idMunicipioFK;
    private String nombreMunicipio;
    private String nombreColonia;
    private String codigoPostal;
    private boolean statusSucursal;


    @Override
    public String toString() {
        return "Sucursa{" + "idSucursalPk=" + idSucursalPk + ", nombreSucursal=" + nombreSucursal + ", calleSucursal=" + calleSucursal + ", cpSucursal=" + cpSucursal + ", telefonoSucursal=" + telefonoSucursal + ", numInt=" + numInt + ", numExt=" + numExt + ", idStatusSucursalfk=" + idStatusSucursalfk + ", nombreStatus=" + nombreStatus + ", idEntidadFk=" + idEntidadFk + ", nombreEntidad=" + nombreEntidad + ", idMunicipioFK=" + idMunicipioFK + ", nombreMunicipio=" + nombreMunicipio + ", nombreColonia=" + nombreColonia + ", codigoPostal=" + codigoPostal + '}';
    }

    @Override
    public void reset() 
    {
        idSucursalPk=null;
        nombreSucursal=null;
        calleSucursal=null;
        cpSucursal=null;
        telefonoSucursal=null;
        numInt=null;
        numExt=null;
        idStatusSucursalfk=null;
        nombreStatus=null;
        idEntidadFk=null;
        nombreEntidad=null;
        idMunicipioFK=null;
        nombreMunicipio=null;
        nombreColonia=null;
        codigoPostal=null;
    }

    public boolean isStatusSucursal() {
        return statusSucursal;
    }

    public void setStatusSucursal(boolean statusSucursal) {
        this.statusSucursal = statusSucursal;
    }

    
    public BigDecimal getIdSucursalPk() {
        return idSucursalPk;
    }

    public void setIdSucursalPk(BigDecimal idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getCalleSucursal() {
        return calleSucursal;
    }

    public void setCalleSucursal(String calleSucursal) {
        this.calleSucursal = calleSucursal;
    }

    public BigDecimal getCpSucursal() {
        return cpSucursal;
    }

    public void setCpSucursal(BigDecimal cpSucursal) {
        this.cpSucursal = cpSucursal;
    }

    public BigDecimal getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(BigDecimal telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    public BigDecimal getNumInt() {
        return numInt;
    }

    public void setNumInt(BigDecimal numInt) {
        this.numInt = numInt;
    }

    public BigDecimal getNumExt() {
        return numExt;
    }

    public void setNumExt(BigDecimal numExt) {
        this.numExt = numExt;
    }

    public BigDecimal getIdStatusSucursalfk() {
        return idStatusSucursalfk;
    }

    public void setIdStatusSucursalfk(BigDecimal idStatusSucursalfk) {
        this.idStatusSucursalfk = idStatusSucursalfk;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public BigDecimal getIdEntidadFk() {
        return idEntidadFk;
    }

    public void setIdEntidadFk(BigDecimal idEntidadFk) {
        this.idEntidadFk = idEntidadFk;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public BigDecimal getIdMunicipioFK() {
        return idMunicipioFK;
    }

    public void setIdMunicipioFK(BigDecimal idMunicipioFK) {
        this.idMunicipioFK = idMunicipioFK;
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    
    

}
