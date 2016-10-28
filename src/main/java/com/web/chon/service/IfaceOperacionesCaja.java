/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceOperacionesCaja {
    public int insertaOperacion(OperacionesCaja es);
    public int updateOperacion(OperacionesCaja es);
    public int updateStatusConcepto(BigDecimal idOperacionPk,BigDecimal idStatusFk,BigDecimal idConceptoFk);
    public int updateCorte(BigDecimal idOperacionPk,BigDecimal idCorteFk);
    public OperacionesCaja getOperacionByIdPk(BigDecimal idPk);
    
    public ArrayList<OperacionesCaja> getOperacionesBy(BigDecimal idCajaFk,BigDecimal idOperacionFk,BigDecimal idConceptoFk,String fechaInicio, String fechaFin,BigDecimal idStatusFk,BigDecimal idUserFk,BigDecimal idCorte,BigDecimal inout);
    
    public ArrayList<OperacionesCaja> getTransferenciasEntrantes(BigDecimal idCajaFk);
    public ArrayList<OperacionesCaja> getDepositosEntrantes();
    public ArrayList<TipoOperacion> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES);
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk);
    public ArrayList<OperacionesCaja> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT);
    public ArrayList<Usuario> getResponsables(BigDecimal idCajaFk);
    public int getNextVal();
    public ArrayList<TipoOperacion> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk,BigDecimal entrada_salida);
    public ArrayList<OperacionesCaja> getDetalles(BigDecimal idCajaFk,BigDecimal idUserFk,BigDecimal entrada_salida,BigDecimal idStatusFk);
}
