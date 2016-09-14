/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.OperacionesCaja;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceOperacionesCaja {
    public int insertaOperacion(OperacionesCaja es);
    public int updateOperacion(OperacionesCaja es);
    public OperacionesCaja getOperacionByIdPk(BigDecimal idPk);
    public ArrayList<OperacionesCaja> getOperacionesBy(BigDecimal idCajaFk,BigDecimal idOperacionFk,BigDecimal idConceptoFk,String fechaInicio, String fechaFin,BigDecimal idStatusFk,BigDecimal idUserFk);
    public int getNextVal();
}
