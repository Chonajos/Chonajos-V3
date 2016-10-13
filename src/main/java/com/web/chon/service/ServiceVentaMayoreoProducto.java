/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreoProducto;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class ServiceVentaMayoreoProducto implements IfaceVentaMayoreoProducto {

    NegocioVentaMayoreoProducto ejb;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVentaMayoreoProducto) Utilidades.getEJBRemote("ejbVentaMayoreoProducto", NegocioVentaMayoreoProducto.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVentaMayoreoProducto(VentaProductoMayoreo venta) {
        getEjb();
        return ejb.insertarVentaProducto(venta);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<VentaProductoMayoreo> getProductosbyIdVmFk(BigDecimal idVmFk) {
        try {
            getEjb();
            System.out.println("VariableService: " + idVmFk);
            ArrayList<VentaProductoMayoreo> lstProductos = new ArrayList<VentaProductoMayoreo>();
            List<Object[]> lstObject = ejb.getProductos(idVmFk);
            for (Object[] obj : lstObject) {

                VentaProductoMayoreo producto = new VentaProductoMayoreo();
                producto.setFolioCarro(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                producto.setClave(obj[1] == null ? "" : obj[1].toString());
                //busca_venta.setNombreCliente(obj[2] == null ? "" : obj[2].toString());
                //busca_venta.setNombreVendedor(obj[3] == null ? "" : obj[3].toString());
                //busca_venta.setIdVenta(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                producto.setNombreProducto(obj[2] == null ? "" : obj[2].toString());
                producto.setNombreEmpaque(obj[3] == null ? "" : obj[3].toString());
                producto.setCantidadEmpaque(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                producto.setKilosVendidos(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                producto.setPrecioProducto(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                producto.setTotalVenta(producto.getKilosVendidos().multiply(producto.getPrecioProducto(), MathContext.UNLIMITED));
                producto.setTotalVenta(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                //busca_venta.setFechaVenta((Date) obj[11]);
                //busca_venta.setFechaPromesaPago((Date) obj[12]);
                //busca_venta.setNombreStatus(obj[13] == null ? "" : obj[13].toString());
                //busca_venta.setStatusFK(obj[14] == null ? 0 : Integer.parseInt(obj[14].toString())); //id status
                //busca_venta.setIdSucursalFk(obj[15] == null ? null : new BigDecimal(obj[15].toString())); 
                //busca_venta.setFolioSucursal(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
                //busca_venta.setIdSubProducto(obj[17] == null ? "" : obj[17].toString());
                //busca_venta.setIdTipoEmpaque(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
                //busca_venta.setIdBodega(obj[19] == null ? null : new BigDecimal(obj[19].toString()));
                //busca_venta.setIdTipoConvenio(obj[20] == null ? null : new BigDecimal(obj[20].toString()));
                //busca_venta.setIdProvedor(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                producto.setIdVentaMayProdPk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                producto.setIdVentaMayoreoFk(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                System.out.println("###################################");
                System.out.println(producto.toString());
                lstProductos.add(producto);
            }
            return lstProductos;
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreoProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public ArrayList<VentaProductoMayoreo> buscaVentaCancelar(BigDecimal  idVenta, BigDecimal  idSucursal) {
        getEjb();
        try {
            ArrayList<VentaProductoMayoreo> lstVentas = new ArrayList<VentaProductoMayoreo>();
            List<Object[]> lstObject = ejb.buscaVentaCancelar(idVenta, idSucursal);
            for (Object[] obj : lstObject) {
                VentaProductoMayoreo producto = new VentaProductoMayoreo();
                producto.setIdExistenciaFk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                producto.setCantidadEmpaque(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                producto.setKilosVendidos(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                lstVentas.add(producto);
            }
            return lstVentas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreoProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    
    
    
    }

}
