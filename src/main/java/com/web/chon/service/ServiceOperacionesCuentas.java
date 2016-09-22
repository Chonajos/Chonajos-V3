/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.negocio.NegocioOperacionesCuentas;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceOperacionesCuentas implements IfaceOperacionesCuentas {

    NegocioOperacionesCuentas ejb;
    private void getEjb() 
    {
        if (ejb == null) {
            try {
                ejb = (NegocioOperacionesCuentas) Utilidades.getEJBRemote("ejbOperacionesCuentas", NegocioOperacionesCuentas.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceOperacionesCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @Override
    public int insertaOperacion(OperacionesCuentas es) {
        getEjb();
        return ejb.insertaOperacion(es);
    
    }

    @Override
    public int updateOperacion(OperacionesCuentas es) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getOperacionesByIdCuenta(BigDecimal idCuentaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
       getEjb();
       return ejb.getNextVal();
    }
    
}
