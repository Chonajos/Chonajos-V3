/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import com.web.chon.dominio.Pagina;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioEntradaMercanciaProducto;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercanciaProducto implements IfaceEntradaMercanciaProducto {

    NegocioEntradaMercanciaProducto ejb;
    @Autowired private IfaceCatBodegas ifaceCatBodegas;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private IfaceEntMerProPaq ifaceEntMerProPaq;


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
    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto) {

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
       getEjb();
        return ejb.updateEntradaMercanciaProducto(dominio);
    }

    @Override
    public List<EntradaMercanciaProducto> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal id) {
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
        int numeroMovimiento = 0;
        for (Object[] obj : lstObject) {

            EntradaMercanciaProducto dominio = new EntradaMercanciaProducto();

            numeroMovimiento++;
            
            dominio.setIdEmpPK(new BigDecimal(obj[0].toString()));
            dominio.setIdEmFK(new BigDecimal(obj[1].toString()));
            dominio.setIdSubProductoFK(obj[2] == null ? null : obj[2].toString());
            dominio.setIdTipoEmpaqueFK(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setKilosTotalesProducto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            dominio.setCantidadPaquetes(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setComentarios(obj[6] == null ? "" : obj[6].toString());
            dominio.setIdBodegaFK(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setIdTipoConvenio(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setPrecio(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setEmpaquesProProvedor(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            dominio.setKilosProProvedor(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            dominio.setNombreProducto(obj[13] == null ? "" : obj[13].toString());
            dominio.setNombreEmpaque(obj[14] == null ? "" : obj[14].toString());
            dominio.setNombreBodega(obj[15] == null ? "" : obj[15].toString());
            dominio.setNombreTipoConvenio(obj[16] == null ? "" : obj[16].toString());
            dominio.setIdSucursalFk(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
            dominio.setNumeroMovimiento(numeroMovimiento);
            dominio.setListaBodegas(ifaceCatBodegas.getBodegaByIdSucursal(dominio.getIdSucursalFk()));
            dominio.setSubProducto(ifaceSubProducto.getSubProductoById(dominio.getIdSubProductoFK()));
            dominio.setListaPaquetes(ifaceEntMerProPaq.getPaquetesByIdEmp(dominio.getIdEmpPK()));
            BigDecimal totalkilosPaquetes = new BigDecimal(0);
            BigDecimal totalcantPaquetes = new BigDecimal(0);
            for(EntradaMercanciaProductoPaquete paquete:dominio.getListaPaquetes())
            {
                if (paquete.getIdStatusFk().intValue()==1){
                totalkilosPaquetes = totalkilosPaquetes.add(paquete.getPesoNeto(), MathContext.UNLIMITED);
                totalcantPaquetes = totalcantPaquetes.add(paquete.getPaquetes(), MathContext.UNLIMITED);
                }
            }
            dominio.setKilosPaquetes(totalkilosPaquetes);
            dominio.setCantPaquetes(totalcantPaquetes);
            
            VentaProductoMayoreo venta = new VentaProductoMayoreo();
            venta =getTotalVentasByIdEMP(dominio.getIdEmpPK());
            
            dominio.setKilosVendidos(venta.getKilosVendidos());
            dominio.setCantidadVendida(venta.getCantidadEmpaque());
            
            dominio.setKilosReales(dominio.getKilosPaquetes().add(dominio.getKilosVendidos(), MathContext.UNLIMITED));
            dominio.setCantidadReales(dominio.getCantPaquetes().add(dominio.getCantidadVendida(), MathContext.UNLIMITED));
            lstEntradaMercanciaProducto.add(dominio);

        }

        return lstEntradaMercanciaProducto;

    }

    @Override
    public int deleteEntradaMercanciaProducto(EntradaMercanciaProducto ep) {
       getEjb();
        return ejb.deleteEntradaProducto(ep);
    }

    

    @Override
    public EntradaMercanciaProducto getEntradaMercanciaProductoByIdEmpPk(BigDecimal idEmpPk) {
       getEjb();
        try {
            getEjb();
            List<Object[]> lstObject = new ArrayList<Object[]>();
            lstObject = ejb.getEntradaMercanciaProductoByIdEmpPk(idEmpPk);
            EntradaMercanciaProducto entradaProducto = new EntradaMercanciaProducto();
            for (Object[] object : lstObject) {
                entradaProducto.setIdEmpPK(object[0] == null ? null : new BigDecimal(object[0].toString()));
                entradaProducto.setCantidadPaquetes(object[1] == null ? null : new BigDecimal(object[1].toString()));
                entradaProducto.setKilosTotalesProducto(object[2] == null ? null : new BigDecimal(object[2].toString()));
                
            }
            return entradaProducto;
        }
        catch (Exception ex) 
        {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public VentaProductoMayoreo getTotalVentasByIdEMP(BigDecimal idEmP) {
       getEjb();
        try {
            getEjb();
            List<Object[]> lstObject = new ArrayList<Object[]>();
            lstObject = ejb.getTotalVentasByIdEMP(idEmP);
            VentaProductoMayoreo venta = new VentaProductoMayoreo();
            for (Object[] object : lstObject) 
            {
                venta.setTotalVenta(object[0] == null ? null : new BigDecimal(object[0].toString()));
                venta.setKilosVendidos(object[1] == null ? null : new BigDecimal(object[1].toString()));
                venta.setCantidadEmpaque(object[2] == null ? null : new BigDecimal(object[2].toString()));
                
            }
            return venta;
        }
        catch (Exception ex) 
        {
            Logger.getLogger(ServiceNegocioExistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
        
    }

}
