/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.negocio.NegocioEntradaMercancia;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbEntradaMercancia")
public class EjbEntradaMercancia implements NegocioEntradaMercancia {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) {
        System.out.println("EJB_INSERTA_ENTRADAMERCANCIA");
        try {
            System.out.println("Entrada: " + entrada);
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIA (ID_EM_PK,ID_PROVEDOR_FK,MOVIMIENTO,FECHA,REMISION,ID_SUCURSAL_FK,IDENTIFICADOR,ID_STATUS_FK,KILOSTOTALES,KILOSTOTALESPROVEDOR)VALUES (?,?,?,sysdate,?,?,?,1,?,?)");
            query.setParameter(1, entrada.getIdEmPK());
            query.setParameter(2, entrada.getIdProvedorFK());
            query.setParameter(3, entrada.getMovimiento());
            query.setParameter(4, entrada.getRemision());
            query.setParameter(5, entrada.getIdSucursalFK());
            query.setParameter(6, entrada.getFolio());
            query.setParameter(7, entrada.getKilosTotales());
            query.setParameter(8, entrada.getKilosTotalesProvedor());
            
            
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public int buscaMaxMovimiento(EntradaMercancia2 entrada) {
        System.out.println("EJB_BUSCA_MAX_MOVIMIENTO");
        try {
            System.out.println("Entrada: "+entrada.toString());
            Query query = em.createNativeQuery("select max(movimiento) from ENTRADAMERCANCIA where ID_PROVEDOR_FK = ? and ID_SUCURSAL_FK = ? AND FECHA BETWEEN ? AND ?");
            query.setParameter(1, entrada.getIdProvedorFK());
            query.setParameter(2, entrada.getIdSucursalFK());
            query.setParameter(3, entrada.getFechaFiltroInicio());
            query.setParameter(4, entrada.getFechaFiltroFin());
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) 
        {
            //Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() 
    {
        Query query = em.createNativeQuery("SELECT S_ENTRADAMERCANCIA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
