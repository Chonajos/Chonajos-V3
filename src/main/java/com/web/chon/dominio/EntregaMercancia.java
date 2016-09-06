package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

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

    //Datos generales 
    private BigDecimal cantidaPaquetes;
    private BigDecimal cantidadKilos;
    private BigDecimal idFolioVenta;
    private String nombreCliente;
    private BigDecimal totalVenta;
    private String tipoVenta;
    private String estatusVenta;
    private String marcaCliente;
    private String nombreProducto;

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

    }

    @Override
    public String toString() {
        return "EntregaMercancia{" + "idEntregaMercanciaPk=" + idEntregaMercanciaPk + ", idVPMayoreo=" + idVPMayoreo + ", idVPMenudeo=" + idVPMenudeo + ", idUsuario=" + idUsuario + ", empaquesEntregados=" + empaquesEntregados + ", kilosEntregados=" + kilosEntregados + ", observaciones=" + observaciones + '}';
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
    

}
