/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.CorteCaja;
import com.web.chon.negocio.NegocioCorteCaja;
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
    public List<Object[]> getCorteByFecha(String fecha) {
        Query query = em.createNativeQuery("select * from CORTE_CAJA cc WHERE TO_DATE(TO_CHAR(cc.FECHA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fecha + "' AND '" + fecha + "' ");
        return query.getResultList();
    }

    @Override
    public int insertCorte(CorteCaja cc) {
        System.out.println("insert corte :" + cc.toString());
        Query query = em.createNativeQuery("INSERT INTO Corte_Caja(ID_CORTE_CAJA_PK,ID_CAJA_FK,FECHA,VENTAS_MAYOREO,VENTAS_MENUDEO,ABONOS_CREDITOS,ANTICIPOS,CANT_CHEQUES,MONTO_CHEQUES,TRANSFERENCIAS_IN,SERVICIOS,PRESTAMOS,TRANSFERENCIAS_OUT,SALDO_ANTERIOR,NUEVO_SALDO,COMENTARIOS,ID_USER_FK) VALUES(S_CORTE_CAJA.nextVal,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        query.setParameter(1, cc.getIdCajaFk());
        query.setParameter(2, cc.getVentasMayoreo());
        query.setParameter(3, cc.getVentasMenudeo());
        query.setParameter(4, cc.getAbonosCreditos());
        query.setParameter(5, cc.getAnticipos());
        query.setParameter(6, cc.getCantCheques());
        query.setParameter(7, cc.getMontoCheques());
        query.setParameter(8, cc.getTransferenciasIN());
        query.setParameter(9, cc.getServicios());
        query.setParameter(10, cc.getPrestamos());
        query.setParameter(11, cc.getTransferenciasOUT());
        query.setParameter(12, cc.getSaldoAnterior());
        query.setParameter(13, cc.getSaldoNuevo());
        query.setParameter(14, cc.getComentarios());
        query.setParameter(15, cc.getIdUserFk());
        
        return query.executeUpdate();

    }

}
