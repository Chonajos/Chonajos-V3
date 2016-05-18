package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.negocio.NegocioBuscaVenta;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class ServiceBuscaVenta implements IfaceBuscaVenta {

    NegocioBuscaVenta ejb;

    @Override
    public ArrayList<BuscaVenta> getVentaById(int idVenta) {
        try {
            ArrayList<BuscaVenta> lstVentas = new ArrayList<BuscaVenta>();
            ejb = (NegocioBuscaVenta) Utilidades.getEJBRemote("ejbBuscaVenta", NegocioBuscaVenta.class.getName());
            List<Object[]> lstObject = ejb.getVentaById(idVenta);
            for (Object[] obj : lstObject) {

                BuscaVenta busca_venta = new BuscaVenta();
                busca_venta.setNombreCliente(obj[0] == null ? "" : obj[0].toString());
                busca_venta.setNombreVendedor(obj[1] == null ? "" : obj[1].toString());
                busca_venta.setIdVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                busca_venta.setNombreSubproducto(obj[3] == null ? "" : obj[3].toString());
                busca_venta.setNombreEmpaque(obj[4] == null ? "" : obj[4].toString());
                busca_venta.setCantidadEmpaque(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                busca_venta.setPrecioProducto(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                busca_venta.setTotal(busca_venta.getCantidadEmpaque().multiply(busca_venta.getPrecioProducto(), MathContext.UNLIMITED));
                busca_venta.setTotalVenta(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                busca_venta.setFechaVenta((Date) obj[8]);
                busca_venta.setFechaPromesaPago((Date) obj[9]);
                busca_venta.setNombreStatus(obj[10] == null ? "" : obj[10].toString());
                busca_venta.setStatusFK(obj[11] == null ? 0 : Integer.parseInt(obj[11].toString())); //id status
                busca_venta.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString())); 
                lstVentas.add(busca_venta);
            }

            return lstVentas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public int updateCliente(int idVenta) {
        return ejb.updateCliente(idVenta);
    }

    @Override
    public ArrayList<BuscaVenta> getVentaMayoreoById(int idVenta) 
    {
       try {
            ArrayList<BuscaVenta> lstVentas = new ArrayList<BuscaVenta>();
            ejb = (NegocioBuscaVenta) Utilidades.getEJBRemote("ejbBuscaVenta", NegocioBuscaVenta.class.getName());
            List<Object[]> lstObject = ejb.getVentaMayoreoById(idVenta);
            for (Object[] obj : lstObject) {

                BuscaVenta busca_venta = new BuscaVenta();
                busca_venta.setCarro(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                busca_venta.setClave(obj[1] == null ? "" : obj[1].toString());
                busca_venta.setNombreCliente(obj[2] == null ? "" : obj[2].toString());
                busca_venta.setNombreVendedor(obj[3] == null ? "" : obj[3].toString());
                busca_venta.setIdVenta(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                busca_venta.setNombreSubproducto(obj[5] == null ? "" : obj[5].toString());
                busca_venta.setNombreEmpaque(obj[6] == null ? "" : obj[6].toString());
                busca_venta.setCantidadEmpaque(obj[0] == null ? null : new BigDecimal(obj[7].toString()));
                busca_venta.setKilosVendidos(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                busca_venta.setPrecioProducto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                busca_venta.setTotal(busca_venta.getKilosVendidos().multiply(busca_venta.getPrecioProducto(), MathContext.UNLIMITED));
                busca_venta.setTotalVenta(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                busca_venta.setFechaVenta((Date) obj[11]);
                busca_venta.setFechaPromesaPago((Date) obj[12]);
                busca_venta.setNombreStatus(obj[13] == null ? "" : obj[13].toString());
                busca_venta.setStatusFK(obj[14] == null ? 0 : Integer.parseInt(obj[14].toString())); //id status
                busca_venta.setIdSucursalFk(obj[15] == null ? null : new BigDecimal(obj[15].toString())); 
                lstVentas.add(busca_venta);
            }

            return lstVentas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public int updateStatusVentaMayoreo(int idVenta) {
        return ejb.updateStatusVentaMayoreo(idVenta);
    }
}
