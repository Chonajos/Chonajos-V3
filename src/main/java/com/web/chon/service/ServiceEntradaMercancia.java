/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia2;

import com.web.chon.negocio.NegocioEntradaMercancia;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercancia implements IfaceEntradaMercancia{
    NegocioEntradaMercancia ejb;
    
    public void getEjb() 
    {
        if (ejb == null) 
        {
            try {
                ejb = (NegocioEntradaMercancia) Utilidades.getEJBRemote("ejbEntradaMercancia", NegocioEntradaMercancia.class.getName());
            } catch (Exception ex) 
            {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) 
    {
       
        getEjb();
        return ejb.insertEntradaMercancia(entrada);
        
        
    }

    @Override
    public int buscaMaxMovimiento(EntradaMercancia2 entrada)
    {
        getEjb();
        try {
            System.out.println("Entrada Service: " + entrada.toString());
        return ejb.buscaMaxMovimiento(entrada);
        }
         catch (Exception ex) {
            //Logger.getLogger(NegocioEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public int getNextVal() {
        getEjb();
        try 
        {
           return ejb.getNextVal();
           
        } catch (Exception ex) 
        {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }
    
}
