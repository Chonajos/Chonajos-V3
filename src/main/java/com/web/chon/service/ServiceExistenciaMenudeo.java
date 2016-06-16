/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaMenudeo;
import com.web.chon.negocio.NegocioExistenciaMenudeo;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceExistenciaMenudeo implements IfaceExistenciaMenudeo {

    NegocioExistenciaMenudeo ejb;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioExistenciaMenudeo) Utilidades.getEJBRemote("ejbExistenciaMenudeo", NegocioExistenciaMenudeo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceExistenciaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertaExistenciaMenudeo(ExistenciaMenudeo em) {
        getEjb();
        return ejb.insertaExistenciaMenudeo(em);
    }

    @Override
    public int updateExistenciaMenudeo(ExistenciaMenudeo em) {
        getEjb();
        return ejb.updateExistenciaMenudeo(em);
    }

    @Override
    public ArrayList<ExistenciaMenudeo> getExistenciasMenudeoByIdSucursal(BigDecimal idSucursal) {

        getEjb();

        ArrayList<ExistenciaMenudeo> lstExistenciaMenudeo = new ArrayList<ExistenciaMenudeo>();

        List<Object[]> lstObject = ejb.getExistenciasMenudeoByIdSucursal(idSucursal);

        for (Object[] obj : lstObject) {

            ExistenciaMenudeo data = new ExistenciaMenudeo();

            data.setIdExMenPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            data.setIdSubProductoPk(obj[1] == null ? null : obj[1].toString());
            data.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            data.setKilos(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            data.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            data.setIdTipoEmpaqueFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            data.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            data.setNombreProducto(obj[7] == null ? null : obj[7].toString());
            data.setNombreEmpaque(obj[8] == null ? null : obj[8].toString());

            lstExistenciaMenudeo.add(data);
        }
        return lstExistenciaMenudeo;
    }

    @Override
    public ExistenciaMenudeo getExistenciasMenudeoById(BigDecimal id) {
        getEjb();

        List<Object[]> lstObject = ejb.getExistenciasMenudeoById(id);
        ExistenciaMenudeo data = new ExistenciaMenudeo();
        for (Object[] obj : lstObject) {

            data.setIdExMenPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            data.setIdSubProductoPk(obj[1] == null ? null : obj[1].toString());
            data.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            data.setKilos(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            data.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            data.setIdTipoEmpaqueFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            data.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            data.setNombreProducto(obj[7] == null ? null : obj[7].toString());
            data.setNombreEmpaque(obj[8] == null ? null : obj[8].toString());

        }
        return data;
    }

    @Override
    public ArrayList<ExistenciaMenudeo> getExistenciasMenudeo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExistenciaMenudeo getExistenciasRepetidasById(String ID_SUBPRODUCTO_FK, BigDecimal ID_SUCURSAL_FK, BigDecimal IDTIPOEMPAQUEFK) {
        getEjb();
        List<Object[]> lstObject = ejb.getExistenciasRepetidasById(ID_SUBPRODUCTO_FK, ID_SUCURSAL_FK, IDTIPOEMPAQUEFK);
        ExistenciaMenudeo data = new ExistenciaMenudeo();
        for (Object[] obj : lstObject) {

            data.setIdExMenPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            data.setIdSubProductoPk(obj[1] == null ? null : obj[1].toString());
            data.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            data.setKilos(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            data.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            data.setIdTipoEmpaqueFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            data.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));

        }
        return data;
    }

    @Override
    public int getNexVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceExistenciaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<ExistenciaMenudeo> getExistenciasMenudeoByIdSucursalAndIdSubproducto(BigDecimal idSucursal, String idSubProducto) {

        getEjb();

        ArrayList<ExistenciaMenudeo> lstExistenciaMenudeo = new ArrayList<ExistenciaMenudeo>();

        List<Object[]> lstObject = ejb.getExistenciasMenudeoByIdSucursalAndIdSubproducto(idSucursal, idSubProducto);

        for (Object[] obj : lstObject) {

            ExistenciaMenudeo data = new ExistenciaMenudeo();

            data.setIdExMenPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            data.setIdSubProductoPk(obj[1] == null ? null : obj[1].toString());
            data.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            data.setKilos(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            data.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            data.setIdTipoEmpaqueFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            data.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            data.setNombreProducto(obj[7] == null ? null : obj[7].toString());
            data.setNombreEmpaque(obj[8] == null ? null : obj[8].toString());

            lstExistenciaMenudeo.add(data);
        }
        return lstExistenciaMenudeo;
    }

}
