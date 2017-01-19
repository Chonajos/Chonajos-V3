package com.web.chon.ejb;

import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioVentaProducto;
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
 * Ejb para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbVentaProducto")
public class EjbVentaProducto implements NegocioVentaProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getVentaProductoByIdVenta(BigDecimal idVenta) {
        try {

            Query query = em.createNativeQuery("select ID_SUBPRODUCTO_FK,CANTIDAD_EMPAQUE from VENTA_PRODUCTO where ID_VENTA_FK =?");
            query.setParameter(1, idVenta);
            List<Object[]> resultList = null;

            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbVentaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int insertarVentaProducto(VentaProducto ventaProducto, int idVenta) {
        Query query = em.createNativeQuery("INSERT INTO VENTA_PRODUCTO(ID_VENTA_PRODUCTO_PK,ID_SUBPRODUCTO_FK,PRECIO_PRODUCTO,KILOS_VENDIDOS,CANTIDAD_EMPAQUE,TOTAL_VENTA,ID_TIPO_EMPAQUE_FK,ID_VENTA_FK)"
                + "VALUES(S_VENTA_PRODUCTO.nextVal,?,?,?,?,?,?,?)");
        query.setParameter(1, ventaProducto.getIdProductoFk());
        query.setParameter(2, ventaProducto.getPrecioProducto());
        query.setParameter(3, ventaProducto.getKilosVenta());
        query.setParameter(4, ventaProducto.getCantidadEmpaque());
        query.setParameter(5, ventaProducto.getTotal());
        query.setParameter(6, ventaProducto.getIdTipoEmpaqueFk().equals(new BigDecimal(-1)) ? null : ventaProducto.getIdTipoEmpaqueFk());
        query.setParameter(7, new BigDecimal(idVenta));

        return query.executeUpdate();
    }

    @Override
    public List<Object[]> getProductosByIdVentaFK(BigDecimal idVentaFK) {
        try {

            Query query = em.createNativeQuery("select sub.NOMBRE_SUBPRODUCTO, vp.CANTIDAD_EMPAQUE, vp.PRECIO_PRODUCTO, sub.ID_SUBPRODUCTO_PK from VENTA_PRODUCTO vp\n"
                    + "inner join SUBPRODUCTO sub on sub.ID_SUBPRODUCTO_PK = vp.ID_SUBPRODUCTO_FK\n"
                    + "where ID_VENTA_FK = ?");
            query.setParameter(1, idVentaFK);
            List<Object[]> resultList = null;

            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbVentaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getReporteVenta(String fechaInicio, String fechaFin, BigDecimal idSucursal) {
        try {
            StringBuffer txtQuery = new StringBuffer("SELECT exm.ID_EXMEN_PK,sp.NOMBRE_SUBPRODUCTO,SUM(vp.CANTIDAD_EMPAQUE)AS kilo_vendidos,SUM(vp.PRECIO_PRODUCTO*vp.CANTIDAD_EMPAQUE) venta_total,"
                    + " SUM(vp.PRECIO_PRODUCTO*vp.CANTIDAD_EMPAQUE)/SUM(vp.CANTIDAD_EMPAQUE) AS precio_promedio,exm.KILOS,mt.COSTOMERMA,"
                    + " (SELECT NVL(SUM(AEM.KILOS_AJUSTADOS-AEM.KILOS_ANTERIOR),0) FROM AJUSTE_EXISTENCIA_MENUDEO AEM WHERE AEM.ID_EXISTENCIA_MENUDEO_FK = exm.ID_EXMEN_PK ) AS AJUSTES,"
                    + " (SELECT NVL(SUM(EMP.KILOS_TOTALES),0) FROM ENTRADAMENUDEOPRODUCTO EMP"
                    + " INNER JOIN ENTRADAMERCANCIAMENUDEO EMM ON EMM.ID_EMM_PK = EMP.ID_EMM_FK"
                    + " WHERE EMP.ID_SUBPRODUCTO_FK = sp.ID_SUBPRODUCTO_PK AND EMM.ID_SUCURSAL_FK =exm.ID_SUCURSAL_FK) AS ENTRADA,"
                    + " (SELECT NVL(SUM(EMP.KILOS_TOTALES*EMP.PRECIO),0) FROM ENTRADAMENUDEOPRODUCTO EMP"
                    + " INNER JOIN ENTRADAMERCANCIAMENUDEO EMM ON EMM.ID_EMM_PK = EMP.ID_EMM_FK"
                    + " WHERE EMP.ID_SUBPRODUCTO_FK = sp.ID_SUBPRODUCTO_PK AND EMM.ID_SUCURSAL_FK =exm.ID_SUCURSAL_FK) AS COSTO_TOTAL_ENTRADA"
                    + " FROM VENTA_PRODUCTO vp"
                    + " INNER JOIN SUBPRODUCTO sp ON sp.ID_SUBPRODUCTO_PK =VP.ID_SUBPRODUCTO_FK"
                    + " INNER JOIN VENTA v ON v.ID_VENTA_PK  = vp.ID_VENTA_FK AND v.STATUS_FK !='4' AND v.ID_SUCURSAL_FK ='" + idSucursal + "'"
                    + " INNER JOIN MANTENIMIENTO_PRECIO mt ON mt.ID_SUBPRODUCTO_FK = sp.ID_SUBPRODUCTO_PK AND mt.ID_SUCURSAL_FK = '" + idSucursal + "'"
                    + " INNER JOIN EXISTENCIAMENUDEO exm ON exm.ID_SUBPRODUCTO_FK =sp.ID_SUBPRODUCTO_PK AND exm.ID_SUCURSAL_FK ='" + idSucursal + "'"
                    + " AND v.STATUS_FK !=4");

            if (fechaInicio != null && fechaFin != null) {
                txtQuery.append(" AND TO_DATE(TO_CHAR(v.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "'");
            }
            txtQuery.append(" GROUP BY exm.ID_SUCURSAL_FK,sp.ID_SUBPRODUCTO_PK,sp.NOMBRE_SUBPRODUCTO,exm.KILOS,mt.COSTOMERMA,exm.ID_EXMEN_PK, exm.ID_SUBPRODUCTO_FK ORDER BY kilo_vendidos DESC");

            Query query = em.createNativeQuery(txtQuery.toString());

            return query.getResultList();

        } catch (Exception ex) {
            System.out.println("error > " + ex.getMessage());
            return null;
        }
    }

}
