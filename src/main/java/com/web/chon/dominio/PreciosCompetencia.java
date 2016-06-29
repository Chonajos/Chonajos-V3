/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author freddy
 */
public class PreciosCompetencia implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idPcPk;
    private BigDecimal idSubProductoPk;
    private BigDecimal idCompetidorFk;
    private Date fechaRegistro;
    private BigDecimal precioVenta;

    @Override
    public String toString() {
        return "PreciosCompetencia{" + "idPcPk=" + idPcPk + ", idSubProductoPk=" + idSubProductoPk + ", fechaRegistro=" + fechaRegistro + ", precioVenta=" + precioVenta + '}';
    }

    public BigDecimal getIdPcPk() {
        return idPcPk;
    }

    public void setIdPcPk(BigDecimal idPcPk) {
        this.idPcPk = idPcPk;
    }

    public BigDecimal getIdSubProductoPk() {
        return idSubProductoPk;
    }

    public void setIdSubProductoPk(BigDecimal idSubProductoPk) {
        this.idSubProductoPk = idSubProductoPk;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getIdCompetidorFk() {
        return idCompetidorFk;
    }

    public void setIdCompetidorFk(BigDecimal idCompetidorFk) {
        this.idCompetidorFk = idCompetidorFk;
    }
    
    
    
}
