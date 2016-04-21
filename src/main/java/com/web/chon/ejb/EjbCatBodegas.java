/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Bodega;
import com.web.chon.negocio.NegocioCatBodega;
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
 * @author marcogante
 */
@Stateless(mappedName = "ejbCatBodegas")
public class EjbCatBodegas implements NegocioCatBodega {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getBodegas() {

        try {

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
        try {
            Query query = em.createNativeQuery("SELECT * FROM BODEGA WHERE  ID_BD_PK = ?");
            query.setParameter(1, idBodega);

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int deleteBodega(int idBodega) {
        Query query = em.createNativeQuery("DELETE BODEGA WHERE ID_BD_PK = ?");
        query.setParameter(1, idBodega);
        
        return query.executeUpdate();
    }

    @Override
    public int updateBodega(Bodega bo) {
        try {

            Query query = em.createNativeQuery("UPDATE BODEGA SET NOMBRE = ? , DESCRIPCION = ? WHERE ID_BD_PK = ?");

            query.setParameter(1, bo.getNombreBodega());
            query.setParameter(2, bo.getDescripcionBodega());
            query.setParameter(3, bo.getIdBodegaPK());

            return query.executeUpdate();

        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int insertBodega(Bodega bo) {
        try {
            Query query = em.createNativeQuery("INSERT INTO BODEGA (ID_BD_PK,NOMBRE,DESCRIPCION) VALUES(S_BODEGA.NextVal,?,?)");

            query.setParameter(1, bo.getNombreBodega());
            query.setParameter(2, bo.getDescripcionBodega());

            return query.executeUpdate();

        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long countBodegas() {
        Query query = em.createNativeQuery("SELECT COUNT(ID_BD_PK) FROM BODEGA");
        BigDecimal value = (BigDecimal) query.getSingleResult();

        return value.longValue();
    }

    @Override
    public List<Object[]> getBodepagasPagination(int first, int pageSize) {

        Query query = em.createNativeQuery("SELECT * FROM(SELECT BOD.*,row_number() over (order by BOD.ID_BD_PK ASC) rn  FROM BODEGA BOD) where rn between ? and ? + ?");

        query.setParameter(1, first);
        query.setParameter(2, first);
        query.setParameter(1, pageSize);

        return query.getResultList();

    }

}
