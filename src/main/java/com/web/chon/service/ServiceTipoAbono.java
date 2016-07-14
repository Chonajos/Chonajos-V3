/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.negocio.NegocioTipoAbono;
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
public class ServiceTipoAbono implements IfaceTipoAbono {
    NegocioTipoAbono ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTipoAbono) Utilidades.getEJBRemote("ejbTipoAbono", NegocioTipoAbono.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceTipoAbono.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<TipoAbono> getAll() {
       getEjb();
        ArrayList<TipoAbono> lstCredito = new ArrayList<TipoAbono>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            TipoAbono ta = new TipoAbono();
            ta.setIdTipoAbono(object[0] == null ? null : new BigDecimal(object[0].toString()));
            ta.setNombreTipoAbono(object[1] == null ? null : object[1].toString());
            ta.setDescripcion(object[2] == null ? null : object[2].toString());
            lstCredito.add(ta);
        }

        return lstCredito;
        
    }

    @Override
    public TipoAbono getById(BigDecimal idtAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal idtAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(TipoAbono tAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(TipoAbono tAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
