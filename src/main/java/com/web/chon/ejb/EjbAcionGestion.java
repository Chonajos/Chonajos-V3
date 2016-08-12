package com.web.chon.ejb;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.AcionGestion;
import com.web.chon.negocio.NegocioAcionGestion;
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
@Stateless(mappedName = "ejbAcionGestion")
public class EjbAcionGestion implements NegocioAcionGestion {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AcionGestion acionGestion) {

        try {


            Query query = em.createNativeQuery("INSERT INTO ACION_GESTION(ID_ACION_GESTION_PK,ID_RESULTADO_GESTION_FK,DESCRIPCION) VALUES(S_ACION_GESTION.NextVal,?,?)");
            query.setParameter(1, acionGestion.getIdResultadoGestion());
            query.setParameter(2, acionGestion.getDescripcion());


            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(AcionGestion acionGestion) {
        try {

            Query query = em.createNativeQuery("UPDATE ACION_GESTION SET DESCRIPCION WHERE ID_ACION_GESTION_PK = ?");
            query.setParameter(1, acionGestion.getDescripcion());
            query.setParameter(2, acionGestion.getIdAcionGestion());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int delete(BigDecimal idAcionGestion) {
        try {

            Query query = em.createNativeQuery("DELETE ACION_GESTION WHERE ID_ACION_GESTION_PK  = ?");

            query.setParameter(1, idAcionGestion);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("SELECT ID_ACION_GESTION_PK,ID_RESULTADO_GESTION_FK,DESCRIPCION  FROM ACION_GESTION");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(BigDecimal idAcionGestion) {
        try {

            Query query = em.createNativeQuery("SELECT ID_ACION_GESTION_PK,ID_RESULTADO_GESTION_FK,DESCRIPCION  FROM ACION_GESTION WHERE ID_ACION_GESTION_PK  = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idAcionGestion);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_ACION_GESTION.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getByIdResultadoGestion(BigDecimal idResultadoGestion) {
        try {

            Query query = em.createNativeQuery("SELECT ID_ACION_GESTION_PK,ID_RESULTADO_GESTION_FK,DESCRIPCION  FROM ACION_GESTION WHERE ID_RESULTADO_GESTION_FK  = ?");
            List<Object[]> resultList = null;
            query.setParameter(1, idResultadoGestion);
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbAcionGestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

  

}
