/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.TipoOrdenCompra;
import com.web.chon.negocio.NegocioTipoOrdenCompra;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceTipoOrdenCompra implements IfaceTipoOrdenCompra {

    NegocioTipoOrdenCompra ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTipoOrdenCompra) Utilidades.getEJBRemote("ejbTipoOrdenCompra", NegocioTipoOrdenCompra.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<TipoOrdenCompra> getTipos() {
        try {
            ArrayList<TipoOrdenCompra> lstTipoOrden = new ArrayList<TipoOrdenCompra>();
            getEjb();

            List<Object[]> lstObject = ejb.getTipos();

            for (Object[] obj : lstObject) 
            {
                TipoOrdenCompra tipoOrden = new TipoOrdenCompra();
                tipoOrden.setIdTocPK(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                tipoOrden.setNombreTipoOrdenCompra(obj[1] == null ? "" : obj[1].toString());
                tipoOrden.setDescripcionTipoOrden(obj[2] == null ? "" : obj[2].toString());
                lstTipoOrden.add(tipoOrden);
            }
            return lstTipoOrden;
        } catch (Exception ex) {
            Logger.getLogger(TipoOrdenCompra.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
