package com.web.chon.ejb;

import com.web.chon.negocio.NegocioCuentasBancarias;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbCuentaBancaria")
public class EjbCuentaBancaria implements NegocioCuentasBancarias {
    
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getCuentas() {
         try {
            Query query = em.createNativeQuery("SELECT * FROM CUENTA_BANCARIA");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

}
