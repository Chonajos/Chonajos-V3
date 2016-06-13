/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.negocio.NegocioExistenciaMenudeo;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceExistenciaMenudeo implements IfaceExistenciaMenudeo{

    NegocioExistenciaMenudeo ejb;
    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioExistenciaMenudeo) Utilidades.getEJBRemote("ejbExistenciaMenudeo", NegocioExistenciaMenudeo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceExistenciaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public int insertaExistenciaMenudeo(ExistenciaMenudeo em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateExistenciaMenudeo(ExistenciaMenudeo em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExistenciaMenudeo> getExistenciasMenudeoByIdSucursal(BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExistenciaMenudeo> getExistenciasMenudeoById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExistenciaMenudeo> getExistenciasMenudeo(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
