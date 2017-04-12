/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.negocio.NegocioProductoFacturado;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jramirez
 */
@Stateless(mappedName = "ejbProductoFacturado")
public class EjbProductoFacturado implements NegocioProductoFacturado {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(EjbSubProducto.class);

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_PRODUCTO_FACTURADO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int insert(ProductoFacturado pf) {
        System.out.println("ProductoFacturado: " + pf.toString());
        try {

            Query query = em.createNativeQuery("INSERT INTO PRODUCTO_FACTURADO (ID_PRODUCTO_FACTURADO_PK,ID_TIPO_LLAVE_FK,ID_LLAVE_FK,ID_FACTURA_FK,IMPORTE,CANTIDAD,KILOS) VALUES(?,?,?,?,?,?,?)");
            query.setParameter(1, pf.getIdProductoFacturadoPk());
            query.setParameter(2, pf.getIdTipoLlaveFk());
            query.setParameter(3, pf.getIdLlaveFk());
            query.setParameter(4, pf.getIdFacturaFk());
            query.setParameter(5, pf.getImporte());
            query.setParameter(6, pf.getCantidad() == null ? new BigDecimal(0) : pf.getCantidad());
            query.setParameter(7, pf.getKilos());

            return query.executeUpdate();

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int update(ProductoFacturado pf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(ProductoFacturado pf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdFacturaFk(BigDecimal idFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdPk(BigDecimal idPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdTipoFolioFk(BigDecimal idTipoFk, BigDecimal idVentaFk) {
        try {

            Query query = em.createNativeQuery("SELECT  PF.ID_TIPO_LLAVE_FK,PF.ID_LLAVE_FK,SUM(PF.IMPORTE)AS IMPORTE,SUM(PF.CANTIDAD) AS CANTIDAD,SUM(PF.KILOS)AS KILOS FROM PRODUCTO_FACTURADO PF\n"
                    + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP\n"
                    + "ON VMP.ID_V_M_P_PK = PF.ID_LLAVE_FK\n"
                    + "LEFT JOIN VENTA_MAYOREO VM\n"
                    + "ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK\n"
                    + "LEFT JOIN VENTA_PRODUCTO VP ON VP.ID_VENTA_PRODUCTO_PK=PF.ID_LLAVE_FK\n"
                    + "LEFT JOIN VENTA V ON V.ID_VENTA_PK = VP.ID_VENTA_PRODUCTO_PK\n"
                    + "WHERE VM.VENTASUCURSAL = ?  AND PF.ID_TIPO_LLAVE_FK= ? \n"
                    + "group by PF.ID_TIPO_LLAVE_FK,PF.ID_LLAVE_FK");
            query.setParameter(1, idVentaFk);
            query.setParameter(2, idTipoFk);
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }

    }

    @Override
    public List<Object[]> getProductosNoFacturados(BigDecimal idTipoFk, BigDecimal idSucursalFk, String fechaInicio, String fechaFin) {
        try {
            System.out.println("IdTipoVenta: " + idTipoFk);
            System.out.println("idSucursal: " + idSucursalFk);
            StringBuffer cadena = new StringBuffer("");
            Query query;
            if (idTipoFk.intValue() == 1) {//mayoreo
                cadena = new StringBuffer("SELECT * FROM (SELECT VMP.ID_V_M_P_PK,VMP.ID_VENTA_MAYOREO_FK,VM.VENTASUCURSAL,SUB.NOMBRE_SUBPRODUCTO ,TE.NOMBRE_EMPAQUE,VMP.CANTIDAD_EMPAQUE,VMP.KILOS_VENDIDOS,VMP.PRECIO_PRODUCTO,\n"
                        + "VMP.TOTAL_VENTA FROM VENTA_MAYOREO VM \n"
                        + "INNER JOIN VENTAMAYOREOPRODUCTO VMP ON \n"
                        + "VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK\n"
                        + "INNER JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK=VMP.ID_SUBPRODUCTO_FK\n"
                        + "INNER JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = VMP.ID_TIPO_EMPAQUE_FK\n"
                        + "WHERE (VM.ID_STATUS_FK=2 or VM.ID_STATUS_FK=3) and vm.ID_TIPO_VENTA_FK=1  AND VM.ID_SUCURSAL_FK = " + idSucursalFk + " \n"
                        + "AND TO_DATE(TO_CHAR(VM.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "') T1\n"
                        + "WHERE NOT EXISTS (SELECT * FROM PRODUCTO_FACTURADO PF WHERE PF.ID_LLAVE_FK=T1.ID_V_M_P_PK and PF.ID_TIPO_LLAVE_FK=" + idTipoFk + ")");
                query = em.createNativeQuery(cadena.toString());
//            query.setParameter(1, idSucursalFk);
//            query.setParameter(2, idTipoFk);
            } else {
                cadena = new StringBuffer("");
                query = em.createNativeQuery(cadena.toString());
            }

            List<Object[]> resultList = null;
            System.out.println("Query: " + query.toString());
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }

    }

    @Override
    public int deleteByIdFacturaFk(BigDecimal idFacturaFk) {
        System.out.println("IdFacturaPk: " + idFacturaFk);
        try {

            Query query = em.createNativeQuery("DELETE  PRODUCTO_FACTURADO WHERE ID_FACTURA_FK=?");
            query.setParameter(1, idFacturaFk);
            return query.executeUpdate();
        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return 0;
        }

    }

    @Override
    public List<Object[]> getProductosNoFacturadosAbonos(BigDecimal idTipoFk, BigDecimal folioAbono) {
        try {
            System.out.println("IdTipoVenta: " + idTipoFk);
            System.out.println("folioAbono: " + folioAbono);
            StringBuffer cadena = new StringBuffer("");
            Query query;
            if (idTipoFk.intValue() == 1) {//mayoreo AND T1.KILOS_VENDIDOS = PF.KILOS
                cadena = new StringBuffer("SELECT * FROM(select "
                        + "vmp.ID_V_M_P_PK,sub.NOMBRE_SUBPRODUCTO,te.NOMBRE_EMPAQUE, "
                        + "vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS,vmp.PRECIO_PRODUCTO,vmp.TOTAL_VENTA "
                        + "from ABONO_CREDITO ac inner join "
                        + "CREDITO cre on cre.ID_CREDITO_PK = ac.ID_CREDITO_FK "
                        + "inner join VENTA_MAYOREO vm on vm.ID_VENTA_MAYOREO_PK = cre.ID_VENTA_MAYOREO "
                        + "inner join VENTAMAYOREOPRODUCTO vmp on vmp.ID_VENTA_MAYOREO_FK = vm.ID_VENTA_MAYOREO_PK "
                        + "inner join SUBPRODUCTO sub on sub.ID_SUBPRODUCTO_PK = vmp.ID_SUBPRODUCTO_FK "
                        + "inner join TIPO_EMPAQUE te on te.ID_TIPO_EMPAQUE_PK = vmp.ID_TIPO_EMPAQUE_FK "
                        + "where ac.NUMERO_ABONO = " + folioAbono + " ) T1 "
                        + "WHERE NOT EXISTS (SELECT * FROM PRODUCTO_FACTURADO PF "
                        + "WHERE PF.ID_LLAVE_FK=T1.ID_V_M_P_PK AND  PF.ID_TIPO_LLAVE_FK=" + idTipoFk + " AND T1.KILOS_VENDIDOS = PF.KILOS )");
                query = em.createNativeQuery(cadena.toString());
            } else {//Menudeo AND T1.CANTIDAD_EMPAQUE = PF.KILOS
                cadena = new StringBuffer("SELECT * FROM(select "
                        + "vp.ID_VENTA_PRODUCTO_PK,sub2.NOMBRE_SUBPRODUCTO,te2.NOMBRE_EMPAQUE, "
                        + "vp.CANTIDAD_EMPAQUE,vp.KILOS_VENDIDOS,vp.PRECIO_PRODUCTO,vp.TOTAL_VENTA "
                        + "from ABONO_CREDITO ac inner join "
                        + "CREDITO cre on cre.ID_CREDITO_PK = ac.ID_CREDITO_FK "
                        + "inner join venta v on v.ID_VENTA_PK = cre.ID_VENTA_MENUDEO "
                        + "inner join VENTA_PRODUCTO vp on vp.ID_VENTA_FK = v.ID_VENTA_PK "
                        + "inner join SUBPRODUCTO sub2 on sub2.ID_SUBPRODUCTO_PK = vp.ID_SUBPRODUCTO_FK "
                        + "inner join TIPO_EMPAQUE te2 on te2.ID_TIPO_EMPAQUE_PK = vp.ID_TIPO_EMPAQUE_FK "
                        + "where ac.NUMERO_ABONO = " + folioAbono + " and cre.ESTATUS_CREDITO=2) T1 "
                        + "WHERE NOT EXISTS (SELECT * FROM PRODUCTO_FACTURADO PF "
                        + "WHERE PF.ID_LLAVE_FK=T1.ID_VENTA_PRODUCTO_PK and PF.ID_TIPO_LLAVE_FK=" + idTipoFk + " AND T1.CANTIDAD_EMPAQUE = PF.KILOS )");
                query = em.createNativeQuery(cadena.toString());
            }

            List<Object[]> resultList = null;
            System.out.println("Query: " + query.toString());
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            logger.error("Error > " + ex.getMessage());
            return null;
        }

    }

}
