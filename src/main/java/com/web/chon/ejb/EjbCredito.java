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

            Query query = em.createNativeQuery("INSERT INTO  CREDITO (ID_CREDITO_PK ,ID_CLIENTE_FK ,ID_VENTA_MENUDEO ,ID_VENTA_MAYOREO ,ID_USUARIO_CREDITO ,ID_TIPO_CREDITO_FK ,ESTATUS_CREDITO ,NUMERO_PROMESA_PAGO ,FECHA_INICIO_CREDITO ,FECHA_FIN_CREDITO ,FECHA_PROMESA_FIN_PAGO ,TAZA_INTERES) "
                    + " VALUES(S_CREDITO.nextval,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, credito.getIdClienteFk());
            query.setParameter(2, credito.getIdVentaMenudeo());
            query.setParameter(3, credito.getIdVentaMayoreo());
            query.setParameter(4, credito.getIdUsuarioCredito());
            query.setParameter(5, credito.getIdTipoCreditoFk());
            query.setParameter(6, credito.getEstatusCredito());
            query.setParameter(7, credito.getNumeroPromesaPago());
            query.setParameter(8, credito.getFechaInicioCredito());
            query.setParameter(9, credito.getFechaFinCredito());
            query.setParameter(10, credito.getFechaPromesaPago());
            query.setParameter(11, credito.getTazaInteres());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(Credito credito) {
        try {

            Query query = em.createNativeQuery("UPDATE CREDITO SET ID_CLIENTE_FK =? ,ID_VENTA_MENUDEO=? ,ID_VENTA_MAYOREO=? ,ID_USUARIO_CREDITO=? ,ID_TIPO_CREDITO_FK=? ,ESTATUS_CREDITO=? ,NUMERO_PROMESA_PAGO=? ,FECHA_INICIO_CREDITO=? ,FECHA_FIN_CREDITO=? ,FECHA_PROMESA_FIN_PAGO=? ,TAZA_INTERES=? WHERE ID_CREDITO_PK = ?");
            query.setParameter(1, credito.getIdClienteFk());
            query.setParameter(2, credito.getIdVentaMenudeo());
            query.setParameter(3, credito.getIdVentaMayoreo());
            query.setParameter(4, credito.getIdUsuarioCredito());
            query.setParameter(5, credito.getIdTipoCreditoFk());
            query.setParameter(6, credito.getEstatusCredito());
            query.setParameter(7, credito.getNumeroPromesaPago());
            query.setParameter(8, credito.getFechaInicioCredito());
            query.setParameter(9, credito.getFechaFinCredito());
            query.setParameter(10, credito.getFechaPromesaPago());
            query.setParameter(11, credito.getTazaInteres());
            query.setParameter(12, credito.getIdCreditoPk());
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

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ID_TIPO_CREDITO_FK,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES  FROM CREDITO");
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

            Query query = em.createNativeQuery("SELECT ID_CREDITO_PK,ID_CLIENTE_FK,ID_VENTA_MENUDEO,ID_VENTA_MAYOREO,ID_USUARIO_CREDITO,ID_TIPO_CREDITO_FK,ESTATUS_CREDITO,NUMERO_PROMESA_PAGO,FECHA_INICIO_CREDITO,FECHA_FIN_CREDITO,FECHA_PROMESA_FIN_PAGO,TAZA_INTERES  FROM CREDITO WHERE ID_CREDITO_PK = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
