/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.OperacionesCaja;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    public int updateStatusConceptoOperacion(BigDecimal idOperacionPk,BigDecimal idStatusFk,BigDecimal idConceptoFk);
    public int updateCorteCaja(BigDecimal idOperacionPk,BigDecimal idCorteCajaFk);
    public List<Object[]> getOperacionByIdOperacionPK(BigDecimal idOperacionPk);
    public List<Object[]> getOperacionesBy(BigDecimal idCajaFk, BigDecimal idOperacionFk, BigDecimal idConceptoFk, String fechaInicio, String fechaFin, BigDecimal idStatusFk, BigDecimal idUserFk,BigDecimal idCorte,BigDecimal inout);    
    public List<Object[]> getTransferenciasEntrantes(BigDecimal idCorteCajaFk);
    public List<Object[]> getDepositosEntrantes();
    public List<Object[]> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES);
    public List<Object[]> getOperaciones(BigDecimal idCajaFk,BigDecimal idUserFk);
    public List<Object[]> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT);
    public List<Object[]> getResponsables(BigDecimal idCajaFk);
    public List<Object[]> getDetalles(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk);
    public List<Object[]> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk,BigDecimal entrada_salida);
    public int getNextVal();
    
}
