
package com.web.chon.service;

import com.web.chon.dominio.Producto;
import com.web.chon.negocio.NegocioProducto;
import com.web.chon.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * Servicio para el catalogo de categorias
 * @author Juan de la Cruz
 */
@Service
public class ServiceProducto implements IfaceProducto {

    NegocioProducto ejb;

    @Override
    public ArrayList<Producto> getProductos() {
        try {
            ArrayList<Producto> lstProducto = new ArrayList<Producto>();
            ejb = (NegocioProducto) Utilidades.getEJBRemote("ejbProducto", NegocioProducto.class.getName());

            List<Object[]> lstObject = ejb.getProductos();

            for (Object[] obj : lstObject) {

                Producto producto = new Producto();
                producto.setIdProductoPk(obj[0] == null ? "" : obj[0].toString());
                producto.setNombreProducto(obj[1] == null ? "" : obj[1].toString());
                producto.setDescripcionProducto(obj[2] == null ? "" : obj[2].toString());

                lstProducto.add(producto);
            }

            return lstProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public Producto getProductoById(String idProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteProducto(String idProducto) {
        try {
            System.out.println("service string delete");
            return ejb.deleteProducto(idProducto);
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateProducto(Producto producto) {
        try {
            System.out.println("service" + producto.toString());
            return ejb.updateProducto(producto);
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int insertarProducto(Producto producto) {
        try {
            System.out.println("producto" + producto.toString());
            return ejb.insertarProducto(producto);
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getLastIdCategoria() {
        try {
            int idCategoria =0;
            ejb = (NegocioProducto) Utilidades.getEJBRemote("ejbProducto", NegocioProducto.class.getName());
            idCategoria = ejb.getLastIdCategoria();
            return idCategoria;
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
         
         
    }

}
