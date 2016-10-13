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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Override
    public List<Object[]> getProductos(BigDecimal idVmFk) {
        System.out.println("=====================Entro a metodo GetProductos==================0");
       try {
            Query query = em.createNativeQuery("select em.CARROSUCURSAL,em.IDENTIFICADOR,sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vmp.CANTIDAD_EMPAQUE,\n" +
"vmp.KILOS_VENDIDOS,vmp.PRECIO_PRODUCTO,vmp.TOTAL_VENTA, vmp.ID_V_M_P_PK,vmp.ID_VENTA_MAYOREO_FK from VENTAMAYOREOPRODUCTO vmp\n" +
"join VENTA_MAYOREO vm\n" +
"on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n" +
"INNER JOIN tipo_empaque tem\n" +
"on vmp.ID_TIPO_EMPAQUE_FK= tem.ID_TIPO_EMPAQUE_PK\n" +
"INNER JOIN subproducto sp\n" +
"on sp.id_subproducto_pk=vmp.ID_SUBPRODUCTO_FK\n" +
"INNER JOIN EXISTENCIA_PRODUCTO exp\n" +
"on exp.ID_EXP_PK = vmp.ID_EXISTENCIA_FK\n" +
"INNER JOIN ENTRADAMERCANCIAPRODUCTO emp\n" +
"on emp.ID_EMP_PK = exp.ID_EMP_FK\n" +
"INNER JOIN ENTRADAMERCANCIA em\n" +
"on em.ID_EM_PK = emp.ID_EM_FK\n" +
"where vmp.ID_VENTA_MAYOREO_FK =?");
            query.setParameter(1, idVmFk);
            System.out.println("Query: "+query.toString());
            System.out.println("Parametro: "+idVmFk);
            return query.getResultList();
        } catch (Exception ex) 
        {
            System.out.println("-----------------------Entro a Error----------------");
            Logger.getLogger(EjbVentaMayoreoProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public List<Object[]> buscaVentaCancelar(BigDecimal idVenta, BigDecimal idSucursal) {
         System.out.println("EJB BuscaVentaCancelar ");
        try {

            Query query = em.createNativeQuery("select vmp.ID_EXISTENCIA_FK,vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS from VENTAMAYOREOPRODUCTO vmp\n"
                    + "INNER JOIN VENTA_MAYOREO vm\n"
                    + "on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n"
                    + "where vm.VENTASUCURSAL = ? and vm.ID_SUCURSAL_FK=?");
            query.setParameter(1, idVenta);
            query.setParameter(2, idSucursal);
            System.out.println(query);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    
    }

    
    
}
