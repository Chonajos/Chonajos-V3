/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author fredy
 */
public class Cliente implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id_cliente;
    private String nombre;
    private String paterno;
    private String materno;
    private String empresa;
    private String calle;
    private int ID_CP;
    private String estado;
    private String municipio;
    private Character sexo;
    private Date fecha_nacimiento;
    private int tel_movil;
    private int tel_fijo;
    private int ext;
    private String email;
    private int num_int;
    private int num_ext;
    private String colonia;
    private int clavecelular;
    private String nombreCombleto;
    private int ladacelular;
    private int ID_CP_FISCAL; 
    private String calleFiscal;
    private int num_int_fiscal;
    private int num_ext_fiscal;
    private String coloniaFiscal;
    private String estadoFiscal;
    private String municipioFiscal;
    private String codigoPostal;
    private String codigoPostalFiscal;
    private int nextel;
    private String razon_social;
    private String rfcFiscal;
    private int ladaoficina;
    private int claveoficina;
    private int nextelclave;
    private String nombreEstado;
    private String nombreDelegacionMunicipio;
    private String nombreEstadoFiscal;
    private String nombreDeleMunFiscal;

    public int getClavecelular() {
        return clavecelular;
    }

    public void setClavecelular(int clavecelular) {
        this.clavecelular = clavecelular;
    }

    public int getLadacelular() {
        return ladacelular;
    }

    public void setLadacelular(int ladacelular) {
        this.ladacelular = ladacelular;
    }

    public int getLadaoficina() {
        return ladaoficina;
    }

    public void setLadaoficina(int ladaoficina) {
        this.ladaoficina = ladaoficina;
    }

    public int getClaveoficina() {
        return claveoficina;
    }

    public void setClaveoficina(int claveoficina) {
        this.claveoficina = claveoficina;
    }

    public int getNextelclave() {
        return nextelclave;
    }

    public void setNextelclave(int nextelclave) {
        this.nextelclave = nextelclave;
    }


    
    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRfcFiscal() {
        return rfcFiscal;
    }

    public void setRfcFiscal(String rfcFiscal) {
        this.rfcFiscal = rfcFiscal;
    }

   public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreDelegacionMunicipio() {
        return nombreDelegacionMunicipio;
    }

    public void setNombreDelegacionMunicipio(String nombreDelegacionMunicipio) {
        this.nombreDelegacionMunicipio = nombreDelegacionMunicipio;
    }

    public String getNombreEstadoFiscal() {
        return nombreEstadoFiscal;
    }

    public void setNombreEstadoFiscal(String nombreEstadoFiscal) {
        this.nombreEstadoFiscal = nombreEstadoFiscal;
    }

    public String getNombreDeleMunFiscal() {
        return nombreDeleMunFiscal;
    }

    public void setNombreDeleMunFiscal(String nombreDeleMunFiscal) {
        this.nombreDeleMunFiscal = nombreDeleMunFiscal;
    }

    

    public String getCalleFiscal() {
        return calleFiscal;
    }

    public void setCalleFiscal(String calleFiscal) {
        this.calleFiscal = calleFiscal;
    }

    public int getNum_int_fiscal() {
        return num_int_fiscal;
    }

    public void setNum_int_fiscal(int num_int_fiscal) {
        this.num_int_fiscal = num_int_fiscal;
    }

    public int getNum_ext_fiscal() {
        return num_ext_fiscal;
    }

    public void setNum_ext_fiscal(int num_ext_fiscal) {
        this.num_ext_fiscal = num_ext_fiscal;
    }

    public String getColoniaFiscal() {
        return coloniaFiscal;
    }

    public void setColoniaFiscal(String coloniaFiscal) {
        this.coloniaFiscal = coloniaFiscal;
    }

    public String getEstadoFiscal() {
        return estadoFiscal;
    }

    public void setEstadoFiscal(String estadoFiscal) {
        this.estadoFiscal = estadoFiscal;
    }

   
    public int getNextel() {
        return nextel;
    }

    public void setNextel(int nextel) {
        this.nextel = nextel;
    }



    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

  

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getTel_movil() {
        return tel_movil;
    }

    public void setTel_movil(int tel_movil) {
        this.tel_movil = tel_movil;
    }

    public int getTel_fijo() {
        return tel_fijo;
    }

    public void setTel_fijo(int tel_fijo) {
        this.tel_fijo = tel_fijo;
    }

    public int getExt() {
        return ext;
    }

    public void setExt(int ext) {
        this.ext = ext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum_int() {
        return num_int;
    }

    public void setNum_int(int num_int) {
        this.num_int = num_int;
    }

    public int getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(int num_ext) {
        this.num_ext = num_ext;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

   

    public String getNombreCombleto() {
        String nombreCompleto = nombre+" "+paterno+" "+materno;
        return nombreCompleto.trim();
    }

    public void setNombreCombleto(String nombreCombleto) {
        this.nombreCombleto = nombreCombleto;
    }
    
    
    public int getID_CP() {
        return ID_CP;
    }

    public void setID_CP(int ID_CP) {
        this.ID_CP = ID_CP;
    }

    public int getID_CP_FISCAL() {
        return ID_CP_FISCAL;
    }

    public void setID_CP_FISCAL(int ID_CP_FISCAL) {
        this.ID_CP_FISCAL = ID_CP_FISCAL;
    }
    
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMunicipioFiscal() {
        return municipioFiscal;
    }

    public void setMunicipioFiscal(String municipioFiscal) {
        this.municipioFiscal = municipioFiscal;
    }
    public String getCodigoPostalFiscal() {
        return codigoPostalFiscal;
    }

    public void setCodigoPostalFiscal(String codigoPostalFiscal) {
        this.codigoPostalFiscal = codigoPostalFiscal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    @Override
    public String toString() {
        return "Cliente{" + "id_cliente=" + id_cliente + ", nombre=" + nombre + ", paterno=" + paterno + ", materno=" + materno + ", empresa=" + empresa + ", calle=" + calle + ", ID_CP=" + ID_CP + ", estado=" + estado + ", sexo=" + sexo + ", fecha_nacimiento=" + fecha_nacimiento + ", tel_movil=" + tel_movil + ", tel_fijo=" + tel_fijo + ", ext=" + ext + ", email=" + email + ", num_int=" + num_int + ", num_ext=" + num_ext + ", colonia=" + colonia + ", clavecelular=" + clavecelular + ", nombreCombleto=" + nombreCombleto + ", ladacelular=" + ladacelular + ", ID_CP_FISCAL=" + ID_CP_FISCAL + ", calleFiscal=" + calleFiscal + ", num_int_fiscal=" + num_int_fiscal + ", num_ext_fiscal=" + num_ext_fiscal + ", coloniaFiscal=" + coloniaFiscal + ", estadoFiscal=" + estadoFiscal + ", nextel=" + nextel + ", razon_social=" + razon_social + ", rfcFiscal=" + rfcFiscal + ", ladaoficina=" + ladaoficina + ", claveoficina=" + claveoficina + ", nextelclave=" + nextelclave + ", nombreEstado=" + nombreEstado + ", nombreDelegacionMunicipio=" + nombreDelegacionMunicipio + ", nombreEstadoFiscal=" + nombreEstadoFiscal + ", nombreDeleMunFiscal=" + nombreDeleMunFiscal + '}';
    }

    
   
}
