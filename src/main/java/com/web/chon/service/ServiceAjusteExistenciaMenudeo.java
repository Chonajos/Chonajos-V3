/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.AjusteExistenciaMenudeo;
import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.negocio.NegocioAjusteExistenciaMenudeo;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceAjusteExistenciaMenudeo implements IfaceAjusteExistenciaMenudeo {

    NegocioAjusteExistenciaMenudeo ejb;

    private void getEjb() {
        try {

            if (ejb == null) {
                ejb = (NegocioAjusteExistenciaMenudeo) Utilidades.getEJBRemote("ejbAjusteExistenciaMenudeo", NegocioAjusteExistenciaMenudeo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceAjusteExistenciaMenudeo.class.getName()).log(Logger.Level.INFO, "Error al Obtener el ejb", ex);
        }
    }

    @Override
    public int insert(AjusteExistenciaMenudeo data) {
        getEjb();

        return ejb.insert(data);
    }

    @Override
    public int update(AjusteExistenciaMenudeo data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AjusteExistenciaMenudeo> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AjusteExistenciaMenudeo> getAllByIdSucursal(BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
