/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMenudeo;
import com.web.chon.negocio.NegocioEntradaMenudeo;
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
@Stateless(mappedName = "ejbEntradaMenudeo")
public class EjbEntradaMenudeo implements NegocioEntradaMenudeo{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMenudeo(EntradaMenudeo entrada) {
        System.out.println("::::::::::::::::::::::::::::::::");
        System.out.println(entrada.toString());
        try {
            System.out.println("Entrada: " + entrada);
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIAMENUDEO (ID_EMM_PK,ID_PROVEDOR_FK,FECHA,ID_SUCURSAL_FK,ID_STATUS_FK,KILOSTOTALES,KILOSTOTALESPROVEDOR,COMENTARIOS,FOLIO,ID_USER_FK)VALUES (?,?,sysdate,?,4,?,?,?,?,?)");
            query.setParameter(1, entrada.getIdEmmPk());
            query.setParameter(2, entrada.getIdProvedorFk());
            query.setParameter(3, entrada.getIdSucursalFk());
            query.setParameter(4, entrada.getKilosTotales());
            query.setParameter(5, entrada.getKilosProvedor());
            query.setParameter(6, entrada.getComentarios());
            query.setParameter(7, entrada.getFolio());
            query.setParameter(8, entrada.getIdUsuario());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        
        
        
    }
    

    @Override
    public int updateEntradaMenudeo(EntradaMenudeo entrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_EntradaMenudeo.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }

    @Override
    public int getFolio(BigDecimal idSucursal) {
        Query query = em.createNativeQuery("select count(*) from ENTRADAMERCANCIAMENUDEO where ID_SUCURSAL_FK=?");
        query.setParameter(1, idSucursal);
        return Integer.parseInt(query.getSingleResult().toString());
        }

    @Override
    public int buscaMaxMovimiento(EntradaMenudeo entrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getEntradaById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
