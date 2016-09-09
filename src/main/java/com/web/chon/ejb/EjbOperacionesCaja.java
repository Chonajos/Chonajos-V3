/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.OperacionesCaja;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioOperacionesCaja;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbOperacionesCaja")
public class EjbOperacionesCaja implements NegocioOperacionesCaja {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_OPERACIONES_CAJA.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }

    }

    @Override
    public int insertaOperacion(OperacionesCaja es) {
        try {
            System.out.println("ejb insert" + es.toString());
            Query query = em.createNativeQuery("INSERT INTO OPERACIONES_CAJA (ID_OPERACIONES_CAJA_PK,ID_CORTE_CAJA_FK,ID_CAJA_DESTINO_FK,ID_CONCEPTO_FK,FECHA,ID_STATUS_FK,ID_USER_FK,COMENTARIOS,MONTO) values(?,?,?,?,sysdate,?,?,?,?,?)");
            query.setParameter(1, es.getIdOperacionesCajaPk());
            query.setParameter(2, es.getIdCorteCajaFk());
            query.setParameter(3, es.getIdCajaFk());
            query.setParameter(4, es.getIdCajaDestinoFk());
            query.setParameter(5, es.getIdConceptoFk());
            query.setParameter(6, es.getIdStatusFk());
            query.setParameter(7, es.getIdUserFk());
            query.setParameter(8, es.getComentarios());
            query.setParameter(9, es.getMonto());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateOperacion(OperacionesCaja es) {

        try {
            System.out.println("ejb UPDATE" + es.toString());
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_CORTE_CAJA_FK = ?,ID_CAJA_DESTINO_FK = ?,ID_CONCEPTO_FK = ?,FECHA = ?,ID_STATUS_FK = ?,ID_USER_FK = ?,COMENTARIOS = ?,MONTO=? WHERE ID_OPERACIONES_CAJA_PK = ?");

            query.setParameter(1, es.getIdCorteCajaFk());
            query.setParameter(2, es.getIdCajaFk());
            query.setParameter(3, es.getIdCajaDestinoFk());
            query.setParameter(4, es.getIdConceptoFk());
            query.setParameter(5, es.getFecha());
            query.setParameter(6, es.getIdStatusFk());
            query.setParameter(7, es.getIdUserFk());
            query.setParameter(8, es.getComentarios());
            query.setParameter(9, es.getMonto());
            query.setParameter(10, es.getIdOperacionesCajaPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getOperacionByIdOperacionPK(BigDecimal idOperacionPk) {
        System.out.println("getOperacionByIdOperacionPK : " + idOperacionPk);
        Query query = em.createNativeQuery("SELECT * FROM OPERACIONES_CAJA WHERE ID_OPERACIONES_CAJA_PK = ?");
        query.setParameter(1, idOperacionPk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getOperacionesBy(BigDecimal idCorteCajaFk, BigDecimal idCajaFk, BigDecimal idCajaDestinoFk, BigDecimal idConceptoFk, String fechaInicio, String fechaFin, BigDecimal idStatusFk, BigDecimal idUserFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
