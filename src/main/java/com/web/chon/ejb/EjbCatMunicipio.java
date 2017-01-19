package com.web.chon.ejb;

import com.web.chon.dominio.Municipios;
import com.web.chon.negocio.NegocioCatMunicipio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbCatMunicipio")
public class EjbCatMunicipio implements NegocioCatMunicipio {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getMunicipios(int idEntidad) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM MUNICIPIOS WHERE ID_ENTIDAD_FK=? order by nombre_municipio");
            query.setParameter(1, idEntidad);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatEntidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Object[] getMunicipioById(int idMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteMunicipio(int idMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateMunicipio(Municipios muni) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertMunicipio(Municipios muni) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
