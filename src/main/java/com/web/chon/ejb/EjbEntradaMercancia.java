/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.negocio.NegocioEntradaMercancia;
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
public class EjbEntradaMercancia implements NegocioEntradaMercancia
{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) 
    {
            System.out.println("EJB_INSERTA_ENTRADAMERCANCIA");
        try {
            System.out.println("Entrada: " + entrada);
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIA (ID_EM_PK,ID_PROVEDOR_FK,MOVIMIENTO,FECHA,REMISION,ID_SUCURSAL_FK)VALUES (?,?,?,?,?,?)");
            query.setParameter(1, entrada.getIdEmPK());
            query.setParameter(2, entrada.getIdProvedorFK());
            query.setParameter(3, entrada.getMovimiento());
            query.setParameter(4, entrada.getFecha());
            query.setParameter(5, entrada.getRemision());
            query.setParameter(6, entrada.getIdSucursalFK());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
       
    
    }

    @Override
    public int getNextVal()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
