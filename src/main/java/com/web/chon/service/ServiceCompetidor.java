/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Competidor;
import com.web.chon.negocio.NegocioCompetidor;
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
 * @author freddy
 */
@Service
public class ServiceCompetidor implements IfaceCompetidores {

    NegocioCompetidor ejb;
    
    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCompetidor) Utilidades.getEJBRemote("ejbCompetidores", NegocioCompetidor.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCompetidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public ArrayList<Competidor> getCometidores() {
       getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<Competidor> lstCompetidores = new ArrayList<Competidor>();
        lstObject = ejb.getCometidores();
        for (Object[] obj : lstObject) {
            Competidor dominio = new Competidor();
            dominio.setIdCompetidorPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));;
            dominio.setNombreCompetidor(obj[1] == null ? "" : obj[1].toString());
            dominio.setUbicacion(obj[2] == null ? "" : obj[2].toString());
            lstCompetidores.add(dominio);
        }
        return lstCompetidores;
        
    }

    @Override
    public int getNextVal() {
     getEjb();
     return ejb.getNextVal();
    }

    @Override
    public int insertCompetidor(Competidor c) {
        getEjb();
        return ejb.insertCompetidor(c);
    }

    @Override
    public int updateCompetidor(Competidor c) {
     getEjb();
     return ejb.updateCompetidor(c);
    }

    @Override
    public Competidor getCometidoresById(BigDecimal idCompetidor) {
        getEjb();
     try {
            Competidor c  = new Competidor();
          List<Object[]> object = ejb.getCometidoresById(idCompetidor);
            for (Object[] obj : object) {
                c.setIdCompetidorPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                c.setNombreCompetidor(obj[1] == null ? "" : obj[1].toString());
                c.setUbicacion(obj[2] == null ? "" : obj[2].toString());
            }

            return c;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCompetidor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
    
    }
    
}
