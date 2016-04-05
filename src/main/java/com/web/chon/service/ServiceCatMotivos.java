/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Motivos;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.negocio.NegocioCatMotivos;
import com.web.chon.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceCatMotivos implements IfaceCatMotivos
{
    NegocioCatMotivos ejb;

    @Override
    public ArrayList<Motivos> getMotivos() 
    {
        try
       {
            ArrayList <Motivos> lista_motivos = new ArrayList<Motivos>();
            ejb = (NegocioCatMotivos) Utilidades.getEJBRemote("ejbCatMotivos", NegocioCatMotivos.class.getName());
            List<Object[]> lstObject = ejb.getMotivos();
             
            for(Object[] obj: lstObject )
            {
                Motivos mov  = new Motivos();
                mov.setId_motivo(Integer.parseInt(obj[0].toString()));
                mov.setNombre_motivo(obj[1] == null ? "" :obj[1].toString());
                mov.setComentarios(obj[2] == null ? "" :obj[2].toString());
                lista_motivos.add(mov);
                
            }
            return lista_motivos;
        }catch(Exception ex)
            {
                Logger.getLogger(ServiceCatMotivos.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
    }
    
    
}
