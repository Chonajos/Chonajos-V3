
package com.web.chon.ejb;



import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.negocio.NegocioEmpaque;
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
@Stateless(mappedName = "ejbEmpaque")
public class EjbEmpaque implements NegocioEmpaque {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
 

    @Override
    public List<Object[]> getEmpaques() {
        try {
            System.out.println("ejb selec all");
            Query query = em.createNativeQuery("SELECT * FROM TIPO_EMPAQUE ORDER BY NOMBRE_EMPAQUE ASC");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteEmpaque(int idEmpaque) {
        try {
            System.out.println("ejb delete");
            Query query = em.createNativeQuery("DELETE TIPO_EMPAQUE WHERE ID_TIPO_EMPAQUE_PK = ?");
            query.setParameter(1, idEmpaque);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int insertarEmpaque(TipoEmpaque tipoEmpaque) {
        try {
            System.out.println("ejb insert");
            Query query = em.createNativeQuery("INSERT INTO TIPO_EMPAQUE (ID_TIPO_EMPAQUE_PK,NOMBRE_EMPAQUE,ESTATUS_EMPAQUE) values(S_TIPO_EMPAQUE.NextVal,?,1)");
            query.setParameter(1, tipoEmpaque.getNombreEmpaque());


            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public int updateEmpaque(TipoEmpaque tipoEmpaque) {
        try {

            System.out.println("ejb update :"+tipoEmpaque.getNombreEmpaque());
            Query query = em.createNativeQuery("UPDATE TIPO_EMPAQUE SET NOMBRE_EMPAQUE = ?,ESTATUS_EMPAQUE = ?  WHERE ID_TIPO_EMPAQUE_PK = ?");
            query.setParameter(1, tipoEmpaque.getNombreEmpaque());
            query.setParameter(2, tipoEmpaque.isEstatus() == true ? "1":"0");
            query.setParameter(3, tipoEmpaque.getIdTipoEmpaquePk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public Object[] getEmpaqueByIdEmpaque(int idEmpaque) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    
    
    
}
