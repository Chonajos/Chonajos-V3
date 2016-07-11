/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.negocio.NegocioPreciosCompetidores;
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;
import java.util.Date;
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
    public List<Object[]> getPreciosCompetidores(String fecha) {
        
        StringBuffer query = new StringBuffer("select pc.ID_PC_PK,pc.ID_COMPETIDOR_FK,pc.ID_SUBPRODUCTO_FK, pc.FECHA_REGISTRO, pc.PRECIO_VENTA, \n" +
"sub.NOMBRE_SUBPRODUCTO, c.NOMBRE_COMPETIDOR\n" +
"from PRECIOSCOMPETENCIA pc\n" +
"inner join SUBPRODUCTO sub\n" +
"on sub.ID_SUBPRODUCTO_PK = pc.ID_SUBPRODUCTO_FK\n" +
"inner join COMPETIDOR c\n" +
"on c.ID_COMPETIDOR_PK = pc.ID_COMPETIDOR_FK\n" +
"WHERE TO_DATE(TO_CHAR(FECHA_REGISTRO,'dd/mm/yyyy'),'dd/mm/yyyy')='"+fecha+"'");
       
        //System.out.println("Query: "+query);
        return em.createNativeQuery(query.toString()).getResultList();
        
    }
    
    

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_PRECIOSCOMPETENCIA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int insertPreciosCompetidores(PreciosCompetencia pc) 
    {
        // System.out.println("EJB_INSERTA_PreciosCompetidor======"+pc.toString());
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
       try {
            //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%"+pc.toString());
            Query query = em.createNativeQuery("UPDATE PRECIOSCOMPETENCIA SET ID_COMPETIDOR_FK=?, ID_SUBPRODUCTO_FK=?, FECHA_REGISTRO=sysdate, PRECIO_VENTA=? where ID_PC_PK= ?");
            query.setParameter(1, pc.getIdCompetidorFk());
            query.setParameter(2, pc.getIdSubProductoPk());
            query.setParameter(3, pc.getPrecioVenta());
            query.setParameter(4, pc.getIdPcPk());
            //System.out.println("queryUpdate: "+query);
            return query.executeUpdate();

        } catch (Exception ex) {
            //System.out.println("Ex: "+ex.toString());
            Logger.getLogger(EjbPreciosCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getCometidoresById(BigDecimal idPreciosCompetidores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deletePreciosCompetidores(PreciosCompetencia pc) {
       try {
            
            Query query = em.createNativeQuery("DELETE PRECIOSCOMPETENCIA  where ID_PC_PK= ?");
            query.setParameter(1, pc.getIdPcPk());
            //System.out.println("queryDelete: "+query);
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbPreciosCompetidores.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public List<Object[]> getPreciosCompetenciasByCompetidorProducto(PreciosCompetencia pc) {
        String tiempoTemporal = TiempoUtil.getFechaDDMMYYYY(pc.getFechaRegistro());
        StringBuffer query = new StringBuffer("select pc.ID_PC_PK,pc.ID_COMPETIDOR_FK,pc.ID_SUBPRODUCTO_FK, pc.FECHA_REGISTRO, pc.PRECIO_VENTA, \n" +
"sub.NOMBRE_SUBPRODUCTO, c.NOMBRE_COMPETIDOR\n" +
"from PRECIOSCOMPETENCIA pc\n" +
"inner join SUBPRODUCTO sub\n" +
"on sub.ID_SUBPRODUCTO_PK = pc.ID_SUBPRODUCTO_FK\n" +
"inner join COMPETIDOR c\n" +
"on c.ID_COMPETIDOR_PK = pc.ID_COMPETIDOR_FK\n" +
"WHERE TO_DATE(TO_CHAR(FECHA_REGISTRO,'dd/mm/yyyy'),'dd/mm/yyyy')='"+tiempoTemporal+"' and pc.ID_COMPETIDOR_FK='"+pc.getIdCompetidorFk()+"' and pc.ID_SUBPRODUCTO_FK='"+pc.getIdSubProductoPk()+"'");
     
        //System.out.println("Query==========: "+query);
        return em.createNativeQuery(query.toString()).getResultList();
    
    }
    
}
