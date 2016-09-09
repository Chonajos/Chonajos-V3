/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.CorteCaja;
import com.web.chon.negocio.NegocioCorteCaja;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbCorteCaja")
public class EjbCorteCaja implements NegocioCorteCaja {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    

    @Override
    public int insertCorte(CorteCaja cc) {
        System.out.println("insert corte :" + cc.toString());
        Query query = em.createNativeQuery("INSERT INTO Corte_Caja(ID_CORTE_CAJA_PK,ID_CAJA_FK,FECHA,CANT_CHEQUES_ANT,MONTO_CHEQUES_ANT,SALDO_ANTERIOR,CANT_CHEQUES_NUEVOS,NUEVO_SALDO,COMENTARIOS,ID_USER_FK,ID_STATUS_FK) VALUES(?,?,sysdate,?,?,?,?,?,?,?,?,?)");
       
        query.setParameter(1, cc.getIdCorteCajaPk());
        query.setParameter(2, cc.getIdCajaFk());
        query.setParameter(3, cc.getCantChequesAnt());
        query.setParameter(4, cc.getMontoChequesAnt());
        query.setParameter(5, cc.getSaldoAnterior());
        query.setParameter(6, cc.getCantChequesNuevos());
        query.setParameter(7, cc.getMontoChequesNuevos());
        query.setParameter(8, cc.getSaldoNuevo());
        query.setParameter(9, cc.getComentarios());
        query.setParameter(10, cc.getIdUserFk());
        query.setParameter(11, cc.getIdStatusFk());
        return query.executeUpdate();

    }

    @Override
    public List<Object[]> getCorteBy(BigDecimal idCajaFk, String fechaInicio, String fechaFin, BigDecimal idUserFk, BigDecimal idStatusFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateCorte(CorteCaja cc) {
       System.out.println("update corte :" + cc.toString());
        Query query = em.createNativeQuery("UPDATE Corte_Caja SET ID_CAJA_FK = ?,FECHA = ?,CANT_CHEQUES_ANT = ?,MONTO_CHEQUES_ANT = ?,SALDO_ANTERIOR = ? ,CANT_CHEQUES_NUEVOS = ?,NUEVO_SALDO = ?,COMENTARIOS = ?,ID_USER_FK = ?,ID_STATUS_FK=? WHERE ID_CORTE_CAJA_PK = ?");
       
        query.setParameter(1, cc.getIdCorteCajaPk());
        query.setParameter(2, cc.getIdCajaFk());
        query.setParameter(3, cc.getCantChequesAnt());
        query.setParameter(4, cc.getMontoChequesAnt());
        query.setParameter(5, cc.getSaldoAnterior());
        query.setParameter(6, cc.getCantChequesNuevos());
        query.setParameter(7, cc.getMontoChequesNuevos());
        query.setParameter(8, cc.getSaldoNuevo());
        query.setParameter(9, cc.getComentarios());
        query.setParameter(10, cc.getIdUserFk());
        query.setParameter(11, cc.getIdStatusFk());
        return query.executeUpdate();
    }

    @Override
    public List<Object[]> getCortesByIdPk(BigDecimal idDestinoFK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_CORTE_CAJA.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

}
