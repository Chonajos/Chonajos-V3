/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.StatusVenta;

import com.web.chon.negocio.NegocioCatStatusVenta;

import com.web.chon.util.Utilidades;
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
public class ServiceCatStatusVenta implements IfaceCatStatusVenta{
    NegocioCatStatusVenta ejb;

    @Override
    public ArrayList<StatusVenta> getStatusVentas() 
    {
       try
       {
            ArrayList <StatusVenta> lista_sucursales = new ArrayList<StatusVenta>();
            ejb = (NegocioCatStatusVenta) Utilidades.getEJBRemote("ejbCatStatusVenta", NegocioCatStatusVenta.class.getName());
            List<Object[]> lstObject = ejb.getStatusVentas();
            for(Object[] obj: lstObject )
            {
                StatusVenta st = new StatusVenta();         
                st.setId_status_pk(Integer.parseInt(obj[0].toString()));
                st.setStatus(obj[1] == null ? "" :obj[1].toString());
                lista_sucursales.add(st);
            }
            return lista_sucursales;
        }catch(Exception ex)
            {
                Logger.getLogger(ServiceCatStatusVenta.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
    }

    @Override
    public List<Object[]> getStatusVentaId(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteStatusVenta(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateStatusVenta(StatusVenta st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertStatusVenta(StatusVenta st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
