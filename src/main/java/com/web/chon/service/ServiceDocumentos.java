/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Documento;
import com.web.chon.negocio.NegocioDocumentos;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceDocumentos implements IfaceDocumentos {

    NegocioDocumentos ejb;
    
    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioDocumentos) Utilidades.getEJBRemote("ejbDocumentos", NegocioDocumentos.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertarDocumento(Documento documento) {
       getEjb();
       return ejb.insertarDocumento(documento);
    }
    
    @Override
    public int getNextVal() {
        getEjb();
        
        return ejb.nextVal();
    }

    @Override
    public Documento getDocumentoByIdDocumentoPk(BigDecimal idDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Documento getDocumentoByIdAbonoFk(BigDecimal idAbonoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Documento> getDocumentosByIdClienteFk(BigDecimal idClienteFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Documento> getDocumentosByIdStatusFk(BigDecimal idStatusFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
