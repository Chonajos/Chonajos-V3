/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTipoVenta;
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
@Stateless(mappedName = "ejbTipoVenta")
public class EjbTipoVenta implements NegocioTipoVenta{

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
 
    
    
    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("select * from tipo_venta");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbTipoVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
    
}
