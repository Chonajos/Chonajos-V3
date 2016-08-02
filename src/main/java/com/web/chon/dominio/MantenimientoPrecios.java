package com.web.chon.dominio;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class MantenimientoPrecios extends ValueObject {
    
    
    private String idSubproducto;
    private BigDecimal idTipoEmpaquePk; 
    private BigDecimal precioVenta;
    private BigDecimal precioMinimo;
    private BigDecimal precioMaximo;
    private int idSucursal;
    private String nombreSubProducto;
    
    private BigDecimal costoReal;
    private BigDecimal costoMerma;
    private BigDecimal existenciaKilos;
    private BigDecimal precioSugerido;
    private BigDecimal precioMercado;
    private Date fechaPrecioMercado;

    @Override
    public String toString() {
        return "MantenimientoPrecios{" + "idSubproducto=" + idSubproducto + ", idTipoEmpaquePk=" + idTipoEmpaquePk + ", precioVenta=" + precioVenta + ", precioMinimo=" + precioMinimo + ", precioMaximo=" + precioMaximo + ", idSucursal=" + idSucursal + ", nombreSubProducto=" + nombreSubProducto + ", costoReal=" + costoReal + ", costoMerma=" + costoMerma + ", existenciaKilos=" + existenciaKilos + ", precioSugerido=" + precioSugerido + ", precioMercado=" + precioMercado + ", fechaPrecioMercado=" + fechaPrecioMercado + '}';
    }
    
    
    public Date getFechaPrecioMercado() {
        return fechaPrecioMercado;
    }

    public void setFechaPrecioMercado(Date fechaPrecioMercado) {
        this.fechaPrecioMercado = fechaPrecioMercado;
    }


    public BigDecimal getPrecioMercado() {
        return precioMercado;
    }

    public void setPrecioMercado(BigDecimal precioMercado) {
        this.precioMercado = precioMercado;
    }

    
    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    

    public String getIdSubproducto() {
        return idSubproducto;
    }

    public void setIdSubproducto(String idSubproducto) {
        this.idSubproducto = idSubproducto;
    }

    public BigDecimal getIdTipoEmpaquePk() {
        return idTipoEmpaquePk;
    }

    public void setIdTipoEmpaquePk(BigDecimal idTipoEmpaquePk) {
        this.idTipoEmpaquePk = idTipoEmpaquePk;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(BigDecimal precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public BigDecimal getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(BigDecimal precioMaximo) {
        this.precioMaximo = precioMaximo;
    } 

    public BigDecimal getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(BigDecimal costoReal) {
        this.costoReal = costoReal;
    }

    public BigDecimal getCostoMerma() {
        return costoMerma;
    }

    public void setCostoMerma(BigDecimal costoMerma) {
        this.costoMerma = costoMerma;
    }

    public String getNombreSubProducto() {
        return nombreSubProducto;
    }

    public void setNombreSubProducto(String nombreSubProducto) {
        this.nombreSubProducto = nombreSubProducto;
    }

    public BigDecimal getExistenciaKilos() {
        return existenciaKilos;
    }

    public void setExistenciaKilos(BigDecimal existenciaKilos) {
        this.existenciaKilos = existenciaKilos;
    }

    public BigDecimal getPrecioSugerido() {
        return precioSugerido;
    }

    public void setPrecioSugerido(BigDecimal precioSugerido) {
        this.precioSugerido = precioSugerido;
    }
    
    

    @Override
    public void reset() {
        idSubproducto =null;
        idSucursal = 0;
    }
    
    
}
