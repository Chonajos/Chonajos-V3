/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

/**
 *
 * @author Juan
 */
public class Receptor {
    private String nombre;
    private String rfc;
    private TUbicacion domicilio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public TUbicacion getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(TUbicacion domicilio) {
        this.domicilio = domicilio;
    }
    
    
    
    
}
