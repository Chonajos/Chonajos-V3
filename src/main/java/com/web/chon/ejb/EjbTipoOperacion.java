
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTiposOperacion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbTipoOperacion")
public class EjbTipoOperacion  implements NegocioTiposOperacion{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getOperaciones() {
       Query query = em.createNativeQuery("select * from TIPOS_OPERACION");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getOperacionesByIdCategoria(BigDecimal id) {
        Query query = em.createNativeQuery("SELECT * FROM TIPOS_OPERACION WHERE ID_GRUPO_FK = ?");
        query.setParameter(1, id);
        System.out.println("Query: "+query.toString());
        System.out.println("Id:"+id);
        return query.getResultList();
    }
}
