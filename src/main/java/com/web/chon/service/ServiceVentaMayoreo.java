/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreo;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceVentaMayoreo implements IfaceVentaMayoreo {
    
    NegocioVentaMayoreo ejb;
    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVentaMayoreo) Utilidades.getEJBRemote("ejbVentaMayoreo", NegocioVentaMayoreo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVenta(VentaMayoreo venta) {
       getEjb();
       return ejb.insertarVenta(venta);
    }

    @Override
    public int getNextVal() {
       getEjb();
        return ejb.getNextVal();
    }
    
}
