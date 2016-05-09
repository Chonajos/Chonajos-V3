/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreoProducto;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceVentaMayoreoProducto implements IfaceVentaMayoreoProducto{
    
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
       return ejb.insertarVentaProducto(venta);}

    @Override
    public int getNextVal() {
         getEjb();
        return ejb.getNextVal();}
}
