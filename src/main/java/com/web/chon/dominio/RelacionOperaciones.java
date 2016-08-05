package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class RelacionOperaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaPk;
    private Date fechaVenta;
    private Date fechaPromesaPago;
    private BigDecimal idClienteFk;
    private BigDecimal idVendedorFk;
    private String nombreCliente;
    private String nombreVendedor;
    private BigDecimal totalVenta;
    private String estatus;
    private Date fechaPago;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private int idSucursal;
    private int idStatus;
    private String comentarioCancel;
    private int folioSucursal;
    private ArrayList<BuscaVenta> listaProductos;


    public RelacionOperaciones() {
        
    }

    public String getComentarioCancel() {
        return comentarioCancel;
    }

    public void setComentarioCancel(String comentarioCancel) {
        this.comentarioCancel = comentarioCancel;
    }
    

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

   

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
    
    public BigDecimal getIdVentaPk() {
        return idVentaPk;
    }

    public void setIdVentaPk(BigDecimal idVentaPk) {
        this.idVentaPk = idVentaPk;
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

    public BigDecimal getIdClienteFk() {
        return idClienteFk;
    }

    public void setIdClienteFk(BigDecimal idClienteFk) {
        this.idClienteFk = idClienteFk;
    }

    public BigDecimal getIdVendedorFk() {
        return idVendedorFk;
    }

    public void setIdVendedorFk(BigDecimal idVendedorFk) {
        this.idVendedorFk = idVendedorFk;
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

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getFolioSucursal() {
        return folioSucursal;
    }

    public void setFolioSucursal(int folioSucursal) {
        this.folioSucursal = folioSucursal;
    }

    @Override
    public String toString() {
        return "RelacionOperaciones{" + "idVentaPk=" + idVentaPk + ", fechaVenta=" + fechaVenta + ", fechaPromesaPago=" + fechaPromesaPago + ", idClienteFk=" + idClienteFk + ", idVendedorFk=" + idVendedorFk + ", nombreCliente=" + nombreCliente + ", nombreVendedor=" + nombreVendedor + ", totalVenta=" + totalVenta + ", estatus=" + estatus + ", fechaPago=" + fechaPago + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", idSucursal=" + idSucursal + ", idStatus=" + idStatus + ", comentarioCancel=" + comentarioCancel + ", folioSucursal=" + folioSucursal + '}';
    }

    public ArrayList<BuscaVenta> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<BuscaVenta> listaProductos) {
        this.listaProductos = listaProductos;
    }
      
}
