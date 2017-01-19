package com.web.chon.ejb;

import com.web.chon.dominio.EntregaMercancia;
import com.web.chon.negocio.NegocioEntregaMercancia;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Ejb Entrega de Mercancia
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbEntregaMercancia")
public class EjbEntregaMercancia implements NegocioEntregaMercancia {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertar(EntregaMercancia entregaMercancia) {
        try {
            Query query = em.createNativeQuery("INSERT INTO ENTREGA_MERCANCIA"
                    + " (ID_ENTREGA_MERCANCIA_PK,ID_VP_MENUDEO_FK,ID_VP_MAYOREO_FK,ID_USUARIO_FK,FECHA,EMPAQUES_ENTREGADOS,KILOS_ENTREGADOS,OBSERVACIONES) "
                    + "VALUES(S_ENTREGA_MERCANCIA.NextVal,?,?,?,SYSDATE,?,?,?)");

            query.setParameter(1, entregaMercancia.getIdVPMenudeo());
            query.setParameter(2, entregaMercancia.getIdVPMayoreo());
            query.setParameter(3, entregaMercancia.getIdUsuario());
            query.setParameter(4, entregaMercancia.getEmpaquesEntregar());
            query.setParameter(5, entregaMercancia.getKilosEntregar());
            query.setParameter(6, entregaMercancia.getObservaciones());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEntregaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() {

        try {

            Query query = em.createNativeQuery("SELECT S_ENTREGA_MERCANCIA.NextVal FROM DUAL ");

            return Integer.parseInt(query.getSingleResult().toString());

        } catch (Exception ex) {
            Logger.getLogger(EjbEntregaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getByIdSucursalAndIdFolioSucursal(BigDecimal idSucursal, BigDecimal idFolioSucursal) {

        try {

            Query query = em.createNativeQuery("SELECT VMP.ID_V_M_P_PK,VMP.ID_VENTA_MAYOREO_FK,VMP.ID_SUBPRODUCTO_FK,SP.NOMBRE_SUBPRODUCTO,VMP.PRECIO_PRODUCTO,VMP.KILOS_VENDIDOS,VMP.CANTIDAD_EMPAQUE,VMP.TOTAL_VENTA, \n"
                    + "VMP.ID_TIPO_EMPAQUE_FK,TE.NOMBRE_EMPAQUE, VM.ID_CLIENTE_FK,C.NOMBRE ||' '||C.APELLIDO_PATERNO ||' '||C.APELLIDO_MATERNO AS NOMBRE_CLIENTE,VM.ID_VENDEDOR_FK,VM.FECHA_VENTA,VM.ID_STATUS_FK, \n"
                    + "SV.NOMBRE_STATUS ,VM.ID_SUCURSAL_FK, \n"
                    + "(SELECT NVL(SUM(EM.EMPAQUES_ENTREGADOS),0) FROM ENTREGA_MERCANCIA EM WHERE EM.ID_VP_MAYOREO_FK =VMP.ID_V_M_P_PK) AS EMPAQUES_ENTREGADOS, \n"
                    + "(SELECT NVL(SUM(EM.KILOS_ENTREGADOS),0) FROM ENTREGA_MERCANCIA EM WHERE EM.ID_VP_MAYOREO_FK =VMP.ID_V_M_P_PK) AS KILOS_ENTREGADOS,VM.ID_TIPO_VENTA_FK,TV.NOMBRE_TIPO_VENTA \n"
                    + ",VMP.CANTIDAD_EMPAQUE-(SELECT NVL(SUM(EM.EMPAQUES_ENTREGADOS),0) FROM ENTREGA_MERCANCIA EM WHERE EM.ID_VP_MAYOREO_FK =VMP.ID_V_M_P_PK) AS EMPAQUES_RESTANTES,EM.CARROSUCURSAL \n"
                    + "FROM VENTAMAYOREOPRODUCTO VMP \n"
                    + "INNER JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK\n"
                    + "INNER JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EMP_PK = EXP.ID_EMP_FK\n"
                    + "INNER JOIN ENTRADAMERCANCIA EM ON EM.ID_EM_PK = EMP.ID_EM_FK\n"
                    + "INNER JOIN SUBPRODUCTO SP ON SP.ID_SUBPRODUCTO_PK = VMP.ID_SUBPRODUCTO_FK \n"
                    + "INNER JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK AND VM.VENTASUCURSAL = ? AND VM.ID_SUCURSAL_FK = ? \n"
                    + "INNER JOIN CLIENTE C ON C.ID_CLIENTE = VM.ID_CLIENTE_FK \n"
                    + "INNER JOIN STATUS_VENTA SV ON SV.ID_STATUS_PK = VM.ID_STATUS_FK \n"
                    + "INNER JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK =VMP.ID_TIPO_EMPAQUE_FK \n"
                    + "INNER JOIN TIPO_VENTA TV ON TV.ID_TIPO_VENTA_PK = VM.ID_TIPO_VENTA_FK ORDER BY EMPAQUES_RESTANTES DESC");

            query.setParameter(1, idFolioSucursal);
            query.setParameter(2, idSucursal);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbEntregaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getByIdVentaMayoreoProducto(BigDecimal idVentaMayoreoProducto) {

        try {

            Query query = em.createNativeQuery("select ID_ENTREGA_MERCANCIA_PK, ID_VP_MENUDEO_FK, ID_VP_MAYOREO_FK, ID_USUARIO_FK, FECHA, EMPAQUES_ENTREGADOS, KILOS_ENTREGADOS, OBSERVACIONES, "
                    + "(U.NOMBRE_USUARIO || ' ' || U.APATERNO_USUARIO || ' ' || U.AMATERNO_USUARIO) AS NOMBRE_USUARIO "
                    + "        from ENTREGA_MERCANCIA EM INNER JOIN USUARIO U ON U.ID_USUARIO_PK = EM.ID_USUARIO_FK "
                    + "        WHERE ID_VP_MAYOREO_FK = ?");

            query.setParameter(1, idVentaMayoreoProducto);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbEntregaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
