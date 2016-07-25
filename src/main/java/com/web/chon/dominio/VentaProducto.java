package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author marcogante
 */
public class VentaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaProductoPk;
    private BigDecimal precioProducto;
    private String idProductoFk;
    private BigDecimal idTipoEmpaqueFk;
    private BigDecimal cantidadEmpaque;
    private BigDecimal kilosVenta;
    private BigDecimal total;
    private String nombreProducto;
    private String nombreEmpaque;
    private BigDecimal idTipoVentaFk;
    
    //Variable para venta a Credito
    private BigDecimal numeroPagos;
    private BigDecimal precioSinInteres;
    private BigDecimal tipoPago;

    public VentaProducto() {
    }

    public VentaProducto(BigDecimal idVentaProductoPk) {
        this.idVentaProductoPk = idVentaProductoPk;
    }

    public BigDecimal getIdVentaProductoPk() {
        return idVentaProductoPk;
    }

    public void setIdVentaProductoPk(BigDecimal idVentaProductoPk) {
        this.idVentaProductoPk = idVentaProductoPk;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

  

    public String getIdProductoFk() {
        return idProductoFk;
    }

    public void setIdProductoFk(String idProductoFk) {
        this.idProductoFk = idProductoFk;
    }

    public BigDecimal getIdTipoEmpaqueFk() {
        return idTipoEmpaqueFk;
    }

    public void setIdTipoEmpaqueFk(BigDecimal idTipoEmpaqueFk) {
        this.idTipoEmpaqueFk = idTipoEmpaqueFk;
    }

    public BigDecimal getIdTipoVentaFk() {
        return idTipoVentaFk;
    }

    public void setIdTipoVentaFk(BigDecimal idTipoVentaFk) {
        this.idTipoVentaFk = idTipoVentaFk;
    }

    public BigDecimal getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigDecimal cantidadEmpaque) {
        this.cantidadEmpaque = cantidadEmpaque;
    }

    public BigDecimal getKilosVenta() {
        return kilosVenta;
    }

    public void setKilosVenta(BigDecimal kilosVenta) {
        this.kilosVenta = kilosVenta;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public BigDecimal getNumeroPagos() {
        return numeroPagos;
    }

    public void setNumeroPagos(BigDecimal numeroPagos) {
        this.numeroPagos = numeroPagos;
    }


    public BigDecimal getPrecioSinInteres() {
        return precioSinInteres;
    }

    public void setPrecioSinInteres(BigDecimal precioSinInteres) {
        this.precioSinInteres = precioSinInteres;
    }

    public BigDecimal getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(BigDecimal tipoPago) {
        this.tipoPago = tipoPago;
    }

    
    
   
  
    @Override
    public String toString() {
        return "VentaProducto{" + "idVentaProductoPk=" + idVentaProductoPk + ", precioProducto=" + precioProducto + ", idProductoFk=" + idProductoFk + ", idTipoEmpaqueFk=" + idTipoEmpaqueFk + ", idTipoVentaFk=" + idTipoVentaFk + ", cantidadEmpaque=" + cantidadEmpaque + ", kilosVenta=" + kilosVenta + ", total=" + total + ", nombreProducto=" + nombreProducto + ", nombreEmpaque=" + nombreEmpaque + '}';
    }

    public void reset() {

        idVentaProductoPk = null;
        precioProducto = null;
        idProductoFk = null;
        cantidadEmpaque = null;
        kilosVenta = null;
        total = null;

    }

}
