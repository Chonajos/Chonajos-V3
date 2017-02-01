package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class HorarioUsuario implements Serializable {

    BigDecimal idHorarioUsuario;
    BigDecimal idUsuario;
    Date horaEntrada;
    Date horaSalida;
    Date fechaInicio;
    Date fechaFin;
    String tolerancia;

    public BigDecimal getIdHorarioUsuario() {
        return idHorarioUsuario;
    }

    public void setIdHorarioUsuario(BigDecimal idHorarioUsuario) {
        this.idHorarioUsuario = idHorarioUsuario;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }
    

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(String tolerancia) {
        this.tolerancia = tolerancia;
    }

    @Override
    public String toString() {
        return "HorarioUsuario{" + "idHorarioUsuario=" + idHorarioUsuario + ", idUsuario=" + idUsuario + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", tolerancia=" + tolerancia + '}';
    }

    public void reset() {
        idHorarioUsuario = null;
        idUsuario = null;
        horaEntrada = null;
        horaSalida = null;
        fechaInicio = null;
        fechaFin = null;
        tolerancia = null;
    }

}
