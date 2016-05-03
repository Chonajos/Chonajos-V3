
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioAccesMenu;
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
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbAccesMenu")
public class EjbAccesMenu implements NegocioAccesMenu {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getAccesosMenuPorIdRol(BigDecimal idRol) {

        try {

            Query query = em.createNativeQuery("SELECT ME.*,NVL((SELECT ME.ID_MENU FROM ACCES_MENU WHERE ID_ROL_FK = ? AND ME.ID_MENU=ID_MENU_FK ),0) ESTATUS FROM MENU ME ORDER BY ME.NIVEL");
            query.setParameter(1, idRol);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbAccesMenu.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int delete(BigDecimal idRol, BigDecimal idMenu) {

        try {
            System.out.println("elimina "+idRol +" "+idMenu);
            Query query = em.createNativeQuery("DELETE ACCES_MENU WHERE ID_MENU_FK = ? AND ID_ROL_FK = ?");

            query.setParameter(1, idMenu);
            query.setParameter(2, idRol);
            
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbAccesMenu.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int create(BigDecimal idRol, BigDecimal idMenu) {
        try {
            System.out.println("ejb "+idRol +" idmenu "+idMenu);
            Query query = em.createNativeQuery("INSERT INTO ACCES_MENU(ID_MENU_FK,ID_ROL_FK) VALUES(?,?)");

            query.setParameter(1, idMenu);
            query.setParameter(2, idRol);

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbAccesMenu.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> exist(BigDecimal idRol, BigDecimal idMenu) {

        try {

            Query query = em.createNativeQuery("SELECT * FROM ACCES_MENU WHERE ID_MENU_FK = ? AND ID_ROL_FK = ?");

            query.setParameter(1, idMenu);
            query.setParameter(2, idRol);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbAccesMenu.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
