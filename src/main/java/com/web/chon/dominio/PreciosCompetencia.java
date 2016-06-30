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
    private String idSubProductoPk;
    private BigDecimal idCompetidorFk;
    private Date fechaRegistro;
    private BigDecimal precioVenta;
    private String nombreCompetidor;
    private String nombreProducto;

    public void reset() {
        idPcPk = null;
        idSubProductoPk = null;
        idCompetidorFk = null;
        fechaRegistro = null;
        precioVenta = null;
        nombreCompetidor = null;
        nombreProducto = null;
    }

    @Override
    public String toString() {
        return "PreciosCompetencia{" + "idPcPk=" + idPcPk + ", idSubProductoPk=" + idSubProductoPk + ", idCompetidorFk=" + idCompetidorFk + ", fechaRegistro=" + fechaRegistro + ", precioVenta=" + precioVenta + ", nombreCompetidor=" + nombreCompetidor + ", nombreProducto=" + nombreProducto + '}';
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getIdPcPk() {
        return idPcPk;
    }

    public void setIdPcPk(BigDecimal idPcPk) {
        this.idPcPk = idPcPk;
    }

    public String getIdSubProductoPk() {
        return idSubProductoPk;
    }

    public void setIdSubProductoPk(String idSubProductoPk) {
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

    public String getNombreCompetidor() {
        return nombreCompetidor;
    }

    public void setNombreCompetidor(String nombreCompetidor) {
        this.nombreCompetidor = nombreCompetidor;
    }

}
