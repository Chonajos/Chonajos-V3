/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreoProducto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbVentaMayoreoProducto")
public class EjbVentaMayoreoProducto implements NegocioVentaMayoreoProducto{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVentaProducto(VentaProductoMayoreo ventaproducto) {
        Query query = em.createNativeQuery("INSERT INTO VENTAMAYOREOPRODUCTO(ID_V_M_P_PK,ID_VENTA_MAYOREO_FK,ID_SUBPRODUCTO_FK,PRECIO_PRODUCTO,KILOS_VENDIDOS,CANTIDAD_EMPAQUE,TOTAL_VENTA,ID_TIPO_EMPAQUE_FK,ID_ENTRADA_MERCANCIA_FK,ID_EXISTENCIA_FK) VALUES(?,?,?,?,?,?,?,?,?,?)");
        System.out.println("venta_mayoreo_producto ejb :" + ventaproducto.toString());
        query.setParameter(1, ventaproducto.getIdVentaMayProdPk());
        query.setParameter(2, ventaproducto.getIdVentaMayoreoFk());
        query.setParameter(3, ventaproducto.getIdSubProductofk());
        query.setParameter(4, ventaproducto.getPrecioProducto());
        query.setParameter(5, ventaproducto.getKilosVendidos());
        query.setParameter(6, ventaproducto.getCantidadEmpaque());
        query.setParameter(7, ventaproducto.getTotalVenta());
        query.setParameter(8, ventaproducto.getIdTipoEmpaqueFk());
        query.setParameter(9, ventaproducto.getIdEntradaMercanciaFk());
        query.setParameter(10, ventaproducto.getIdExistenciaFk());
        return query.executeUpdate();
        
    }

    @Override
    public int getNextVal() {
       Query query = em.createNativeQuery("SELECT S_VENTA_MAYOREO_PRODUCTO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    
    
}
