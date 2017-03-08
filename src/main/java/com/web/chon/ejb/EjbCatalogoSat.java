/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioCatalogoSat;
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
 * @author jramirez
 */
@Stateless(mappedName = "ejbCatalogoSat")
public class EjbCatalogoSat implements NegocioCatalogoSat {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getCatalogo(BigDecimal idTipoFk) {
        try {
            System.out.println("======TIPO:  "+idTipoFk);

            Query query = em.createNativeQuery("select * from CATALOGO_SAT where TIPO = ?");
            query.setParameter(1, idTipoFk);
            List<Object[]> resultList = null;
            System.out.println("Query: "+query.toString());
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatalogoSat.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
}
