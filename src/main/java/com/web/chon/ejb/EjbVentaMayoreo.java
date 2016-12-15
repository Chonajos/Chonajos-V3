
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
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, BigDecimal idTipoVenta, String idSubProducto, BigDecimal idCliente) {
        Query query;
        int cont = 0;

        //FUNCIONA Y TRAE SOLO LA UTILIDAD POR COSTO
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

        if (idSubProducto != null && !idSubProducto.equals("")) {
            cadena.append(" INNER JOIN VENTAMAYOREOPRODUCTO VMPP ON VMPP.ID_VENTA_MAYOREO_FK = ven.ID_VENTA_MAYOREO_PK WHERE VMPP.ID_SUBPRODUCTO_FK ='" + idSubProducto + "'");
            cont++;

        }
        
        if (!fechaInicio.equals("")) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }
            
            cadena.append(" TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            cont++;

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

        if (idCliente != null) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" ven.ID_CLIENTE_FK  = '" + idCliente + "' ");
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
        try {
            Query query = em.createNativeQuery("select vm.ID_VENTA_MAYOREO_PK,vm.ID_CLIENTE_FK,vm.ID_VENDEDOR_FK,vm.FECHA_VENTA,\n"
                    + "vm.FECHA_PROMESA_PAGO,vm.ID_STATUS_FK,vm.FECHA_PAGO,vm.ID_SUCURSAL_FK, vm.ID_TIPO_VENTA_FK,\n"
                    + "vm.VENTASUCURSAL, vm.ID_CAJERO_FK, vm.ID_USER_CANCEL_FK, vm.FECHA_CANCELACION,vm.COMENTARIOS_CANCEL,\n"
                    + "(CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE,\n"
                    + "(usu.NOMBRE_USUARIO||' '||usu.APATERNO_USUARIO ||' '||usu.AMATERNO_USUARIO ) AS Vendedor,\n"
                    + "tv.NOMBRE_TIPO_VENTA, sv.NOMBRE_STATUS\n"
                    + "from VENTA_MAYOREO vm \n"
                    + "inner join CLIENTE cli on cli.ID_CLIENTE = vm.ID_CLIENTE_FK\n"
                    + "inner join USUARIO usu on usu.ID_USUARIO_PK = vm.ID_VENDEDOR_FK\n"
                    + "inner join TIPO_VENTA tv on tv.ID_TIPO_VENTA_PK = vm.ID_TIPO_VENTA_FK\n"
                    + "inner join STATUS_VENTA sv on sv.ID_STATUS_PK = vm.ID_STATUS_FK\n"
                    + "where vm.VENTASUCURSAL= ? and vm.ID_SUCURSAL_FK = ?");
            query.setParameter(1, idFolio);
            query.setParameter(2, idSucursal);
            return query.getResultList();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EjbVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getDetalleReporteVentas(BigDecimal carro, BigDecimal idSucursal, BigDecimal idTipoVenta) {

        try {
            StringBuffer txtQuery = new StringBuffer("SELECT ENM.CARROSUCURSAL,EMP.ID_SUBPRODUCTO_FK,SUB.NOMBRE_SUBPRODUCTO, "
                    + "EMP.ID_TIPO_EMPAQUE_FK,TE.NOMBRE_EMPAQUE,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,VMP.CANTIDAD_EMPAQUE,VMP.KILOS_VENDIDOS, "
                    + "VMP.PRECIO_PRODUCTO,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,VM.FECHA_VENTA,VM.ID_CLIENTE_FK, "
                    + "CLI.NOMBRE ||' '||CLI.APELLIDO_PATERNO ||' '|| CLI.APELLIDO_MATERNO AS NOMBRE_CLIENTE,EMP.ID_EMP_PK  FROM ENTRADAMERCANCIA ENM "
                    + "RIGHT JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EM_FK = ENM.ID_EM_PK "
                    + "RIGHT JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK = EMP.ID_SUBPRODUCTO_FK "
                    + "RIGHT JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = EMP.ID_TIPO_EMPAQUE_FK "
                    + "RIGHT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EMP_FK = EMP.ID_EMP_PK "
                    + "RIGHT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_EXISTENCIA_FK = EXP.ID_EXP_PK "//LLEVAVA LEF PARA MOSTRAR INCLUSO LOS PRODUCTOS QUE NO SE AN ECHO VENTAS
                    + "RIGHT JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK "
                    + "RIGHT JOIN CLIENTE CLI ON CLI.ID_CLIENTE = VM.ID_CLIENTE_FK "
                    + "WHERE ENM.ID_SUCURSAL_FK =" + idSucursal + " AND ENM.CARROSUCURSAL =" + carro + " AND VM.ID_STATUS_FK !=4");

            if (idTipoVenta != null) {
                txtQuery.append(" AND VM.ID_TIPO_VENTA_FK =" + idTipoVenta);
            }
            txtQuery.append(" ORDER BY SUB.ID_SUBPRODUCTO_PK, VM.FECHA_VENTA");

            Query query = em.createNativeQuery(txtQuery.toString());

            return query.getResultList();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EjbVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getReporteVentas(BigDecimal carro, BigDecimal idSucursal, BigDecimal idTipoVenta) {

        try {
            StringBuffer txtQuery = new StringBuffer("SELECT ENM.CARROSUCURSAL,EMP.ID_SUBPRODUCTO_FK,SUB.NOMBRE_SUBPRODUCTO, "
                    + "EMP.ID_TIPO_EMPAQUE_FK,TE.NOMBRE_EMPAQUE,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,SUM(VMP.CANTIDAD_EMPAQUE),SUM(VMP.KILOS_VENDIDOS), "
                    + "SUM(VMP.KILOS_VENDIDOS*VMP.PRECIO_PRODUCTO) AS TOTAL_VENTA,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,EMP.ID_EMP_PK,EXP.PRECIO_MINIMO FROM ENTRADAMERCANCIA ENM "
                    + "RIGHT JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EM_FK = ENM.ID_EM_PK "
                    + "RIGHT JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK = EMP.ID_SUBPRODUCTO_FK "
                    + "RIGHT JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = EMP.ID_TIPO_EMPAQUE_FK "
                    + "RIGHT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EMP_FK = EMP.ID_EMP_PK "
                    + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_EXISTENCIA_FK = EXP.ID_EXP_PK "
                    + "LEFT JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK "
                    + "LEFT JOIN CLIENTE CLI ON CLI.ID_CLIENTE = VM.ID_CLIENTE_FK "
                    + "WHERE ENM.ID_SUCURSAL_FK =" + idSucursal + " AND (VM.ID_STATUS_FK !=4 OR VM.ID_STATUS_FK IS NULL) AND ENM.CARROSUCURSAL =" + carro);

            if (idTipoVenta != null) {
                txtQuery.append(" AND VM.ID_TIPO_VENTA_FK =" + idTipoVenta);
            }

            txtQuery.append(" GROUP BY ENM.CARROSUCURSAL,EMP.ID_SUBPRODUCTO_FK,SUB.NOMBRE_SUBPRODUCTO, "
                    + " EMP.ID_TIPO_EMPAQUE_FK,TE.NOMBRE_EMPAQUE,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,EMP.ID_EMP_PK,EXP.PRECIO_MINIMO");
            Query query = em.createNativeQuery(txtQuery.toString());

            return query.getResultList();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EjbVentaMayoreo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getDetalleVentasCarro(BigDecimal idSucursal, BigDecimal carro) {
        try {
            Query query = em.createNativeQuery("SELECT VM.ID_VENTA_MAYOREO_PK,VM.FECHA_VENTA,VM.VENTASUCURSAL,TV.NOMBRE_TIPO_VENTA,CRE.ESTATUS_CREDITO,SC.NOMBRE_STATUS, "
                    + "SUM(VMP.CANTIDAD_EMPAQUE) AS PAQUETES_VENDIDOS,SUM(VMP.KILOS_VENDIDOS) AS KILOS_VENDIDOS,SUM(VMP.TOTAL_VENTA) AS TOTAL_VENTA "
                    + ",CLI.NOMBRE ||' '||CLI.APELLIDO_PATERNO||' '||CLI.APELLIDO_MATERNO AS NOMBRE_CLIENTE,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_CONVENIO_FK,EMP.CONVENIO FROM VENTA_MAYOREO VM "
                    + "LEFT JOIN VENTAMAYOREOPRODUCTO VMP ON VMP.ID_VENTA_MAYOREO_FK = VM.ID_VENTA_MAYOREO_PK "
                    + "LEFT JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                    + "LEFT JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EMP_PK = EXP.ID_EMP_FK "
                    + "LEFT JOIN ENTRADAMERCANCIA EM ON EM.ID_EM_PK = EMP.ID_EM_FK "
                    + "LEFT JOIN TIPO_VENTA TV ON TV.ID_TIPO_VENTA_PK = VM.ID_TIPO_VENTA_FK "
                    + "LEFT JOIN CREDITO CRE ON CRE.ID_VENTA_MAYOREO = VM.ID_VENTA_MAYOREO_PK "
                    + "LEFT JOIN STATUS_CREDITO SC ON SC.ID_STATUS_CREDITO_PK = CRE.ESTATUS_CREDITO "
                    + "LEFT JOIN CLIENTE CLI ON CLI.ID_CLIENTE = VM.ID_CLIENTE_FK "
                    + "WHERE EM.CARROSUCURSAL = ? AND EM.ID_SUCURSAL_FK =? AND VM.ID_STATUS_FK !=4 GROUP BY VM.ID_VENTA_MAYOREO_PK,TV.NOMBRE_TIPO_VENTA,VM.FECHA_VENTA,VM.VENTASUCURSAL, "
                    + "CRE.ESTATUS_CREDITO,SC.NOMBRE_STATUS,CLI.NOMBRE ,CLI.APELLIDO_PATERNO, CLI.APELLIDO_MATERNO "
                    + ",EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_CONVENIO_FK,EMP.CONVENIO ORDER BY VM.FECHA_VENTA ");

            query.setParameter(1, carro);
            query.setParameter(2, idSucursal);

            return query.getResultList();

        } catch (Exception ex) {
            System.out.println("error > " + ex.getMessage().toString());
            ex.getStackTrace();
            return null;
        }
    }

}
