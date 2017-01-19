
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioCatCodigosPostales;
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

@Stateless(mappedName = "ejbCatCodigosPostales")
public class EjbCatCodigosPostales implements NegocioCatCodigosPostales{
    
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getCodigosByCP(String cp) 
    {
        try {

            System.out.println("EJB_GET_CODIGOS_POSTALES");
            Query query = em.createNativeQuery("select cp.id_pk,cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,m.ID_MUNICIPIO_PK,m.NOMBRE_MUNICIPIO,e.ID_ENTIDAD_PK,e.NOMBRE_ENTIDAD\n" +
"from CODIGOS_POSTALES cp\n" +
"INNER JOIN municipios m\n" +
"on cp.ID_MUNICIPIO_FK=m.ID_MUNICIPIO_PK\n" +
"INNER JOIN entidad e\n" +
"on m.ID_ENTIDAD_FK=e.ID_ENTIDAD_PK\n" +
"where cp.CODIGO_POSTAL=?");
            query.setParameter(1, cp);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) 
        {
            Logger.getLogger(EjbCatCodigosPostales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
    }

    @Override
    public List<Object[]> getCodigosByIdMun(int idMun) 
    {
        
         try {

            System.out.println("EJB_GET_CODIGOS_POSTALES");
            Query query = em.createNativeQuery("select cp.id_pk,cp.CODIGO_POSTAL,cp.NOMBRE_COLONIA,m.ID_MUNICIPIO_PK,m.NOMBRE_MUNICIPIO,e.ID_ENTIDAD_PK,e.NOMBRE_ENTIDAD\n" +
"from CODIGOS_POSTALES cp\n" +
"INNER JOIN municipios m\n" +
"on cp.ID_MUNICIPIO_FK=m.ID_MUNICIPIO_PK\n" +
"INNER JOIN entidad e\n" +
"on m.ID_ENTIDAD_FK=e.ID_ENTIDAD_PK\n" +
"where cp.ID_MUNICIPIO_FK=?");
            query.setParameter(1, idMun);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) 
        {
            Logger.getLogger(EjbCatCodigosPostales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    
}
