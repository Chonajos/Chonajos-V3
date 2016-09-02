package com.web.chon.ejb;

import com.web.chon.dominio.Credito;
import com.web.chon.negocio.NegocioCredito;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.transform.Source;

/**
 *
 * @author juan
 */
@Stateless(mappedName = "ejbCredito")
public class EjbCredito implements NegocioCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(Credito credito, int IdCredito) {

        try {

            Query query = em.createNativeQuery("INSERT INTO  CREDITO (ID_CREDITO_PK ,ID_CLIENTE_FK ,ID_VENTA_MENUDEO ,ID_VENTA_MAYOREO ,ID_USUARIO_CREDITO  ,ESTATUS_CREDITO ,NUMERO_PROMESA_PAGO ,FECHA_INICIO_CREDITO ,FECHA_FIN_CREDITO ,FECHA_PROMESA_FIN_PAGO ,TAZA_INTERES,PLAZOS,NUMERO_PAGOS,MONTO_CREDITO,ACUENTA,STATUSACUENTA) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, IdCredito);
            query.setParameter(2, credito.getIdClienteFk());
            query.setParameter(3, credito.getIdVentaMenudeo());
            query.setParameter(4, credito.getIdVentaMayoreo());
            query.setParameter(5, credito.getIdUsuarioCredito());
            query.setParameter(6, credito.getEstatusCredito());
            query.setParameter(7, credito.getNumeroPromesaPago());
            query.setParameter(8, credito.getFechaInicioCredito());
            query.setParameter(9, credito.getFechaFinCredito());
            query.setParameter(10, credito.getFechaPromesaPago());
            query.setParameter(11, credito.getTazaInteres());
            query.setParameter(12, credito.getPlasos());
            query.setParameter(13, credito.getNumeroPagos());
            query.setParameter(14, credito.getMontoCredito());
            query.setParameter(15, credito.getDejaCuenta());
            query.setParameter(16, credito.getStatusACuenta());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(Credito credito) {
        try {
            System.out.println(credito.toString());

            Query query = em.createNativeQuery("UPDATE CREDITO SET ID_CLIENTE_FK =? ,ID_VENTA_MENUDEO=? ,ID_VENTA_MAYOREO=? ,ID_USUARIO_CREDITO=?  ,ESTATUS_CREDITO=? ,NUMERO_PROMESA_PAGO=? ,FECHA_INICIO_CREDITO=? ,FECHA_FIN_CREDITO=? ,FECHA_PROMESA_FIN_PAGO=? ,TAZA_INTERES=? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, credito.getIdClienteFk());
            query.setParameter(2, credito.getIdVentaMenudeo());
            query.setParameter(3, credito.getIdVentaMayoreo());
            query.setParameter(4, credito.getIdUsuarioCredito());
//            query.setParameter(5, credito.getIdTipoCreditoFk());
            query.setParameter(5, credito.getEstatusCredito());
            query.setParameter(6, credito.getNumeroPromesaPago());
            query.setParameter(7, credito.getFechaInicioCredito());
            query.setParameter(8, credito.getFechaFinCredito());
            query.setParameter(9, credito.getFechaPromesaPago());
            query.setParameter(10, credito.getTazaInteres());
            query.setParameter(11, credito.getIdCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idCredito) {
        try {

            Query query = em.createNativeQuery("DELETE CREDITO WHERE ID_CREDITO_PK = ?");

            query.setParameter(1, idCredito);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idCredito) {
        try {

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO WHERE ID_CREDITO_PK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditosActivos(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("select cre.ID_CREDITO_PK as folio,stc.NOMBRE_STATUS,cre.FECHA_INICIO_CREDITO,cre.PLAZOS,cre.MONTO_CREDITO,\n"
                    + "(select NVL(sum(ac.MONTO_ABONO),0)from ABONO_CREDITO ac\n"
                    + "where ac.ID_CREDITO_FK= cre.ID_CREDITO_PK and ac.ESTATUS=1)\n"
                    + "as Total_Abonado,\n"
                    + "cre.ESTATUS_CREDITO,cre.ACUENTA,cre.STATUSACUENTA, cre.NUMERO_PAGOS,\n"
                    + "(select NVL(sum(ac.MONTO_ABONO),0)from ABONO_CREDITO ac\n"
                    + "where ac.ID_CREDITO_FK= cre.ID_CREDITO_PK and ac.ESTATUS=2)\n"
                    + "as CHEQUES_PENDIENTES\n"
                    + "from credito cre\n"
                    + "inner join STATUS_CREDITO stc\n"
                    + "on stc.ID_STATUS_CREDITO_PK = cre.ESTATUS_CREDITO\n"
                    + "where cre.ESTATUS_CREDITO=1 and cre.ID_CLIENTE_FK ='" + idCliente + "' order by cre.FECHA_INICIO_CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus) {
        try {

            Query query = em.createNativeQuery("UPDATE CREDITO SET ESTATUS_CREDITO = ?, FECHA_FIN_CREDITO = sysdate WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, estatus);
            query.setParameter(2, idCreditoPk);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int updateACuenta(Credito credito) {
        try {
            Query query = em.createNativeQuery("UPDATE CREDITO SET STATUSACUENTA = ? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, credito.getStatusACuenta());
            query.setParameter(2, credito.getIdCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int nextVal() {
        try {
            Query query = em.createNativeQuery("select S_CREDITO.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public List<Object[]> getTotalAbonado(BigDecimal idCredito) {
        try {

            Query query = em.createNativeQuery("select c.ID_CREDITO_PK,c.MONTO_CREDITO, sum(ab.MONTO_ABONO) as TotalAbonado from CREDITO c \n"
                    + "inner join ABONO_CREDITO ab\n"
                    + "on ab.ID_CREDITO_FK = c.ID_CREDITO_PK\n"
                    + "where ab.ESTATUS=1 \n"
                    + "and c.ID_CREDITO_PK= ?\n"
                    + "group by  c.ID_CREDITO_PK,c.MONTO_CREDITO\n");
            List<Object[]> resultList = null;
            query.setParameter(1, idCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Object[]> getAllCreditosActivos() {
        Query query = em.createNativeQuery("SELECT CRE.ID_CREDITO_PK AS FOLIO,STC.NOMBRE_STATUS,CRE.FECHA_INICIO_CREDITO,CRE.PLAZOS,CRE.MONTO_CREDITO, "
                + " (SELECT NVL(SUM(ac.MONTO_ABONO),0)FROM ABONO_CREDITO AC "
                + " WHERE AC.ID_CREDITO_FK= CRE.ID_CREDITO_PK AND AC.ESTATUS=1) "
                + " AS Total_Abonado, "
                + " CRE.ESTATUS_CREDITO,CRE.ACUENTA,CRE.STATUSACUENTA, CRE.NUMERO_PAGOS, "
                + " (SELECT NVL(SUM(ac.MONTO_ABONO),0)from ABONO_CREDITO AC "
                + " WHERE AC.ID_CREDITO_FK= CRE.ID_CREDITO_PK AND AC.ESTATUS=2) "
                + " AS CHEQUES_PENDIENTES,(C.NOMBRE ||' '||C.APELLIDO_PATERNO||' '||C.APELLIDO_MATERNO)AS NOMBRE_COMPLETO,(C.LADAOFICINA||C.TELEFONO_FIJO) AS TELEFONO_FIJO,(C.LADACELULAR||C.TELEFONO_MOVIL) AS TELEFONO_MOVIL, "
                + " (SELECT COR.CORREO FROM CORREOS COR WHERE COR.ID_CLIENTE_FK =C.ID_CLIENTE AND ROWNUM =1)AS CORREO, CRE.FECHA_PROMESA_FIN_PAGO "
                + " FROM CREDITO CRE "
                + " INNER JOIN STATUS_CREDITO STC "
                + " ON STC.ID_STATUS_CREDITO_PK = CRE.ESTATUS_CREDITO "
                + " INNER JOIN CLIENTE C ON C.ID_CLIENTE = CRE.ID_CLIENTE_FK "
                + " WHERE CRE.ESTATUS_CREDITO=1 ORDER BY CRE.FECHA_INICIO_CREDITO");

        try {

            return query.getResultList();

        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditosByIdVentaMenudeo(BigDecimal idVenta) {

        try {

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO WHERE ID_VENTA_MENUDEO = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idVenta);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     @Override
    public List<Object[]> getCreditosByIdVentaMayoreo(BigDecimal idVentaMayoreo) 
    {
        try {
            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES,PLAZOS  FROM CREDITO WHERE ID_VENTA_MAYOREO = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idVentaMayoreo);
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
