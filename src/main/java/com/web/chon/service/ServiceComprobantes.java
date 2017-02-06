/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.negocio.NegocioComprobantes;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.sql.SQLException;
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
public class ServiceComprobantes implements IfaceComprobantes{
    
    NegocioComprobantes ejb;
    
    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioComprobantes) Utilidades.getEJBRemote("ejbComprobantesDigitales", NegocioComprobantes.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceComprobantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getNextVal() 
    {
        getEjb();
        return ejb.getNextVal();
        
    }

    @Override
    public int insertaComprobante(ComprobantesDigitales cd) {
        getEjb();
        return ejb.insertaComprobante(cd);
       
    }

    @Override
    public int updateComprobante(ComprobantesDigitales cd) {
        getEjb();
        return ejb.updateComprobante(cd);
    }

    @Override
    public int deleteComprobante(ComprobantesDigitales cd) {
       getEjb();
       return ejb.deleteComprobante(cd);
    }

    @Override
    public ArrayList<ComprobantesDigitales> getComprobanteByIdTipoLlave(BigDecimal idTipoFk, BigDecimal idLlave) {
       getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<ComprobantesDigitales> lstComprobantes = new ArrayList<ComprobantesDigitales>();
        lstObject = ejb.getComprobanteByIdTipoLlave(idTipoFk, idLlave);
        for (Object[] obj : lstObject) {
            ComprobantesDigitales dominio = new ComprobantesDigitales();
            dominio.setIdComprobantesDigitalesPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));;
            dominio.setIdTipoFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));;
            dominio.setIdLlaveFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));;
            dominio.setNombre(obj[3] == null ? "" : obj[3].toString());
            dominio.setFichero((obj[4] == null ? null : (byte[]) (obj[4])));

            lstComprobantes.add(dominio);
        }
        return lstComprobantes;
    
    }

    @Override
    public int insertarImagen(BigDecimal id, byte[] fichero) throws SQLException 
    {
        getEjb();
        return ejb.insertarImagen(id, fichero);
    }
}
