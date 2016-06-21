/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.negocio.NegocioEntradaMenudeo;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceEntradaMenudeo implements IfaceEntradaMenudeo{
    NegocioEntradaMenudeo ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMenudeo) Utilidades.getEJBRemote("ejbEntradaMenudeo", NegocioEntradaMenudeo.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMenudeo entrada) {
        getEjb();
        return ejb.insertEntradaMenudeo(entrada);
    }

    @Override
    public int buscaMaxMovimiento(EntradaMenudeo entrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
      getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getFolio(BigDecimal idSucursal) {
       getEjb();
        try {
            return ejb.getFolio(idSucursal);

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public ArrayList<EntradaMenudeo> getEntradaProductoByIntervalDate(Date fechaFiltroInicio, Date fechaFiltroFin, BigDecimal idSucursal) {
        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMenudeo> lstEntradaMercancia2 = new ArrayList<EntradaMenudeo>();
        lstObject = ejb.getEntradaProductoByIntervalDate(fechaFiltroInicio, fechaFiltroFin, idSucursal);
        for (Object[] obj : lstObject) {
            EntradaMenudeo dominio = new EntradaMenudeo();
            dominio.setIdEmmPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setFecha(obj[2] == null ? null : (Date) obj[2]);
            dominio.setIdSucursalFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setIdStatusFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setKilosTotales(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setKilosProvedor(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            dominio.setComentarios(obj[7] == null ? "" : obj[7].toString());
            dominio.setFolio(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setIdUsuario(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setNombreProvedor(obj[10] == null ? "" : obj[10].toString());
            dominio.setApPaternoProvedor(obj[11] == null ? "" : obj[11].toString());
            dominio.setApMaternoProvedor(obj[12] == null ? "" : obj[12].toString());
            
            lstEntradaMercancia2.add(dominio);
        }
        return lstEntradaMercancia2;
    }
}
