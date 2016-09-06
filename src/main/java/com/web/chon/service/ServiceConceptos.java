/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ConceptosES;
import com.web.chon.negocio.NegocioConceptos;
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
public class ServiceConceptos implements IfaceConceptos {

    NegocioConceptos ejb;
     private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioConceptos) Utilidades.getEJBRemote("ejbConceptos", NegocioConceptos.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceConceptos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public ArrayList<ConceptosES> getConceptos() {
        
        getEjb();
        ArrayList<ConceptosES> lstConceptos= new ArrayList<ConceptosES>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getConceptos();
        for (Object[] object : lstObject) {
            ConceptosES c = new ConceptosES();
            c.setIdConceptoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            c.setNombre(object[1] == null ? null : object[1].toString());
            c.setDescripcion(object[2] == null ? null : object[2].toString());
            lstConceptos.add(c);
        }
        return lstConceptos;
        
        
        
    }
    
}
