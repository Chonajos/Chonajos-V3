/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import com.web.chon.negocio.NegocioEntradaMerProPaquete;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceEntraMercPaquete implements IfaceEntMerProPaq {

    NegocioEntradaMerProPaquete ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMerProPaquete) Utilidades.getEJBRemote("ejbEntradaMerProPaquete", NegocioEntradaMerProPaquete.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntraMercPaquete.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertPaquete(EntradaMercanciaProductoPaquete paquete) {
        getEjb();
        return ejb.insertPaquete(paquete);
    }

    @Override
    public ArrayList<EntradaMercanciaProductoPaquete> getPaquetesByIdEmp(BigDecimal idEmpPk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMercanciaProductoPaquete> paquetes = new ArrayList<EntradaMercanciaProductoPaquete>();
        lstObject = ejb.getPaquetesById(idEmpPk);
        BigDecimal count= new BigDecimal(0);
        for (Object[] obj : lstObject) 
        {
            EntradaMercanciaProductoPaquete paquete = new EntradaMercanciaProductoPaquete();
            paquete.setIdEmPP(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            paquete.setKilos(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            paquete.setPaquetes(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            paquete.setTara(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            paquete.setPesoNeto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            paquete.setIdEmpFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            paquete.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            count = count.add(new BigDecimal(1), MathContext.UNLIMITED);
            paquete.setFolio(count);
            paquetes.add(paquete);
            System.out.println("Paquete: "+paquete);
        }
        return paquetes;

    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public int eliminarPaquete(BigDecimal id) {
       getEjb();
        return ejb.eliminarPaquete(id);
    }

    @Override
    public int updatePaquete(BigDecimal idEmpFk) {
        getEjb();
        return ejb.updatePaquete(idEmpFk);
    }
    
}
