package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class MantenimientoPrecios implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String idSubproducto;
    private BigDecimal idTipoEmpaquePk; 
    private BigDecimal precioVenta;
    private BigDecimal precioMinimo;
    private BigDecimal precioMaximo;

    @Override
    public String toString() {
        return "MantenimientoPrecios{" + "idSubproducto=" + idSubproducto + ", idTipoEmpaquePk=" + idTipoEmpaquePk + ", precioVenta=" + precioVenta + ", precioMinimo=" + precioMinimo + ", precioMaximo=" + precioMaximo + '}';
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
    
    
}
