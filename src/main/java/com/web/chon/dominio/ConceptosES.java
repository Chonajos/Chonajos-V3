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
    private BigDecimal E_S;

    @Override
    public String toString() {
        return "ConceptosES{" + "idConceptoPk=" + idConceptoPk + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

    public BigDecimal getE_S() {
        return E_S;
    }

    public void setE_S(BigDecimal E_S) {
        this.E_S = E_S;
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
