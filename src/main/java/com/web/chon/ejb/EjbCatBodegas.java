/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Bodega;
import com.web.chon.negocio.NegocioCatBodega;
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
@Stateless(mappedName = "ejbCatBodegas")
public class EjbCatBodegas implements NegocioCatBodega
{

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
    
    @Override
    public List<Object[]> getBodegas() {
        
        try {

            System.out.println("EJB_GET_bodegas");
            Query query = em.createNativeQuery("select * from bodega");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatBodegas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getBodegaById(int idBodega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteBodega(int idBodega) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateBodega(Bodega bo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertBodega(Bodega bo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
