package com.web.chon.service;

import com.web.chon.dominio.AcionGestion;
import com.web.chon.negocio.NegocioAcionGestion;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceAcionGestion implements IfaceAcionGestion {

    NegocioAcionGestion ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioAcionGestion) Utilidades.getEJBRemote("ejbAcionGestion", NegocioAcionGestion.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int update(AcionGestion acionGestion) {
        getEjb();
        return ejb.update(acionGestion);
    }

    @Override
    public int insert(AcionGestion acionGestion) {
        getEjb();
        return ejb.insert(acionGestion);
    }

    @Override
    public int delete(BigDecimal idAcionGestion) {
        getEjb();
        return ejb.delete(idAcionGestion);
    }

    @Override
    public ArrayList<AcionGestion> getAll() {
        getEjb();
        ArrayList<AcionGestion> lstAcionGestion = new ArrayList<AcionGestion>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            AcionGestion acionGestion = new AcionGestion();

            acionGestion.setIdAcionGestion(object[0] == null ? null : new BigDecimal(object[0].toString()));
            acionGestion.setIdResultadoGestion(object[1] == null ? null : new BigDecimal(object[1].toString()));
            acionGestion.setDescripcion(object[2] == null ? null : object[2].toString());

            lstAcionGestion.add(acionGestion);
        }

        return lstAcionGestion;

    }

    @Override
    public AcionGestion getById(BigDecimal idAcionGestion) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getById(idAcionGestion);
        AcionGestion acionGestion = new AcionGestion();
        for (Object[] object : lstObject) {

            acionGestion.setIdAcionGestion(object[0] == null ? null : new BigDecimal(object[0].toString()));
            acionGestion.setIdResultadoGestion(object[1] == null ? null : new BigDecimal(object[1].toString()));
            acionGestion.setDescripcion(object[2] == null ? null : object[2].toString());
        }

        return acionGestion;
    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public ArrayList<AcionGestion> getByIdResultadoGestion(BigDecimal idResultadoGestion) {

        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getByIdResultadoGestion(idResultadoGestion);
        ArrayList<AcionGestion> lstAcionGestion = new ArrayList<AcionGestion>();
        for (Object[] object : lstObject) {
            
            AcionGestion acionGestion = new AcionGestion();
            acionGestion.setIdAcionGestion(object[0] == null ? null : new BigDecimal(object[0].toString()));
            acionGestion.setIdResultadoGestion(object[1] == null ? null : new BigDecimal(object[1].toString()));
            acionGestion.setDescripcion(object[2] == null ? null : object[2].toString());

            lstAcionGestion.add(acionGestion);
        }

        return lstAcionGestion;
    }

}
