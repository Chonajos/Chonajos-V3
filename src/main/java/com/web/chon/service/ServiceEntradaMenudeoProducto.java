/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.negocio.NegocioMenudeoProducto;
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
public class ServiceEntradaMenudeoProducto implements IfaceEntradaMenudeoProducto{
    NegocioMenudeoProducto ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioMenudeoProducto) Utilidades.getEJBRemote("ejbEntradaMenudeoProducto", NegocioMenudeoProducto.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaMenudeoProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercanciaProducto(EntradaMenudeoProducto producto) {
       getEjb();
        return ejb.insertEntradaMenudeoProducto(producto);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<EntradaMenudeoProducto> getEntradaProductoById(BigDecimal id) {
       getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMenudeoProducto> lstEntradaMercancia2 = new ArrayList<EntradaMenudeoProducto>();
        lstObject = ejb.getEntradaMenudeoProductoByIdEM(id);
        for (Object[] obj : lstObject) {
            EntradaMenudeoProducto dominio = new EntradaMenudeoProducto();
            dominio.setIdEmmpPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdEmmFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setIdSubproductoFk(obj[2] == null ? "" : obj[2].toString());
            dominio.setIdtipoEmpaqueFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setKilosTotales(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setCantidadEmpaque(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setComentarios(obj[6] == null ? "" : obj[6].toString());
            dominio.setNombreProducto(obj[7] == null ? "" : obj[7].toString());
            dominio.setNombreEmpaque(obj[8] == null ? "" : obj[8].toString());
            dominio.setPorcentarjeMerma(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setPrecio(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            lstEntradaMercancia2.add(dominio);
        }
        return lstEntradaMercancia2;
    }
}
