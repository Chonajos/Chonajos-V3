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
    private int cp;
    private String estado;
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
    private int ladacelular;
    private String Del_Mun;
    
    private String calleFiscal;
    private int num_int_fiscal;
    private int num_ext_fiscal;
    private String coloniaFiscal;
    private String estadoFiscal;
    private String del_mun_fiscal;
    private int nextel;
    private String razon_social;
    private String rfcFiscal;
    private int cpFiscal;
    private int ladaoficina;
    private int claveoficina;
    private int nextelclave;

    public int getcpFiscal() {
        return cpFiscal;
    }

    public void setcpFiscal(int  cpFiscal) {
        this.cpFiscal = cpFiscal;
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

    public int getCpFiscal() {
        return cpFiscal;
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

    public String getDel_mun_fiscal() {
        return del_mun_fiscal;
    }

    public void setDel_mun_fiscal(String del_mun_fiscal) {
        this.del_mun_fiscal = del_mun_fiscal;
    }

    public int getNextel() {
        return nextel;
    }

    public void setNextel(int nextel) {
        this.nextel = nextel;
    }
    

   
    @Override
    public String toString() {
        return "Cliente:  {" + "idCliente=" + id_cliente + 
                ", Nombre = " + nombre + 
                ", paterno = " + paterno+ 
                ", materno = " + materno + 
                ", Empresa = " + empresa + 
                ", Calle = " + calle+            
                ", cp = "+ cp+ 
                ", Estado ="+ estado+
                ", sexo = " + sexo + 
                ", Fecha Nacimiento = " + fecha_nacimiento + 
                ", telefonoMovil = " + tel_movil + 
                ", telefonoFijo =" + tel_fijo+ 
                ", Extension = " + ext + 
                ", correoCliente=" + email+ 
                ", Num_Interior = " + num_int + 
                ", Num_Exterior" + num_ext + 
                ", Colonia = " + colonia + 
                ", ClaveCelular = " + clavecelular + 
                ", LadaCelular = " + ladacelular + 
                ", Del Mun = " + Del_Mun + 
                ", Calle Fiscal =" + num_int_fiscal + 
                ", Num_Interior Fiscal = " + num_int_fiscal+ 
                ", Num_Exterior Fiscal = " + num_ext_fiscal +
                ", Colonia Fiscal = " + coloniaFiscal + 
                ", Estado Fiscal = " + estadoFiscal + 
                ", Del Mun Fiscal = " + del_mun_fiscal + 
                ", Nextel = " + nextel + 
                ", Razon = " + razon_social+ 
                ", RFC =" + rfcFiscal + 
                ", CPFiscal = " + cpFiscal + 
                 ",LADAOFICINA = " + ladaoficina +
                 ",CLAVE OFICINA = " + claveoficina + 
                ", NEXTELCLAVE = " + nextelclave + 
                + '}';
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

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
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

    public String getDel_Mun() {
        return Del_Mun;
    }

    public void setDel_Mun(String Del_Mun) {
        this.Del_Mun = Del_Mun;
    }

    public String getNombreCombleto() {
        String nombreCompleto = nombre+" "+paterno+" "+materno;
        return nombreCompleto.trim();
    }

    public void setNombreCombleto(String nombreCombleto) {
        this.nombreCombleto = nombreCombleto;
    }
    
    
}
