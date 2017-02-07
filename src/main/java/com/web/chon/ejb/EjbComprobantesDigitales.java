/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.negocio.NegocioComprobantes;
import java.math.BigDecimal;
import java.sql.SQLException;
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
@Stateless(mappedName = "ejbComprobantesDigitales")
public class EjbComprobantesDigitales implements NegocioComprobantes {
    
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_COMPROBANTES_DIGITALES.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }

    @Override
    public int insertaComprobante(ComprobantesDigitales cd) {
        try {

            Query query = em.createNativeQuery("INSERT INTO COMPROBANTES_DIGITALES (ID_COMPROBANTES_DIGITALES_PK,ID_TIPO_FK,ID_LLAVE_FK,FECHA,NOMBRE)VALUES (?,?,?,sysdate,?)");
            query.setParameter(1, cd.getIdComprobantesDigitalesPk());
            query.setParameter(2, cd.getIdTipoFk());
            query.setParameter(3, cd.getIdLlaveFk());
            query.setParameter(4, cd.getNombre());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbComprobantesDigitales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    @Override
    public int insertarImagen(BigDecimal id, byte[] fichero) throws SQLException {

        Query querys = em.createNativeQuery("update COMPROBANTES_DIGITALES SET FICHERO = ? WHERE ID_COMPROBANTES_DIGITALES_PK = ?");
        querys.setParameter(1, fichero);
        querys.setParameter(2, id);
        return querys.executeUpdate();

    }

    @Override
    public int updateComprobante(ComprobantesDigitales cd) {
       try {
            Query query = em.createNativeQuery("update COMPROBANTES_DIGITALES SET ID_TIPO_FK=?, ID_LLAVE_FK=?,NOMBRE=? WHERE ID_COMPROBANTES_DIGITALES_PK = ?");
            query.setParameter(1, cd.getIdTipoFk());
            query.setParameter(2, cd.getIdLlaveFk());
            query.setParameter(3, cd.getNombre());
            query.setParameter(4, cd.getIdComprobantesDigitalesPk());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbComprobantesDigitales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int deleteComprobante(ComprobantesDigitales cd) {
        try {

            Query query = em.createNativeQuery("DELETE COMPROBANTES_DIGITALES where ID_COMPROBANTES_DIGITALES_PK = ?");
            query.setParameter(1, cd.getIdComprobantesDigitalesPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbComprobantesDigitales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    
    }

    @Override
    public List<Object[]> getComprobantesByIdTipoLlave(BigDecimal idTipoFk, BigDecimal idLlave) {
         try {
            Query query = em.createNativeQuery("select * from COMPROBANTES_DIGITALES where ID_LLAVE_FK=? and ID_TIPO_FK=? ");
            query.setParameter(1, idLlave);
            query.setParameter(2, idTipoFk);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbComprobantesDigitales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getComprobanteByIdTipoLlave(BigDecimal idTipoFk, BigDecimal idLlave) {
        System.out.println("Variables: Tipo: "+idTipoFk + " LLave: "+idLlave);
        try {
            Query query = em.createNativeQuery("select * from COMPROBANTES_DIGITALES where ID_LLAVE_FK=? and ID_TIPO_FK=? ");
            query.setParameter(1, idLlave);
            query.setParameter(2, idTipoFk);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbComprobantesDigitales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
