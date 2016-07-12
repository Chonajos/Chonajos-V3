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

            Query query = em.createNativeQuery("INSERT INTO  ABONO_CREDITO (ID_ABONO_CREDITO_PK ,ID_CREDITO_FK ,MONTO_ABONO ,FECHA_ABONO ,ID_USUARIO_FK ) "
                    + " VALUES(S_ABONO_CREDITO.nextval,?,?,?,?)");
            query.setParameter(1, abonoCredito.getIdCreditoFk());
            query.setParameter(2, abonoCredito.getMontoAbono());
            query.setParameter(3, abonoCredito.getFechaAbono());
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
  
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(AbonoCredito abonoCredito) {
        try {

            Query query = em.createNativeQuery("UPDATE ABONO_CREDITO SET ID_CREDITO_FK =? ,MONTO_ABONO =? ,FECHA_ABONO =? ,ID_USUARIO_FK =?  WHERE ID_ABONO_CREDITO_PK = ?");
            query.setParameter(1, abonoCredito.getIdCreditoFk());
            query.setParameter(2, abonoCredito.getMontoAbono());
            query.setParameter(3, abonoCredito.getFechaAbono());
            query.setParameter(4, abonoCredito.getIdUsuarioFk());
            query.setParameter(5, abonoCredito.getIdAbonoCreditoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idAbonoCredito) {
        try {

            Query query = em.createNativeQuery("DELETE ABONO_CREDITO WHERE ID_ABONO_CREDITO_PK  = ?");

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

}
