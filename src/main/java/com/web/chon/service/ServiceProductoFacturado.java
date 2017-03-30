/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioProductoFacturado;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceProductoFacturado implements IfaceProductoFacturado {
    
    NegocioProductoFacturado ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioProductoFacturado) Utilidades.getEJBRemote("ejbProductoFacturado", NegocioProductoFacturado.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceProductoFacturado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insert(ProductoFacturado pf) {
        getEjb();
        return ejb.insert(pf);
    }

    @Override
    public int update(ProductoFacturado pf) {
        getEjb();
        return ejb.update(pf);
    }

    @Override
    public int delete(ProductoFacturado pf) {
       getEjb();
        return ejb.delete(pf);
    }

    @Override
    public int getNextVal() {
         getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<ProductoFacturado> getByIdFacturaFk(BigDecimal idFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoFacturado getByIdPk(BigDecimal idPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ProductoFacturado> getByIdTipoFolioFk(BigDecimal idTipoFk, BigDecimal idVentaFk) {
       getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<ProductoFacturado> lstProductoFacturado = new ArrayList<ProductoFacturado>();
        lstObject = ejb.getByIdTipoFolioFk(idTipoFk, idVentaFk);
        
        for (Object[] obj : lstObject) {
            ProductoFacturado dominio = new ProductoFacturado();
            //dominio.setIdProductoFacturadoPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdTipoLlaveFk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdLlaveFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            //dominio.setIdFacturaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setImporte(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setCantidad(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            dominio.setKilos(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
        
            lstProductoFacturado.add(dominio);
        }
        return lstProductoFacturado;
    
    }

    @Override
    public ArrayList<VentaProductoMayoreo> getProductosNoFacturados(BigDecimal idTipoFk, BigDecimal idSucursalFk, String fechaInicio, String fechaFin) {
        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<VentaProductoMayoreo> lstProductoNoFacturado = new ArrayList<VentaProductoMayoreo>();
        lstObject = ejb.getProductosNoFacturados( idTipoFk,  idSucursalFk,  fechaInicio,  fechaFin);
        
        for (Object[] obj : lstObject) {
            VentaProductoMayoreo dominio = new VentaProductoMayoreo();
            dominio.setIdVentaMayProdPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdVentaMayoreoFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setFolioVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setNombreProducto(obj[3] == null ? null : obj[3].toString());
            dominio.setNombreEmpaque(obj[4] == null ? null : obj[4].toString());
            dominio.setCantidadEmpaque(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setKilosVendidos(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            dominio.setPrecioProducto(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setTotalVenta(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setIdTipoVenta(idTipoFk);
           
            lstProductoNoFacturado.add(dominio);
        }
        return lstProductoNoFacturado;
    
    }

    @Override
    public int deleteByIdFacturaFk(BigDecimal idFacturaFk) {
        getEjb();
        return ejb.deleteByIdFacturaFk(idFacturaFk);
    
    }

    @Override
    public ArrayList<VentaProductoMayoreo> getProductosNoFacturadosAbonos(BigDecimal idTipoFk, BigDecimal folioAbono) {
         getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<VentaProductoMayoreo> lstProductoNoFacturado = new ArrayList<VentaProductoMayoreo>();
        lstObject = ejb.getProductosNoFacturadosAbonos( idTipoFk,  folioAbono);
        
        for (Object[] obj : lstObject) {
            VentaProductoMayoreo dominio = new VentaProductoMayoreo();
            dominio.setIdVentaMayProdPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            //dominio.setIdVentaMayoreoFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            //dominio.setFolioVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setNombreProducto(obj[1] == null ? null : obj[3].toString());
            dominio.setNombreEmpaque(obj[2] == null ? null : obj[4].toString());
            dominio.setCantidadEmpaque(obj[3] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setKilosVendidos(obj[4] == null ? null : new BigDecimal(obj[6].toString()));
            dominio.setPrecioProducto(obj[5] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setTotalVenta(obj[6] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setIdTipoVenta(idTipoFk);
           
            lstProductoNoFacturado.add(dominio);
        }
        return lstProductoNoFacturado;
    
    
    
    }

    
}
