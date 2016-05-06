/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;



import com.web.chon.dominio.TipoVenta;
import com.web.chon.negocio.NegocioTipoVenta;
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
public class ServiceTipoVenta implements IfaceTipoVenta{
    
    
    NegocioTipoVenta ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTipoVenta) Utilidades.getEJBRemote("ejbTipoVenta", NegocioTipoVenta.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceTipoVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<TipoVenta> getAll() {
       
        try {
            ArrayList<TipoVenta> lstTipos = new ArrayList<TipoVenta>();
            getEjb();

            List<Object[]> lstObject = ejb.getAll();

            for (Object[] obj : lstObject) 
            {
                TipoVenta tipo = new TipoVenta();
                tipo.setIdTipoVentaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                tipo.setNombreTipoVenta(obj[1] == null ? "" : obj[1].toString());
                lstTipos.add(tipo);
            }
            return lstTipos;
        } catch (Exception ex) {
            Logger.getLogger(TipoVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
