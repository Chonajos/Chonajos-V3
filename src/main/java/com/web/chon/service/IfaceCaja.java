/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Caja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceCaja {
    public int insertCaja(Caja c);
    public int updateCaja(Caja c);
    public int updateMontoCaja(Caja c);
    public ArrayList<Caja> getCajas(BigDecimal idSucursalFk,BigDecimal tipo);
    public Caja getCajaByIdSucuTipo(BigDecimal idSucursalFk,BigDecimal tipo);
    public ArrayList<Caja> getCajasByIdPk(BigDecimal idCajaPk);
}
