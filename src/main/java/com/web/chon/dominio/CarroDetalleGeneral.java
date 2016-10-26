package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class CarroDetalleGeneral extends ValueObject implements Serializable {

    private BigDecimal carro;
    private BigDecimal idEntradaMercancia;
    private Date fecha;
    private String identificador;
    private String nombreProvedor;
    private String status;
    private BigDecimal venta;
    private BigDecimal comision;

//    private ArrayList<EntradaMercanciaProducto> listaProductos;
    @Override
    public String toString() {
        return "CarroDetalleGeneral{" + "carro=" + carro + ", idEntradaMercancia=" + idEntradaMercancia + ", fecha=" + fecha + ", identificador=" + identificador + '}';
    }

    public BigDecimal getCarro() {
        return carro;
    }

    public void setCarro(BigDecimal carro) {
        this.carro = carro;
    }

    public BigDecimal getIdEntradaMercancia() {
        return idEntradaMercancia;
    }

    public void setIdEntradaMercancia(BigDecimal idEntradaMercancia) {
        this.idEntradaMercancia = idEntradaMercancia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombreProvedor() {
        return nombreProvedor;
    }

    public void setNombreProvedor(String nombreProvedor) {
        this.nombreProvedor = nombreProvedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getVenta() {
        return venta;
    }

    public void setVenta(BigDecimal venta) {
        this.venta = venta;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    
    @Override
    public void reset() {

        idEntradaMercancia = null;
        identificador = null;
        carro = null;
        fecha = null;
        nombreProvedor = null;
        status = null;
        venta = null;
        comision = null;
    }

}
