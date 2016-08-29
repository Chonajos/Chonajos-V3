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
public class ExistenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idExistenciaProductoPk;
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
    private BigDecimal idSucursal;
    private BigDecimal idProvedor;
    private String nombreProvedorCompleto;
    private String identificador;
    private String nombreSucursal;
    private BigDecimal precioMinimo;
    private BigDecimal precioVenta;
    private BigDecimal precioMaximo;
    private boolean estatusBloqueo;
    private BigDecimal convenio;
    private BigDecimal carroSucursal;
    private BigDecimal idEntradaMercanciaProductoFK;
    
    //variables de venta por credito
    private BigDecimal precioSinIteres;

    public void reset() {
        idSucursal = null;
        idBodegaFK = null;
        idProvedor = null;
        idTipoEmpaqueFK = null;
        idTipoConvenio = null;

        precioMinimo = null;
        precioVenta = null;
        precioMaximo = null;
        estatusBloqueo = true;
        carroSucursal = null;
        
        precioSinIteres = null;
    }

    @Override
    public String toString() {
        return "ExistenciaProducto{" + "idExistenciaProductoPk=" + idExistenciaProductoPk + ", idEmFK=" + idEmFK + ", idSubProductoFK=" + idSubProductoFK + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", cantidadPaquetes=" + cantidadPaquetes + ", kilosTotalesProducto=" + kilosTotalesProducto + ", comentarios=" + comentarios + ", precio=" + precio + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + ", idTipoConvenio=" + idTipoConvenio + ", idBodegaFK=" + idBodegaFK + ", nombreTipoConvenio=" + nombreTipoConvenio + ", nombreBodega=" + nombreBodega + ", kilospromprod=" + kilospromprod + ", numeroMovimiento=" + numeroMovimiento + ", pesoTara=" + pesoTara + ", idSucursal=" + idSucursal + ", idProvedor=" + idProvedor + ", nombreProvedorCompleto=" + nombreProvedorCompleto + ", identificador=" + identificador + ", nombreSucursal=" + nombreSucursal + ", precioMinimo=" + precioMinimo + ", precioVenta=" + precioVenta + ", precioMaximo=" + precioMaximo + ", estatusBloqueo=" + estatusBloqueo + ", convenio=" + convenio + ", carroSucursal=" + carroSucursal + ", idEntradaMercanciaProductoFK=" + idEntradaMercanciaProductoFK + ", precioSinIteres=" + precioSinIteres + '}';
    }

  

    public BigDecimal getIdEntradaMercanciaProductoFK() {
        return idEntradaMercanciaProductoFK;
    }

    public void setIdEntradaMercanciaProductoFK(BigDecimal idEntradaMercanciaProductoFK) {
        this.idEntradaMercanciaProductoFK = idEntradaMercanciaProductoFK;
    }

    
    public BigDecimal getCarroSucursal() {
        return carroSucursal;
    }

    public void setCarroSucursal(BigDecimal carroSucursal) {
        this.carroSucursal = carroSucursal;
    }

    

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreProvedorCompleto() {
        return nombreProvedorCompleto;
    }

    public void setNombreProvedorCompleto(String nombreProvedorCompleto) {
        this.nombreProvedorCompleto = nombreProvedorCompleto;
    }

    public BigDecimal getIdProvedor() {
        return idProvedor;
    }

    public void setIdProvedor(BigDecimal idProvedor) {
        this.idProvedor = idProvedor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public BigDecimal getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(BigDecimal pesoTara) {
        this.pesoTara = pesoTara;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdExistenciaProductoPk() {
        return idExistenciaProductoPk;
    }

    public void setIdExistenciaProductoPk(BigDecimal idExistenciaProductoPk) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
    }

    public BigDecimal getIdEmFK() {
        return idEmFK;
    }

    public void setIdEmFK(BigDecimal idEmFK) {
        this.idEmFK = idEmFK;
    }

    public String getIdSubProductoFK() {
        return idSubProductoFK;
    }

    public void setIdSubProductoFK(String idSubProductoFK) {
        this.idSubProductoFK = idSubProductoFK;
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

    public BigDecimal getKilosTotalesProducto() {
        return kilosTotalesProducto;
    }

    public void setKilosTotalesProducto(BigDecimal kilosTotalesProducto) {
        this.kilosTotalesProducto = kilosTotalesProducto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    public BigDecimal getIdTipoConvenio() {
        return idTipoConvenio;
    }

    public void setIdTipoConvenio(BigDecimal idTipoConvenio) {
        this.idTipoConvenio = idTipoConvenio;
    }

    public BigDecimal getIdBodegaFK() {
        return idBodegaFK;
    }

    public void setIdBodegaFK(BigDecimal idBodegaFK) {
        this.idBodegaFK = idBodegaFK;
    }

    public String getNombreTipoConvenio() {
        return nombreTipoConvenio;
    }

    public void setNombreTipoConvenio(String nombreTipoConvenio) {
        this.nombreTipoConvenio = nombreTipoConvenio;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public BigDecimal getKilospromprod() {
        return kilospromprod;
    }

    public void setKilospromprod(BigDecimal kilospromprod) {
        this.kilospromprod = kilospromprod;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public BigDecimal getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(BigDecimal precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(BigDecimal precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public boolean isEstatusBloqueo() {
        return estatusBloqueo;
    }

    public void setEstatusBloqueo(boolean estatusBloqueo) {
        this.estatusBloqueo = estatusBloqueo;
    }

    public BigDecimal getConvenio() {
        return convenio;
    }

    public void setConvenio(BigDecimal convenio) {
        this.convenio = convenio;
    }

    public BigDecimal getPrecioSinIteres() {
        return precioSinIteres;
    }

    public void setPrecioSinIteres(BigDecimal precioSinIteres) {
        this.precioSinIteres = precioSinIteres;
    }
    
    

}
