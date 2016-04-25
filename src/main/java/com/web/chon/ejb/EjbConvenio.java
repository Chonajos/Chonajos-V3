/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioConvenio;

/**
 *
 * @author marcogante
 */
@Stateless(mappedName = "ejbConvenio")
public class EjbConvenio implements NegocioConvenio{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getTipos() 
    {
        try {
            System.out.println("ejb selec all orden de compra");
            Query query = em.createNativeQuery("SELECT * FROM TIPO_CONVENIO");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbConvenio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
