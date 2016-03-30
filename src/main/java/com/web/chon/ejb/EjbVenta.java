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
        Query query = em.createNativeQuery("INSERT INTO VENTA(ID_VENTA_PK,FECHA_VENTA,ID_CLIENTE_FK,ID_VENDEDOR_FK,STATUS_FK) VALUES(?,sysdate,?,?,1)");
        System.out.println("venta ejb :" + venta.toString());
        query.setParameter(1, venta.getIdVentaPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFk());
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_VENTA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin) {

        try {
            Query query = em.createNativeQuery("SELECT VEN.*, (CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, "
                    + "(USU.NOMBRE_USUARIO||' '||USU.APATERNO_USUARIO ||' '||USU.AMATERNO_USUARIO ) AS VENDEDOR, (select sum(VTP.TOTAL_VENTA) "
                    + "FROM VENTA_PRODUCTO VTP WHERE VTP.ID_VENTA_FK =ven.ID_VENTA_PK) AS TOTAL_VENTA FROM VENTA ven "
                    + "INNER JOIN CLIENTE CLI ON CLI.ID_CLIENTE = ven.ID_CLIENTE_FK "
                    + "INNER JOIN USUARIO USU ON USU.ID_USUARIO_PK = ven.ID_VENDEDOR_FK "
                    + "WHERE TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? ORDER BY ven.ID_VENTA_PK");
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            List<Object[]> lstObject = query.getResultList();

            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

}
