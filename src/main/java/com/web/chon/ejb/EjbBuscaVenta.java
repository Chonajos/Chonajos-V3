
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

            Query query = em.createNativeQuery("select c.nombre ||' '||c.APELLIDO_PATERNO||' '||c.APELLIDO_MATERNO   as nombre_cliente,u.NOMBRE_USUARIO||' '||u.APATERNO_USUARIO||' '||u.AMATERNO_USUARIO as nombre_vendedor,v.ID_VENTA_PK, sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vp.CANTIDAD_EMPAQUE,vp.PRECIO_PRODUCTO,vp.TOTAL_VENTA,v.FECHA_VENTA,v.FECHA_PROMESA_PAGO,sv.NOMBRE_STATUS,v.status_fk,v.ID_SUCURSAL_FK\n" +
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
"       where v.ID_VENTA_PK = ?");
            System.out.println("EJbBuscaVenta:getVentabyId: idVenta: "+idVenta);
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
    public int updateCliente(int idVenta) 
    {
        
        try {
            Query query = em.createNativeQuery("UPDATE VENTA SET STATUS_FK= ?,FECHA_PAGO = sysdate WHERE ID_VENTA_PK = ? ");
            query.setParameter(1, 2);
            query.setParameter(2, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getVentaMayoreoById(int idVenta) {
       
        try {

            Query query = em.createNativeQuery("select vmp.ID_ENTRADA_MERCANCIA_FK as carro, em.IDENTIFICADOR as clave, c.nombre ||' '||c.APELLIDO_PATERNO||' '||c.APELLIDO_MATERNO   as nombre_cliente,u.NOMBRE_USUARIO||' '||u.APATERNO_USUARIO||' '||u.AMATERNO_USUARIO as nombre_vendedor,vm.ID_VENTA_MAYOREO_PK, sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS,vmp.PRECIO_PRODUCTO,vmp.TOTAL_VENTA,vm.FECHA_VENTA,vm.FECHA_PROMESA_PAGO,sv.NOMBRE_STATUS,vm.ID_STATUS_FK,vm.ID_SUCURSAL_FK\n" +
"from VENTA_MAYOREO vm\n" +
"INNER JOIN VENTAMAYOREOPRODUCTO vmp\n" +
"on vm.ID_VENTA_MAYOREO_PK=vmp.ID_VENTA_MAYOREO_FK\n" +
"INNER JOIN subproducto sp\n" +
" on sp.id_subproducto_pk=vmp.ID_SUBPRODUCTO_FK\n" +
"INNER JOIN tipo_empaque tem\n" +
" on vmp.ID_TIPO_EMPAQUE_FK= tem.ID_TIPO_EMPAQUE_PK\n" +
" INNER JOIN cliente c\n" +
" on vm.ID_CLIENTE_FK= c.ID_CLIENTE\n" +
" INNER JOIN usuario u\n" +
" on  vm.ID_VENDEDOR_FK=u.ID_USUARIO_PK\n" +
" INNER JOIN status_venta sv\n" +
" on vm.ID_STATUS_FK=sv.ID_STATUS_PK\n" +
" inner join ENTRADAMERCANCIA em\n" +
" on em.ID_EM_PK = vmp.ID_ENTRADA_MERCANCIA_FK\n" +
" where vm.ID_VENTA_MAYOREO_PK = ?");
            System.out.println("EJbBuscaVenta:getVentaMayoreobyId: idVenta: "+idVenta);
            query.setParameter(1, idVenta);

            return query.getResultList();

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateStatusVentaMayoreo(int idVenta) {
      
        try {
            Query query = em.createNativeQuery("UPDATE VENTA_MAYOREO SET ID_STATUS_FK= ?,FECHA_PAGO = sysdate WHERE ID_VENTA_MAYOREO_PK = ? ");
            query.setParameter(1, 2);
            query.setParameter(2, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) 
        {
           
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
}
