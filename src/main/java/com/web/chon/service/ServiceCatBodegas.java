/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.Pagina;
import com.web.chon.negocio.NegocioCatBodega;
import com.web.chon.repository.UsuarioRepository;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Juan
 */
@Service

public class ServiceCatBodegas implements IfaceCatBodegas {

    NegocioCatBodega ejb;
   

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatBodega) Utilidades.getEJBRemote("ejbCatBodegas", NegocioCatBodega.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Bodega> getBodegas() {
        try {
            ArrayList<Bodega> lista_bodegas = new ArrayList<Bodega>();

            getEjb();
            List<Object[]> lstObject = ejb.getBodegas();

            for (Object[] obj : lstObject) {
                Bodega s = new Bodega();
                s.setIdBodegaPK(new BigDecimal(obj[0].toString()));
                s.setNombreBodega((obj[1] == null ? "" : obj[1].toString()));
                s.setDescripcionBodega((obj[2] == null ? "" : obj[2].toString()));
                lista_bodegas.add(s);

            }
            return lista_bodegas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public List<Bodega[]> getBodegaById(int idBodega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<Bodega> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<Bodega> findAllDominio(Bodega filters, int first, int pageSize) {

        try {

            getEjb();
            ArrayList<Bodega> lstBodegas = new ArrayList<Bodega>();
            long size = ejb.countBodegas();

            if (first != 0) {
                first++;
            }
            getEjb();
            List<Object[]> lstObject = ejb.getBodegas();

            for (Object[] obj : lstObject) {
                Bodega bodega = new Bodega();
                bodega.setIdBodegaPK(new BigDecimal(obj[0].toString()));
                bodega.setNombreBodega((obj[1] == null ? "" : obj[1].toString()));
                bodega.setDescripcionBodega((obj[2] == null ? "" : obj[2].toString()));

                lstBodegas.add(bodega);

            }

            return new Pagina<Bodega>(lstBodegas, size);
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public Bodega getById(BigDecimal dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Bodega dominio) {

        getEjb();
        return ejb.insertBodega(dominio);
    }

    @Override
    public int update(Bodega dominio) {
        getEjb();
        return ejb.updateBodega(dominio);
    }

    @Override
    public List<Bodega> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal id) {
        getEjb();
        return ejb.deleteBodega(id.intValue());
    }

    @Override
    public Bodega getById(String dominio) {

        List<Object[]> lstObject = null;
        Bodega bodega = new Bodega();
        getEjb();
        lstObject = ejb.getBodegaById(Integer.parseInt(dominio));

        getEjb();
        for (Object[] obj : lstObject) {

            bodega.setIdBodegaPK(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            bodega.setNombreBodega(obj[1] == null ? "" : obj[1].toString());
            bodega.setDescripcionBodega(obj[2] == null ? "" : obj[2].toString());
        }

        return bodega;

    }

    @Override
    public ArrayList<Bodega> getBodegaByIdSucursal(BigDecimal idSucursal) {
        try {
            ArrayList<Bodega> lista_bodegas = new ArrayList<Bodega>();

            getEjb();
            List<Object[]> lstObject = ejb.getBodegaByIdSucursal(idSucursal);

            for (Object[] obj : lstObject) {
                Bodega s = new Bodega();
                s.setIdBodegaPK(new BigDecimal(obj[0].toString()));
                s.setNombreBodega((obj[1] == null ? "" : obj[1].toString()));
                s.setDescripcionBodega((obj[2] == null ? "" : obj[2].toString()));
                s.setIdSucursalFk(new BigDecimal(obj[3].toString()));
                lista_bodegas.add(s);

            }
            return lista_bodegas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
        
        
        
    }
}
