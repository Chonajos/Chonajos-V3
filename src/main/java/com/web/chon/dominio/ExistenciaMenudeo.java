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
public class ExistenciaMenudeo implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idExMenPk;
    private BigDecimal idSucursalFk;
    private BigDecimal kilos;
    private String idSubProductoPk;
    private BigDecimal cantidadEmpaque;
    private BigDecimal idTipoEmpaqueFK;
    private BigDecimal idStatusFk;
    private String nombreProducto;
    private String nombreEmpaque;

    //campo que se utiliza en ajustes de existencia
    private String observaciones;
    private String motivoAjuste;
    private BigDecimal kilosAjustados;

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getIdTipoEmpaqueFK() {
        return idTipoEmpaqueFK;
    }

    public void setIdTipoEmpaqueFK(BigDecimal idTipoEmpaqueFK) {
        this.idTipoEmpaqueFK = idTipoEmpaqueFK;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public BigDecimal getIdExMenPk() {
        return idExMenPk;
    }

    public void setIdExMenPk(BigDecimal idExMenPk) {
        this.idExMenPk = idExMenPk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public String getIdSubProductoPk() {
        return idSubProductoPk;
    }

    public void setIdSubProductoPk(String idSubProductoPk) {
        this.idSubProductoPk = idSubProductoPk;
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

    @Override
    public String toString() {
        return "ExistenciaMenudeo{" + "idExMenPk=" + idExMenPk + ", idSucursalFk=" + idSucursalFk + ", kilos=" + kilos + ", idSubProductoPk=" + idSubProductoPk + ", cantidadEmpaque=" + cantidadEmpaque + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", idStatusFk=" + idStatusFk + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + '}';
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMotivoAjuste() {
        return motivoAjuste;
    }

    public void setMotivoAjuste(String motivoAjuste) {
        this.motivoAjuste = motivoAjuste;
    }

    public BigDecimal getKilosAjustados() {
        return kilosAjustados;
    }

    public void setKilosAjustados(BigDecimal kilosAjustados) {
        this.kilosAjustados = kilosAjustados;
    }
    
    
    
    

}
