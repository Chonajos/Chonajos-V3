/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private String nombreProvedor;
    private String apPaternoProvedor;
    private String apMaternoProvedor;
    private String idSubproductoFK;
    private BigDecimal sumaKilos;
    private BigDecimal sumaCostos;
    private String nombreUsuarioRecibe;
    private String nombreSucursal;

    private ArrayList<EntradaMenudeoProducto> listaDetalleProducto;

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
        idSubproductoFK=null;
        listaDetalleProducto=null;
        nombreUsuarioRecibe = null;
        nombreSucursal = null;
        //idUsuario = null;

    }

    @Override
    public String toString() {
        return "EntradaMenudeo{" + "idEmmPk=" + idEmmPk + ", idProvedorFk=" + idProvedorFk + ", fecha=" + fecha + ", idSucursalFk=" + idSucursalFk + ", idStatusFk=" + idStatusFk + ", kilosTotales=" + kilosTotales + ", kilosProvedor=" + kilosProvedor + ", comentarios=" + comentarios + ", ticketProvedor=" + ticketProvedor + ", ticketBascula=" + ticketBascula + ", folio=" + folio + ", idUsuario=" + idUsuario + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", nombreProvedor=" + nombreProvedor + ", apPaternoProvedor=" + apPaternoProvedor + ", apMaternoProvedor=" + apMaternoProvedor + ", idSubproductoFK=" + idSubproductoFK + '}';
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

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public String getNombreProvedor() {
        return nombreProvedor;
    }

    public void setNombreProvedor(String nombreProvedor) {
        this.nombreProvedor = nombreProvedor;
    }

    public String getApPaternoProvedor() {
        return apPaternoProvedor;
    }

    public void setApPaternoProvedor(String apPaternoProvedor) {
        this.apPaternoProvedor = apPaternoProvedor;
    }

    public String getApMaternoProvedor() {
        return apMaternoProvedor;
    }

    public void setApMaternoProvedor(String apMaternoProvedor) {
        this.apMaternoProvedor = apMaternoProvedor;
    }

    public String getIdSubproductoFK() {
        return idSubproductoFK;
    }

    public void setIdSubproductoFK(String idSubproductoFK) {
        this.idSubproductoFK = idSubproductoFK;
    }

    public ArrayList<EntradaMenudeoProducto> getListaDetalleProducto() {
        return listaDetalleProducto;
    }

    public void setListaDetalleProducto(ArrayList<EntradaMenudeoProducto> listaDetalleProducto) {
        this.listaDetalleProducto = listaDetalleProducto;
    }

    public BigDecimal getSumaKilos() {
        return sumaKilos;
    }

    public void setSumaKilos(BigDecimal sumaKilos) {
        this.sumaKilos = sumaKilos;
    }

    public BigDecimal getSumaCostos() {
        return sumaCostos;
    }

    public void setSumaCostos(BigDecimal sumaCostos) {
        this.sumaCostos = sumaCostos;
    }

    public String getNombreUsuarioRecibe() {
        return nombreUsuarioRecibe;
    }

    public void setNombreUsuarioRecibe(String nombreUsuarioRecibe) {
        this.nombreUsuarioRecibe = nombreUsuarioRecibe;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    
    
}
