package com.web.chon.service;

import com.web.chon.dominio.GestionCredito;
import com.web.chon.dominio.ResultadoGestion;
import com.web.chon.negocio.NegocioResultadoGestion;
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
public class ServiceResultadoGestion implements IfaceResultadoGestion {

    NegocioResultadoGestion ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioResultadoGestion) Utilidades.getEJBRemote("ejbResultadoGestion", NegocioResultadoGestion.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceResultadoGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int update(ResultadoGestion resultadoGestion) {
        getEjb();
        return ejb.update(resultadoGestion);
    }

    @Override
    public int insert(ResultadoGestion resultadoGestion) {
        getEjb();
        return ejb.insert(resultadoGestion);
    }

    @Override
    public int delete(BigDecimal idResultadoGestion) {
        getEjb();
        return ejb.delete(idResultadoGestion);
    }

    @Override
    public ArrayList<ResultadoGestion> getAll() {
        getEjb();
        ArrayList<ResultadoGestion> lstResultadoGestion = new ArrayList<ResultadoGestion>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            ResultadoGestion resultadoGestion = new ResultadoGestion();

            resultadoGestion.setIdResultadoGestion(object[0] == null ? null : new BigDecimal(object[0].toString()));
            resultadoGestion.setDescripcion(object[1] == null ? null : object[1].toString());

            lstResultadoGestion.add(resultadoGestion);
        }

        return lstResultadoGestion;

    }

//    @Override
//    public ResultadoGestion getById(BigDecimal idResultadoGestion) {
//        getEjb();
//        ResultadoGestion resultadoGestion = new ResultadoGestion();
//        List<Object[]> lstObject = new ArrayList<Object[]>();
//        lstObject = ejb.get();
//        for (Object[] object : lstObject) {
//
//            resultadoGestion.setIdResultadoGestion(object[0] == null ? null : new BigDecimal(object[0].toString()));
//            resultadoGestion.setDescripcion(object[1] == null ? null : object[1].toString());
//
//        }
//
//        return resultadoGestion;
//    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceResultadoGestion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public ResultadoGestion getById(BigDecimal idResultadoGestion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

}
