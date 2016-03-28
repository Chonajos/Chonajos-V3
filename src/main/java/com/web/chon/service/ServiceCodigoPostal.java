/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CodigoPostal;

import com.web.chon.negocio.NegocioCatCodigosPostales;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceCodigoPostal implements IfaceCatCodigosPostales {
    NegocioCatCodigosPostales ejb;
    @Override
    public ArrayList<CodigoPostal> getCodigoPostalById(String codigo_postal) 
    {
         try
       {
            ArrayList <CodigoPostal> lista_codigos= new ArrayList<CodigoPostal>();
            
            ejb = (NegocioCatCodigosPostales) Utilidades.getEJBRemote("ejbCatCodigosPostales", NegocioCatCodigosPostales.class.getName());
            List<Object[]> lstObject = ejb.getCodigosByCP(codigo_postal);
             
            for(Object[] obj: lstObject )
            {
                CodigoPostal colonia = new CodigoPostal();
                colonia.setId_cp(Integer.parseInt(obj[0] == null ? "":obj[0].toString()));
                //colonia.setCodigoPostal(obj[1] == null ? "" : obj[1].toString());
                colonia.setNombreColonia(obj[1] == null ? "" : obj[1].toString());
                colonia.setIdMunicipio(Integer.parseInt(obj[2] == null ? "":obj[2].toString()));
                colonia.setNombreMunicipio(obj[3] == null ? "" : obj[3].toString());
                colonia.setIdEntidad(Integer.parseInt(obj[4] == null ? "":obj[4].toString()));
                colonia.setNombreEntidad(obj[5] == null ? "" : obj[5].toString());
                lista_codigos.add(colonia);
               
            }
            return lista_codigos;
        }catch(Exception ex)
            {
                
                Logger.getLogger(ServiceCodigoPostal.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
        
        
    }
    
}
