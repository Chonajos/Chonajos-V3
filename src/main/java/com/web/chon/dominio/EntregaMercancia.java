package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class EntregaMercancia implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idEntregaMercanciaPk;
    private BigDecimal idVPMayoreo;
    private BigDecimal idVPMenudeo;
    private BigDecimal idUsuario;
    private BigDecimal empaquesEntregados;
    private BigDecimal kilosEntregados;
    private String observaciones;
    
    //Datos de la consulta
    private String idProducto;
    private String nombreProducto;
    private BigDecimal precioProducto;
    private BigDecimal cantidadKilos;
    private BigDecimal cantidaPaquetes;
    private BigDecimal totalVenta;
    private BigDecimal idTipoEmpaque;
    private String nombreEmpaque;
    private BigDecimal idCliente;
    private String nombreCliente;
    private Date fechaVenta;
    private BigDecimal idEstatus;
    private String estatusVenta;
    private BigDecimal idSucursal;
    private BigDecimal idTipoVenta;
    private String nombreTipoVenta;
    
    private BigDecimal idFolioVenta;
    private String tipoVenta;
    private String marcaCliente;
    

    public void reset() {

        idEntregaMercanciaPk = null;
        idVPMayoreo = null;
        idVPMenudeo = null;
        idUsuario = null;
        empaquesEntregados = null;
        kilosEntregados = null;
        observaciones = null;
        idFolioVenta = null;

        idFolioVenta = null;
        nombreCliente = null;
        totalVenta = null;
        tipoVenta = null;
        estatusVenta = null;
        marcaCliente = null;
        cantidaPaquetes = null;
        cantidadKilos = null;
        nombreProducto = null;
        idTipoVenta = null;
        nombreTipoVenta = null;

    }

    @Override
    public String toString() {
        return "EntregaMercancia{" + "idEntregaMercanciaPk=" + idEntregaMercanciaPk + ", idVPMayoreo=" + idVPMayoreo + ", idVPMenudeo=" + idVPMenudeo + ", idUsuario=" + idUsuario + ", empaquesEntregados=" + empaquesEntregados + ", kilosEntregados=" + kilosEntregados + ", observaciones=" + observaciones + ", idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", precioProducto=" + precioProducto + ", cantidadKilos=" + cantidadKilos + ", cantidaPaquetes=" + cantidaPaquetes + ", totalVenta=" + totalVenta + ", idTipoEmpaque=" + idTipoEmpaque + ", nombreEmpaque=" + nombreEmpaque + ", idCliente=" + idCliente + ", nombreCliente=" + nombreCliente + ", fechaVenta=" + fechaVenta + ", idEstatus=" + idEstatus + ", estatusVenta=" + estatusVenta + ", idSucursal=" + idSucursal + ", idFolioVenta=" + idFolioVenta + ", tipoVenta=" + tipoVenta + ", marcaCliente=" + marcaCliente + '}';
    }


    public BigDecimal getIdEntregaMercanciaPk() {
        return idEntregaMercanciaPk;
    }

    public void setIdEntregaMercanciaPk(BigDecimal idEntregaMercanciaPk) {
        this.idEntregaMercanciaPk = idEntregaMercanciaPk;
    }

    public BigDecimal getIdVPMayoreo() {
        return idVPMayoreo;
    }

    public void setIdVPMayoreo(BigDecimal idVPMayoreo) {
        this.idVPMayoreo = idVPMayoreo;
    }

    public BigDecimal getIdVPMenudeo() {
        return idVPMenudeo;
    }

    public void setIdVPMenudeo(BigDecimal idVPMenudeo) {
        this.idVPMenudeo = idVPMenudeo;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getEmpaquesEntregados() {
        return empaquesEntregados;
    }

    public void setEmpaquesEntregados(BigDecimal empaquesEntregados) {
        this.empaquesEntregados = empaquesEntregados;
    }

    public BigDecimal getKilosEntregados() {
        return kilosEntregados;
    }

    public void setKilosEntregados(BigDecimal kilosEntregados) {
        this.kilosEntregados = kilosEntregados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getIdFolioVenta() {
        return idFolioVenta;
    }

    public void setIdFolioVenta(BigDecimal idFolioVenta) {
        this.idFolioVenta = idFolioVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getEstatusVenta() {
        return estatusVenta;
    }

    public void setEstatusVenta(String estatusVenta) {
        this.estatusVenta = estatusVenta;
    }

    public String getMarcaCliente() {
        return marcaCliente;
    }

    public void setMarcaCliente(String marcaCliente) {
        this.marcaCliente = marcaCliente;
    }

    public BigDecimal getCantidaPaquetes() {
        return cantidaPaquetes;
    }

    public void setCantidaPaquetes(BigDecimal cantidaPaquetes) {
        this.cantidaPaquetes = cantidaPaquetes;
    }

    public BigDecimal getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(BigDecimal cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public BigDecimal getIdTipoEmpaque() {
        return idTipoEmpaque;
    }

    public void setIdTipoEmpaque(BigDecimal idTipoEmpaque) {
        this.idTipoEmpaque = idTipoEmpaque;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(BigDecimal idEstatus) {
        this.idEstatus = idEstatus;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }
    
    
    
}