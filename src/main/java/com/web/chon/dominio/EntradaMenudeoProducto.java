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
 * @author freddy
 */
public class EntradaMenudeoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEmmpPk;
    private BigDecimal idEmmFk;
    private String idSubproductoFk;
    private BigDecimal idtipoEmpaqueFk;
    private BigDecimal kilosTotales;
    private BigDecimal cantidadEmpaque;
    private String comentarios;
    private BigDecimal precio;
    private String nombreProducto;
    private String nombreEmpaque;

    public void reset() {
        idEmmpPk = null;
        idEmmFk = null;
        idSubproductoFk = null;
        idtipoEmpaqueFk = null;
        kilosTotales = null;
        cantidadEmpaque = null;
        comentarios = null;
        precio = null;
        nombreProducto = null;
        nombreEmpaque = null;
    }

    @Override
    public String toString() {
        return "EntradaMenudeoProducto{" + "idEmmpPk=" + idEmmpPk + ", idEmmFk=" + idEmmFk + ", idSubproductoFk=" + idSubproductoFk + ", idtipoEmpaqueFk=" + idtipoEmpaqueFk + ", kilosTotales=" + kilosTotales + ", cantidadEmpaque=" + cantidadEmpaque + ", comentarios=" + comentarios + ", precio=" + precio + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + '}';
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getIdEmmpPk() {
        return idEmmpPk;
    }

    public void setIdEmmpPk(BigDecimal idEmmpPk) {
        this.idEmmpPk = idEmmpPk;
    }

    public BigDecimal getIdEmmFk() {
        return idEmmFk;
    }

    public void setIdEmmFk(BigDecimal idEmmFk) {
        this.idEmmFk = idEmmFk;
    }

    public String getIdSubproductoFk() {
        return idSubproductoFk;
    }

    public void setIdSubproductoFk(String idSubproductoFk) {
        this.idSubproductoFk = idSubproductoFk;
    }

    public BigDecimal getIdtipoEmpaqueFk() {
        return idtipoEmpaqueFk;
    }

    public void setIdtipoEmpaqueFk(BigDecimal idtipoEmpaqueFk) {
        this.idtipoEmpaqueFk = idtipoEmpaqueFk;
    }

    public BigDecimal getKilosTotales() {
        return kilosTotales;
    }

    public void setKilosTotales(BigDecimal kilosTotales) {
        this.kilosTotales = kilosTotales;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

}
