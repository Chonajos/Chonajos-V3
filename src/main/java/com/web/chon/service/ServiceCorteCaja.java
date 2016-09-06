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
public class ServiceCorteCaja implements IfaceCorteCaja{
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
    public CorteCaja getCorteByFecha(String fecha) {
       getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCorteByFecha(fecha);
        CorteCaja corte = new CorteCaja();
        for (Object[] object : lstObject) {

            corte.setIdCorteCajaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            corte.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            corte.setFecha(object[2] == null ? null : (Date) object[2]);
            corte.setVentasMenudeo(object[3] == null ? null : new BigDecimal(object[3].toString()));
            corte.setVentasMenudeo(object[4] == null ? null : new BigDecimal(object[4].toString()));
            corte.setAbonosCreditos(object[5] == null ? null : new BigDecimal(object[5].toString()));
            corte.setAnticipos(object[6] == null ? null : new BigDecimal(object[6].toString()));
            corte.setCantCheques(object[7] == null ? null : new BigDecimal(object[7].toString()));
            corte.setMontoCheques(object[8] == null ? null : new BigDecimal(object[8].toString()));
            corte.setTransferenciasIN(object[9] == null ? null : new BigDecimal(object[9].toString()));
            corte.setServicios(object[10] == null ? null : new BigDecimal(object[10].toString()));
            corte.setPrestamos(object[11] == null ? null : new BigDecimal(object[11].toString()));
            corte.setTransferenciasOUT(object[12] == null ? null : new BigDecimal(object[12].toString()));
            corte.setSaldoAnterior(object[13] == null ? null : new BigDecimal(object[13].toString()));
            corte.setSaldoNuevo(object[14] == null ? null : new BigDecimal(object[14].toString()));
            corte.setComentarios(object[15] == null ? null : object[15].toString());
            corte.setIdUserFk(object[16] == null ? null : new BigDecimal(object[16].toString()));
        }

        return corte;
    }

    @Override
    public int insertCorte(CorteCaja cc) {
        getEjb();
        return ejb.insertCorte(cc);
    }
    
}
