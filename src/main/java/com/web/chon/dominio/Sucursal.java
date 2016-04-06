/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author marcogante
 */
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idSucursalPk;
    private String nombreSucursal;
    private String calleSucursal;
    private int cpSucursal;
    private Long telefonoSucursal;
    private BigDecimal numInt;
    private BigDecimal numExt;
    private int statusSucursal;

    @Override
    public String toString() {
        return "Sucursal{" + "idSucursalPk=" + idSucursalPk + ", nombreSucursal=" + nombreSucursal + ", calleSucursal=" + calleSucursal + ", cpSucursal=" + cpSucursal + ", telefonoSucursal=" + telefonoSucursal + ", numInt=" + numInt + ", numExt=" + numExt + ", statusSucursal=" + statusSucursal + '}';
    }
    

    public String getCalleSucursal() {
        return calleSucursal;
    }

    public void setCalleSucursal(String calleSucursal) {
        this.calleSucursal = calleSucursal;
    }

    public int getCpSucursal() {
        return cpSucursal;
    }

    public void setCpSucursal(int cpSucursal) {
        this.cpSucursal = cpSucursal;
    }

    public Long getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(Long telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    public BigDecimal getNumInt() {
        return numInt;
    }

    public void setNumInt(BigDecimal numInt) {
        this.numInt = numInt;
    }

    public BigDecimal getNumExt() {
        return numExt;
    }

    public void setNumExt(BigDecimal numExt) {
        this.numExt = numExt;
    }

    public int getStatusSucursal() {
        return statusSucursal;
    }

    public void setStatusSucursal(int statusSucursal) {
        this.statusSucursal = statusSucursal;
    }

    public int getIdSucursalPk() {
        return idSucursalPk;
    }

    public void setIdSucursalPk(int idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

   
    

   
}
