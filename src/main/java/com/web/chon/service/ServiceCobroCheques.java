/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CobroCheques;
import com.web.chon.negocio.NegocioCobroCheques;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceCobroCheques implements IfaceCobroCheques{
    
    NegocioCobroCheques ejb;
    
    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCobroCheques) Utilidades.getEJBRemote("ejbCobroCheques", NegocioCobroCheques.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCobroCheques.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertarDocumento(CobroCheques cc) {
        getEjb();
        return ejb.insertarDocumento(cc);
    }

    @Override
    public int nextVal() {
        getEjb();
        return ejb.nextVal();
    }
}
