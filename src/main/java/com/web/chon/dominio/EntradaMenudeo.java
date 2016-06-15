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
public class EntradaMenudeo implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEmmPk;
    private BigDecimal idProvedorFk;
    private Date fecha;
    private BigDecimal idSucursalFk;
    private BigDecimal idStatusFk;
    private BigDecimal kilosTotales;
    private BigDecimal kilosProvedor;
    private String comentarios;
    private String ticketProvedor;
    private String ticketBascula;
    private BigDecimal folio;
    private BigDecimal idUsuario;

    public void reset() {
        idEmmPk = null;
        idProvedorFk = null;
        fecha = null;
        //idSucursalFk = null;
        idStatusFk = null;
        kilosTotales = null;
        kilosProvedor = null;
        comentarios = null;
        ticketProvedor = null;
        ticketBascula = null;
        folio = null;
        //idUsuario = null;

    }

    @Override
    public String toString() {
        return "EntradaMenudeo{" + "idEmmPk=" + idEmmPk + ", idProvedorFk=" + idProvedorFk + ", fecha=" + fecha + ", idSucursalFk=" + idSucursalFk + ", idStatusFk=" + idStatusFk + ", kilosTotales=" + kilosTotales + ", kilosProvedor=" + kilosProvedor + ", comentarios=" + comentarios + ", ticketProvedor=" + ticketProvedor + ", ticketBascula=" + ticketBascula + ", folio=" + folio + ", idUsuario=" + idUsuario + '}';
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getIdEmmPk() {
        return idEmmPk;
    }

    public void setIdEmmPk(BigDecimal idEmmPk) {
        this.idEmmPk = idEmmPk;
    }

    public BigDecimal getIdProvedorFk() {
        return idProvedorFk;
    }

    public void setIdProvedorFk(BigDecimal idProvedorFk) {
        this.idProvedorFk = idProvedorFk;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public BigDecimal getKilosTotales() {
        return kilosTotales;
    }

    public void setKilosTotales(BigDecimal kilosTotales) {
        this.kilosTotales = kilosTotales;
    }

    public BigDecimal getKilosProvedor() {
        return kilosProvedor;
    }

    public void setKilosProvedor(BigDecimal kilosProvedor) {
        this.kilosProvedor = kilosProvedor;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTicketProvedor() {
        return ticketProvedor;
    }

    public void setTicketProvedor(String ticketProvedor) {
        this.ticketProvedor = ticketProvedor;
    }

    public String getTicketBascula() {
        return ticketBascula;
    }

    public void setTicketBascula(String ticketBascula) {
        this.ticketBascula = ticketBascula;
    }

    public BigDecimal getFolio() {
        return folio;
    }

    public void setFolio(BigDecimal folio) {
        this.folio = folio;
    }

}
