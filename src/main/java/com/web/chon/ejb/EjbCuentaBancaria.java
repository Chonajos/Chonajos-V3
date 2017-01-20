package com.web.chon.ejb;

import com.web.chon.negocio.NegocioCuentasBancarias;
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

    @Override
    public List<Object[]> getCuentasByIdSucursalFk(BigDecimal idSucursalFk) {
        try {
            Query query = em.createNativeQuery("select cb.NOMBRE_BANCO,cb.RAZON,cb.CUENTA,cb.CLABE,cb.SUCURSAL from CUENTA_SUCURSAL_1 cs \n" +
"inner join CUENTA_BANCARIA cb on cb.ID_CUENTA_BANCARIA_PK= cs.ID_ID_CUENTA_BANCARIA_1_FK\n" +
"where cs.ID_SUCURSAL_1_FK=? ");
            query.setParameter(1, idSucursalFk);
            System.out.println("Query: "+query.toString());
            System.out.println("IdSucursal: "+idSucursalFk);
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

}
