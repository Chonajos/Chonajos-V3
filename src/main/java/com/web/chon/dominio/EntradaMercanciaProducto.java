/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author freddy
 */
public class EntradaMercanciaProducto extends ValueObject implements Serializable
{
    private BigDecimal idEmpPK;
    private BigDecimal idEmFK;
    private String idSubProductoFK;
    private BigDecimal idTipoEmpaqueFK;
    private BigDecimal cantidadPaquetes;
    private BigDecimal kilosTotalesProducto;
    private String comentarios;
    private BigDecimal precio;
    private String nombreProducto;
    private String nombreEmpaque;
    private BigDecimal idTipoConvenio;
    private BigDecimal idBodegaFK;
    private String nombreTipoConvenio;
    private String nombreBodega;
    private BigDecimal kilospromprod;
    private int numeroMovimiento;
    private BigDecimal pesoTara;
    private BigDecimal pesoNeto;
    private BigDecimal idSucursalFk;
    private ArrayList<Bodega> listaBodegas;
    private BigDecimal empaquesProProvedor;
    private BigDecimal kilosProProvedor;
    private String urlVideo;
    private byte[] videoByte;
    
    private Subproducto subProducto;
    private ArrayList<EntradaMercanciaProductoPaquete> listaPaquetes;
    
    //---variables para autoajuste----//
    private BigDecimal kilosVendidos;
    private BigDecimal cantidadVendida;
    private BigDecimal kilosReales;
    private BigDecimal cantidadReales;
    private BigDecimal kilosPaquetes;
    private BigDecimal cantPaquetes;

    @Override
    public String toString() {
        return "EntradaMercanciaProducto{" + "idEmpPK=" + idEmpPK + ", idEmFK=" + idEmFK + ", idSubProductoFK=" + idSubProductoFK + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", cantidadPaquetes=" + cantidadPaquetes + ", kilosTotalesProducto=" + kilosTotalesProducto + ", comentarios=" + comentarios + ", precio=" + precio + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + ", idTipoConvenio=" + idTipoConvenio + ", idBodegaFK=" + idBodegaFK + ", nombreTipoConvenio=" + nombreTipoConvenio + ", nombreBodega=" + nombreBodega + ", kilospromprod=" + kilospromprod + ", numeroMovimiento=" + numeroMovimiento + ", pesoTara=" + pesoTara + ", pesoNeto=" + pesoNeto + ", idSucursalFk=" + idSucursalFk + ", listaBodegas=" + listaBodegas + ", empaquesProProvedor=" + empaquesProProvedor + ", kilosProProvedor=" + kilosProProvedor + ", urlVideo=" + urlVideo + ", videoByte=" + videoByte + ", subProducto=" + subProducto + ", listaPaquetes=" + listaPaquetes + ", kilosVendidos=" + kilosVendidos + ", cantidadVendida=" + cantidadVendida + ", kilosReales=" + kilosReales + ", cantidadReales=" + cantidadReales + ", kilosPaquetes=" + kilosPaquetes + ", cantPaquetes=" + cantPaquetes + '}';
    }

    @Override
    public void reset() {
       idEmFK = null;
       idSubProductoFK = null;
       idTipoEmpaqueFK = null;
       cantidadPaquetes  = null;
       kilosTotalesProducto = null;
       comentarios = null;
       precio = null;
       nombreProducto = null;
       nombreEmpaque = null;
       idTipoConvenio = null;
       idBodegaFK = null;
       nombreTipoConvenio=null;
       nombreBodega  = null;
       pesoTara=null;
       pesoNeto=null;
       idSucursalFk=null;
       subProducto=null;
    }

    public ArrayList<EntradaMercanciaProductoPaquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(ArrayList<EntradaMercanciaProductoPaquete> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }
    
    public BigDecimal getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(BigDecimal pesoNeto) {
        this.pesoNeto = pesoNeto;
    }
    

    public BigDecimal getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(BigDecimal pesoTara) {
        this.pesoTara = pesoTara;
    }

    public BigDecimal getKilospromprod() {
        return kilospromprod;
    }

    public void setKilospromprod(BigDecimal kilospromprod) {
        this.kilospromprod = kilospromprod;
    }

    public BigDecimal getIdTipoConvenio() {
        return idTipoConvenio;
    }

    public void setIdTipoConvenio(BigDecimal idTipoConvenio) {
        this.idTipoConvenio = idTipoConvenio;
    }

    public String getNombreTipoConvenio() {
        return nombreTipoConvenio;
    }

    public void setNombreTipoConvenio(String nombreTipoConvenio) {
        this.nombreTipoConvenio = nombreTipoConvenio;
    }

    public BigDecimal getKilosTotalesProducto() {
        return kilosTotalesProducto;
    }

    public void setKilosTotalesProducto(BigDecimal kilosTotalesProducto) {
        this.kilosTotalesProducto = kilosTotalesProducto;
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

    public String getIdSubProductoFK() {
        return idSubProductoFK;
    }

    public void setIdSubProductoFK(String idSubProductoFK) {
        this.idSubProductoFK = idSubProductoFK;
    }


    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    

    public BigDecimal getIdEmpPK() {
        return idEmpPK;
    }

    public void setIdEmpPK(BigDecimal idEmpPK) {
        this.idEmpPK = idEmpPK;
    }

    public BigDecimal getIdEmFK() {
        return idEmFK;
    }

    public void setIdEmFK(BigDecimal idEmFK) {
        this.idEmFK = idEmFK;
    }

   

    public BigDecimal getIdTipoEmpaqueFK() {
        return idTipoEmpaqueFK;
    }

    public void setIdTipoEmpaqueFK(BigDecimal idTipoEmpaqueFK) {
        this.idTipoEmpaqueFK = idTipoEmpaqueFK;
    }

    public BigDecimal getCantidadPaquetes() {
        return cantidadPaquetes;
    }

    public void setCantidadPaquetes(BigDecimal cantidadPaquetes) {
        this.cantidadPaquetes = cantidadPaquetes;
    }

    


    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getIdBodegaFK() {
        return idBodegaFK;
    }

    public void setIdBodegaFK(BigDecimal idBodegaFK) {
        this.idBodegaFK = idBodegaFK;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigDecimal kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public BigDecimal getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(BigDecimal cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public BigDecimal getKilosReales() {
        return kilosReales;
    }

    public void setKilosReales(BigDecimal kilosReales) {
        this.kilosReales = kilosReales;
    }

    public BigDecimal getCantidadReales() {
        return cantidadReales;
    }

    public void setCantidadReales(BigDecimal cantidadReales) {
        this.cantidadReales = cantidadReales;
    }

    public BigDecimal getKilosPaquetes() {
        return kilosPaquetes;
    }

    public void setKilosPaquetes(BigDecimal kilosPaquetes) {
        this.kilosPaquetes = kilosPaquetes;
    }

    public BigDecimal getCantPaquetes() {
        return cantPaquetes;
    }

    public void setCantPaquetes(BigDecimal cantPaquetes) {
        this.cantPaquetes = cantPaquetes;
    }

    public BigDecimal getEmpaquesProProvedor() {
        return empaquesProProvedor;
    }

    public void setEmpaquesProProvedor(BigDecimal empaquesProProvedor) {
        this.empaquesProProvedor = empaquesProProvedor;
    }

    public BigDecimal getKilosProProvedor() {
        return kilosProProvedor;
    }

    public void setKilosProProvedor(BigDecimal kilosProProvedor) {
        this.kilosProProvedor = kilosProProvedor;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public byte[] getVideoByte() {
        return videoByte;
    }

    public void setVideoByte(byte[] videoByte) {
        this.videoByte = videoByte;
    }
    
}
