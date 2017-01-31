/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.TipoOperacion;
import com.web.chon.negocio.NegocioTiposOperacion;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceTipoOperacion implements IfaceTiposOperacion {

    NegocioTiposOperacion ejb;
     private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTiposOperacion) Utilidades.getEJBRemote("ejbTipoOperacion", NegocioTiposOperacion.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceTipoOperacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public ArrayList<TipoOperacion> getOperaciones() {
       getEjb();
        ArrayList<TipoOperacion> lstOperaciones= new ArrayList<TipoOperacion>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getOperaciones();
        for (Object[] object : lstObject) {
            TipoOperacion c = new TipoOperacion();
            c.setIdTipoOperacionPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
           c.setNombre(object[1] == null ? null : object[1].toString());
            c.setDescripcion(object[2] == null ? null : object[2].toString());
            lstOperaciones.add(c);
        }
        return lstOperaciones;
    }

    @Override
    public ArrayList<TipoOperacion> getOperacionesByIdCategoria(BigDecimal id) {
        getEjb();
        ArrayList<TipoOperacion> lstOperaciones= new ArrayList<TipoOperacion>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getOperacionesByIdCategoria(id);
        for (Object[] object : lstObject) {
            TipoOperacion c = new TipoOperacion();
            c.setIdTipoOperacionPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            c.setNombre(object[1] == null ? null : object[1].toString());
            c.setDescripcion(object[2] == null ? null : object[2].toString());
            lstOperaciones.add(c);
        }
        return lstOperaciones;
    }
    
}
