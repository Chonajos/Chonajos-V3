package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author freddy
 */
public class BuscaVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreCliente;
    private String nombreVendedor;
    private BigDecimal idVenta;
    private String nombreSubproducto;
    private String nombreEmpaque;
    private BigDecimal cantidadEmpaque;
    private BigDecimal precioProducto;
    private BigDecimal total;
    private BigDecimal totalVenta;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private String nombreStatus;
    private int statusFK;
    private BigDecimal idSucursalFk;
    private String clave;
    private BigDecimal carro;
    private BigDecimal kilosVendidos;
    private String nombreSucursal;

    public void reset() {
        nombreCliente="";
        nombreVendedor="";
        idVenta=null;
        nombreSubproducto="";
        nombreEmpaque="";
        cantidadEmpaque=null;
        precioProducto=null;
        total=null;
        totalVenta=null;
        fechaVenta=null;
        fechaPromesaPago=null;
        nombreStatus=null;
        statusFK=0;
        idSucursalFk=null;
        clave="";
        carro=null;
        kilosVendidos=null;
        nombreSucursal=null;
    }

    @Override
    public String toString() {
        return "BuscaVenta{" + "nombreCliente=" + nombreCliente + ", nombreVendedor=" + nombreVendedor + ", idVenta=" + idVenta + ", nombreSubproducto=" + nombreSubproducto + ", nombreEmpaque=" + nombreEmpaque + ", cantidadEmpaque=" + cantidadEmpaque + ", precioProducto=" + precioProducto + ", total=" + total + ", totalVenta=" + totalVenta + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", nombreStatus=" + nombreStatus + ", statusFK=" + statusFK + ", idSucursalFk=" + idSucursalFk + ", clave=" + clave + ", carro=" + carro + ", kilosVendidos=" + kilosVendidos + '}';
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public int getStatusFK() {
        return statusFK;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public BigDecimal getCarro() {
        return carro;
    }

    public void setCarro(BigDecimal carro) {
        this.carro = carro;
    }

    public BigDecimal getKilosVendidos() {
        return kilosVendidos;
    }

    public void setKilosVendidos(BigDecimal kilosVendidos) {
        this.kilosVendidos = kilosVendidos;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public void setStatusFK(int statusFK) {
        this.statusFK = statusFK;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public BigDecimal getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(BigDecimal idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreSubproducto() {
        return nombreSubproducto;
    }

    public void setNombreSubproducto(String nombreSubproducto) {
        this.nombreSubproducto = nombreSubproducto;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaPromesaPago() {
        return fechaPromesaPago;
    }

    public void setFechaPromesaPago(Date fechaPromesaPago) {
        this.fechaPromesaPago = fechaPromesaPago;
    }

    public String getNombreStatus() {
        return nombreStatus;
    }

    public void setNombreStatus(String nombreStatus) {
        this.nombreStatus = nombreStatus;
    }

}
