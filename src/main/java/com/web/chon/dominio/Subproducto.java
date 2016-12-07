/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Juan de la Cruz
 */
public class Subproducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idSubproductoPk;
    private String nombreSubproducto;
    private String descripcionSubproducto;
    private String urlImagenSubproducto;
    private String idProductoFk;
    private String nombreCategoria;
    private BigDecimal precioProducto;

    public Subproducto() {
    }

    public Subproducto(String idSubproductoPk) {
        this.idSubproductoPk = idSubproductoPk;
    }

    public Subproducto(String idSubproductoPk, String nombreSubproducto) {
        this.idSubproductoPk = idSubproductoPk;
        this.nombreSubproducto = nombreSubproducto;
    }

    public String getIdSubproductoPk() {
        return idSubproductoPk;
    }

    public void setIdSubproductoPk(String idSubproductoPk) {
        this.idSubproductoPk = idSubproductoPk;
    }

    public String getNombreSubproducto() {
        return nombreSubproducto;
    }

    public void setNombreSubproducto(String nombreSubproducto) {
        this.nombreSubproducto = nombreSubproducto;
    }

    public String getDescripcionSubproducto() {
        return descripcionSubproducto;
    }

    public void setDescripcionSubproducto(String descripcionSubproducto) {
        this.descripcionSubproducto = descripcionSubproducto;
    }

    public String getUrlImagenSubproducto() {
        return urlImagenSubproducto;
    }

    public void setUrlImagenSubproducto(String urlImagenSubproducto) {
        this.urlImagenSubproducto = urlImagenSubproducto;
    }

    public String getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(String idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }
    
    
 
    @Override
    public String toString() {
        return "Subproducto{" + "idSubproductoPk=" + idSubproductoPk + ", nombreSubproducto=" + nombreSubproducto + ", descripcionSubproducto=" + descripcionSubproducto + ", urlImagenSubproducto=" + urlImagenSubproducto + ", idProductoFk=" + idProductoFk + ", nombreCategoria=" + nombreCategoria + '}';
    }

   

   

    
    
    
}
