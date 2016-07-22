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

/**
 *
 * @author juan
 */
@Stateless(mappedName = "ejbCredito")
public class EjbCredito implements NegocioCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(Credito credito) {

        try {

            Query query = em.createNativeQuery("INSERT INTO  CREDITO (ID_CREDITO_PK ,ID_CLIENTE_FK ,ID_VENTA_MENUDEO ,ID_VENTA_MAYOREO ,ID_USUARIO_CREDITO  ,ESTATUS_CREDITO ,NUMERO_PROMESA_PAGO ,FECHA_INICIO_CREDITO ,FECHA_FIN_CREDITO ,FECHA_PROMESA_FIN_PAGO ,TAZA_INTERES,PLAZOS) "
                    + " VALUES(S_CREDITO.nextval,?,?,?,?,?,?,?,?,?,?,?)");
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
            query.setParameter(11, credito.getPlasos());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(Credito credito) {
        try {

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
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCreditosActivos(BigDecimal idCliente) {
        try {

            Query query = em.createNativeQuery("select cre.ID_CREDITO_PK as folio,stc.NOMBRE_STATUS,cre.FECHA_INICIO_CREDITO,cre.PLAZOS,cre.MONTO_CREDITO \n" +
",\n" +
"(select NVL(sum(ac.MONTO_ABONO),0)from ABONO_CREDITO ac\n" +
"where ac.ID_CREDITO_FK= cre.ID_CREDITO_PK and ac.ESTATUS=1) \n" +
"as Total_Abonado,cre.ESTATUS_CREDITO,cre.ACUENTA,cre.STATUSACUENTA\n" +
"from credito cre\n" +
"inner join STATUS_CREDITO stc\n" +
"on stc.ID_STATUS_CREDITO_PK = cre.ESTATUS_CREDITO\n" +
"where cre.ESTATUS_CREDITO=1 and cre.ID_CLIENTE_FK ='" + idCliente + "'");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus) {
        try {

            Query query = em.createNativeQuery("UPDATE CREDITO SET ESTATUS_CREDITO = ? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, estatus);
            query.setParameter(2, idCreditoPk);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

}
