package com.web.chon.service;


import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioVentaProducto;
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
    public ArrayList<VentaProducto> getVentasProductoByIdVenta(int idVenta) {
        getEjb();
        ArrayList<VentaProducto> lstVenta = new ArrayList<VentaProducto>();
        List<Object[]> lstObject = ejb.getVentaProductoByIdVenta(new BigDecimal(idVenta));
        for(Object[] obj : lstObject){
           
            VentaProducto venta = new VentaProducto();
            venta.setIdProductoFk(obj[0] == null ? null : obj[0].toString());
            venta.setKilosVenta(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            lstVenta.add(venta);
        }
        
        return lstVenta;
    }

    @Override
    public int insertarVentaProducto(VentaProducto ventaProducto,int idVenta) {
        if (ejb == null) {
            getEjb();
        }
        System.out.println("" + ventaProducto.toString());
        return ejb.insertarVentaProducto(ventaProducto, idVenta);
    }

}
