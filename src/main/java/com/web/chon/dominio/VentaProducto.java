/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author marcogante
 */
public class VentaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idVentaProductoPk;
    private BigInteger precioProducto;
    private String idProductoFk;
    private BigDecimal idTipoEmpaqueFk;
    private TipoVenta idTipoVentaFk;
    private BigInteger cantidadEmpaque;
    private BigDecimal kilosVenta;
    private BigDecimal total;
    private String nombreProducto;
    private String nombreEmpaque;

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

    public BigInteger getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigInteger precioProducto) {
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

    public TipoVenta getIdTipoVentaFk() {
        return idTipoVentaFk;
    }

    public void setIdTipoVentaFk(TipoVenta idTipoVentaFk) {
        this.idTipoVentaFk = idTipoVentaFk;
    }

    public BigInteger getCantidadEmpaque() {
        return cantidadEmpaque;
    }

    public void setCantidadEmpaque(BigInteger cantidadEmpaque) {
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
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaProductoPk != null ? idVentaProductoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaProducto)) {
            return false;
        }
        VentaProducto other = (VentaProducto) object;
        if ((this.idVentaProductoPk == null && other.idVentaProductoPk != null) || (this.idVentaProductoPk != null && !this.idVentaProductoPk.equals(other.idVentaProductoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VentaProducto{" + "idVentaProductoPk=" + idVentaProductoPk + ", precioProducto=" + precioProducto + ", idProductoFk=" + idProductoFk + ", idTipoEmpaqueFk=" + idTipoEmpaqueFk + ", idTipoVentaFk=" + idTipoVentaFk + ", cantidadEmpaque=" + cantidadEmpaque + ", total=" + total + '}';
    }

    public void reset() {
        
      idVentaProductoPk = null;
      precioProducto = null;
      idProductoFk = null;
      idTipoEmpaqueFk = new BigDecimal(-1);
      cantidadEmpaque = null;
      kilosVenta = null;
      total = null;

}

}
