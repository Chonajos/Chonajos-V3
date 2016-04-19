/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.negocio.NegocioExistenciaProducto;
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
public class ServiceNegocioExistencia implements IfaceNegocioExistencia {

    NegocioExistenciaProducto ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioExistenciaProducto) Utilidades.getEJBRemote("ejbExistenciaProducto", NegocioExistenciaProducto.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(NegocioExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertExistenciaProducto(ExistenciaProducto ep) {
        getEjb();
        return  ejb.insertaExistencia(ep);
      
        
    }

    
    @Override
    public int updateExistenciaProducto(ExistenciaProducto ep) {
       getEjb();
       return ejb.updateExistenciaProducto(ep);
    
    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciaProductoId(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk) {
    
        getEjb();
        try{
        ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
         List<Object[]> lstObject = ejb.getExistenciaProductoId(idSucursal, idSubproductoFk, idTipoEmpaqueFk, idBodegaFk);
         for(Object[] obj: lstObject )
            {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdSucursalFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                expro.setIdSubProductoFk(obj[2] == null ? "" : obj[2].toString());
                expro.setKilosExistencia(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                expro.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setIdTipoEmpaque(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                expro.setKilosEmpaque(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                expro.setIdBodegaFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                expro.setIdProvedorFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                lista.add(expro);
            }
         return lista;
        }catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

}
