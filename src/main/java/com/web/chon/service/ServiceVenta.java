package com.web.chon.service;

import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Venta;
import com.web.chon.negocio.NegocioVenta;
import com.web.chon.util.TiempoUtil;
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
 * Servicio para el modulo de Ventas
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceVenta implements IfaceVenta {

    NegocioVenta ejb;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVenta) Utilidades.getEJBRemote("ejbVenta", NegocioVenta.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVenta(Venta venta,int folioVenta) {
        getEjb();
        return ejb.insertarVenta(venta,folioVenta);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<Venta> getVentasByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta,String idProducto) {
        getEjb();
        ArrayList<Venta> lstVenta = new ArrayList<Venta>();
        List<Object[]> lstObject = ejb.getVentasByInterval(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin), idSucursal, idStatusVenta,idProducto);
        for (Object[] obj : lstObject) 
        {

            Venta venta = new Venta();
            venta.setIdVentaPk(new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFk(new BigDecimal(obj[2].toString()));
            venta.setFechaVenta((Date) obj[3]);
            venta.setIdStatus(Integer.parseInt(obj[5] == null ? "0" : obj[5].toString()));
       
//            venta.setIdStatus(Integer.parseInt(obj[5] == null ? "0" : obj[5].toString()));
//            venta.setFechaPago(obj[6] == null ? null : (Date) obj[6]);
//            venta.setIdSucursal(Integer.parseInt(obj[7] == null ? "0" : obj[7].toString()));
//            venta.setNombreCliente(obj[8].toString());
//            venta.setNombreVendedor(obj[9].toString());
//            venta.setTotalVenta(new BigDecimal(obj[10].toString()));
//            venta.setFolioSucursal(Integer.parseInt(obj[11] == null ? "0" : obj[11].toString()));
            
            //aqui vamos a llamar para llenar los productos.
            lstVenta.add(venta);
        }

        return lstVenta;
    }

    @Override
    public int getFolioByIdSucursal(int idSucursal) {
        getEjb();
        return (ejb.getFolioByIdSucursal(idSucursal)+1);
    }

}
