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
    public ArrayList<ConceptosES> getConceptosByTipoOperacion(BigDecimal idTipoOperacionFk) {
        
        getEjb();
        ArrayList<ConceptosES> lstConceptos= new ArrayList<ConceptosES>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getConceptosByTipoOperacion(idTipoOperacionFk);
        for (Object[] object : lstObject) {
            ConceptosES c = new ConceptosES();
            c.setIdConceptoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            c.setIdTipoOperacionFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            c.setNombre(object[2] == null ? null : object[2].toString());
            c.setDescripcion(object[3] == null ? null : object[3].toString());
            lstConceptos.add(c);
        }
        return lstConceptos;
        
        
        
    }

    @Override
    public ArrayList<ConceptosES> getConceptos() {
        getEjb();
        ArrayList<ConceptosES> lstConceptos= new ArrayList<ConceptosES>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getConceptos();
        for (Object[] object : lstObject) 
        {
            ConceptosES c = new ConceptosES();
            c.setIdConceptoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            c.setIdTipoOperacionFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            c.setNombreOperacion(object[2] == null ? null : object[2].toString());
            c.setNombre(object[3] == null ? null : object[3].toString());
            c.setDescripcion(object[4] == null ? null : object[4].toString());
            lstConceptos.add(c);
        }
        return lstConceptos;
    }

    @Override
    public int insertConcepto(ConceptosES c) {
        getEjb();
        return ejb.insertConcepto(c);
        
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public int updateConcepto(ConceptosES c) {
        getEjb();
        return ejb.updateConcepto(c);
    }

    @Override
    public ArrayList<ConceptosES> getConceptosByIdCategoria(BigDecimal id) {
        getEjb();
        ArrayList<ConceptosES> lstConceptos= new ArrayList<ConceptosES>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getConceptosByIdCategoria(id);
        for (Object[] object : lstObject) 
        {
            ConceptosES c = new ConceptosES();
            c.setIdConceptoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            c.setIdTipoOperacionFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            //c.setNombreOperacion(object[2] == null ? null : object[2].toString());
            c.setNombre(object[2] == null ? null : object[2].toString());
            c.setDescripcion(object[3] == null ? null : object[3].toString());
            lstConceptos.add(c);
        }
        return lstConceptos;
    }
    
}
