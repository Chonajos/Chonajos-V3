/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Sucursal;
import com.web.chon.negocio.NegocioCatSucursales;
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
@Stateless(mappedName = "ejbCatSucursales")
public class EjbCatSucursales implements NegocioCatSucursales
{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getSucursales() 
    {
          try {

            System.out.println("EJB_GET_CLIENTE");
            Query query = em.createNativeQuery("select * from sucursal");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) 
        {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getSucursalId(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteSucursal(int idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateSucursal(Sucursal sucu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertSucursal(Sucursal sucu) 
    {
          System.out.println("EJB_INSERTA_SUCURSAL");
        try {
                System.out.println("insert : " + sucu);
                Query query = em.createNativeQuery("INSERT INTO SUCURSALES (ID_SUCURSAL_PK,NOMBRE_SUCURSAL,CALLE_SUCURSAL,CP_SUCURSAL,TELEFONO_SUCURSAL,NUM_INT,NUM_EXT,STATUS_SUCURSAL)"
                        + "VALUES(S_SUCURSAL.NextVal,?,?,?,?,?,?,?)");
                query.setParameter(1, sucu.getNombreSucursal());
                query.setParameter(2, sucu.getCalleSucursal());
                query.setParameter(3, sucu.getCpSucursal());
                query.setParameter(4, sucu.getTelefonoSucursal());
                query.setParameter(5, sucu.getNumInt());
                query.setParameter(6, sucu.getNumExt());
                query.setParameter(7, sucu.getStatusSucursal());
                return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
