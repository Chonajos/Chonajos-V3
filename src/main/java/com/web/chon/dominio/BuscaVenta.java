/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.util.Date;
import static javassist.CtMethod.ConstParameter.integer;

/**
 *
 * @author freddy
 */
public class BuscaVenta implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private String nombreCliente;
    private String nombreVendedor;
    private int idVenta;
    private String nombreSubproducto;
    private String nombreEmpaque;
    private int cantidadEmpaque;
    private int precioProducto;
    private int total;
    private int totalVenta;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private String nombreStatus;
    private int statusFK;

    public int getStatusFK() {
        return statusFK;
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

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
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

    public int getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(int cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(int totalVenta) {
        this.totalVenta = totalVenta;
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

    @Override
    public String toString() {
        return "BuscaVenta{" + "nombreCliente=" + nombreCliente + ", nombreVendedor=" + nombreVendedor + ", idVenta=" + idVenta + ", nombreSubproducto=" + nombreSubproducto + ", nombreEmpaque=" + nombreEmpaque + ", cantidadEmpaque=" + cantidadEmpaque + ", precioProducto=" + precioProducto + ", total=" + total + ", totalVenta=" + totalVenta + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", nombreStatus=" + nombreStatus + ", statusFK=" + statusFK + '}';
    }
   
   
    

    
}
