
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class AjusteExistenciaMenudeo implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idAjusteMenudeo;
    private BigDecimal idExistenciaMenudeoFK;
    private BigDecimal idUsuarioAjusteFK;
    private BigDecimal idUsuarioApruebaFK;
    private BigDecimal idTipoEmpaqueFK;
    private BigDecimal idSucursalFK;
    private Date fechaAjuste;
    private BigDecimal empaqueAnterior;
    private BigDecimal empaqueAjustados;
    private BigDecimal kilosAnteior;
    private BigDecimal kilosAjustados;
    private String observaciones;

    @Override
    public String toString() {
        return "AjusteExistenciaMenudeo{" + "idAjusteMenudeo=" + idAjusteMenudeo + ", idExistenciaMenudeoFK=" + idExistenciaMenudeoFK + ", idUsuarioAjusteFK=" + idUsuarioAjusteFK + ", idUsuarioApruebaFK=" + idUsuarioApruebaFK + ", idTipoEmpaqueFK=" + idTipoEmpaqueFK + ", fechaAjuste=" + fechaAjuste + ", empaqueAnterior=" + empaqueAnterior + ", empaqueAjustados=" + empaqueAjustados + ", kilosAnteior=" + kilosAnteior + ", kilosAjustados=" + kilosAjustados + ", observaciones=" + observaciones + '}';
    }
    

    public BigDecimal getIdAjusteMenudeo() {
        return idAjusteMenudeo;
    }

    public void setIdAjusteMenudeo(BigDecimal idAjusteMenudeo) {
        this.idAjusteMenudeo = idAjusteMenudeo;
    }

    public BigDecimal getIdExistenciaMenudeoFK() {
        return idExistenciaMenudeoFK;
    }

    public void setIdExistenciaMenudeoFK(BigDecimal idExistenciaMenudeoFK) {
        this.idExistenciaMenudeoFK = idExistenciaMenudeoFK;
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

    public BigDecimal getIdTipoEmpaqueFK() {
        return idTipoEmpaqueFK;
    }

    public void setIdTipoEmpaqueFK(BigDecimal idTipoEmpaqueFK) {
        this.idTipoEmpaqueFK = idTipoEmpaqueFK;
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

    public BigDecimal getIdSucursalFK() {
        return idSucursalFK;
    }

    public void setIdSucursalFK(BigDecimal idSucursalFK) {
        this.idSucursalFK = idSucursalFK;
    }


    
    
}
