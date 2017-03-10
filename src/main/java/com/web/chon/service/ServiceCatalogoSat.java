/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CatalogoSat;
import com.web.chon.negocio.NegocioCatalogoSat;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceCatalogoSat implements IfaceCatalogoSat {
NegocioCatalogoSat ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatalogoSat) Utilidades.getEJBRemote("ejbCatalogoSat", NegocioCatalogoSat.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatalogoSat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public ArrayList<CatalogoSat> getCatalogo(BigDecimal idTipoFk) {
        try {
            ArrayList<CatalogoSat> lista = new ArrayList<CatalogoSat>();
            getEjb();
            List<Object[]> lstObject = ejb.getCatalogo(idTipoFk);

            for (Object[] obj : lstObject) {
                CatalogoSat c = new CatalogoSat();
                c.setIdCatalogoSatPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                c.setCodigo(obj[1] == null ? null : obj[1].toString());
                c.setDescripcion(obj[2] == null ? null : obj[2].toString());
                c.setTipo(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                c.setValor(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                lista.add(c);

            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatalogoSat.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    
    }
    
}
