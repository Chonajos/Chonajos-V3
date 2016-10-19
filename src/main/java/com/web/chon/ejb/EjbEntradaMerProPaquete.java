/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercanciaProductoPaquete;
import com.web.chon.negocio.NegocioEntradaMerProPaquete;
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
@Stateless(mappedName = "ejbEntradaMerProPaquete")
public class EjbEntradaMerProPaquete  implements NegocioEntradaMerProPaquete{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertPaquete(EntradaMercanciaProductoPaquete paquete) {
       try {
            Query query = em.createNativeQuery("INSERT INTO ENTRADA_PAQUETES (ID_PAQUETE_PK,KILOS,CANTIDAD,TARA,PESO_NETO,ID_EMP_FK,ID_STATUS_FK)VALUES (?,?,?,?,?,?,?)");
            query.setParameter(1, paquete.getIdEmPP());
            query.setParameter(2, paquete.getKilos());
            query.setParameter(3, paquete.getPaquetes());
            query.setParameter(4, paquete.getTara());
            query.setParameter(5, paquete.getPesoNeto());
            query.setParameter(6, paquete.getIdEmpFK());
            query.setParameter(7, paquete.getIdStatusFk());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMerProPaquete.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }
    @Override
    public int updatePaquete(BigDecimal idEmpFk) {
        //System.out.println("IDEMPFK: "+idEmpFk);
       try {
            Query query = em.createNativeQuery("UPDATE  ENTRADA_PAQUETES SET ID_STATUS_FK = 2 WHERE ID_EMP_FK = ?");
            query.setParameter(1, idEmpFk);
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMerProPaquete.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public List<Object[]> getPaquetesById(BigDecimal id) {
       Query query = em.createNativeQuery("select * from ENTRADA_PAQUETES where ID_EMP_FK = ? AND ID_STATUS_FK = 1");
        query.setParameter(1, id);
        return query.getResultList();
    }
    
    
    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_ENTRADA_PAQUETES.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int eliminarPaquete(BigDecimal id) {
        try {
            Query query = em.createNativeQuery("DELETE ENTRADA_PAQUETES WHERE ID_PAQUETE_PK = ?");
            query.setParameter(1, id);
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMerProPaquete.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
