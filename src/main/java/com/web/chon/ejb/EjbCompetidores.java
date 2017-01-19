package com.web.chon.ejb;

import com.web.chon.dominio.Competidor;
import com.web.chon.negocio.NegocioCompetidor;
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
 * @author
 */
@Stateless(mappedName = "ejbCompetidores")
public class EjbCompetidores implements NegocioCompetidor {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getCometidores() {
        try {
            Query query = em.createNativeQuery("select * from COMPETIDOR ");
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT s_COMPETIDOR.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int insertCompetidor(Competidor c) {
        try {

            Query query = em.createNativeQuery("INSERT INTO COMPETIDOR (ID_COMPETIDOR_PK,NOMBRE_COMPETIDOR,UBICACION)VALUES (?,?,?)");
            query.setParameter(1, c.getIdCompetidorPk());
            query.setParameter(2, c.getNombreCompetidor());
            query.setParameter(3, c.getUbicacion());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateCompetidor(Competidor c) {
        try {

            Query query = em.createNativeQuery("update COMPETIDOR SET NOMBRE_COMPETIDOR=?, UBICACION=? WHERE ID_COMPETIDOR_PK = ?");
            query.setParameter(1, c.getNombreCompetidor());
            query.setParameter(2, c.getUbicacion());
            query.setParameter(3, c.getIdCompetidorPk());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getCometidoresById(BigDecimal idCompetidor) {
        Query query = em.createNativeQuery("select * from COMPETIDOR where ID_COMPETIDOR_PK = ?");
        query.setParameter(1, idCompetidor);
        return query.getResultList();
    }

}
