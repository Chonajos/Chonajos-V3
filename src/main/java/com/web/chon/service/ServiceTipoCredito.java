
package com.web.chon.service;

import com.web.chon.dominio.TipoCredito;
import com.web.chon.negocio.NegocioTipoCredito;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan de la Cruz
 */
public class ServiceTipoCredito implements IfaceTipoCredito {
    
   NegocioTipoCredito ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioTipoCredito) Utilidades.getEJBRemote("ejbTipoCredito", NegocioTipoCredito.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceTipoCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<TipoCredito> getAll() {
        getEjb();
        ArrayList<TipoCredito> lstTipoCredito = new ArrayList<TipoCredito>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            TipoCredito tipoCredito = new TipoCredito();

            tipoCredito.setIdTipoCredito(object[0] == null ? null : new BigDecimal(object[0].toString()));
            tipoCredito.setValorCredito(object[1] == null ? null : new BigDecimal(object[1].toString()));
            tipoCredito.setDescripcion(object[2] == null ? null : new BigDecimal(object[2].toString()));
            
            lstTipoCredito.add(tipoCredito);
        }

        return lstTipoCredito;
    }

    @Override
    public TipoCredito getById(BigDecimal idTipoCredito) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        TipoCredito tipoCredito = new TipoCredito();
        for (Object[] object : lstObject) {
            
            tipoCredito.setIdTipoCredito(object[0] == null ? null : new BigDecimal(object[0].toString()));
            tipoCredito.setValorCredito(object[1] == null ? null : new BigDecimal(object[1].toString()));
            tipoCredito.setDescripcion(object[2] == null ? null : new BigDecimal(object[2].toString()));
            
        }

        return tipoCredito;
    }

    @Override
    public int delete(BigDecimal idTipoCredito) {
        getEjb();
        return ejb.delete(idTipoCredito);
    }

    @Override
    public int update(TipoCredito tipoCredito) {
        getEjb();
        return ejb.update(tipoCredito);
    }

    @Override
    public int insert(TipoCredito tipoCredito) {
        getEjb();
        return ejb.insert(tipoCredito);
    }
    
    
}
