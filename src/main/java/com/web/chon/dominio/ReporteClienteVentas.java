package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public class ReporteClienteVentas implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreCliente;
    private BigDecimal idClientePk;
    private BigDecimal totalMenudeoContado;
    private BigDecimal totalMenudeoCredito;
    private BigDecimal utilidadMenudeo;
    private BigDecimal totalMayoreoContado;
    private BigDecimal totalMayoreoCredito;
    private BigDecimal utilidadMayoreoCosto;
    private BigDecimal utilidadMayoreoComision;
    private BigDecimal utilidadMayoreoPacto;
    private BigDecimal diasRecuperacion;
    private BigDecimal recuperacion;
    private BigDecimal porcentajeUtilidad;
    private int clasificacion;

    @Override
    public String toString() {
        return "ReporteClienteVentas{" + "idClientePk=" + idClientePk + ", totalMenudeoContado=" + totalMenudeoContado + ", totalMenudeoCredito=" + totalMenudeoCredito + ", utilidadMenudeo=" + utilidadMenudeo + ", totalMayoreoContado=" + totalMayoreoContado + ", totalMayoreoCredito=" + totalMayoreoCredito + ", utilidadMayoreoCosto=" + utilidadMayoreoCosto + ", utilidadMayoreoComision=" + utilidadMayoreoComision + ", utilidadMayoreoPacto=" + utilidadMayoreoPacto + ", diasRecuperacion=" + diasRecuperacion + ", recuperacion=" + recuperacion + '}';
    }

    public BigDecimal getIdClientePk() {
        return idClientePk;
    }

    public void setIdClientePk(BigDecimal idClientePk) {
        this.idClientePk = idClientePk;
    }

    public BigDecimal getTotalMenudeoContado() {
        return totalMenudeoContado;
    }

    public void setTotalMenudeoContado(BigDecimal totalMenudeoContado) {
        this.totalMenudeoContado = totalMenudeoContado;
    }

    public BigDecimal getTotalMenudeoCredito() {
        return totalMenudeoCredito;
    }

    public void setTotalMenudeoCredito(BigDecimal totalMenudeoCredito) {
        this.totalMenudeoCredito = totalMenudeoCredito;
    }

    public BigDecimal getUtilidadMenudeo() {
        return utilidadMenudeo;
    }

    public void setUtilidadMenudeo(BigDecimal utilidadMenudeo) {
        this.utilidadMenudeo = utilidadMenudeo;
    }

    public BigDecimal getTotalMayoreoContado() {
        return totalMayoreoContado;
    }

    public void setTotalMayoreoContado(BigDecimal totalMayoreoContado) {
        this.totalMayoreoContado = totalMayoreoContado;
    }

    public BigDecimal getTotalMayoreoCredito() {
        return totalMayoreoCredito;
    }

    public void setTotalMayoreoCredito(BigDecimal totalMayoreoCredito) {
        this.totalMayoreoCredito = totalMayoreoCredito;
    }

    public BigDecimal getUtilidadMayoreoCosto() {
        return utilidadMayoreoCosto;
    }

    public void setUtilidadMayoreoCosto(BigDecimal utilidadMayoreoCosto) {
        this.utilidadMayoreoCosto = utilidadMayoreoCosto;
    }

    public BigDecimal getUtilidadMayoreoComision() {
        return utilidadMayoreoComision;
    }

    public void setUtilidadMayoreoComision(BigDecimal utilidadMayoreoComision) {
        this.utilidadMayoreoComision = utilidadMayoreoComision;
    }

    public BigDecimal getUtilidadMayoreoPacto() {
        return utilidadMayoreoPacto;
    }

    public void setUtilidadMayoreoPacto(BigDecimal utilidadMayoreoPacto) {
        this.utilidadMayoreoPacto = utilidadMayoreoPacto;
    }

    public BigDecimal getDiasRecuperacion() {
        return diasRecuperacion;
    }

    public void setDiasRecuperacion(BigDecimal diasRecuperacion) {
        this.diasRecuperacion = diasRecuperacion;
    }

    public BigDecimal getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(BigDecimal recuperacion) {
        this.recuperacion = recuperacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public void reset() {

        porcentajeUtilidad = null;
        clasificacion = 0;
        idClientePk = null;
        totalMenudeoContado = null;
        totalMenudeoCredito = null;
        utilidadMenudeo = null;
        totalMayoreoContado = null;
        totalMayoreoCredito = null;
        utilidadMayoreoCosto = null;
        utilidadMayoreoComision = null;
        utilidadMayoreoPacto = null;
        diasRecuperacion = null;
        recuperacion = null;
        nombreCliente = null;

    }

}
