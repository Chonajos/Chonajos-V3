/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.BajaClientes;
import com.web.chon.negocio.NegocioBajaClientes;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceBajaCliente implements IfaceBajaCliente
{
    NegocioBajaClientes ejb;

    @Override
    public int insertCliente(BajaClientes cliente) 
    {
    try 
        {
            ejb = (NegocioBajaClientes) Utilidades.getEJBRemote("ejbBajaClientes", NegocioBajaClientes.class.getName());
            return ejb.insertCliente(cliente);
        } catch (Exception ex) 
        {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
       
    }

    @Override
    public int deleteCliente(int idCliente) 
    {
          try 
        {
            System.out.println("service delete Bajaclientes");
       return ejb.deleteClienteBajas(idCliente);
        } catch (Exception ex) 
        {
            Logger.getLogger(ServiceBajaCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
        
       
    }
    
}
