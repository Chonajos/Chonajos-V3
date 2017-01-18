package com.web.chon.ejb;

import com.web.chon.dominio.Venta;
import com.web.chon.negocio.NegocioVenta;
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
 * Ejb para ventas menudeo
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbVenta")
public class EjbVenta implements NegocioVenta {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVenta(Venta venta, int folioVenta) {
        Query query = em.createNativeQuery("INSERT INTO VENTA(ID_VENTA_PK,FECHA_VENTA,ID_CLIENTE_FK,ID_VENDEDOR_FK,STATUS_FK,ID_SUCURSAL_FK,FOLIO_SUCURSAL,TIPO_VENTA,ID_USUARIOLOG_FK) VALUES(?,sysdate,?,?,1,?,?,?,?)");
        
        query.setParameter(1, venta.getIdVentaPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFk());
        query.setParameter(4, venta.getIdSucursal());
        query.setParameter(5, folioVenta);
        query.setParameter(6, venta.getTipoVenta());
        query.setParameter(7, venta.getIdUsuarioLogueadoFk());
        
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_VENTA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, String idProducto, BigDecimal idTipoVenta, BigDecimal idCliente) {
        Query query;
        int cont = 0;

        System.out.println("idTipoVenta " + idTipoVenta);

        StringBuffer cadena = new StringBuffer("SELECT ven.ID_VENTA_PK,ven.ID_CLIENTE_FK, "
                + "  ven.ID_VENDEDOR_FK, ven.FECHA_VENTA,ven.STATUS_FK, "
                + "  USU.ID_SUCURSAL_FK, "
                + "  (CLI.NOMBRE||' '||CLI.APELLIDO_PATERNO ||' '||CLI.APELLIDO_MATERNO ) AS CLIENTE, "
                + "  (USU.NOMBRE_USUARIO||' '||USU.APATERNO_USUARIO ||' '||USU.AMATERNO_USUARIO ) AS VENDEDOR, "
                + "  (select NVL(sum(VTP.TOTAL_VENTA),0)"
                + "  FROM VENTA_PRODUCTO VTP WHERE VTP.ID_VENTA_FK =ven.ID_VENTA_PK) "
                + "  AS TOTAL_VENTA,FOLIO_SUCURSAL,sucu.NOMBRE_SUCURSAL, sv.NOMBRE_STATUS,ven.TIPO_VENTA, "
                + "  C.ID_CREDITO_PK,C.FECHA_PROMESA_FIN_PAGO,C.MONTO_CREDITO,C.NUMERO_PAGOS,C.PLAZOS,C.ACUENTA "
                + "  , (U.NOMBRE_USUARIO||' '||U.APATERNO_USUARIO ||' '||U.AMATERNO_USUARIO ) AS LOGUEADO  FROM VENTA ven "
                + "  INNER JOIN CLIENTE CLI ON CLI.ID_CLIENTE = ven.ID_CLIENTE_FK "
                + "  INNER JOIN USUARIO USU ON USU.ID_USUARIO_PK = ven.ID_VENDEDOR_FK "
                + "  INNER join usuario u on u.ID_USUARIO_PK= ven.ID_USUARIOLOG_FK"
                + "  INNER JOIN SUCURSAL sucu on sucu.ID_SUCURSAL_PK = ven.ID_SUCURSAL_FK "
                + "  INNER JOIN STATUS_VENTA sv on sv.ID_STATUS_PK = ven.STATUS_FK "
                + "  LEFT JOIN CREDITO C ON C.ID_VENTA_MENUDEO = ven.ID_VENTA_PK");

        if (idProducto != null && !idProducto.equals("")) {
            cadena.append(" INNER JOIN VENTA_PRODUCTO vp on vp.ID_VENTA_FK = ven.ID_VENTA_PK WHERE  vp.ID_SUBPRODUCTO_FK = '" + idProducto + "'");
            cont++;

        }

        if (!fechaInicio.equals("")) {
            if (cont == 0) {
                cadena.append(" WHERE TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            } else {
                cadena.append(" AND TO_DATE(TO_CHAR(ven.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            cont++;
        }

        if (idSucursal.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }
            cadena.append("ven.ID_SUCURSAL_FK = '" + idSucursal + "' ");
            cont++;
        }

        if (idStatusVenta != null && idStatusVenta.intValue() != 0) {
            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" ven.STATUS_FK  = '" + idStatusVenta + "' ");
            cont++;

        }

        if (idTipoVenta != null) {

            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" ven.TIPO_VENTA  = '" + idTipoVenta + "' ");
            cont++;

        }

        if (idCliente != null) {

            if (cont == 0) {
                cadena.append(" WHERE ");
            } else {
                cadena.append(" AND ");
            }

            cadena.append(" ven.ID_CLIENTE_FK = '" + idCliente + "' ");
            cont++;

        }

        cadena.append(" ORDER BY ven.ID_VENTA_PK");
        System.out.println("query [] " + cadena.toString());

        query = em.createNativeQuery(cadena.toString());

        try {
            List<Object[]> lstObject = query.getResultList();
            return lstObject;
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return null;
        }

    }

    @Override
    public int getFolioByIdSucursal(int idSucursal) {
        try {
            Query query = em.createNativeQuery("select NVL(max(FOLIO_SUCURSAL),0) folioVenta from VENTA where ID_SUCURSAL_FK = ?");
            query.setParameter(1, idSucursal);

            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception e) {
            System.out.println("Error >" + e.getMessage());
            return 1;
        }
    }

    @Override
    public int cancelarVenta(int idVenta, int idUsuario, String comentarios) {
        System.out.println("idVenta: " + idVenta);
        System.out.println("idUsuario: " + idUsuario);
        System.out.println("comentarios: " + comentarios);
        try {
            Query query = em.createNativeQuery("UPDATE VENTA SET STATUS_FK= ?,ID_CANCEL_USER_FK=?,COMENTARIOS_CANCEL=?,FECHA_CANCELACION=sysdate WHERE ID_VENTA_PK = ? ");
            query.setParameter(1, 4);
            query.setParameter(2, idUsuario);
            query.setParameter(3, comentarios);
            query.setParameter(4, idVenta);
            return query.executeUpdate();

        } catch (Exception ex) {

            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public BigDecimal getTotalVentasByDay(String fecha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
