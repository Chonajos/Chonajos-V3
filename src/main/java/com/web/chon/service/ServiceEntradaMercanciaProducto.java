/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.Pagina;
import com.web.chon.negocio.NegocioEntradaMercanciaProducto;
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
 * @author marcogante
 */
@Service
public class ServiceEntradaMercanciaProducto implements IfaceEntradaMercanciaProducto {

    NegocioEntradaMercanciaProducto ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMercanciaProducto) Utilidades.getEJBRemote("ejbEntradaMercanciaProducto", NegocioEntradaMercanciaProducto.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercanciaProducto producto) {

        getEjb();
        return ejb.insertEntradaMercanciaProducto(producto);
    }

    @Override
    public Pagina<EntradaMercanciaProducto> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercanciaProducto> findAllDominio(EntradaMercanciaProducto filters, int first, int pageSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercanciaProducto getById(BigDecimal dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercanciaProducto getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(EntradaMercanciaProducto dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(EntradaMercanciaProducto dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EntradaMercanciaProducto> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {

        getEjb();
        return ejb.getNextVal();

    }

    @Override
    public ArrayList<EntradaMercanciaProducto> getEntradaProductoByIdEM(BigDecimal idEntradaMercancia) {

        getEjb();

        ArrayList<EntradaMercanciaProducto> lstEntradaMercanciaProducto = new ArrayList<EntradaMercanciaProducto>();
        List<Object[]> lstObject = new ArrayList<Object[]>();

        lstObject = ejb.getEntradaProductoByIdEM(idEntradaMercancia);

        for (Object[] obj : lstObject) {

            EntradaMercanciaProducto dominio = new EntradaMercanciaProducto();

            dominio.setIdEmpPK(new BigDecimal(obj[0].toString()));
            dominio.setIdEmFK(new BigDecimal(obj[1].toString()));
            dominio.setIdSubProductoFK(obj[2] == null ? null : obj[2].toString());
            dominio.setIdTipoEmpaqueFK(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setKilosTotalesProducto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setCantidadPaquetes(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setComentarios(obj[6] == null ? "" : obj[6].toString());
            dominio.setIdTipoConvenio(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setIdBodegaFK(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setNombreProducto(obj[9] == null ? "" : obj[9].toString());
            dominio.setNombreEmpaque(obj[10] == null ? "" : obj[10].toString());
            dominio.setNombreBodega(obj[11] == null ? "" : obj[11].toString());
            dominio.setNombreTipoConvenio(obj[12] == null ? "" : obj[12].toString());

            lstEntradaMercanciaProducto.add(dominio);

        }

        return lstEntradaMercanciaProducto;

    }

}
