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
@Stateless(mappedName = "ejbCorteCaja")
public class EjbCorteCaja implements NegocioCorteCaja {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    

    @Override
    public int insertCorte(CorteCaja cc) {
        System.out.println("insert corte :" + cc.toString());
        Query query = em.createNativeQuery("INSERT INTO Corte_Caja(ID_CORTE_CAJA_PK,ID_CAJA_FK,FECHA,CANT_CHEQUES_ANT,"
                + "                         MONTO_CHEQUES_ANT,SALDO_ANTERIOR,CANT_CHEQUES_NUEVOS,MONTO_CHEQUES_NUEVOS,NUEVO_SALDO,"
                + "                         COMENTARIOS,ID_USER_FK,ID_STATUS_FK,MONTO_CUENTA_ANT,MONTO_CUENTA_NUEVO) VALUES(?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?)");
       
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
        query.setParameter(12, cc.getMontoCuentaAnterior());
        query.setParameter(13, cc.getMontoCuentaNuevo());
        return query.executeUpdate();

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
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_CORTE_CAJA.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public List<Object[]> getCortesByIdCajaFk(BigDecimal idCajaFK, String fechaIni, String fechaFin) {
        try {
            Query query = em.createNativeQuery("select cj.*,c.NOMBRE,u.NOMBRE_USUARIO from corte_caja cj\n" +
"inner join caja c on c.ID_CAJA_PK = cj.ID_CORTE_CAJA_PK\n" +
"inner join usuario u on u.ID_USUARIO_PK = cj.ID_USER_FK\n" +
"WHERE TO_DATE(TO_CHAR(cj.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '"+fechaIni+"' AND ''"+fechaFin+"'\n" +
"and cj.ID_CAJA_FK=?");
            query.setParameter(1, idCajaFK);
            System.out.println("Query: "+query);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCorteCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        

    }

    @Override
    public List<Object[]> getCorteByidPk(BigDecimal idPk) {
       try {
            Query query = em.createNativeQuery("select * from corte_caja c where c.ID_CORTE_CAJA_PK = ? ");
            query.setParameter(1, idPk);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCorteCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getLastCorteByCaja(BigDecimal idCajaPk) {
        System.out.println("IdCaja: "+idCajaPk);
        try {
            Query query = em.createNativeQuery("select * from(select *  from CORTE_CAJA cj where cj.ID_CAJA_FK = ? ORDER BY cj.ID_CORTE_CAJA_PK desc)  t1 where rownum =1");
            query.setParameter(1, idCajaPk);
            
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCorteCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getCortesByFechaCajaUsuario(BigDecimal idCajaFk, BigDecimal idUsuarioFk, String fecha) {
       try {
            Query query = em.createNativeQuery(" select cj.* from CORTE_CAJA cj where cj.ID_CAJA_FK = ? and cj.ID_USER_FK = ? and  TO_DATE(TO_CHAR(cj.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN ' "+fecha+" ' AND '"+fecha+"' ");
            query.setParameter(1, idCajaFk);
            query.setParameter(2, idUsuarioFk);
            
            System.out.println("Query: "+query);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbCorteCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

}
