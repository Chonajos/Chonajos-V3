package com.web.chon.service;

import com.web.chon.dominio.Pagina;
import com.web.chon.dominio.Rol;

import com.web.chon.negocio.NegocioCatRol;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service

public class ServiceCatRol implements IfaceCatRol {

    NegocioCatRol ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatRol) Utilidades.getEJBRemote("ejbCatRol", NegocioCatRol.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatRol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @Override
    public int getNextVal() {
        
        getEjb();
        
        return ejb.getNextVal();
    }

    @Override
    public Pagina<Rol> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<Rol> findAllDominio(Rol filters, int first, int pageSize) {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Rol rol) {

        getEjb();
        return ejb.insert(rol);
    }

    @Override
    public int update(Rol rol) {
        getEjb();
        return ejb.update(rol);
    }

    @Override
    public List<Rol> getAll() {

        try {
            ArrayList<Rol> lstRol = new ArrayList<Rol>();

            getEjb();
            
            List<Object[]> lstObject = ejb.getAll();

            for (Object[] obj : lstObject) {
                Rol rol = new Rol();
                rol.setIdRolPk(new BigDecimal(obj[0].toString()));
                rol.setNombreRol((obj[1] == null ? "" : obj[1].toString()));
                rol.setDescripcionRol((obj[2] == null ? "" : obj[2].toString()));
                lstRol.add(rol);

            }
            return lstRol;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatRol.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public int delete(BigDecimal id) {
        getEjb();
        return ejb.delete(id.intValue());
    }

    @Override
    public Rol getById(BigDecimal idRol) {

        List<Object[]> lstObject = null;
        Rol rol = new Rol();
        getEjb();
        lstObject = ejb.getById(idRol.intValue());

        getEjb();
        for (Object[] obj : lstObject) {

            rol.setIdRolPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            rol.setNombreRol(obj[1] == null ? "" : obj[1].toString());
            rol.setDescripcionRol(obj[2] == null ? "" : obj[2].toString());
        }

        return rol;

    }

    @Override
    public Rol getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
