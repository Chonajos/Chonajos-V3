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
import javax.persistence.TemporalType;

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
        System.out.println("=================================================ejb insert" + es.toString());
            
        try {
            Query query = em.createNativeQuery("INSERT INTO OPERACIONES_CAJA (ID_OPERACIONES_CAJA_PK,ID_CORTE_CAJA_FK,ID_CAJA_FK,ID_CAJA_DESTINO_FK,ID_CONCEPTO_FK,FECHA,ID_STATUS_FK,ID_USER_FK,COMENTARIOS,MONTO,E_S,ID_CUENTA_DESTINO_FK) values(?,?,?,?,?,sysdate,?,?,?,?,?,?)");
            query.setParameter(1, es.getIdOperacionesCajaPk());
            query.setParameter(2, es.getIdCorteCajaFk());
            query.setParameter(3, es.getIdCajaFk());
            query.setParameter(4, es.getIdCajaDestinoFk());
            query.setParameter(5, es.getIdConceptoFk());
            query.setParameter(6, es.getIdStatusFk());
            query.setParameter(7, es.getIdUserFk());
            query.setParameter(8, es.getComentarios());
            query.setParameter(9, es.getMonto());
            query.setParameter(10, es.getEntradaSalida());
            query.setParameter(11, es.getIdCuentaDestinoFk());
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
        Query query = em.createNativeQuery("select opc.*,cj.NOMBRE,con.NOMBRE as concepto,tio.NOMBRE as Operacion,u.NOMBRE_USUARIO from OPERACIONES_CAJA opc\n" +
"inner join caja cj on cj.ID_CAJA_PK = opc.ID_CAJA_FK\n" +
"inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n" +
"inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n" +
"inner join USUARIO u on u.ID_USUARIO_PK = opc.ID_USER_FK\n" +
"WHERE opc.ID_CORTE_CAJA_FK is null and opc.ID_CAJA_FK = ?\n" +
"and opc.ID_USER_FK=?");
        query.setParameter(1,idCajaFk);
        query.setParameter(2,idUserFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTransferenciasEntrantes(BigDecimal idCorteCajaFk) {
        Query query = em.createNativeQuery("select opc.*,cj1.NOMBRE,con.NOMBRE as concepto,tio.NOMBRE as Operacion,u.NOMBRE_USUARIO from OPERACIONES_CAJA opc\n" +
"inner join caja cj on cj.ID_CAJA_PK = opc.ID_CAJA_DESTINO_FK\n" +
"inner join caja cj1 on cj1.ID_CAJA_PK = opc.ID_CAJA_FK\n" +
"inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n" +
"inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n" +
"inner join USUARIO u on u.ID_USUARIO_PK = opc.ID_USER_FK\n" +
"where opc.ID_STATUS_FK=2 and opc.E_S=2 and opc.ID_CAJA_DESTINO_FK= ?");
        query.setParameter(1, idCorteCajaFk);
        return query.getResultList();
    }

    @Override
    public int updateStatusOperacion(BigDecimal idOperacionPk, BigDecimal idStatusFk) {
         try {
            System.out.println("ejb UPDATE" + idOperacionPk);
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_STATUS_FK = ? WHERE ID_OPERACIONES_CAJA_PK = ?");

            query.setParameter(1, idStatusFk);
            query.setParameter(2, idOperacionPk);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }
    @Override
    public List<Object[]> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES) {
         Query query = em.createNativeQuery("select con.ID_TIPO_OPERACION_FK,tio.NOMBRE,sum(opc.MONTO) from operaciones_caja opc\n" +
"inner join usuario u on u.ID_USUARIO_PK = opc.ID_USER_FK\n" +
"inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n" +
"inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n" +
"where opc.ID_STATUS_FK=1 and opc.E_S=? and opc.ID_CORTE_CAJA_FK is null\n" +
"and opc.ID_USER_FK = ? and opc.ID_CAJA_FK = ? \n" +
"group by con.ID_TIPO_OPERACION_FK,tio.NOMBRE");
        System.out.println("===========Consulta=========");
        System.out.println(query);
        System.out.println("Variables: "+ "Caja: "+idCajaFk +"User: "+idUserFk +" E/S: " + idES);
        query.setParameter(1, idES);
        query.setParameter(2, idUserFk);
        query.setParameter(3, idCajaFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk) {
       Query query = em.createNativeQuery("select opc.* from operaciones_caja opc\n" +
"inner join usuario u on u.ID_USUARIO_PK = opc.ID_USER_FK\n" +
"inner join CONCEPTOS con on con.ID_CONCEPTOS_PK = opc.ID_CONCEPTO_FK\n" +
"inner join TIPOS_OPERACION tio on tio.ID_TIPO_OPERACION_PK = con.ID_TIPO_OPERACION_FK\n" +
"where opc.ID_STATUS_FK=1  and opc.ID_CORTE_CAJA_FK is null\n" +
"and opc.ID_USER_FK = ? and opc.ID_CAJA_FK = ?");
         System.out.println("===========Consulta=========");
         System.out.println(query);
         System.out.println("Variables: "+ "Caja: "+idCajaFk +"User: "+idUserFk);
        query.setParameter(1, idUserFk);
        query.setParameter(2, idCajaFk);
        return query.getResultList();
    }
     @Override
    public List<Object[]> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT) {
       Query query = em.createNativeQuery("select * from OPERACIONES_CAJA opc where opc.ID_CONCEPTO_FK = 12\n" +
"and opc.ID_CAJA_FK = ? and opc.ID_USER_FK = ? and opc.ID_CORTE_CAJA_FK is null and opc.E_S=?");
        query.setParameter(1, idCajaFk);
        query.setParameter(2, idUserFk);
        query.setParameter(3, idINOUT);
        return query.getResultList();
    }

    @Override
    public int updateCorteCaja(BigDecimal idOperacionPk, BigDecimal idCorteCajaFk) {
        try {
            System.out.println("ejb UPDATE" + idOperacionPk);
            Query query = em.createNativeQuery("UPDATE OPERACIONES_CAJA SET ID_CORTE_CAJA_FK = ? WHERE ID_OPERACIONES_CAJA_PK = ?");

            query.setParameter(1, idCorteCajaFk);
            query.setParameter(2, idOperacionPk);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEmpaque.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    
    }

}
