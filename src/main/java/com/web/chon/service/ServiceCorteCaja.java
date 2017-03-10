/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CorteCaja;
import com.web.chon.negocio.NegocioCorteCaja;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceCorteCaja implements IfaceCorteCaja {

    NegocioCorteCaja ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCorteCaja) Utilidades.getEJBRemote("ejbCorteCaja", NegocioCorteCaja.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCorteCaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertCorte(CorteCaja cc) {
        getEjb();
        return ejb.insertCorte(cc);
    }

    @Override
    public int updateCorte(CorteCaja cc) {
        getEjb();
        return ejb.updateCorte(cc);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<CorteCaja> getCortesByIdCajaFk(BigDecimal idDestinoFK, String fechaIni, String fechaFin) {
        getEjb();
        ArrayList<CorteCaja> lstCortes = new ArrayList<CorteCaja>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCortesByIdCajaFk(idDestinoFK, fechaIni, fechaFin);
        for (Object[] object : lstObject) {
            CorteCaja corte = new CorteCaja();
            corte.setIdCorteCajaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corte.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setCantChequesAnt(object[2] == null ? null : new BigDecimal(object[2].toString()));
            corte.setMontoChequesAnt(object[3] == null ? null : new BigDecimal(object[3].toString()));
            corte.setSaldoAnterior(object[4] == null ? null : new BigDecimal(object[4].toString()));
            corte.setCantChequesNuevos(object[5] == null ? null : new BigDecimal(object[5].toString()));
            corte.setMontoChequesNuevos(object[6] == null ? null : new BigDecimal(object[6].toString()));
            corte.setSaldoNuevo(object[7] == null ? null : new BigDecimal(object[7].toString()));
            corte.setComentarios(object[8] == null ? null : object[8].toString());
            corte.setIdUserFk(object[9] == null ? null : new BigDecimal(object[9].toString()));
            corte.setIdStatusFk(object[10] == null ? null : new BigDecimal(object[10].toString()));
            ///
            corte.setNombreCaja(object[11] == null ? null : object[11].toString());
            corte.setNombreUsuario(object[12] == null ? null : object[12].toString());
            corte.setNombreStatus(object[13] == null ? null : object[13].toString());
            lstCortes.add(corte);
        }
        return lstCortes;

    }

    @Override
    public CorteCaja getCorteByidPk(BigDecimal idPk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCorteByidPk(idPk);
        CorteCaja corte = new CorteCaja();
        for (Object[] object : lstObject) {
            corte.setIdCorteCajaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corte.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setCantChequesAnt(object[2] == null ? null : new BigDecimal(object[2].toString()));
            corte.setMontoChequesAnt(object[3] == null ? null : new BigDecimal(object[3].toString()));
            corte.setSaldoAnterior(object[4] == null ? null : new BigDecimal(object[4].toString()));
            corte.setCantChequesNuevos(object[5] == null ? null : new BigDecimal(object[5].toString()));
            corte.setMontoChequesNuevos(object[6] == null ? null : new BigDecimal(object[6].toString()));
            corte.setSaldoNuevo(object[7] == null ? null : new BigDecimal(object[7].toString()));
            corte.setComentarios(object[8] == null ? null : object[8].toString());
            corte.setIdUserFk(object[9] == null ? null : new BigDecimal(object[9].toString()));
            corte.setIdStatusFk(object[10] == null ? null : new BigDecimal(object[10].toString()));
            ///
            corte.setNombreCaja(object[11] == null ? null : object[11].toString());
            corte.setNombreUsuario(object[12] == null ? null : object[12].toString());
            corte.setNombreStatus(object[13] == null ? null : object[13].toString());
        }
        return corte;

    }

    @Override
    public CorteCaja getLastCorteByCaja(BigDecimal idCajaPk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getLastCorteByCaja(idCajaPk);
        BigDecimal cero = new BigDecimal(0);
        CorteCaja corte = new CorteCaja();
        for (Object[] object : lstObject) {
            corte.setIdCorteCajaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corte.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setFecha(object[2] == null ? null : (Date) object[2]);
            corte.setCantChequesAnt(object[3] == null ? cero : new BigDecimal(object[3].toString()));
            corte.setMontoChequesAnt(object[4] == null ? cero : new BigDecimal(object[4].toString()));
            corte.setSaldoAnterior(object[5] == null ? cero : new BigDecimal(object[5].toString()));
            corte.setCantChequesNuevos(object[6] == null ? cero : new BigDecimal(object[6].toString()));
            corte.setMontoChequesNuevos(object[7] == null ? cero : new BigDecimal(object[7].toString()));
            corte.setSaldoNuevo(object[8] == null ? cero : new BigDecimal(object[8].toString()));
            corte.setComentarios(object[9] == null ? "" : object[9].toString());
            corte.setIdUserFk(object[10] == null ? null : new BigDecimal(object[10].toString()));
            corte.setIdStatusFk(object[11] == null ? null : new BigDecimal(object[11].toString()));
            corte.setMontoCuentaAnterior(object[12] == null ? cero : new BigDecimal(object[12].toString()));
            corte.setMontoCuentaNuevo(object[13] == null ? cero : new BigDecimal(object[13].toString()));
            ///

        }
        return corte;
    }

    @Override
    public CorteCaja getLastCorteByCajaHistorial(BigDecimal idCajaPk, BigDecimal idCorteFk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getLastCorteByCajaHistorial(idCajaPk, idCorteFk);
        BigDecimal cero = new BigDecimal(0);
        CorteCaja corte = new CorteCaja();
        for (Object[] object : lstObject) {
            corte.setIdCorteCajaPk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setIdCajaFk(object[2] == null ? null : new BigDecimal(object[2].toString()));
            corte.setFecha(object[3] == null ? null : (Date) object[3]);
            corte.setCantChequesAnt(object[4] == null ? cero : new BigDecimal(object[4].toString()));
            corte.setMontoChequesAnt(object[5] == null ? cero : new BigDecimal(object[5].toString()));
            corte.setSaldoAnterior(object[6] == null ? cero : new BigDecimal(object[6].toString()));
            corte.setCantChequesNuevos(object[7] == null ? cero : new BigDecimal(object[7].toString()));
            corte.setMontoChequesNuevos(object[8] == null ? cero : new BigDecimal(object[8].toString()));
            corte.setSaldoNuevo(object[9] == null ? cero : new BigDecimal(object[9].toString()));
            corte.setComentarios(object[10] == null ? null : object[10].toString());
            corte.setIdUserFk(object[11] == null ? null : new BigDecimal(object[11].toString()));
            corte.setIdStatusFk(object[12] == null ? null : new BigDecimal(object[12].toString()));
            corte.setMontoCuentaAnterior(object[13] == null ? cero : new BigDecimal(object[13].toString()));
            corte.setMontoCuentaNuevo(object[14] == null ? cero : new BigDecimal(object[14].toString()));
            ///

        }
        return corte;
    }

    @Override
    public ArrayList<CorteCaja> getCortesByFechaCajaUsuario(BigDecimal idCajaFk, BigDecimal idUsuarioFk, String fecha) {
        getEjb();
        ArrayList<CorteCaja> lstCortes = new ArrayList<CorteCaja>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCortesByFechaCajaUsuario(idCajaFk, idUsuarioFk, fecha);
        for (Object[] object : lstObject) {
            CorteCaja corte = new CorteCaja();
            corte.setIdCorteCajaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corte.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setFecha((Date) object[2]);
            corte.setCantChequesAnt(object[3] == null ? null : new BigDecimal(object[3].toString()));
            corte.setMontoChequesAnt(object[4] == null ? null : new BigDecimal(object[4].toString()));
            corte.setSaldoAnterior(object[5] == null ? null : new BigDecimal(object[5].toString()));
            corte.setCantChequesNuevos(object[6] == null ? null : new BigDecimal(object[6].toString()));
            corte.setMontoChequesNuevos(object[7] == null ? null : new BigDecimal(object[7].toString()));
            corte.setSaldoNuevo(object[8] == null ? null : new BigDecimal(object[8].toString()));
            corte.setComentarios(object[9] == null ? null : object[9].toString());
            corte.setIdUserFk(object[10] == null ? null : new BigDecimal(object[10].toString()));
            corte.setIdStatusFk(object[11] == null ? null : new BigDecimal(object[11].toString()));
            corte.setMontoCuentaAnterior(object[12] == null ? null : new BigDecimal(object[12].toString()));
            corte.setMontoCuentaNuevo(object[13] == null ? null : new BigDecimal(object[13].toString()));
            lstCortes.add(corte);
        }
        return lstCortes;
    }

    @Override
    public ArrayList<CorteCaja> getSaldoCajaByIdCaja(BigDecimal idCaja) {
        getEjb();
        ArrayList<CorteCaja> lstCorteCaja = new ArrayList<CorteCaja>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getSaldoCajaByIdCaja(idCaja);
        for (Object[] object : lstObject) {
            CorteCaja corteCaja = new CorteCaja();
            
            corteCaja.setIdCajaFk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corteCaja.setCantChequesNuevos(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corteCaja.setMontoChequesNuevos(object[2] == null ? null : new BigDecimal(object[2].toString()));
            corteCaja.setSaldoNuevo(object[3] == null ? null : new BigDecimal(object[3].toString()));
           
            lstCorteCaja.add(corteCaja);
        }
        return lstCorteCaja;
    }

}
