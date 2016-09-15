/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioCatCuentas;
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
@Stateless(mappedName = "ejbCatCuentas")
public class EjbCatCuentas  implements NegocioCatCuentas{
     @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getCuentas() {
        try 
        {
            Query query = em.createNativeQuery("SELECT * FROM CUENTA_BANCARIA");
            return  query.getResultList();
        } catch (Exception ex) 
        {
            Logger.getLogger(EjbCatCuentas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    
    }
}
