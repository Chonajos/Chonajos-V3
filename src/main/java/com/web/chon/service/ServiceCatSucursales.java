/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Sucursal;
import com.web.chon.negocio.NegocioCatSucursales;
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
public class ServiceCatSucursales implements IfaceCatSucursales
{
    NegocioCatSucursales ejb;

    @Override
    public ArrayList<Sucursal> getSucursales() 
    {
       try
       {
            ArrayList <Sucursal> lista_sucursales = new ArrayList<Sucursal>();
            ejb = (NegocioCatSucursales) Utilidades.getEJBRemote("ejbCatSucursales", NegocioCatSucursales.class.getName());
            List<Object[]> lstObject = ejb.getSucursales();
             
            for(Object[] obj: lstObject )
            {
                Sucursal s = new Sucursal();
                s.setIdSucursalPk(Integer.parseInt(obj[0].toString()));
                s.setNombreSucursal((obj[1] == null ? "" :obj[1].toString()));
                lista_sucursales.add(s);

            }
            return lista_sucursales;
        }catch(Exception ex)
            {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
    }

    @Override
    public List<Object[]> getSucursalId(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteSucursal(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateSucursal(Sucursal sucu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertSucursal(Sucursal sucu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
