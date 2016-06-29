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
public class Competidor implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idCompetidorPk;
    private String nombreCompetidor;
    private String ubicacion;

    @Override
    public String toString() {
        return "Competidor{" + "idCompetidorPk=" + idCompetidorPk + ", nombreCompetidor=" + nombreCompetidor + ", ubicacion=" + ubicacion + '}';
    }

    
    public BigDecimal getIdCompetidorPk() {
        return idCompetidorPk;
    }

    public void setIdCompetidorPk(BigDecimal idCompetidorPk) {
        this.idCompetidorPk = idCompetidorPk;
    }

    public String getNombreCompetidor() {
        return nombreCompetidor;
    }

    public void setNombreCompetidor(String nombreCompetidor) {
        this.nombreCompetidor = nombreCompetidor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
