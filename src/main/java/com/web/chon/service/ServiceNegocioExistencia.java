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
        return ejb.insertaExistencia(ep);

    }

    @Override
    public int updateCantidadKilo(ExistenciaProducto ep) {
        getEjb();
        return ejb.updateCantidadKilo(ep);

    }

    @Override
    public ArrayList<ExistenciaProducto> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor, String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio, BigDecimal idEmpPK,BigDecimal carro) {
        getEjb();

        try {
            ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            List<Object[]> lstObject = ejb.getExistencias(idSucursal, idBodega, idProvedor, idProducto, idEmpaque, idConvenio, idEmpPK,carro);
            
            for (Object[] obj : lstObject) {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdentificador(obj[1] == null ? "" : obj[1].toString());
                expro.setNombreProducto(obj[2] == null ? "" : obj[2].toString().toUpperCase().trim());
                expro.setNombreEmpaque(obj[3] == null ? "" : obj[3].toString().toUpperCase().trim());
                expro.setCantidadPaquetes(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setKilosTotalesProducto(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                expro.setNombreTipoConvenio(obj[6] == null ? "" : obj[6].toString());
                expro.setNombreProvedorCompleto(obj[7] == null ? "" : obj[7].toString());
                expro.setNombreSucursal(obj[8] == null ? "" : obj[8].toString());
                expro.setNombreBodega(obj[9] == null ? "" : obj[9].toString().toUpperCase().trim());
                expro.setPrecioMinimo(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                expro.setPrecioVenta(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                expro.setPrecioMaximo(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                expro.setEstatusBloqueo(obj[13] == null ? false : (obj[13].toString().equals("0")));
                expro.setIdSubProductoFK(obj[14] == null ? "" : obj[14].toString());
                expro.setIdTipoEmpaqueFK(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
                expro.setIdBodegaFK(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
                expro.setConvenio(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
                expro.setCarroSucursal(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
                expro.setPrecioSinIteres(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                expro.setIdEntradaMercanciaProductoFK(obj[19] == null ? null : new BigDecimal(obj[19].toString()));
                expro.setComentarios(obj[20] == null ? null : obj[20].toString());
                expro.setIdTipoConvenio(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                expro.setKilospromprod(obj[22] == null ? null : new BigDecimal(obj[22].toString()));
                expro.setIdSucursal(obj[23] == null ? null : new BigDecimal(obj[23].toString()));

                lista.add(expro);
            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciaProductoRepetidos(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idTipoConvenio,BigDecimal idEMProducto) {
        
        getEjb();

        try {
            ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            
            List<Object[]> lstObject = ejb.getExistenciasRepetidas(idSucursal, idSubproductoFk, idTipoEmpaqueFk, idBodegaFk, idProvedorFk, idTipoConvenio,idEMProducto);
            for (Object[] obj : lstObject) {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdSubProductoFK(obj[1] == null ? "" : obj[1].toString());
                expro.setIdTipoEmpaqueFK(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                expro.setKilosTotalesProducto(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                expro.setCantidadPaquetes(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setComentarios(obj[5] == null ? "" : obj[5].toString());
                expro.setIdBodegaFK(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                expro.setIdTipoConvenio(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                expro.setConvenio(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                expro.setKilospromprod(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                expro.setPrecioMinimo(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                expro.setPrecioVenta(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                expro.setPrecioMaximo(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                expro.setEstatusBloqueo(obj[13] == null ? false : (new BigDecimal(obj[13].toString()).equals(new BigDecimal(0)) ? false : true));
                expro.setIdSucursal(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
                expro.setIdEntradaMercanciaProductoFK(obj[15] == null ? null : new BigDecimal(obj[15].toString()));

                lista.add(expro);
            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciasbyIdSubProducto(String idSubproductoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updatePrecio(ExistenciaProducto ep) {
        getEjb();
        return ejb.updatePrecio(ep);
    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciaById(BigDecimal idExistencia) {
        getEjb();

        try {
            ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            List<Object[]> lstObject = ejb.getExistenciaById(idExistencia);
            for (Object[] obj : lstObject) {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setKilosTotalesProducto(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                expro.setCantidadPaquetes(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setIdBodegaFK(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                lista.add(expro);
            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciasCancelar(BigDecimal idExistencia) {
        getEjb();

        try {
            ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            List<Object[]> lstObject = ejb.getExistenciasCancelar(idExistencia);
            for (Object[] obj : lstObject) {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setCantidadPaquetes(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                expro.setKilosTotalesProducto(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                lista.add(expro);
            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteExistenciaProducto(ExistenciaProducto ep) {
        getEjb();
        return ejb.deleteExistenciaProducto(ep);
    }

    @Override
    public ExistenciaProducto getExistenciaByIdEmpFk(BigDecimal idEmpFk) {
        getEjb();
        try {
            getEjb();
            List<Object[]> lstObject = new ArrayList<Object[]>();
            lstObject = ejb.getExistenciaByIdEmpFk(idEmpFk);
            ExistenciaProducto existencia = new ExistenciaProducto();
            for (Object[] object : lstObject) {
                existencia.setIdExistenciaProductoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
                existencia.setIdSubProductoFK(object[1] == null ? null : object[1].toString());
                existencia.setIdTipoEmpaqueFK(object[2] == null ? null : new BigDecimal(object[2].toString()));
                existencia.setKilosTotalesProducto(object[3] == null ? null : new BigDecimal(object[3].toString()));
                existencia.setCantidadPaquetes(object[4] == null ? null : new BigDecimal(object[4].toString()));
                existencia.setComentarios(object[5] == null ? "" : object[5].toString());
                existencia.setIdBodegaFK(object[6] == null ? null : new BigDecimal(object[6].toString()));
                existencia.setIdTipoConvenio(object[7] == null ? null : new BigDecimal(object[7].toString()));
                existencia.setConvenio(object[8] == null ? null : new BigDecimal(object[8].toString()));
                existencia.setKilospromprod(object[9] == null ? null : new BigDecimal(object[9].toString()));
                existencia.setPrecioMinimo(object[10] == null ? null : new BigDecimal(object[10].toString()));
                existencia.setPrecioVenta(object[11] == null ? null : new BigDecimal(object[11].toString()));
                existencia.setPrecioMaximo(object[12] == null ? null : new BigDecimal(object[12].toString()));
                //existencia.setEstatusBloqueo(object[13] == null ? null : object[12].toString());
                existencia.setIdSucursal(object[14] == null ? null : new BigDecimal(object[14].toString()));
                existencia.setIdEntradaMercanciaProductoFK(object[15] == null ? null : new BigDecimal(object[15].toString()));
            }
            return existencia;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int update(ExistenciaProducto ep) {
        getEjb();
        return ejb.update(ep);

    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<ExistenciaProducto> getExistenciaByBarCode(String idSubProducto, BigDecimal idTipoEmpaqueFk, BigDecimal idTipoConvenioFk, BigDecimal idCarro, BigDecimal idSucursalFk) {
       
        getEjb();
        try {
            ArrayList<ExistenciaProducto> lista = new ArrayList<ExistenciaProducto>();
            //System.out.println("SerivceNegocioExistencia: getExistencias : "+idSucursal+ " idProvedorFk: "+idProvedorFk);
            List<Object[]> lstObject = ejb.getExistenciaByBarCode(idSubProducto, idTipoEmpaqueFk,  idTipoConvenioFk,  idCarro, idSucursalFk);
            for (Object[] obj : lstObject) {
                ExistenciaProducto expro = new ExistenciaProducto();
                expro.setIdExistenciaProductoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                expro.setIdentificador(obj[1] == null ? "" : obj[1].toString());
                expro.setNombreProducto(obj[2] == null ? "" : obj[2].toString().toUpperCase().trim());
                expro.setNombreEmpaque(obj[3] == null ? "" : obj[3].toString().toUpperCase().trim());
                expro.setCantidadPaquetes(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                expro.setKilosTotalesProducto(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                expro.setNombreTipoConvenio(obj[6] == null ? "" : obj[6].toString().toUpperCase().trim());
                expro.setNombreProvedorCompleto(obj[7] == null ? "" : obj[7].toString());
                expro.setNombreSucursal(obj[8] == null ? "" : obj[8].toString());
                expro.setNombreBodega(obj[9] == null ? "" : obj[9].toString().toUpperCase().trim());
                expro.setPrecioMinimo(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                expro.setPrecioVenta(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                expro.setPrecioMaximo(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                expro.setEstatusBloqueo(obj[13] == null ? false : (obj[13].toString().equals("0")));
                expro.setIdSubProductoFK(obj[14] == null ? "" : obj[14].toString());
                expro.setIdTipoEmpaqueFK(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
                expro.setIdBodegaFK(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
                expro.setConvenio(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
                expro.setCarroSucursal(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
                expro.setPrecioSinIteres(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                expro.setIdEntradaMercanciaProductoFK(obj[19] == null ? null : new BigDecimal(obj[19].toString()));
                expro.setComentarios(obj[20] == null ? null : obj[20].toString());
                expro.setIdTipoConvenio(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                expro.setKilospromprod(obj[22] == null ? null : new BigDecimal(obj[22].toString()));
                expro.setIdSucursal(obj[23] == null ? null : new BigDecimal(obj[23].toString()));

                lista.add(expro);
            }
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
        
    }
}
