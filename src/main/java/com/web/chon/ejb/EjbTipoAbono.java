/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.negocio.NegocioTipoAbono;
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
@Stateless(mappedName = "ejbTipoAbono")
public class EjbTipoAbono implements NegocioTipoAbono{
    
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(AbonoCredito tabono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(AbonoCredito idtAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal idtAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getAll() {
        try {

            Query query = em.createNativeQuery("select * from Tipo_Abono");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbTipoAbono.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public List<Object[]> getById(BigDecimal idtAbono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
