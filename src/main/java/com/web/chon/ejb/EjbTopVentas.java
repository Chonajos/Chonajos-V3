/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.negocio.NegocioTopVentas;
import java.math.BigDecimal;
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
    public List<Object[]> getMenudeo(String fechaInicio, String fechaFin, String orden, BigDecimal rows) {
        Query query;

        try {

            StringBuffer cadena = new StringBuffer("SELECT * FROM(SELECT t1.*,row_number() over (order by t1.Dinero " + orden + ") rn FROM \n"
                    + "(SELECT * FROM(select sucu.NOMBRE_SUCURSAL,usu.NOMBRE_USUARIO, usu.APATERNO_USUARIO,\n"
                    + "usu.AMATERNO_USUARIO,v.ID_VENDEDOR_FK,sum(vp.CANTIDAD_EMPAQUE) as Empaques, sum(vp.CANTIDAD_EMPAQUE) as Kilos, \n"
                    + "sum(vp.TOTAL_VENTA) as Dinero\n"
                    + "from VENTA_PRODUCTO vp\n"
                    + "inner join VENTA v\n"
                    + "on v.ID_VENTA_PK = vp.ID_VENTA_FK\n"
                    + "inner join SUCURSAL sucu\n"
                    + "on sucu.ID_SUCURSAL_PK = v.ID_SUCURSAL_FK\n"
                    + "inner join USUARIO usu\n"
                    + "on usu.ID_USUARIO_PK = v.ID_VENDEDOR_FK WHERE v.STATUS_FK !=4");

            if (!fechaInicio.equals("")) {
                cadena.append(" AND TO_DATE(TO_CHAR(v.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            cadena.append(" GROUP BY V.ID_VENDEDOR_FK,sucu.NOMBRE_SUCURSAL,usu.APATERNO_USUARIO,usu.AMATERNO_USUARIO,usu.NOMBRE_USUARIO\n" + ") )t1)");
            if (rows != null) {
                cadena.append(" WHERE rn between '0' and '" + rows.toString() + "'");
            }
            System.out.println("========================================");
            System.out.println("Cadena: " + cadena);
            System.out.println("========================================");
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
    public List<Object[]> getMayoreo(String fechaInicio, String fechaFin, String orden, BigDecimal rows) {
        Query query;
        try {

            StringBuffer cadena = new StringBuffer("SELECT * FROM(SELECT t1.*,row_number() over (order by t1.Dinero " + orden + ") rn FROM \n"
                    + "(SELECT * FROM(select sucu.NOMBRE_SUCURSAL,usu.NOMBRE_USUARIO, usu.APATERNO_USUARIO,\n"
                    + "usu.AMATERNO_USUARIO,vm.ID_VENDEDOR_FK,sum(vmp.CANTIDAD_EMPAQUE) as Empaques, sum(vmp.kilos_vendidos) as Kilos, \n"
                    + "sum(vmp.TOTAL_VENTA) as Dinero\n"
                    + "from VENTAMAYOREOPRODUCTO vmp\n"
                    + "inner join VENTA_MAYOREO vm\n"
                    + "on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n"
                    + "inner join SUCURSAL sucu\n"
                    + "on sucu.ID_SUCURSAL_PK = vm.ID_SUCURSAL_FK\n"
                    + "inner join USUARIO usu\n"
                    + "on usu.ID_USUARIO_PK = vm.ID_VENDEDOR_FK AND vm.ID_STATUS_FK !=4 ");

            if (!fechaInicio.equals("")) {
                cadena.append(" AND TO_DATE(TO_CHAR(vm.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            cadena.append(" GROUP BY VM.ID_VENDEDOR_FK,sucu.NOMBRE_SUCURSAL,usu.APATERNO_USUARIO,usu.AMATERNO_USUARIO,usu.NOMBRE_USUARIO\n"
                    + ") )t1)");
            if (rows != null) {
                cadena.append(" WHERE rn between '0' and '" + rows.toString() + "'");
            }
            System.out.println("========================================");
            System.out.println("Cadena: " + cadena);
            System.out.println("========================================");
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
