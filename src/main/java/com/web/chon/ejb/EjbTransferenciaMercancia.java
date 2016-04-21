/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.TransferenciaMercancia;
import com.web.chon.negocio.NegocioTransferenciaMercancia;
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
@Stateless(mappedName = "ejbTransferenciaMercancia")
public class EjbTransferenciaMercancia implements NegocioTransferenciaMercancia {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaTransferencia(TransferenciaMercancia tm) 
    {
       System.out.println("EJB_INSERTA_TRANSFERENCIA");
        try {
            System.out.println("Transferencia: " + tm);
            Query query = em.createNativeQuery("INSERT INTO TRANSFERENCIA_MERCANCIA (ID_TRANSFERENCIA_PK,ID_EXISTENCIA_PRODUCTO_FK,CANTIDAD,KILOS,CANTIDAD_MOVIDA,KILOS_MOVIDOS,ID_BODEGA_NUEVA_FK,ID_SUCURSAL_FK,FECHA_MOVIMIENTO)VALUES (S_TRANSFERENCIA_MERCANCIA.NextVal,?,?,?,?,?,?,?,sysdate)");
            
            query.setParameter(1, tm.getIdExistenciaProductoFK());
            query.setParameter(2, tm.getCantidad());
            query.setParameter(3, tm.getKilos());
            query.setParameter(4, tm.getCantidadMovida());
            query.setParameter(5, tm.getKilosMovios());
            query.setParameter(6, tm.getIdBodegaNueva());
            query.setParameter(7, tm.getIdSucursalNuevaFK());
            //fecha
            //query.setParameter(8, tm.getIdUsuarioFK()));
            
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbTransferenciaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
}
