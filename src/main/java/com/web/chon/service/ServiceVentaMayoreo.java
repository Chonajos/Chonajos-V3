/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;



import com.web.chon.dominio.RelacionOperacionesMayoreo;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreo;
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
 * @author freddy
 */
@Service
public class ServiceVentaMayoreo implements IfaceVentaMayoreo {
    
    NegocioVentaMayoreo ejb;
    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioVentaMayoreo) Utilidades.getEJBRemote("ejbVentaMayoreo", NegocioVentaMayoreo.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVenta(VentaMayoreo venta) {
       getEjb();
       return ejb.insertarVenta(venta);
    }

    @Override
    public int getNextVal() {
       getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<RelacionOperacionesMayoreo> getVentasByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta,BigDecimal idTipoVenta) {
       
        getEjb();
        ArrayList<RelacionOperacionesMayoreo> lstVenta = new ArrayList<RelacionOperacionesMayoreo>();
        List<Object[]> lstObject = ejb.getVentasByInterval(TiempoUtil.getFechaDDMMYYYY(fechaInicio),TiempoUtil.getFechaDDMMYYYY(fechaFin),idSucursal,idStatusVenta,idTipoVenta);
        for(Object[] obj : lstObject){
           
            RelacionOperacionesMayoreo venta = new RelacionOperacionesMayoreo();
            venta.setIdVentaPk(new BigDecimal(obj[0].toString()));
            venta.setIdClienteFk(new BigDecimal(obj[1].toString()));
            venta.setIdVendedorFk(new BigDecimal(obj[2].toString()));
            venta.setFechaVenta((Date)obj[3]);
            venta.setFechaPromesaPago(obj[4] == null ? null: (Date) obj[4]);
            venta.setIdStatus(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            venta.setFechaPago(obj[6] == null ? null: (Date) obj[6]);
            venta.setIdSucursal(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            venta.setIdTipoVenta(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            venta.setNombreCliente(obj[9].toString());
            venta.setNombreVendedor(obj[10].toString());
            venta.setTotalVenta(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            venta.setNombreTipoVenta(obj[12].toString());
            lstVenta.add(venta);
        }
        
        return lstVenta;
    }
    
}
