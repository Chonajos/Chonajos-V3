/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Caja;
import com.web.chon.negocio.NegocioCaja;
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
@Stateless(mappedName = "ejbCaja")
public class EjbCaja implements NegocioCaja {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertCaja(Caja c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateCaja(Caja c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Object[]> getCajas() {
        try {
            Query query = em.createNativeQuery("select * from caja");
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getCajaByIdPk(BigDecimal idCajaPk) {
        try {
            Query query = em.createNativeQuery("select * from caja c where c.ID_CAJA_PK = ? ");
            query.setParameter(1, idCajaPk);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getCajaByIdUsuarioPk(BigDecimal idUsuarioPk) {
        System.out.println("IDUser: "+idUsuarioPk);
        try {
            Query query = em.createNativeQuery("select * from caja c where c.ID_USER_FK = ? ");
            query.setParameter(1, idUsuarioPk);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    @Override
    public List<Object[]> getSucursalesByIdCaja(BigDecimal idCajaFk) {
        try {
            Query query = em.createNativeQuery("select sucu.ID_SUCURSAL_PK,sucu.NOMBRE_SUCURSAL from CAJA_SUCURSAL cs \n" +
"inner join sucursal sucu on sucu.ID_SUCURSAL_PK = cs.ID_SUCURSAL_FK  where cs.ID_CAJA_FK = ? ");
            query.setParameter(1, idCajaFk);
            System.out.println("Query: "+query);
            System.out.println("Variable: "+idCajaFk);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
