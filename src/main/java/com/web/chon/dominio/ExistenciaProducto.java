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
 * @author freddy
 */
public class ExistenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idExistenciaProductoPk;
    
    private String idSubProductoFk;
    private BigDecimal idExistenciaPk;
    private BigDecimal idEmpFk;
    private BigDecimal cantidadEmpaque;
    private BigDecimal kilosExistencia;
    private BigDecimal pesokiloproducto;
    private BigDecimal idSucursalFk;
    private BigDecimal idBodegaFk;
    
    
    //datos para consulta de existencias.
    //idExistenciasProducto ya lo tengo.
    private BigDecimal idEmFk;
    private String identificador;
    private String nombreSubproducto;
    private String nombreEmpaque;
    private String nombreBodega;
    private BigDecimal idProductoFK;
    private BigDecimal idProvedorFk;
    //i

    @Override
    public String toString() {
        return "ExistenciaProducto{" + "idExistenciaProductoPk=" + idExistenciaProductoPk + ", idSubProductoFk=" + idSubProductoFk + ", idExistenciaPk=" + idExistenciaPk + ", idEmpFk=" + idEmpFk + ", cantidadEmpaque=" + cantidadEmpaque + ", kilosExistencia=" + kilosExistencia + ", pesokiloproducto=" + pesokiloproducto + ", idSucursalFk=" + idSucursalFk + ", idBodegaFk=" + idBodegaFk + '}';
    }

    public BigDecimal getIdProductoFK() {
        return idProductoFK;
    }

    public void setIdProductoFK(BigDecimal idProductoFK) {
        this.idProductoFK = idProductoFK;
    }

    public BigDecimal getIdProvedorFk() {
        return idProvedorFk;
    }

    public void setIdProvedorFk(BigDecimal idProvedorFk) {
        this.idProvedorFk = idProvedorFk;
    }

    
    public BigDecimal getIdEmFk() {
        return idEmFk;
    }

    public void setIdEmFk(BigDecimal idEmFk) {
        this.idEmFk = idEmFk;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombreSubproducto() {
        return nombreSubproducto;
    }

    public void setNombreSubproducto(String nombreSubproducto) {
        this.nombreSubproducto = nombreSubproducto;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    
    public BigDecimal getIdExistenciaProductoPk() {
        return idExistenciaProductoPk;
    }

    public void setIdExistenciaProductoPk(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public String getIdSubProductoFk() {
        return idSubProductoFk;
    }

    public void setIdSubProductoFk(String idSubProductoFk) {
        this.idSubProductoFk = idSubProductoFk;
    }

    public BigDecimal getIdExistenciaPk() {
        return idExistenciaPk;
    }

    public void setIdExistenciaPk(BigDecimal idExistenciaPk) {
        this.idExistenciaPk = idExistenciaPk;
    }

    public BigDecimal getIdEmpFk() {
        return idEmpFk;
    }

    public void setIdEmpFk(BigDecimal idEmpFk) {
        this.idEmpFk = idEmpFk;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getKilosExistencia() {
        return kilosExistencia;
    }

    public void setKilosExistencia(BigDecimal kilosExistencia) {
        this.kilosExistencia = kilosExistencia;
    }

    public BigDecimal getPesokiloproducto() {
        return pesokiloproducto;
    }

    public void setPesokiloproducto(BigDecimal pesokiloproducto) {
        this.pesokiloproducto = pesokiloproducto;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getIdBodegaFk() {
        return idBodegaFk;
    }

    public void setIdBodegaFk(BigDecimal idBodegaFk) {
        this.idBodegaFk = idBodegaFk;
    }


    

    
}
