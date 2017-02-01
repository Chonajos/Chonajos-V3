package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public class RegistroEntradaSalida implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idRegEntSalPk;
    private Date fecha;
    private Date fechaEntrada;
    private Date fechaSalida;
    private String horaEntrada;
    private String horaSalida;
    private String horarioEntrada;
    private String horarioSalida;
    private double latitudEntrada;
    private double latitudSalida;
    private double longitudEntrada;
    private double longitudSalida;
    private BigDecimal idUsuarioFk;
    private BigDecimal idSucursalFk;
    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String dia;

    private boolean diaDescanso;
    private boolean retardo;
    private boolean falta;
    private String[] diasDescanso;

    @Override
    public String toString() {
        return "RegistroEntradaSalida{" + "idRegEntSalPk=" + idRegEntSalPk + ", fecha=" + fecha + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", horarioEntrada=" + horarioEntrada + ", horarioSalida=" + horarioSalida + ", latitudEntrada=" + latitudEntrada + ", latitudSalida=" + latitudSalida + ", longitudEntrada=" + longitudEntrada + ", longitudSalida=" + longitudSalida + ", idUsuarioFk=" + idUsuarioFk + ", idSucursalFk=" + idSucursalFk + ", fechaFiltroInicio=" + fechaFiltroInicio + ", fechaFiltroFin=" + fechaFiltroFin + ", nombre=" + nombre + ", apPaterno=" + apPaterno + ", apMaterno=" + apMaterno + ", dia=" + dia + ", diaDescanso=" + diaDescanso + ", retardo=" + retardo + ", falta=" + falta + ", diasDescanso=" + diasDescanso + '}';
    }

    public BigDecimal getIdRegEntSalPk() {
        return idRegEntSalPk;
    }

    public void setIdRegEntSalPk(BigDecimal idRegEntSalPk) {
        this.idRegEntSalPk = idRegEntSalPk;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getLatitudEntrada() {
        return latitudEntrada;
    }

    public void setLatitudEntrada(double latitudEntrada) {
        this.latitudEntrada = latitudEntrada;
    }

    public double getLatitudSalida() {
        return latitudSalida;
    }

    public void setLatitudSalida(double latitudSalida) {
        this.latitudSalida = latitudSalida;
    }

    public double getLongitudEntrada() {
        return longitudEntrada;
    }

    public void setLongitudEntrada(double longitudEntrada) {
        this.longitudEntrada = longitudEntrada;
    }

    public double getLongitudSalida() {
        return longitudSalida;
    }

    public void setLongitudSalida(double longitudSalida) {
        this.longitudSalida = longitudSalida;
    }

    public BigDecimal getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(BigDecimal idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public boolean isDiaDescanso() {
        return diaDescanso;
    }

    public void setDiaDescanso(boolean diaDescanso) {
        this.diaDescanso = diaDescanso;
    }

    public String[] getDiasDescanso() {
        return diasDescanso;
    }

    public void setDiasDescanso(String[] diasDescanso) {
        this.diasDescanso = diasDescanso;
    }

    public boolean isRetardo() {
        return retardo;
    }

    public void setRetardo(boolean retardo) {
        this.retardo = retardo;
    }

    public boolean isFalta() {
        return falta;
    }

    public void setFalta(boolean falta) {
        this.falta = falta;
    }

}
