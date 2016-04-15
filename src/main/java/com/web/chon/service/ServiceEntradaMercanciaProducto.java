/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.negocio.NegocioEntradaMercanciaProducto;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercanciaProducto implements IfaceEntradaMercanciaProducto
{
    NegocioEntradaMercanciaProducto ejb;
    
    public void getEjb() 
    {
        if (ejb == null) 
        {
            try {
                ejb = (NegocioEntradaMercanciaProducto) Utilidades.getEJBRemote("ejbEntradaMercanciaProducto", NegocioEntradaMercanciaProducto.class.getName());
            } catch (Exception ex) 
            {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercanciaProducto producto) 
    {
        
        getEjb();
        return ejb.insertEntradaMercanciaProducto(producto);
    }
    
}
