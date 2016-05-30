
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioBuscaVenta;
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
@Stateless(mappedName = "ejbBuscaVenta")
public class EjbBuscaVenta implements NegocioBuscaVenta
{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getVentaById(int idVenta) 
    {
        try {

            Query query = em.createNativeQuery("select c.nombre ||' '||c.APELLIDO_PATERNO||' '||c.APELLIDO_MATERNO   as nombre_cliente,u.NOMBRE_USUARIO||' '||u.APATERNO_USUARIO||' '||u.AMATERNO_USUARIO as nombre_vendedor,v.ID_VENTA_PK, sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vp.CANTIDAD_EMPAQUE,vp.PRECIO_PRODUCTO,vp.TOTAL_VENTA,v.FECHA_VENTA,v.FECHA_PROMESA_PAGO,sv.NOMBRE_STATUS,v.status_fk,v.ID_SUCURSAL_FK,sucu.NOMBRE_SUCURSAL\n" +
"       from Venta v\n" +
"       INNER JOIN Venta_Producto vp\n" +
"       on v.id_venta_pk=vp.id_venta_fk\n" +
"       INNER JOIN subproducto sp\n" +
"       on sp.id_subproducto_pk=vp.id_subproducto_fk\n" +
"       INNER JOIN tipo_empaque tem\n" +
"       on vp.ID_TIPO_EMPAQUE_FK= tem.ID_TIPO_EMPAQUE_PK\n" +
"       INNER JOIN cliente c\n" +
"       on v.id_cliente_fk= c.ID_CLIENTE\n" +
"       INNER JOIN usuario u\n" +
"       on v.ID_VENDEDOR_FK=u.ID_USUARIO_PK\n" +
"       INNER JOIN status_venta sv\n" +
"       on v.status_fk=sv.ID_STATUS_PK\n" +
"       INNER JOIN SUCURSAL sucu\n" +
"       on sucu.ID_SUCURSAL_PK = v.ID_SUCURSAL_FK\n" +
"      where v.ID_VENTA_PK = ?");
            //System.out.println("EJbBuscaVenta:getVentabyId: idVenta: "+idVenta);
            query.setParameter(1, idVenta);

            return query.getResultList();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateVenta(int idVenta,int idUsuario) 
    {
        
        try {
            Query query = em.createNativeQuery("UPDATE VENTA SET STATUS_FK= ?,ID_CAJERO_FK=?, FECHA_PAGO = sysdate WHERE ID_VENTA_PK = ? ");
            query.setParameter(1, 2);
            query.setParameter(2, idUsuario);
            query.setParameter(3, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getVentaMayoreoById(int idVenta,int idSucursal) {
       
        try {

            Query query = em.createNativeQuery("select em.CARROSUCURSAL,em.IDENTIFICADOR,sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS,vmp.PRECIO_PRODUCTO,vmp.TOTAL_VENTA from VENTAMAYOREOPRODUCTO vmp \n" +
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
"where vm.VENTASUCURSAL=? and vm.ID_SUCURSAL_FK=?");
            //System.out.println("EJbBuscaVenta:getVentaMayoreobyId: idVenta: "+idVenta);
            query.setParameter(1, idVenta);
            query.setParameter(2, idSucursal);
            System.out.println("EjB: idVenta"+idVenta);
            System.out.println("EJB: idSucursal: "+idSucursal);
            System.out.println("==========================================");
            System.out.println(query);
            System.out.println("==========================================");

            return query.getResultList();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateStatusVentaMayoreo(int idVenta,int idUsuario) {
        //System.out.println("Entro a EJB");
        //System.out.println("idusuario:" + idUsuario);
      
        try {
            Query query = em.createNativeQuery("UPDATE VENTA_MAYOREO SET ID_STATUS_FK= ?,ID_CAJERO_FK=?,FECHA_PAGO = sysdate WHERE ID_VENTA_MAYOREO_PK = ? ");
            query.setParameter(1, 2);
            query.setParameter(2, idUsuario);
            query.setParameter(3, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int cancelarVenta(int idVenta, int idUsuario, String comentarios) {
        System.out.println("idVenta: "+idVenta);
        System.out.println("idUsuario: "+idUsuario);
        System.out.println("comentarios: "+comentarios);
        try {
            Query query = em.createNativeQuery("UPDATE VENTA SET STATUS_FK= ?,ID_CANCEL_USER_FK=?,COMENTARIOS_CANCEL=?,FECHA_CANCELACION=sysdate WHERE ID_VENTA_PK = ? ");
            query.setParameter(1, 4);
            query.setParameter(2, idUsuario);
            query.setParameter(3, comentarios);
            query.setParameter(4, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int cancelarVentaMayoreo(int idVenta, int idUsuario, String comentarios) {
       System.out.println("idVenta: "+idVenta);
        System.out.println("idUsuario: "+idUsuario);
        System.out.println("comentarios: "+comentarios);
        try {
            Query query = em.createNativeQuery("UPDATE VENTA_MAYOREO SET ID_STATUS_FK= ?,ID_USER_CANCEL_FK=?,COMENTARIOS_CANCEL=?,FECHA_CANCELACION=sysdate WHERE ID_VENTA_MAYOREO_PK = ? ");
            query.setParameter(1, 4);
            query.setParameter(2, idUsuario);
            query.setParameter(3, comentarios);
            query.setParameter(4, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
        
    }

    @Override
    public List<Object[]> getVentaMayoreoByIdBuscaVenta(int idVenta, int idSucursal) {
        System.out.println("EJB BuscaVenta ");
        try {

            Query query = em.createNativeQuery("select vm.ID_VENTA_MAYOREO_PK, vm.VENTASUCURSAL as folio, usu.NOMBRE_USUARIO as Vendedor, cl.NOMBRE as Cliente,\n" +
"sub.NOMBRE_SUBPRODUCTO,te.NOMBRE_EMPAQUE,vmp.PRECIO_PRODUCTO, vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS, vmp.TOTAL_VENTA, st.NOMBRE_STATUS,st.ID_STATUS_PK\n" +
"from VENTA_MAYOREO vm \n" +
"join USUARIO usu\n" +
"on usu.ID_USUARIO_PK = vm.ID_VENDEDOR_FK\n" +
"inner join cliente cl\n" +
"on cl.ID_CLIENTE = vm.ID_CLIENTE_FK\n" +
"join VENTAMAYOREOPRODUCTO vmp\n" +
"on vmp.ID_VENTA_MAYOREO_FK = vm.ID_VENTA_MAYOREO_PK\n" +
"join SUBPRODUCTO sub\n" +
"on sub.ID_SUBPRODUCTO_PK = vmp.ID_SUBPRODUCTO_FK\n" +
"join TIPO_EMPAQUE te\n" +
"on te.ID_TIPO_EMPAQUE_PK = vmp.ID_TIPO_EMPAQUE_FK\n" +
"join STATUS_VENTA st\n" +
"on st.ID_STATUS_PK = vm.ID_STATUS_FK\n" +
"where vm.VENTASUCURSAL = ? and vm.ID_SUCURSAL_FK = ?");
           query.setParameter(1, idVenta);
            query.setParameter(2, idSucursal);
            System.out.println(query);


            return query.getResultList();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    
    }

    @Override
    public List<Object[]> buscaVentaCancelar(int idVenta, int idSucursal) {
       System.out.println("EJB BuscaVentaCancelar ");
        try {

            Query query = em.createNativeQuery("select vmp.ID_EXISTENCIA_FK,vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS from VENTAMAYOREOPRODUCTO vmp\n" +
"INNER JOIN VENTA_MAYOREO vm\n" +
"on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n" +
"where vm.VENTASUCURSAL = ? and vm.ID_SUCURSAL_FK=?");
            query.setParameter(1, idVenta);
            query.setParameter(2, idSucursal);
            System.out.println(query);
            return query.getResultList();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

   
    
}
