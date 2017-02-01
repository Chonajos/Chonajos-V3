package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class DiaDescansoUsuario implements Serializable {

    BigDecimal idDiaDescansoUsuario;
    BigDecimal idUsuario;
    Date fechaInicio;
    Date fechaFin;
    String dia;

    //Arreglo que llevara los dias de descanso
    private String[] diasSelecionados;

    public BigDecimal getIdDiaDescansoUsuario() {
        return idDiaDescansoUsuario;
    }

    public void setIdDiaDescansoUsuario(BigDecimal idDiaDescansoUsuario) {
        this.idDiaDescansoUsuario = idDiaDescansoUsuario;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String[] getDiasSelecionados() {
        return diasSelecionados;
    }

    public void setDiasSelecionados(String[] diasSelecionados) {
        this.diasSelecionados = diasSelecionados;
    }

    @Override
    public String toString() {
        return "DiaDescansoUsuario{" + "idDiaDescansoUsuario=" + idDiaDescansoUsuario + ", idUsuario=" + idUsuario + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", dia=" + dia + ", diasSelecionados=" + diasSelecionados + '}';
    }

    public void reset() {

        idDiaDescansoUsuario = null;
        idUsuario = null;
        fechaInicio = null;
        fechaFin = null;
        dia = null;
        diasSelecionados = null;

    }

}
