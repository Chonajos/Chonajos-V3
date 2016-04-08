package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.negocio.NegocioBuscaVenta;
import com.web.chon.negocio.NegocioEntradaProductoCentral;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan
 */
@Stateless(mappedName = "ejbEntradaProductoCentral")
public class EjbEntradaProductoCentral implements NegocioEntradaProductoCentral {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia) {
        try {
            Query query = em.createNativeQuery("INSERT INTO entrada_producto_central (ID_ENTRADA,PRECIO_VENTA,TONELADAS,FECHA) VALUES(S_ENTRADA_PRODUCTO_CENTRAL.NEXTVAL,?,?,SYSDATE)");

            query.setParameter(1, entradaMercancia.getPrecio());
            query.setParameter(2, entradaMercancia.getCantidadToneladas());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroDay(String fechaInicio, String fechaFin) {
        try {
            System.out.println("dates : " + fechaFin + " " + fechaFin);
            Query query = em.createNativeQuery("SELECT * FROM ENTRADA_PRODUCTO_CENTRAL WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? ORDER BY FECHA ASC");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroWeek(String fechaInicio, String fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT AVG(PRECIO_VENTA) PRECIO_VENTA, SUM(TONELADAS)TONELADAS FROM ENTRADA_PRODUCTO_CENTRAL WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ?");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroMonth(String fechaInicio, String fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT AVG(PRECIO_VENTA) PRECIO_VENTA, SUM(TONELADAS)TONELADAS FROM ENTRADA_PRODUCTO_CENTRAL WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ?");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroYear(String fechaInicio, String fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT AVG(PRECIO_VENTA) PRECIO_VENTA, SUM(TONELADAS)TONELADAS FROM ENTRADA_PRODUCTO_CENTRAL WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ?");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int update(EntradaMercancia entradaMercancia) {

        try {
            Query query = em.createNativeQuery("UPDATE entrada_producto_central SET TONELADAS = ?,PRECIO_VENTA = ? WHERE ID_ENTRADA = ?");
          
            query.setParameter(1, entradaMercancia.getCantidadToneladas().toString());
            query.setParameter(2, entradaMercancia.getPrecio().toString());
            query.setParameter(3, entradaMercancia.getIdEntrada());

            return query.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

    }

}
