/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.Municipios;
import com.web.chon.negocio.NegocioCatMunicipio;
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
public class ServiceCatMunicipio implements IfaceCatMunicipio
{
    NegocioCatMunicipio ejb;

    @Override
    public ArrayList<Municipios> getMunicipios(int idMunicipio)
    {
        try
       {
            ArrayList <Municipios> lista_municipios= new ArrayList<Municipios>();
            
            ejb = (NegocioCatMunicipio) Utilidades.getEJBRemote("ejbCatMunicipio", NegocioCatMunicipio.class.getName());
            List<Object[]> lstObject = ejb.getMunicipios(idMunicipio);
             
            for(Object[] obj: lstObject )
            {
                Municipios muni = new Municipios();
                muni.setIdMunicipioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                muni.setNombreMunicipio(obj[1].toString());
              
                lista_municipios.add(muni);
               
            }
            return lista_municipios;
        }catch(Exception ex)
            {
                
                Logger.getLogger(ServiceCatEntidad.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
        
    }

    @Override
    public Municipios getMunicipioById(int idMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteMunicipio(int idMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateMunicipios(Municipios muni) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertMunicipios(Municipios muni) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
