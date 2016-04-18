/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.Bodega;
import com.web.chon.negocio.NegocioCatBodega;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceCatBodegas implements IfaceCatBodegas {
     NegocioCatBodega ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatBodega) Utilidades.getEJBRemote("ejbCatBodegas", NegocioCatBodega.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Bodega> getBodegas() {
        try {
            ArrayList<Bodega> lista_bodegas= new ArrayList<Bodega>();
           
            getEjb();
            List<Object[]> lstObject = ejb.getBodegas();

            for (Object[] obj : lstObject) {
                Bodega s = new Bodega();
                s.setIdBodegaPK(new BigDecimal(obj[0].toString()));
                s.setNombreBodega((obj[1] == null ? "" : obj[1].toString()));
                s.setDescripcionBodega((obj[2] == null ? "" : obj[2].toString()));
                lista_bodegas.add(s);

            }
            return lista_bodegas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public List<Bodega[]> getBodegaById(int idBodega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteBodega(int idBodega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateBodega(Bodega bo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertBodega(Bodega bo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
