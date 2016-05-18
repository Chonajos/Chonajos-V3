/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbVentaMayoreo")
public class EjbVentaMayoreo implements NegocioVentaMayoreo {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVenta(VentaMayoreo venta) {
       Query query = em.createNativeQuery("INSERT INTO VENTA_MAYOREO(ID_VENTA_MAYOREO_PK,ID_CLIENTE_FK,ID_VENDEDOR_FK,FECHA_VENTA,ID_SUCURSAL_FK,ID_TIPO_VENTA_FK,ID_STATUS_FK,VENTASUCURSAL) VALUES(?,?,?,sysdate,?,?,1,?)");
        System.out.println("venta_mayoreo ejb :" + venta.toString());
        query.setParameter(1, venta.getIdVentaMayoreoPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFK());
        query.setParameter(4, venta.getIdSucursalFk());
        query.setParameter(5, venta.getIdtipoVentaFk());
        query.setParameter(6, venta.getVentaSucursal());
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
       Query query = em.createNativeQuery("SELECT s_venta_Mayoreo.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }
    @Override
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta,BigDecimal idTipoVenta) {
        Query query;
        System.out.println("Entro a EJB");
        int cont = 0;
        StringBuffer cadena = new StringBuffer("SELECT VEN.*, (CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,\n" +
"(USU.NOMBRE_USUARIO||' '||USU.APATERNO_USUARIO ||' '||USU.AMATERNO_USUARIO ) AS VENDEDOR, (select NVL(sum(VTP.TOTAL_VENTA),0)\n" +
"FROM VENTAMAYOREOPRODUCTO VTP WHERE VTP.ID_VENTA_MAYOREO_FK=ven.ID_VENTA_MAYOREO_PK) AS TOTAL_VENTA, TV.NOMBRE_TIPO_VENTA FROM VENTA_MAYOREO ven\n" +
"INNER JOIN CLIENTE CLI ON CLI.ID_CLIENTE = ven.ID_CLIENTE_FK\n" +
"INNER JOIN USUARIO USU ON USU.ID_USUARIO_PK = ven.ID_VENDEDOR_FK\n" +
"INNER JOIN TIPO_VENTA TV ON TV.ID_TIPO_VENTA_PK = ven.ID_TIPO_VENTA_FK ");

        if (!fechaInicio.equals("")) 
        {
            cont++;
            cadena.append(" WHERE TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
        }

        if (idSucursal!=null && idSucursal.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }
            
            cadena.append(" ven.ID_SUCURSAL_FK = '" + idSucursal + "' ");
            cont++;

        }
       
        System.out.println("idtipoventa: "+idTipoVenta);
        
        if (idTipoVenta!=null && idTipoVenta.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }
            
            cadena.append("ven.ID_TIPO_VENTA_FK = '" + idTipoVenta + "' ");
            cont++;
        }
        
        System.out.println("idStatusVenta:"+idStatusVenta);
        if (idStatusVenta != null && idStatusVenta.intValue() !=0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }
            
            cadena.append(" ven.ID_STATUS_FK  = '" + idStatusVenta + "' ");
            cont++;
        }

        cadena.append(" ORDER BY ven.ID_VENTA_MAYOREO_PK");
        
        query = em.createNativeQuery(cadena.toString());
        System.out.println("Query: "+query);

        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }
    @Override
    public int getVentaSucursal(BigDecimal idSucursal) {
        Query query = em.createNativeQuery("select count(*) from VENTA_MAYOREO where ID_SUCURSAL_FK=?");
        query.setParameter(1, idSucursal);
        return Integer.parseInt(query.getSingleResult().toString());
    }
    
}
