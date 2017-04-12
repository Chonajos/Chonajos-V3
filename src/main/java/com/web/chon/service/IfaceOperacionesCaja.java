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
    public int getNextVal();
    public int insertaOperacion(OperacionesCaja es);
    public int updateOperacion(OperacionesCaja es);
    public int updateStatusConcepto(BigDecimal idOperacionPk,BigDecimal idStatusFk,BigDecimal idConceptoFk);
    public int updateCorte(BigDecimal idOperacionPk,BigDecimal idCorteFk);
    public OperacionesCaja getOperacionByIdPk(BigDecimal idPk);
    
    public ArrayList<OperacionesCaja> getOperacionesBy(BigDecimal idCajaFk,BigDecimal idOperacionFk,BigDecimal idConceptoFk,String fechaInicio, String fechaFin,BigDecimal idStatusFk,BigDecimal idUserFk,BigDecimal idCorte,BigDecimal inout,BigDecimal idFormaPago);
    public ArrayList<OperacionesCaja> getTransferenciasEntrantes(BigDecimal idCajaFk);
    public ArrayList<OperacionesCaja> getDepositosEntrantes(BigDecimal idSucursalFk);
    public ArrayList<TipoOperacion> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES);
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk);
    public ArrayList<OperacionesCaja> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT);
    public ArrayList<Usuario> getResponsables(BigDecimal idCajaFk);
    public ArrayList<TipoOperacion> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk,BigDecimal entrada_salida);
    public ArrayList<OperacionesCaja> getDetalles(BigDecimal idCajaFk,BigDecimal idUserFk,BigDecimal entrada_salida,BigDecimal idStatusFk);
    public ArrayList<OperacionesCaja> getDetallesCorte(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk,BigDecimal idCorteFk);
    public ArrayList<OperacionesCaja> getOperacionesByCategoria(BigDecimal idCategoriaFk,BigDecimal idSucursalFk,BigDecimal idCajaFk,BigDecimal idStatusFk,BigDecimal idConceptoFk,BigDecimal idTipoOperacionFk,String fechaInicio,String fechaFin);
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk,BigDecimal idEntradaSalida,BigDecimal idUsuarioFk);
    
    
    //Prueba de Nuevos Métodos para Corte de Caja//
    //-- no toco los alteriores para no afecar en caso de falla--//
    public ArrayList<OperacionesCaja> getGenerales(BigDecimal idCajaFk,BigDecimal idEntradaSalida,BigDecimal idUsuarioFk,BigDecimal idStatusFk, BigDecimal idSucursalFk,BigDecimal TIPO);
    public int updateCortebyIdCaja(BigDecimal idCajaFk,BigDecimal idCorteFk);
    
    /**
     * Obtiene las operaciones de caja por id de sucursal, fecha de inicio y de fin
     * 
     * @param idSucursal
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public ArrayList<OperacionesCaja> getByIdSucuralAndDate(BigDecimal idSucursal,String fechaInicio, String fechaFin);
    

}
