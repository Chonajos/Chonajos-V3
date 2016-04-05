package com.web.chon.ejb;

import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Venta;
import com.web.chon.negocio.NegocioSubProducto;
import com.web.chon.negocio.NegocioVenta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * Ejb para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbVenta")
public class EjbVenta implements NegocioVenta {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVenta(Venta venta) {
        Query query = em.createNativeQuery("INSERT INTO VENTA(ID_VENTA_PK,FECHA_VENTA,ID_CLIENTE_FK,ID_VENDEDOR_FK,STATUS_FK,ID_SUCURSAL_FK) VALUES(?,sysdate,?,?,1,?)");
        System.out.println("venta ejb :" + venta.toString());
        query.setParameter(1, venta.getIdVentaPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFk());
        query.setParameter(4, venta.getIdSucursal());
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_VENTA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, int idSucursal, int idStatusVenta) {
        Query query;
        String q = "";
        String cadena = "SELECT VEN.*, (CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, "
                + "(USU.NOMBRE_USUARIO||' '||USU.APATERNO_USUARIO ||' '||USU.AMATERNO_USUARIO ) AS VENDEDOR, (select sum(VTP.TOTAL_VENTA) "
                + "FROM VENTA_PRODUCTO VTP WHERE VTP.ID_VENTA_FK =ven.ID_VENTA_PK) AS TOTAL_VENTA FROM VENTA ven "
                + "INNER JOIN CLIENTE CLI ON CLI.ID_CLIENTE = ven.ID_CLIENTE_FK "
                + "INNER JOIN USUARIO USU ON USU.ID_USUARIO_PK = ven.ID_VENDEDOR_FK "
                + "WHERE TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? ";
        String post = "ORDER BY ven.ID_VENTA_PK";
        if (idSucursal == 0 && idStatusVenta == 0) {

            q = cadena + post;
            System.out.println(q);
            System.out.println("No le hace nada a la cadena original");
            
            query = em.createNativeQuery(q);
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
        } else if (idSucursal  != 0 && idStatusVenta == 0) {
            System.out.println("if 2");
            q = cadena + "and ven.ID_SUCURSAL_FK=? " + post;
            System.out.println(q);
            query = em.createNativeQuery(q);
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idSucursal);
        } else if (idSucursal == 0 && idStatusVenta != 0) {
            q = cadena + "and ven.STATUS_FK = ? " + post;
            System.out.println(q);
            query = em.createNativeQuery(q);
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idStatusVenta);

        } else {
            q = cadena + "and ven.ID_SUCURSAL_FK = ? and ven.STATUS_FK= ? " + post;
            query = em.createNativeQuery(q);
            System.out.println(q);
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idSucursal);
            query.setParameter(4, idStatusVenta);
        }
        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

}
