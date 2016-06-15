/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.negocio.NegocioMenudeoProducto;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceEntradaMenudeoProducto implements IfaceEntradaMenudeoProducto{
    NegocioMenudeoProducto ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioMenudeoProducto) Utilidades.getEJBRemote("ejbEntradaMenudeoProducto", NegocioMenudeoProducto.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaMenudeoProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercanciaProducto(EntradaMenudeoProducto producto) {
       getEjb();
        return ejb.insertEntradaMenudeoProducto(producto);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }
}
