/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTipoOrdenCompra;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author marcogante
 */
@Stateless(mappedName = "ejbTipoOrdenCompra")
public class EjbTipoOrdenCompra implements NegocioTipoOrdenCompra{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getTipos() 
    {
        try {
            System.out.println("ejb selec all orden de compra");
            Query query = em.createNativeQuery("SELECT * FROM TIPO_ORDEN_COMPRA");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbTipoOrdenCompra.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
