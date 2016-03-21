/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Entidad;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NegocioCatEntidad;

/**
 *
 * @author freddy
 */
@Service
public class ServiceCatEntidad  implements IfaceCatEntidad  {

    NegocioCatEntidad ejb;
    
    @Override
    public ArrayList<Entidad> getEntidades()       
    {
       try
       {
            ArrayList <Entidad> lista_entidades= new ArrayList<Entidad>();
            ejb = (NegocioCatEntidad) Utilidades.getEJBRemote("ejbCatEntidad", NegocioCatEntidad.class.getName());
            List<Object[]> lstObject = ejb.getEntidades();
             
            for(Object[] obj: lstObject )
            {
                Entidad enti = new Entidad();
                enti.setIdEntidadPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                enti.setNombreEntidad(obj[1].toString());
                enti.setAbreabiacionEntidad(obj[2].toString());
                //System.out.println(enti.getIdEntidadPk()+enti.getNombreEntidad());
                lista_entidades.add(enti);
               
            }
            return lista_entidades;
        }catch(Exception ex)
            {
                
                Logger.getLogger(ServiceCatEntidad.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
    }

    @Override
    public Entidad getEntidadById(int idEntidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteEntidad(int idEntidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateEntidad(Entidad enti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertEntidad(Entidad enti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
