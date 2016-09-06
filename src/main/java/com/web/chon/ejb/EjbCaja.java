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
    public int updateMontoCaja(Caja c) {
        System.out.println("Caja: "+c);
        try {
            Query query = em.createNativeQuery("UPDATE CAJA SET MONTO = ?, MONTO_MENUDEO = ?, MONTO_MAYOREO = ?, MONTO_CREDITOS = ? WHERE ID_CAJA_PK = ? ");
            query.setParameter(1, c.getMonto());
            query.setParameter(2, c.getMontoMenudeo());
            query.setParameter(3, c.getMontoMayoreo());
            query.setParameter(4, c.getMontoCredito());
            query.setParameter(5, c.getIdCajaPk());
            return query.executeUpdate();

        } catch (Exception ex) {

            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getCajas(BigDecimal idSucursalFk, BigDecimal tipo) {
       try {
            Query query = em.createNativeQuery("select * from caja c where c.ID_SUCURSAL_FK = ? and TIPO = ?");
            query.setParameter(1, idSucursalFk);
            query.setParameter(2, tipo);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public List<Object[]> getCajaByIdPk(BigDecimal idCajaPk)
    {
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
    public List<Object[]> getCajaByIdUsuarioPk(BigDecimal idUsuarioPk, BigDecimal tipo) {
        try {
            Query query = em.createNativeQuery("select * from caja c where c.ID_USER_FK = ? and TIPO = ?");
            query.setParameter(1, idUsuarioPk);
            query.setParameter(2, tipo);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
