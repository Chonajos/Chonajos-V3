/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTopVentas;
import java.util.ArrayList;
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
@Stateless(mappedName = "ejbTopVentas")
public class EjbTopVentas implements NegocioTopVentas {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getMenudeo(String fechaInicio, String fechaFin) {
        Query query;

        try {

            StringBuffer cadena = new StringBuffer("SELECT sucu.NOMBRE_SUCURSAL, USU.NOMBRE_USUARIO,USU.APATERNO_USUARIO,VM.ID_VENDEDOR_FK, COUNT(VM.ID_VENTA_MAYOREO_PK) as Ventas\n"
                    + "FROM VENTA_MAYOREO VM\n"
                    + "INNER JOIN USUARIO USU\n"
                    + "ON  USU.ID_USUARIO_PK = VM.ID_VENDEDOR_FK \n"
                    + "INNER JOIN SUCURSAL sucu\n"
                    + "on sucu.ID_SUCURSAL_PK = USU.ID_SUCURSAL_FK\n");
                    
                    

            if (!fechaInicio.equals("")) {
                cadena.append("WHERE TO_DATE(TO_CHAR(vm.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            cadena.append("GROUP BY VM.ID_VENDEDOR_FK,VM.ID_VENDEDOR_FK, USU.NOMBRE_USUARIO,sucu.NOMBRE_SUCURSAL,USU.APATERNO_USUARIO");
            query = em.createNativeQuery(cadena.toString());

            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbTopVentas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getMayoreo(String fechaInicio, String fechaFin) {
        Query query;
        try {

            StringBuffer cadena = new StringBuffer("SELECT sucu.NOMBRE_SUCURSAL, USU.NOMBRE_USUARIO,USU.APATERNO_USUARIO,V.ID_VENDEDOR_FK, COUNT(V.ID_VENTA_PK) as Ventas\n"
                    + "FROM VENTA V\n"
                    + "INNER JOIN USUARIO USU\n"
                    + "ON  USU.ID_USUARIO_PK = V.ID_VENDEDOR_FK\n"
                    + "INNER JOIN SUCURSAL sucu\n"
                    + "on sucu.ID_SUCURSAL_PK = USU.ID_SUCURSAL_FK\n");
                   
            if (!fechaInicio.equals("")) {
                cadena.append("WHERE TO_DATE(TO_CHAR(v.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            cadena.append("GROUP BY V.ID_VENDEDOR_FK,V.ID_VENDEDOR_FK, USU.NOMBRE_USUARIO,sucu.NOMBRE_SUCURSAL,USU.APATERNO_USUARIO");
            query = em.createNativeQuery(cadena.toString());

            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbTopVentas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
