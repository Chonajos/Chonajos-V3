package com.web.chon.ejb;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.negocio.NegocioBuscaVenta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioAnalisisMercado;
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
@Stateless(mappedName = "ejbAnalisisMercado")
public class EjbAnalisisMercado implements NegocioAnalisisMercado {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int saveEntradaProductoCentral(AnalisisMercado entradaMercancia) {
        try {
            Query query = em.createNativeQuery("INSERT INTO ANALISIS_MERCADO (ID_ENTRADA,PRECIO_VENTA,TONELADAS,FECHA,ID_SUBPRODUCTO) VALUES(S_ANALISIS_MERCADO.NEXTVAL,?,?,SYSDATE,?)");

            query.setParameter(1, entradaMercancia.getPrecio());
            query.setParameter(2, entradaMercancia.getCantidadToneladas());
            query.setParameter(3, entradaMercancia.getIdProductoFk());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroDay(String fechaInicio, String fechaFin, String idProducto) {
        try {

            Query query = em.createNativeQuery("SELECT * FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? AND ID_SUBPRODUCTO = ? ORDER BY FECHA ");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idProducto.trim());

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroWeek(String fechaInicio, String fechaFin, String idProducto) {
        try {
            Query query = em.createNativeQuery("SELECT NVL(AVG(PRECIO_VENTA),0) PRECIO_VENTA, NVL(SUM(TONELADAS),0)TONELADAS,NVL(SUM(REMANENTE),0) REMANENTE FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? AND ID_SUBPRODUCTO = ? ORDER BY FECHA ");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idProducto.trim());

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroMonth(String fechaInicio, String fechaFin, String idSubproducto) {
        try {
            Query query = em.createNativeQuery("SELECT NVL(AVG(PRECIO_VENTA),0) PRECIO_VENTA, NVL(SUM(TONELADAS),0)TONELADAS FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? AND ID_SUBPRODUCTO = ? ");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idSubproducto);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getEntradaProductoByFiltroYear(String fechaInicio, String fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT AVG(PRECIO_VENTA) PRECIO_VENTA, SUM(TONELADAS)TONELADAS FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ?");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int update(AnalisisMercado entradaMercancia) {

        try {
            Query query = em.createNativeQuery("UPDATE ANALISIS_MERCADO SET TONELADAS = ?,PRECIO_VENTA = ? WHERE ID_ENTRADA = ?");

            query.setParameter(1, entradaMercancia.getCantidadToneladas().toString());
            query.setParameter(2, entradaMercancia.getPrecio().toString());
            query.setParameter(3, entradaMercancia.getIdEntrada());

            return query.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

    }

    @Override
    public List<Object[]> getEntradaProductoByIdProducto(String idProducto) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN SYSDATE-1 and SYSDATE AND ID_SUBPRODUCTO = ? ORDER BY FECHA ");

            query.setParameter(1, idProducto.trim());

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public BigDecimal getRemanente(String fechaInicio, String fechaFin, String idProducto) {
        try {

            Query query = em.createNativeQuery("SELECT NVL(SUM(REMANENTE),0) FROM ANALISIS_MERCADO WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ? AND ? AND ID_SUBPRODUCTO = ? ORDER BY FECHA ");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);
            query.setParameter(3, idProducto.trim());

            return new BigDecimal(query.getSingleResult().toString());
        } catch (Exception ex) {
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(0);
        }
    }

    @Override
    public int updateByIdProductoAndFecha(AnalisisMercado entradaMercancia) {

        try {
            Query query = em.createNativeQuery("UPDATE ANALISIS_MERCADO SET REMANENTE = ? WHERE TO_DATE(TO_CHAR(FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') = ? AND ID_SUBPRODUCTO = ?");
            System.out.println(entradaMercancia.toString());
            query.setParameter(1, entradaMercancia.getRemantePorSemana());
            query.setParameter(2, TiempoUtil.getFechaDDMMYYYY(entradaMercancia.getFecha()));
            query.setParameter(3, entradaMercancia.getIdProductoFk());
            System.out.println("query " + query.toString());
            return query.executeUpdate();
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            Logger.getLogger(EjbAnalisisMercado.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

    }

}
