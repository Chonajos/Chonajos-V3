package com.web.chon.service;

import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Venta;
import com.web.chon.negocio.NegocioVenta;
import com.web.chon.util.Utilidades;
import java.math.BigInteger;
import java.util.ArrayList;
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
            ejb = (NegocioVenta) Utilidades.getEJBRemote("ejbVenta", NegocioVenta.class.getName());
        } catch (Exception ex) {
            Logger.getLogger(ServiceVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insertarVenta(Venta venta) {
        if (ejb == null) {
            getEjb();
        }
       
        return ejb.insertarVenta(venta);
    }

    @Override
    public int getNextVal() {
        if (ejb == null) {
            getEjb();
        }
        
        return ejb.getNextVal();
    }

}
