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
public class Provedor extends ValueObject implements Serializable{
    private static final long serialVersionUID = 1L;
    private BigDecimal idProvedorPK;  
    private String nombreProvedor;   
    private String apaternoProve;  
    private String amaternoProve;  
    private String empresa;
    private String calleProve;
    private Character sexoProve;
    private Date fechaNacimientoProve;
    private int telefonoMovilProve;
    private int telefonoFijoMovil;
    private int extensionProve;
    private int numIntProve;
    private int numExtProve;
    private int claveCelularProve;
    private int ladaCelularProve;
    private BigDecimal idCPProveFK;
    private String calleFiscalProve;
    private int numIntFiscalProve;
    private int numExtFiscalProve;
    private BigDecimal idCPFiscalProveFK;
    private int nextelProve;
    private String razonProve;
    private String rfcProve;
    private int ladaOficinaProve;
    private int claveOficinaProve;
    private int nextelClaveProve;
    private BigDecimal idStatusFK;
    private Date fechaAlta;
    private String nickName;
    private BigDecimal idEntidadFK;
    private String nombreEntidad;
    private BigDecimal idMunicipioFK;
    private String nombreMunicipio;
    private String nombreColonia;
    private String codigoPostal;
    private BigDecimal idEntidadFiscalFK;
    private String nombreEntidadFiscal;
    private BigDecimal idMunicipioFiscalFK;
    private String nombreMunicipioFiscal;
    private String nombreColoniaFiscal;
    private String codigoPostalFiscal;
    private String nombreStatus;
    private boolean statusProvedorBoolean;
    private String correo;

    @Override
    public String toString() {
        return "Provedor{" + "idProvedorPK=" + idProvedorPK + ", nombreProvedor=" + nombreProvedor + ", apaternoProve=" + apaternoProve + ", amaternoProve=" + amaternoProve + ", empresa=" + empresa + ", calleProve=" + calleProve + ", sexoProve=" + sexoProve + ", fechaNacimientoProve=" + fechaNacimientoProve + ", telefonoMovilProve=" + telefonoMovilProve + ", telefonoFijoMovil=" + telefonoFijoMovil + ", extensionProve=" + extensionProve + ", numIntProve=" + numIntProve + ", numExtProve=" + numExtProve + ", claveCelularProve=" + claveCelularProve + ", ladaCelularProve=" + ladaCelularProve + ", idCPProveFK=" + idCPProveFK + ", calleFiscalProve=" + calleFiscalProve + ", numIntFiscalProve=" + numIntFiscalProve + ", numExtFiscalProve=" + numExtFiscalProve + ", idCPFiscalProveFK=" + idCPFiscalProveFK + ", nextelProve=" + nextelProve + ", razonProve=" + razonProve + ", rfcProve=" + rfcProve + ", ladaOficinaProve=" + ladaOficinaProve + ", claveOficinaProve=" + claveOficinaProve + ", nextelClaveProve=" + nextelClaveProve + ", idStatusFK=" + idStatusFK + ", fechaAlta=" + fechaAlta + ", nickName=" + nickName + ", idEntidadFK=" + idEntidadFK + ", nombreEntidad=" + nombreEntidad + ", idMunicipioFK=" + idMunicipioFK + ", nombreMunicipio=" + nombreMunicipio + ", nombreColonia=" + nombreColonia + ", codigoPostal=" + codigoPostal + ", idEntidadFiscalFK=" + idEntidadFiscalFK + ", nombreEntidadFiscal=" + nombreEntidadFiscal + ", idMunicipioFiscalFK=" + idMunicipioFiscalFK + ", nombreMunicipioFiscal=" + nombreMunicipioFiscal + ", nombreColoniaFiscal=" + nombreColoniaFiscal + ", codigoPostalFiscal=" + codigoPostalFiscal + ", nombreStatus=" + nombreStatus + ", statusProvedorBoolean=" + statusProvedorBoolean + ", correo=" + correo + '}';
    }

   

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    

    public boolean isStatusProvedorBoolean() {
        return statusProvedorBoolean;
    }

    public void setStatusProvedorBoolean(boolean statusProvedorBoolean) {
        this.statusProvedorBoolean = statusProvedorBoolean;
    }
    
    

    public BigDecimal getIdProvedorPK() {
        return idProvedorPK;
    }

    public void setIdProvedorPK(BigDecimal idProvedorPK) {
        this.idProvedorPK = idProvedorPK;
    }

    public String getNombreProvedor() {
        return nombreProvedor;
    }

    public void setNombreProvedor(String nombreVendedor) {
        this.nombreProvedor = nombreVendedor;
    }

    public String getApaternoProve() {
        return apaternoProve;
    }

    public void setApaternoProve(String apaternoProve) {
        this.apaternoProve = apaternoProve;
    }

    public String getAmaternoProve() {
        return amaternoProve;
    }

    public void setAmaternoProve(String amaternoProve) {
        this.amaternoProve = amaternoProve;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCalleProve() {
        return calleProve;
    }

    public void setCalleProve(String calleProve) {
        this.calleProve = calleProve;
    }

    public Character getSexoProve() {
        return sexoProve;
    }

    public void setSexoProve(Character sexoProve) {
        this.sexoProve = sexoProve;
    }

    public Date getFechaNacimientoProve() {
        return fechaNacimientoProve;
    }

    public void setFechaNacimientoProve(Date fechaNacimientoProve) {
        this.fechaNacimientoProve = fechaNacimientoProve;
    }

    public int getTelefonoMovilProve() {
        return telefonoMovilProve;
    }

    public void setTelefonoMovilProve(int telefonoMovilProve) {
        this.telefonoMovilProve = telefonoMovilProve;
    }

    public int getTelefonoFijoMovil() {
        return telefonoFijoMovil;
    }

    public void setTelefonoFijoMovil(int telefonoFijoMovil) {
        this.telefonoFijoMovil = telefonoFijoMovil;
    }

    public int getExtensionProve() {
        return extensionProve;
    }

    public void setExtensionProve(int extensionProve) {
        this.extensionProve = extensionProve;
    }

    public int getNumIntProve() {
        return numIntProve;
    }

    public void setNumIntProve(int numIntProve) {
        this.numIntProve = numIntProve;
    }

    public int getNumExtProve() {
        return numExtProve;
    }

    public void setNumExtProve(int numExtProve) {
        this.numExtProve = numExtProve;
    }

    public int getClaveCelularProve() {
        return claveCelularProve;
    }

    public void setClaveCelularProve(int claveCelularProve) {
        this.claveCelularProve = claveCelularProve;
    }

    public int getLadaCelularProve() {
        return ladaCelularProve;
    }

    public void setLadaCelularProve(int ladaCelularProve) {
        this.ladaCelularProve = ladaCelularProve;
    }

    public BigDecimal getIdCPProveFK() {
        return idCPProveFK;
    }

    public void setIdCPProveFK(BigDecimal idCPProveFK) {
        this.idCPProveFK = idCPProveFK;
    }

    public String getCalleFiscalProve() {
        return calleFiscalProve;
    }

    public void setCalleFiscalProve(String calleFiscalProve) {
        this.calleFiscalProve = calleFiscalProve;
    }

    public int getNumIntFiscalProve() {
        return numIntFiscalProve;
    }

    public void setNumIntFiscalProve(int numIntFiscalProve) {
        this.numIntFiscalProve = numIntFiscalProve;
    }

    public int getNumExtFiscalProve() {
        return numExtFiscalProve;
    }

    public void setNumExtFiscalProve(int numExtFiscalProve) {
        this.numExtFiscalProve = numExtFiscalProve;
    }

    public BigDecimal getIdCPFiscalProveFK() {
        return idCPFiscalProveFK;
    }

    public void setIdCPFiscalProveFK(BigDecimal idCPFiscalProveFK) {
        this.idCPFiscalProveFK = idCPFiscalProveFK;
    }

    public int getNextelProve() {
        return nextelProve;
    }

    public void setNextelProve(int nextelProve) {
        this.nextelProve = nextelProve;
    }

    public String getRazonProve() {
        return razonProve;
    }

    public void setRazonProve(String razonProve) {
        this.razonProve = razonProve;
    }

    public String getRfcProve() {
        return rfcProve;
    }

    public void setRfcProve(String rfcProve) {
        this.rfcProve = rfcProve;
    }

    public int getLadaOficinaProve() {
        return ladaOficinaProve;
    }

    public void setLadaOficinaProve(int ladaOficinaProve) {
        this.ladaOficinaProve = ladaOficinaProve;
    }

    public int getClaveOficinaProve() {
        return claveOficinaProve;
    }

    public void setClaveOficinaProve(int claveOficinaProve) {
        this.claveOficinaProve = claveOficinaProve;
    }

    public int getNextelClaveProve() {
        return nextelClaveProve;
    }

    public void setNextelClaveProve(int nextelClaveProve) {
        this.nextelClaveProve = nextelClaveProve;
    }

    public BigDecimal getIdStatusFK() {
        return idStatusFK;
    }

    public void setIdStatusFK(BigDecimal idStatusFK) {
        this.idStatusFK = idStatusFK;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getIdEntidadFK() {
        return idEntidadFK;
    }

    public void setIdEntidadFK(BigDecimal idEntidadFK) {
        this.idEntidadFK = idEntidadFK;
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

    public BigDecimal getIdEntidadFiscalFK() {
        return idEntidadFiscalFK;
    }

    public void setIdEntidadFiscalFK(BigDecimal idEntidadFiscalFK) {
        this.idEntidadFiscalFK = idEntidadFiscalFK;
    }

    public String getNombreEntidadFiscal() {
        return nombreEntidadFiscal;
    }

    public void setNombreEntidadFiscal(String nombreEntidadFiscal) {
        this.nombreEntidadFiscal = nombreEntidadFiscal;
    }

    public BigDecimal getIdMunicipioFiscalFK() {
        return idMunicipioFiscalFK;
    }

    public void setIdMunicipioFiscalFK(BigDecimal idMunicipioFiscalFK) {
        this.idMunicipioFiscalFK = idMunicipioFiscalFK;
    }

    public String getNombreMunicipioFiscal() {
        return nombreMunicipioFiscal;
    }

    public void setNombreMunicipioFiscal(String nombreMunicipioFiscal) {
        this.nombreMunicipioFiscal = nombreMunicipioFiscal;
    }

    public String getNombreColoniaFiscal() {
        return nombreColoniaFiscal;
    }

    public void setNombreColoniaFiscal(String nombreColoniaFiscal) {
        this.nombreColoniaFiscal = nombreColoniaFiscal;
    }

    public String getCodigoPostalFiscal() {
        return codigoPostalFiscal;
    }

    public void setCodigoPostalFiscal(String codigoPostalFiscal) {
        this.codigoPostalFiscal = codigoPostalFiscal;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

   
   

    @Override
    public void reset() {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
   
    
}
