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
    public ArrayList<ExistenciaProducto> getExistencias(BigDecimal idSucursal, BigDecimal idProvedorFk) {
        getEjb();
        
        try{
        ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            System.out.println("SerivceNegocioExistencia: getExistencias : "+idSucursal+ " idProvedorFk: "+idProvedorFk);
         List<Object[]> lstObject = ejb.getExistencias(idSucursal, idProvedorFk);
         for(Object[] obj: lstObject )
            {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdEmFK(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                expro.setIdentificador(obj[2] == null ? "" : obj[2].toString());
                expro.setNombreProducto(obj[3] == null ? "" : obj[3].toString());
                expro.setNombreEmpaque(obj[4] == null ? "" : obj[4].toString());
                expro.setNombreBodega(obj[5] == null ? "" : obj[5].toString());
                expro.setCantidadPaquetes(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                expro.setKilosTotalesProducto(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                lista.add(expro);
            }
         return lista;
        }catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

//    @Override
//    public ArrayList<ExistenciaProducto> getExistencias(BigDecimal idSucursal, BigDecimal idProvedorFk) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
    

//    @Override
//    public ArrayList<ExistenciaProducto> getExistenciasbyIdSubProducto(String idSubproductoFk) {
//        getEjb();
//        
//        try{
//        ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
//            System.out.println("SerivceNegocioExistencia: getExistenciasbyIdSubProducto : "+idSubproductoFk+"");
//         List<Object[]> lstObject = ejb.getExistenciasByIdSubProducto(idSubproductoFk);
//         for(Object[] obj: lstObject )
//            {
//                ExistenciaProducto expro = new ExistenciaProducto();
//                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
////                expro.setIdEmFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
////                expro.setIdentificador(obj[2] == null ? "" : obj[2].toString());
////                expro.setNombreSubproducto(obj[3] == null ? "" : obj[3].toString());
////                expro.setNombreEmpaque(obj[4] == null ? "" : obj[4].toString());
////                expro.setNombreBodega(obj[5] == null ? "" : obj[5].toString());
////                expro.setCantidadEmpaque(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
////                expro.setKilosExistencia(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
//                lista.add(expro);
//            }
//         return lista;
//        }catch (Exception ex) {
//            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//
//        }
//    
//    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciaProductoRepetidos(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idEMFK,BigDecimal idTipoConvenio) {
        
        getEjb();
        
        try{
        ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            System.out.println("SerivceNegocioExistencia: getExistenciaProductoRepetidos : "+idSucursal+ " idProvedorFk: "+idProvedorFk);
         List<Object[]> lstObject = ejb.getExistenciasRepetidas(idSucursal, idSubproductoFk,  idTipoEmpaqueFk,  idBodegaFk,  idProvedorFk, idEMFK,idTipoConvenio);
         for(Object[] obj: lstObject )
            {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdEmFK(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                expro.setIdSubProductoFK(obj[2] == null ? "" : obj[2].toString());
                expro.setIdTipoEmpaqueFK(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                expro.setKilosTotalesProducto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setCantidadPaquetes(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                expro.setComentarios(obj[6] == null ? "" : obj[6].toString());
                expro.setIdBodegaFK(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                expro.setIdTipoConvenio(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                expro.setPrecio(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                expro.setKilospromprod(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                
                lista.add(expro);
            }
         return lista;
        }catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    
    
    
    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciasbyIdSubProducto(String idSubproductoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
