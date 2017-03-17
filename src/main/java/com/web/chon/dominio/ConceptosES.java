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
 * @author JesusAlfredo
 */
public class ConceptosES implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idConceptoPk;
    private BigDecimal idTipoOperacionFk;
    private String nombre;
    private String descripcion;
    private BigDecimal idStatusFk;
    private String nombreStatus;
    private String nombreOperacion;

    public void reset() {
        idConceptoPk = null;
        idTipoOperacionFk = null;
        nombre = null;
        descripcion = null;
        idStatusFk = null;
        String nombreStatus = null;
        nombreOperacion = null;
    }

    @Override
    public String toString() {
        return "ConceptosES{" + "idConceptoPk=" + idConceptoPk + ", idTipoOperacionFk=" + idTipoOperacionFk + ", nombre=" + nombre + ", descripcion=" + descripcion + ", idStatusFk=" + idStatusFk + ", nombreStatus=" + nombreStatus + ", nombreOperacion=" + nombreOperacion + '}';
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public BigDecimal getIdTipoOperacionFk() {
        return idTipoOperacionFk;
    }

    public void setIdTipoOperacionFk(BigDecimal idTipoOperacionFk) {
        this.idTipoOperacionFk = idTipoOperacionFk;
    }

    public BigDecimal getIdConceptoPk() {
        return idConceptoPk;
    }

    public void setIdConceptoPk(BigDecimal idConceptoPk) {
        this.idConceptoPk = idConceptoPk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
