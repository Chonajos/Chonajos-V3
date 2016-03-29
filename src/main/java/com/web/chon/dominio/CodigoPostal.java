/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

/**
 *
 * @author freddy
 */
public class CodigoPostal 
{
    private int id_cp;

    public int getId_cp() {
        return id_cp;
    }

    public void setId_cp(int id_cp) {
        this.id_cp = id_cp;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreColonia() {
        return nombreColonia;
    }

    public void setNombreColonia(String nombreColonia) {
        this.nombreColonia = nombreColonia;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }
    

    @Override
    public String toString() {
        return "CodigoPostal{" + "id_cp=" + id_cp + ", codigoPostal=" + codigoPostal + ", nombreColonia=" + nombreColonia + ", idMunicipio=" + idMunicipio + ", nombreMunicipio=" + nombreMunicipio + ", idEntidad=" + idEntidad + ", nombreEntidad=" + nombreEntidad + '}';
    }
    private String numeropostal;

    public String getNumeropostal() {
        return numeropostal;
    }

    public void setNumeropostal(String numeropostal) {
        this.numeropostal = numeropostal;
    }
    private String codigoPostal;
    private String codigoPostalFiscal;

    public String getCodigoPostalFiscal() {
        return codigoPostalFiscal;
    }

    public void setCodigoPostalFiscal(String codigoPostalFiscal) {
        this.codigoPostalFiscal = codigoPostalFiscal;
    }
    private String nombreColonia;
    private int idMunicipio;
    private String nombreMunicipio;
    private int idEntidad;
    private String nombreEntidad;
}
