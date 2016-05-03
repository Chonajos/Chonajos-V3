package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Juan de la Cruz
 */
public class AccesMenu extends ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idMenu;
    private BigDecimal idRol;
    private String descripcion;
    private String tipo;
    private String nivel;
    private String urlSistema;
    private boolean estatus;

    @Override
    public String toString() {
        return "AccesMenu{" + "idMenu=" + idMenu + ", descripcion=" + descripcion + ", tipo=" + tipo + ", nivel=" + nivel + ", urlSistema=" + urlSistema + ", estatus=" + estatus + '}';
    }

    public BigDecimal getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(BigDecimal idMenu) {
        this.idMenu = idMenu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getUrlSistema() {
        return urlSistema;
    }

    public void setUrlSistema(String urlSistema) {
        this.urlSistema = urlSistema;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public BigDecimal getIdRol() {
        return idRol;
    }

    public void setIdRol(BigDecimal idRol) {
        this.idRol = idRol;
    }

 
    @Override
    public void reset() {

        idMenu = null;
        descripcion = null;
        tipo = null;
        nivel = null;
        urlSistema = null;
        estatus = false;
        idRol = null;

    }

}
