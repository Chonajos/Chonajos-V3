package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class AjusteExistenciaMayoreo extends ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idAjusteMayoreoPk;
    private BigDecimal idExpFk;
    private BigDecimal idUsuarioAjusteFK;
    private BigDecimal idUsuarioApruebaFK;
    private Date fechaAjuste;
    private BigDecimal empaqueAnterior;
    private BigDecimal empaqueAjustados;
    private BigDecimal kilosAnteior;
    private BigDecimal kilosAjustados;
    private String observaciones;
    private String motivoAjuste;

    @Override
    public String toString() {
        return "AjusteExistenciaMayoreo{" + "idAjusteMayoreoPk=" + idAjusteMayoreoPk + ", idExpFk=" + idExpFk + ", idUsuarioAjusteFK=" + idUsuarioAjusteFK + ", idUsuarioApruebaFK=" + idUsuarioApruebaFK + ", fechaAjuste=" + fechaAjuste + ", empaqueAnterior=" + empaqueAnterior + ", empaqueAjustados=" + empaqueAjustados + ", kilosAnteior=" + kilosAnteior + ", kilosAjustados=" + kilosAjustados + ", observaciones=" + observaciones + ", motivoAjuste=" + motivoAjuste + '}';
    }


    public BigDecimal getIdUsuarioAjusteFK() {
        return idUsuarioAjusteFK;
    }

    public void setIdUsuarioAjusteFK(BigDecimal idUsuarioAjusteFK) {
        this.idUsuarioAjusteFK = idUsuarioAjusteFK;
    }

    public BigDecimal getIdUsuarioApruebaFK() {
        return idUsuarioApruebaFK;
    }

    public void setIdUsuarioApruebaFK(BigDecimal idUsuarioApruebaFK) {
        this.idUsuarioApruebaFK = idUsuarioApruebaFK;
    }

    public Date getFechaAjuste() {
        return fechaAjuste;
    }

    public void setFechaAjuste(Date fechaAjuste) {
        this.fechaAjuste = fechaAjuste;
    }

    public BigDecimal getEmpaqueAnterior() {
        return empaqueAnterior;
    }

    public void setEmpaqueAnterior(BigDecimal empaqueAnterior) {
        this.empaqueAnterior = empaqueAnterior;
    }

    public BigDecimal getEmpaqueAjustados() {
        return empaqueAjustados;
    }

    public void setEmpaqueAjustados(BigDecimal empaqueAjustados) {
        this.empaqueAjustados = empaqueAjustados;
    }

    public BigDecimal getKilosAnteior() {
        return kilosAnteior;
    }

    public void setKilosAnteior(BigDecimal kilosAnteior) {
        this.kilosAnteior = kilosAnteior;
    }

    public BigDecimal getKilosAjustados() {
        return kilosAjustados;
    }

    public void setKilosAjustados(BigDecimal kilosAjustados) {
        this.kilosAjustados = kilosAjustados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMotivoAjuste() {
        return motivoAjuste;
    }

    public void setMotivoAjuste(String motivoAjuste) {
        this.motivoAjuste = motivoAjuste;
    }

    public BigDecimal getIdAjusteMayoreoPk() {
        return idAjusteMayoreoPk;
    }

    public void setIdAjusteMayoreoPk(BigDecimal idAjusteMayoreoPk) {
        this.idAjusteMayoreoPk = idAjusteMayoreoPk;
    }

    public BigDecimal getIdExpFk() {
        return idExpFk;
    }

    public void setIdExpFk(BigDecimal idExpFk) {
        this.idExpFk = idExpFk;
    }

    

    @Override
    public void reset() {
        
        idAjusteMayoreoPk = null;
        idExpFk = null;
        idUsuarioAjusteFK = null;
        idUsuarioApruebaFK = null;
        fechaAjuste = null;
        empaqueAnterior = null;
        empaqueAjustados = null;
        kilosAnteior = null;
        kilosAjustados = null;
        observaciones = null;
        motivoAjuste = null;

    }

}
