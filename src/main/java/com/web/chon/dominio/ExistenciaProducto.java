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
        idExistenciaProductoPk = null;
        idEmFK = null;
        idSubProductoFK = null;
        idTipoEmpaqueFK = null;
        cantidadPaquetes = null;
        kilosTotalesProducto = null;
        comentarios = null;
        precio = null;
        nombreProducto = null;
        nombreEmpaque = null;
        idTipoConvenio = null;
        idBodegaFK = null;
        nombreTipoConvenio = null;
        nombreBodega = null;
        kilospromprod = null;
        numeroMovimiento = 0;
        pesoTara = null;
        idSucursal = null;
        idProvedor = null;
        nombreProvedorCompleto = null;
        identificador = null;
        nombreSucursal = null;
        precioMinimo = null;
        precioVenta = null;
        precioMaximo = null;
        estatusBloqueo = false;
        convenio = null;
        carroSucursal = null;
        idEntradaMercanciaProductoFK = null;
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

    public ExistenciaProducto(BigDecimal idExistenciaProductoPk, BigDecimal idEmFK, String idSubProductoFK, BigDecimal idTipoEmpaqueFK, BigDecimal cantidadPaquetes, BigDecimal kilosTotalesProducto, String comentarios, BigDecimal precio, String nombreProducto, String nombreEmpaque, BigDecimal idTipoConvenio, BigDecimal idBodegaFK, String nombreTipoConvenio, String nombreBodega, BigDecimal kilospromprod, int numeroMovimiento, BigDecimal pesoTara, BigDecimal idSucursal, BigDecimal idProvedor, String nombreProvedorCompleto, String identificador, String nombreSucursal, BigDecimal precioMinimo, BigDecimal precioVenta, BigDecimal precioMaximo, boolean estatusBloqueo, BigDecimal convenio, BigDecimal carroSucursal, BigDecimal idEntradaMercanciaProductoFK, BigDecimal precioSinIteres) {
        this.idExistenciaProductoPk = idExistenciaProductoPk;
        this.idEmFK = idEmFK;
        this.idSubProductoFK = idSubProductoFK;
        this.idTipoEmpaqueFK = idTipoEmpaqueFK;
        this.cantidadPaquetes = cantidadPaquetes;
        this.kilosTotalesProducto = kilosTotalesProducto;
        this.comentarios = comentarios;
        this.precio = precio;
        this.nombreProducto = nombreProducto;
        this.nombreEmpaque = nombreEmpaque;
        this.idTipoConvenio = idTipoConvenio;
        this.idBodegaFK = idBodegaFK;
        this.nombreTipoConvenio = nombreTipoConvenio;
        this.nombreBodega = nombreBodega;
        this.kilospromprod = kilospromprod;
        this.numeroMovimiento = numeroMovimiento;
        this.pesoTara = pesoTara;
        this.idSucursal = idSucursal;
        this.idProvedor = idProvedor;
        this.nombreProvedorCompleto = nombreProvedorCompleto;
        this.identificador = identificador;
        this.nombreSucursal = nombreSucursal;
        this.precioMinimo = precioMinimo;
        this.precioVenta = precioVenta;
        this.precioMaximo = precioMaximo;
        this.estatusBloqueo = estatusBloqueo;
        this.convenio = convenio;
        this.carroSucursal = carroSucursal;
        this.idEntradaMercanciaProductoFK = idEntradaMercanciaProductoFK;
        this.precioSinIteres = precioSinIteres;
    }

    public ExistenciaProducto() {
    }

}
