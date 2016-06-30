/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.negocio.NegocioPreciosCompetidores;
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
 * @author freddy
 */
@Stateless(mappedName = "ejbPreciosCompetidores")
public class EjbPreciosCompetidores implements NegocioPreciosCompetidores {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getPreciosCompetidores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_PRECIOSCOMPETENCIA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int insertPreciosCompetidores(PreciosCompetencia pc) 
    {
         System.out.println("EJB_INSERTA_PreciosCompetidor======"+pc.toString());
        try {
            
            Query query = em.createNativeQuery("INSERT INTO PRECIOSCOMPETENCIA (ID_PC_PK,ID_COMPETIDOR_FK,ID_SUBPRODUCTO_FK,FECHA_REGISTRO,PRECIO_VENTA)VALUES (?,?,?,sysdate,?)");
            query.setParameter(1, pc.getIdPcPk());
            query.setParameter(2, pc.getIdCompetidorFk());
            query.setParameter(3, pc.getIdSubProductoPk());
            query.setParameter(4, pc.getPrecioVenta());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbPreciosCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updatePreciosCompetidores(PreciosCompetencia pc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getCometidoresById(BigDecimal idPreciosCompetidores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
