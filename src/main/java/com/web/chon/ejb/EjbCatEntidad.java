package com.web.chon.ejb;

import com.web.chon.dominio.Entidad;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioCatEntidad;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbCatEntidad")
public class EjbCatEntidad implements NegocioCatEntidad {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getEntidades() {
        try {

            Query query = em.createNativeQuery("SELECT * FROM ENTIDAD order by nombre_entidad");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatEntidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Object[] getEntidadById(int idEntidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteEntidad(int idEntidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateEntidad(Entidad enti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertEntidad(Entidad enti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
