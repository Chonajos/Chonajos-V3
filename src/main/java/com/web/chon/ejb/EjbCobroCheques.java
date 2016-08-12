/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

/**
 *
 * @author JesusAlfredo
 */

import com.web.chon.dominio.CobroCheques;
import com.web.chon.negocio.NegocioCobroCheques;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(mappedName = "ejbCobroCheques")
public class EjbCobroCheques implements NegocioCobroCheques {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarDocumento(CobroCheques cc) 
    {
        try {
            Query query = em.createNativeQuery("INSERT INTO  "
                    + "COBRO_CHEQUES (ID_COBRO_CHEQUES_PK,ID_DOCUMENTO_FK,ID_TIPO_COBRO,"
                    + "FECHA_DEPOSITO, BANCO_DEPOSITO, CUENTA_DEPOSITO,IMPORTE_DEPOSITO,OBSERVACIONES,FECHA_REGISTRO) "
                    + " VALUES(?,?,?,?,?,?,?,?,sysdate)");
            query.setParameter(1, cc.getIdCobroChequePk());
            query.setParameter(2, cc.getIdDocumentoFk());
            query.setParameter(3, cc.getIdTipoCobro());
            query.setParameter(4, cc.getFechaDeposito());
            query.setParameter(5, cc.getBancoDeposito());
            query.setParameter(6, cc.getCuentaDeposito());
            query.setParameter(7, cc.getImporteDeposito());
            query.setParameter(8, cc.getObservaciones());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
        
    }
    @Override
    public int nextVal() {
        try {
            Query query = em.createNativeQuery("select S_COBRO_CHEQUES.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }
    
}
