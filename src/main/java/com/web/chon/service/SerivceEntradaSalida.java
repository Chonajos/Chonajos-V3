/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NegocioOperacionesCaja;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class SerivceEntradaSalida implements IfaceEntradaSalida {
    NegocioOperacionesCaja ejb;
    private void getEjb() 
    {
        if (ejb == null) {
            try {
                ejb = (NegocioOperacionesCaja) Utilidades.getEJBRemote("ejbEntradaSalida", NegocioOperacionesCaja.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(SerivceEntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<OperacionesCaja> getMovimientosByIdCaja(BigDecimal idCaja, String fechaInicio, String fechaFin) {
         getEjb();
        ArrayList<OperacionesCaja> lstmovimientos = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getMovimientosByIdCaja(idCaja, fechaInicio, fechaFin);
        BigDecimal count= new BigDecimal(0);
        for (Object[] object : lstObject) {
            OperacionesCaja ent = new OperacionesCaja();
            ent.setIdEntradaSalidaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            ent.setIdCajaFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            ent.setTipoES(object[2] == null ? null : new BigDecimal(object[2].toString()));
            ent.setFecha(object[3] == null ? null : (Date) object[3]);
            ent.setIdConceptoFk(object[4] == null ? null : new BigDecimal(object[4].toString()));
            ent.setComentarios(object[5] == null ? null : object[5].toString());
            ent.setMonto(object[6] == null ? null : new BigDecimal(object[6].toString()));
            ent.setIdCajaOrigen(object[7] == null ? null : new BigDecimal(object[7].toString()));
            ent.setIdCajaDestino(object[8] == null ? null : new BigDecimal(object[8].toString()));
            ent.setNumero(count = count.add(new BigDecimal(1), MathContext.UNLIMITED));
            ent.setNombreCaja(object[9] == null ? null : object[9].toString());
            ent.setNombreConcepto(object[10] == null ? null : object[10].toString());
            ent.setNombreOperacion(object[11] == null ? null : object[11].toString());
            lstmovimientos.add(ent);
        }

        return lstmovimientos;
    
    
    
    }

    @Override
    public int insertaMovimiento(OperacionesCaja es) {
      getEjb();
      return ejb.insertaMovimiento(es);
    
    }

    @Override
    public int getNextVal() {
       getEjb();
       return ejb.getNextVal();
    
    
    }
    
}
