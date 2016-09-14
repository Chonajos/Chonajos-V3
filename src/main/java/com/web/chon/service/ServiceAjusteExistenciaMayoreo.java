
package com.web.chon.service;

import com.web.chon.dominio.AjusteExistenciaMayoreo;
import com.web.chon.negocio.NegocioAjusteExistenciaMayoreo;
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
public class ServiceAjusteExistenciaMayoreo implements IfaceAjusteExistenciaMayoreo {

    NegocioAjusteExistenciaMayoreo ejb;

    private void getEjb() {
        try {

            if (ejb == null) {
                ejb = (NegocioAjusteExistenciaMayoreo) Utilidades.getEJBRemote("ejbAjusteExistenciaMayoreo", NegocioAjusteExistenciaMayoreo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceAjusteExistenciaMayoreo.class.getName()).log(Logger.Level.INFO, "Error al Obtener el ejb", ex);
        }
    }

    @Override
    public int insert(AjusteExistenciaMayoreo data) {
        getEjb();

        return ejb.insert(data);
    }

    @Override
    public int update(AjusteExistenciaMayoreo data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AjusteExistenciaMayoreo> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AjusteExistenciaMayoreo> getAllByIdSucursal(BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
