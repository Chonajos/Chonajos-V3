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
    public int insertarVenta(Venta venta) {
        getEjb();
        return ejb.insertarVenta(venta);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<RelacionOperaciones> getVentasByIntervalDate(Date fechaInicio, Date fechaFin,int idSucursal,int idStatusVenta) {
        getEjb();
        ArrayList<RelacionOperaciones> lstVenta = new ArrayList<RelacionOperaciones>();
        List<Object[]> lstObject = ejb.getVentasByInterval(TiempoUtil.getFechaDDMMYYYY(fechaInicio),TiempoUtil.getFechaDDMMYYYY(fechaFin),idSucursal,idStatusVenta);
        for(Object[] obj : lstObject){
           
            RelacionOperaciones venta = new RelacionOperaciones();
            venta.setIdVentaPk(new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFk(new BigDecimal(obj[2].toString()));
            venta.setFechaVenta((Date)obj[3]);
            System.out.println("Fecha: "+venta.getFechaVenta());
            venta.setFechaPromesaPago(obj[4] == null ? null: (Date) obj[4]);
            venta.setIdStatus(Integer.parseInt(obj[5]==null ? "0" :obj[5].toString()));
            venta.setFechaPago(obj[6] == null ? null: (Date) obj[6]);
            venta.setIdSucursal(Integer.parseInt(obj[7]==null ? "0" :obj[7].toString()));
            venta.setNombreCliente(obj[8].toString());
            venta.setNombreVendedor(obj[9].toString());
            venta.setTotalVenta(new BigDecimal(obj[10].toString()));
            lstVenta.add(venta);
        }
        
        return lstVenta;
    }

}
