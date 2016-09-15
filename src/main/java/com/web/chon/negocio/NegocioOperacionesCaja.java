/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.OperacionesCaja;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioOperacionesCaja {
    
    public int insertaOperacion(OperacionesCaja es);
    public int updateOperacion(OperacionesCaja es);
    public int updateStatusOperacion(BigDecimal idOperacionPk,BigDecimal idStatusFk);
    public int updateCorteCaja(BigDecimal idOperacionPk,BigDecimal idCorteCajaFk);
    public List<Object[]> getOperacionByIdOperacionPK(BigDecimal idOperacionPk);
    public List<Object[]> getOperacionesBy(BigDecimal idCorteCajaFk, BigDecimal idCajaFk,BigDecimal idCajaDestinoFk,BigDecimal idConceptoFk,String fechaInicio, String fechaFin,BigDecimal idStatusFk,BigDecimal idUserFk);
    public List<Object[]> getTransferenciasEntrantes(BigDecimal idCorteCajaFk);
    public List<Object[]> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES);
    public List<Object[]> getOperaciones(BigDecimal idCajaFk,BigDecimal idUserFk);
    public List<Object[]> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT);
    public int getNextVal();
    
}
