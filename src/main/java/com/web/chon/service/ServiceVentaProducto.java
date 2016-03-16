package com.web.chon.service;

import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioVentaProducto;
import com.web.chon.util.Utilidades;
import java.util.ArrayList;
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
        if (ejb == null) {
            getEjb();
        }
        return null;
    }

    @Override
    public int insertarVentaProducto(VentaProducto ventaProducto,int idVenta) {
        if (ejb == null) {
            getEjb();
        }
        return ejb.insertarVentaProducto(ventaProducto, idVenta);
    }

}
