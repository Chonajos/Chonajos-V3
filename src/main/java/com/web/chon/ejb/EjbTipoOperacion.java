
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTiposOperacion;
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
}
