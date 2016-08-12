package com.web.chon.ejb;

import com.web.chon.dominio.GestionCredito;
import com.web.chon.negocio.NegocioGestionCredito;
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
@Stateless(mappedName = "ejbGestionCredito")
public class EjbGestionCredito implements NegocioGestionCredito {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(GestionCredito gestionCredito) {

        try {

            Query query = em.createNativeQuery("INSERT INTO  GESTION_CREDITO (ID_GESTION_CREDITO_PK,ID_ACION_GESTION_FK,ID_USUARIO_FK,ID_CREDITO_FK,OBSERVACIONES) "
                    + " VALUES(S_GESTION_CREDITO.NextVal,?,?,?,?)");

            

            query.setParameter(1, gestionCredito.getIdAcionGestion());
            query.setParameter(2, gestionCredito.getIdUsario());
            query.setParameter(3, gestionCredito.getIdCredito());
            query.setParameter(4, gestionCredito.getObservaciones());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbGestionCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(GestionCredito gestionCredito) {
        try {

            Query query = em.createNativeQuery("UPDATE GESTION_CREDITO SET ID_CREDITO_FK =? ,MONTO_ABONO =? ,FECHA_ABONO =? ,ID_USUARIO_FK =? ,ESTATUS=? WHERE ID_GESTION_CREDITO_PK = ?");
//            query.setParameter(1, abonoCredito.getIdCreditoFk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbGestionCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idGestionCredito) {
        try {

            Query query = em.createNativeQuery("DELETE GESTION_CREDITO WHERE ID_GESTION_CREDITO_PK  = ?");

            query.setParameter(1, idGestionCredito);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbGestionCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_GESTION_CREDITO_PK,ID_ACION_GESTION_FK,ID_USUARIO_FK,ID_CREDITO_FK,OBSERVACIONES  FROM GESTION_CREDITO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbGestionCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idGestionCredito) {
        try {

            Query query = em.createNativeQuery("SELECT ID_GESTION_CREDITO_PK,ID_ACION_GESTION_FK,ID_USUARIO_FK,ID_CREDITO_FK,OBSERVACIONES  FROM GESTION_CREDITO WHERE ID_GESTION_CREDITO_PK  = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idGestionCredito);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbGestionCredito.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_GESTION_CREDITO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
