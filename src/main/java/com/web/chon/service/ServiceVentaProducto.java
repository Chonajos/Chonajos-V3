package com.web.chon.service;

import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioVentaProducto;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * Servicio para el modulo de venta de productos
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceVentaProducto implements IfaceVentaProducto {

    NegocioVentaProducto ejb;

    private void getEjb() {
        try {
            ejb = (NegocioVentaProducto) Utilidades.getEJBRemote("ejbVentaProducto", NegocioVentaProducto.class.getName());
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVentaProducto(VentaProducto ventaProducto, int idVenta) {
        if (ejb == null) {
            getEjb();
        }
        System.out.println("" + ventaProducto.toString());
        return ejb.insertarVentaProducto(ventaProducto, idVenta);
    }

    @Override
    public ArrayList<VentaProducto> getVentasProductoByIdVenta(BigDecimal idVenta) {
        try {
            ArrayList<VentaProducto> lstProductos = new ArrayList<VentaProducto>();
            getEjb();
            List<Object[]> lstObject = ejb.getProductosByIdVentaFK(idVenta);
            BigDecimal count = new BigDecimal(0);
            for (Object[] obj : lstObject) {
                VentaProducto vp = new VentaProducto();
                vp.setNombreProducto(obj[0] == null ? "" : obj[0].toString());
                vp.setCantidadEmpaque(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                vp.setPrecioProducto(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                vp.setIdProductoFk(obj[3] == null ? "" : obj[3].toString());
                vp.setTotal(vp.getCantidadEmpaque().multiply(vp.getPrecioProducto(), MathContext.UNLIMITED));

                count = count.add(new BigDecimal(1), MathContext.UNLIMITED);
                vp.setCount(count);

                lstProductos.add(vp);
            }

            return lstProductos;
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

    @Override
    public ArrayList<VentaProducto> getReporteVenta(String fechaInicio, String fechaFin, BigDecimal idSucursal) {
        try {
            ArrayList<VentaProducto> lstProductos = new ArrayList<VentaProducto>();
            getEjb();
            List<Object[]> lstObject = ejb.getReporteVenta(fechaInicio, fechaFin, idSucursal);
            BigDecimal count = new BigDecimal(0);
            BigDecimal costo = new BigDecimal(0);

            for (Object[] obj : lstObject) {
                VentaProducto vp = new VentaProducto();

                vp.setIdExistencia(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                vp.setNombreProducto(obj[1] == null ? "" : obj[1].toString());
                vp.setKilosVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                vp.setTotal(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                vp.setPrecioProducto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                vp.setExistencia(obj[5] == null ? null : new BigDecimal(obj[5].toString()));

                vp.setAjuste(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                vp.setEntrada(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                vp.setTotalCosto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));

                if (!vp.getTotalCosto().equals(new BigDecimal(0))) {
                    costo = vp.getTotalCosto().divide(vp.getEntrada(),2,RoundingMode.HALF_UP);
                }
                
                vp.setCostoMerma(costo);

                count = count.add(new BigDecimal(1), MathContext.UNLIMITED);
                vp.setCount(count);

                lstProductos.add(vp);
            }

            return lstProductos;
        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

}
