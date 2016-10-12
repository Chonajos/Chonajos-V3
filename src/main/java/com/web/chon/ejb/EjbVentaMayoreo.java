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
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jboss.logging.Logger;

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
        Query query = em.createNativeQuery("INSERT INTO VENTA_MAYOREO(ID_VENTA_MAYOREO_PK,ID_CLIENTE_FK,ID_VENDEDOR_FK,FECHA_VENTA,ID_SUCURSAL_FK,ID_TIPO_VENTA_FK,ID_STATUS_FK,VENTASUCURSAL) VALUES(?,?,?,sysdate,?,?,?,?)");
        query.setParameter(1, venta.getIdVentaMayoreoPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFK());
        query.setParameter(4, venta.getIdSucursalFk());
        query.setParameter(5, venta.getIdtipoVentaFk());
        query.setParameter(6, venta.getIdStatusFk());
        query.setParameter(7, venta.getVentaSucursal());
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT s_venta_Mayoreo.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());

    }

    @Override
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, BigDecimal idTipoVenta) {
        Query query;
        int cont = 0;
        StringBuffer cadena = new StringBuffer("SELECT VEN.*, (CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, "
                + " (USU.NOMBRE_USUARIO||' '||USU.APATERNO_USUARIO ||' '||USU.AMATERNO_USUARIO ) AS VENDEDOR, (select NVL(sum(VTP.TOTAL_VENTA),0) "
                + " FROM VENTAMAYOREOPRODUCTO VTP WHERE VTP.ID_VENTA_MAYOREO_FK=ven.ID_VENTA_MAYOREO_PK) AS TOTAL_VENTA, TV.NOMBRE_TIPO_VENTA , "
                + " (select SUM((emp.KILOSPROMPROD*emp.CONVENIO*vmp.CANTIDAD_EMPAQUE)) AS COSTO_VENTA from VENTA_MAYOREO VENC "
                + " inner join VENTAMAYOREOPRODUCTO vmp on vmp.ID_VENTA_MAYOREO_FK = VENC.ID_VENTA_MAYOREO_PK "
                + " inner join EXISTENCIA_PRODUCTO exp on exp.ID_EXP_PK = vmp.ID_EXISTENCIA_FK "
                + " inner join ENTRADAMERCANCIAPRODUCTO emp on emp.ID_EMP_PK = exp.ID_EMP_FK where VENC.ID_VENTA_MAYOREO_PK =ven.ID_VENTA_MAYOREO_PK)AS COSTO_TOTAL FROM VENTA_MAYOREO ven "
                + " INNER JOIN CLIENTE CLI ON CLI.ID_CLIENTE = ven.ID_CLIENTE_FK "
                + " INNER JOIN USUARIO USU ON USU.ID_USUARIO_PK = ven.ID_VENDEDOR_FK "
                + " INNER JOIN TIPO_VENTA TV ON TV.ID_TIPO_VENTA_PK = ven.ID_TIPO_VENTA_FK ");

        if (!fechaInicio.equals("")) {
            cont++;
            cadena.append(" WHERE TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
        }

        if (idSucursal != null && idSucursal.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" ven.ID_SUCURSAL_FK = '" + idSucursal + "' ");
            cont++;

        }

        if (idTipoVenta != null && idTipoVenta.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append("ven.ID_TIPO_VENTA_FK = '" + idTipoVenta + "' ");
            cont++;
        }

        if (idStatusVenta != null && idStatusVenta.intValue() != 0) {
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

        try {
            System.out.println("" + cadena.toString());
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public int getVentaSucursal(BigDecimal idSucursal) {
        Query query = em.createNativeQuery("select NVL(max(VENTASUCURSAL),0) from VENTA_MAYOREO where ID_SUCURSAL_FK=?");
        query.setParameter(1, idSucursal);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int updateEstatusVentaByFolioSucursalAndIdSucursal(BigDecimal folioSucursal, BigDecimal idSucursal, BigDecimal estatusVenta) {
        try {
            Query query = em.createNativeQuery("UPDATE VENTA_MAYOREO SET ID_STATUS_FK = ? WHERE ID_SUCURSAL_FK = ? AND VENTASUCURSAL =?");
            
            query.setParameter(1, estatusVenta);
            query.setParameter(2, idSucursal);
            query.setParameter(3, folioSucursal);
            

            return query.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error >" + ex.getMessage());
            Logger.getLogger(EjbVentaMayoreo.class).log(Logger.Level.FATAL, ex);
            return 0;

        }
    }

    @Override
    public int cancelarVentaMayoreo(BigDecimal idVenta, BigDecimal idUsuario, String comentarios) {
        System.out.println("idVenta: " + idVenta);
        System.out.println("idUsuario: " + idUsuario);
        System.out.println("comentarios: " + comentarios);
        try {
            Query query = em.createNativeQuery("UPDATE VENTA_MAYOREO SET ID_STATUS_FK= ?,ID_USER_CANCEL_FK=?,COMENTARIOS_CANCEL=?,FECHA_CANCELACION=sysdate WHERE ID_VENTA_MAYOREO_PK = ? ");
            query.setParameter(1, 4);
            query.setParameter(2, idUsuario);
            query.setParameter(3, comentarios);
            query.setParameter(4, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) {

            java.util.logging.Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    
    }

    @Override
    public List<Object[]> getVentaMayoreoByFolioidSucursalFk(BigDecimal idFolio, BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
