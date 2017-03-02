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
public class Emisor {
    
    private String nombre;
    private String rfc;
    private TUbicacionFiscal domicilioFiscal;
    private TUbicacion expedidoEn;

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

    public TUbicacionFiscal getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(TUbicacionFiscal domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public TUbicacion getExpedidoEn() {
        return expedidoEn;
    }

    public void setExpedidoEn(TUbicacion expedidoEn) {
        this.expedidoEn = expedidoEn;
    }
    
    
    
}
