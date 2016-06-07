/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author freddy
 */
public class ExistenciaMenudeo implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idExMenPk;
    private BigDecimal idSucursalFk;
    private BigDecimal kilos;
    private BigDecimal idSubProductoPk;

    @Override
    public String toString() {
        return "ExistenciaMenudeo{" + "idExMenPk=" + idExMenPk + ", idSucursalFk=" + idSucursalFk + ", kilos=" + kilos + ", idSubProductoPk=" + idSubProductoPk + '}';
    }

   
    public BigDecimal getIdExMenPk() {
        return idExMenPk;
    }

    public void setIdExMenPk(BigDecimal idExMenPk) {
        this.idExMenPk = idExMenPk;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public BigDecimal getIdSubProductoPk() {
        return idSubProductoPk;
    }

    public void setIdSubProductoPk(BigDecimal idSubProductoPk) {
        this.idSubProductoPk = idSubProductoPk;
    }


}
