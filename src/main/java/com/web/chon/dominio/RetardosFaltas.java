package com.web.chon.dominio;

import java.io.Serializable;

/**
 *
 * @author Juan de la Cruz
 */
public class RetardosFaltas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nombreUsuario;
    private Integer faltas;
    private Integer retardos;

    @Override
    public String toString() {
        return "RetardosFaltas{" + "nombreUsuario=" + nombreUsuario + ", faltas=" + faltas + ", retardos=" + retardos + '}';
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getRetardos() {
        return retardos;
    }

    public void setRetardos(Integer retardos) {
        this.retardos = retardos;
    }
    
    

  

}
