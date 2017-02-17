package com.web.chon.ejb;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.negocio.NegocioAbonoCredito;
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
 * @author juan
 */
@Stateless(mappedName = "ejbAbonoCredito")
public class EjbAbonoCredito implements NegocioAbonoCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AbonoCredito abonoCredito) {

        try {

            Query query = em.createNativeQuery("INSERT INTO  ABONO_CREDITO (ID_ABONO_CREDITO_PK ,"
                    + "ID_CREDITO_FK "
                    + ",MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK,TIPO_ABONO_FK,ESTATUS,NUMERO_CHEQUE,"
                    + "LIBRADOR,FECHA_COBRO,BANCO_EMISOR,NUMERO_FACTURA,REFERENCIA,CONCEPTO,FECHA_TRANSFERENCIA,NUMERO_ABONO) "
                    + " VALUES(?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, abonoCredito.getIdAbonoCreditoPk());
            query.setParameter(2, abonoCredito.getIdCreditoFk());
            query.setParameter(3, abonoCredito.getMontoAbono());
            //fecha
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
            query.setParameter(5, abonoCredito.getIdtipoAbonoFk());
            query.setParameter(6, abonoCredito.getEstatusAbono());
            query.setParameter(7, abonoCredito.getNumeroCheque());
            query.setParameter(8, abonoCredito.getLibrador());
            query.setParameter(9, abonoCredito.getFechaCobro());
            query.setParameter(10, abonoCredito.getBanco());
            query.setParameter(11, abonoCredito.getFactura());
            query.setParameter(12, abonoCredito.getReferencia());
            query.setParameter(13, abonoCredito.getConcepto());
            query.setParameter(14, abonoCredito.getFechaTransferencia());
            query.setParameter(15, abonoCredito.getNumeroAbono());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(AbonoCredito abonoCredito) {
        try {

            Query query = em.createNativeQuery("UPDATE ABONO_CREDITO SET ID_CREDITO_FK =? ,MONTO_ABONO =? ,FECHA_ABONO =? ,ID_USUARIO_FK =? ,ESTATUS=? WHERE ID_ABONO_CREDITO_PK = ?");
            query.setParameter(1, abonoCredito.getIdCreditoFk());
            query.setParameter(2, abonoCredito.getMontoAbono());
            query.setParameter(3, abonoCredito.getFechaAbono());
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
            query.setParameter(5, abonoCredito.getEstatusAbono());
            query.setParameter(6, abonoCredito.getIdAbonoCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idAbonoCredito) {
        try {

            Query query = em.createNativeQuery("UPDATE ABONO_CREDITO SET ESTATUS=2 WHERE ID_ABONO_CREDITO_PK = ?");

            query.setParameter(1, idAbonoCredito);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_ABONO_CREDITO_PK ,ID_CREDITO_FK ,MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK  FROM ABONO_CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idAbonoCredito) {
        try {

            Query query = em.createNativeQuery("SELECT ID_ABONO_CREDITO_PK ,ID_CREDITO_FK ,MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK  FROM ABONO_CREDITO WHERE ID_ABONO_CREDITO_PK  = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idAbonoCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_ABONO_CREDITO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getByIdCredito(BigDecimal idAbonoCredito) {
        try {
            Query query = em.createNativeQuery("select * from ABONO_CREDITO abc\n"
                    + "where abc.ESTATUS = 2 and abc.TIPO_ABONO_FK=3 and abc.ID_CREDITO_FK ='" + idAbonoCredito + "'");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getChequesPendientes(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idClienteFk, BigDecimal filtro, BigDecimal filtroStatus) {

        StringBuffer cadena = new StringBuffer("select ab.*,dc.ID_DOCUMENTO_PK,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO "
                + "||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, SD.DESCRIPCION, dc.ID_STATUS_FK,cre.ID_CLIENTE_FK from ABONO_CREDITO ab inner join USUARIO "
                + "u on u.ID_USUARIO_PK = ab.ID_USUARIO_FK inner join DOCUMENTOS_COBRAR dc on dc.ID_ABONO_FK "
                + "= ab.ID_ABONO_CREDITO_PK inner join CREDITO cre on cre.ID_CREDITO_PK = ab.ID_CREDITO_FK inner "
                + "join cliente cli on cli.ID_CLIENTE = cre.ID_CLIENTE_FK "
                + "inner join STATUS_DOCUMENTOS SD ON SD.ID_STATUS_DOCUMENTO_PK = DC.ID_STATUS_FK ");
        if (filtro.intValue() == 1) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')<= '" + fechaInicio + "'");
            cadena.append(" and ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='" + filtroStatus + "'");
        } else if (filtro.intValue() == 2) {
            cadena.append(" WHERE TO_DATE(TO_CHAR(ab.FECHA_COBRO,'dd/mm/yyyy'),'dd/mm/yyyy')> '" + fechaInicio + "'");
            cadena.append(" and ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='" + filtroStatus + "'");
        } else {
            cadena.append(" where ab.ESTATUS=1 and ab.TIPO_ABONO_FK=3 and dc.ID_STATUS_FK='" + filtroStatus + "'");
        }

        if (idSucursal != null && !idSucursal.equals("")) {
            cadena.append(" and u.ID_SUCURSAL_FK='" + idSucursal + "'");
        }
        if (idClienteFk != null && !idClienteFk.equals("")) {
            cadena.append(" and cli.ID_CLIENTE ='" + idClienteFk + "'");
        }

        cadena.append(" order by ab.FECHA_COBRO asc");

        Query query;

        query = em.createNativeQuery(cadena.toString());

        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public List<Object[]> getByIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk) {
        try {

            Query query = em.createNativeQuery("SELECT ab.ID_ABONO_CREDITO_PK ,ab.ID_CREDITO_FK ,ab.MONTO_ABONO ,ab.FECHA_ABONO ,ab.ID_USUARIO_FK from credito c\n"
                    + "inner join ABONO_CREDITO ab on ab.ID_CREDITO_FK = c.ID_CREDITO_PK\n"
                    + "where c.ID_VENTA_MAYOREO =?");
            List<Object[]> resultList = null;
            query.setParameter(1, idVentaMayoreoFk);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getByIdVentaMenudeoFk(BigDecimal idVentaMenudeoFk) {
        try {

            Query query = em.createNativeQuery("SELECT ab.ID_ABONO_CREDITO_PK ,ab.ID_CREDITO_FK ,ab.MONTO_ABONO ,ab.FECHA_ABONO ,ab.ID_USUARIO_FK from credito c\n"
                    + "inner join ABONO_CREDITO ab on ab.ID_CREDITO_FK = c.ID_CREDITO_PK\n"
                    + "where c.ID_VENTA_MENUDEO =?");
            List<Object[]> resultList = null;
            query.setParameter(1, idVentaMenudeoFk);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getAbonosByIdCredito(BigDecimal idCredito) {

        try {

            Query query = em.createNativeQuery("SELECT ID_ABONO_CREDITO_PK,ID_CREDITO_FK,MONTO_ABONO,FECHA_ABONO,ID_USUARIO_FK,"
                    + "TIPO_ABONO_FK,ESTATUS,NUMERO_CHEQUE,LIBRADOR,FECHA_COBRO,BANCO_EMISOR,NUMERO_FACTURA,REFERENCIA,CONCEPTO,"
                    + "FECHA_TRANSFERENCIA FROM ABONO_CREDITO ABC WHERE ABC.ID_CREDITO_FK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getHistorialAbonos(BigDecimal idClienteFk, BigDecimal idCajeroFk, String fechaInicio, String fechaFin, BigDecimal idTipoPagoFk, BigDecimal idAbonoPk, BigDecimal idCreditoFk) {
        StringBuffer cadena = new StringBuffer("select ab.ID_ABONO_CREDITO_PK as folio,(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,\n"
                + "(usu.NOMBRE_USUARIO||' '||usu.APATERNO_USUARIO) AS CAJERO,\n"
                + "ab.ID_CREDITO_FK as folio_credito,\n"
                + "ab.FECHA_ABONO,tipo.NOMBRE_ABONO,ab.MONTO_ABONO,\n"
                + "ab.ID_USUARIO_FK,ab.TIPO_ABONO_FK,ab.ESTATUS,\n"
                + "ab.NUMERO_CHEQUE,ab.LIBRADOR,ab.FECHA_COBRO,\n"
                + "ab.BANCO_EMISOR,ab.NUMERO_FACTURA,ab.REFERENCIA,ab.CONCEPTO,ab.FECHA_TRANSFERENCIA,\n"
                + "cli.ID_CLIENTE,ab.NUMERO_ABONO \n"
                + "from ABONO_CREDITO ab inner join credito cre on cre.ID_CREDITO_PK = ab.ID_CREDITO_FK\n"
                + "inner join cliente cli on cli.ID_CLIENTE = cre.ID_CLIENTE_FK\n"
                + "inner join usuario usu  on usu.ID_USUARIO_PK = ab.ID_USUARIO_FK\n"
                + "inner join TIPO_ABONO tipo on tipo.ID_TIPO_ABONO_PK = ab.TIPO_ABONO_FK\n"
                + "WHERE  TO_DATE(TO_CHAR(ab.FECHA_ABONO,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + " ' AND '" + fechaFin + "'");

        if (idClienteFk != null && !idClienteFk.equals("")) {
            cadena.append(" and cli.ID_CLIENTE =" + idClienteFk + "");
        }
        if (idCajeroFk != null && !idCajeroFk.equals("")) {
            cadena.append(" and ab.ID_USUARIO_FK =" + idCajeroFk + "");
        }
        if (idCreditoFk != null && !idCreditoFk.equals("")) {
            cadena.append(" and ab.ID_CREDITO_FK =" + idCreditoFk + "");
        }
        if (idTipoPagoFk != null && !idTipoPagoFk.equals("")) {
            cadena.append(" and ab.TIPO_ABONO_FK =" + idTipoPagoFk + "");
        }
        if (idAbonoPk != null && !idAbonoPk.equals("")) {
            cadena.append(" and ab.ID_ABONO_CREDITO_PK =" + idAbonoPk + "");
        }

        Query query;
        query = em.createNativeQuery(cadena.toString());
        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public List<Object[]> getHistorialCrediticio(BigDecimal idClienteFk, String fechaInicio, String fechaFin) {
        try {
            Query query;

            StringBuffer cadena = new StringBuffer("select ab.NUMERO_ABONO,ab.FECHA_ABONO,sum(ab.MONTO_ABONO) as monto from ABONO_CREDITO ab \n"
                    + "inner join credito cre on cre.ID_CREDITO_PK = ab.ID_CREDITO_FK");

            if (!fechaInicio.equals("") && !fechaFin.equals("")) {
                cadena.append(" WHERE TO_DATE(TO_CHAR(ab.FECHA_ABONO,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN  '" + fechaInicio + "' and '" + fechaFin + "'");
                cadena.append(" AND ");
            } else {
                cadena.append(" WHERE ");

            }
            cadena.append(" cre.ID_CLIENTE_FK= ? \n"
                    + " group by ab.NUMERO_ABONO,ab.FECHA_ABONO\n"
                    + " order by ab.FECHA_ABONO asc");

            query = em.createNativeQuery(cadena.toString());

            System.out.println("Query: " + query.toString());
            List<Object[]> resultList = null;
            query.setParameter(1, idClienteFk);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public BigDecimal getTotalAbonos(BigDecimal idClienteFk, String fechaInicio) {
        Query query = em.createNativeQuery("select nvl(sum(ab.MONTO_ABONO),0) from ABONO_CREDITO ab\n"
                + "inner join credito cre on cre.ID_CREDITO_PK = ab.ID_CREDITO_FK\n"
                + "WHERE TO_DATE(TO_CHAR(ab.FECHA_ABONO,'dd/mm/yyyy'),'dd/mm/yyyy')  <  '" + fechaInicio + "' \n"
                + " and cre.ID_CLIENTE_FK = " + idClienteFk);
        BigDecimal d = new BigDecimal(query.getSingleResult().toString());
        return (d);
    }

}
