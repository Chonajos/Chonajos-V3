package com.web.chon.ejb;

import com.web.chon.dominio.EntregaMercancia;
import com.web.chon.dominio.Producto;
import com.web.chon.negocio.NegocioEntregaMercancia;
import com.web.chon.negocio.NegocioProducto;
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
//            System.out.println("insert : " + producto.getNombreProducto() + " " + producto.getDescripcionProducto());
            Query query = em.createNativeQuery("insert into PRODUCTO (ID_PRODUCTO_PK,NOMBRE_PRODUCTO,DESCRIPCION_PRODUCTO) values(?,?,?)");
//            query.setParameter(1, producto.getIdProductoPk());
//            query.setParameter(2, producto.getNombreProducto());
//            query.setParameter(3, producto.getDescripcionProducto());

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

            Query query = em.createNativeQuery("SELECT VMP.ID_V_M_P_PK,VMP.ID_VENTA_MAYOREO_FK,VMP.ID_SUBPRODUCTO_FK,SP.NOMBRE_SUBPRODUCTO,VMP.PRECIO_PRODUCTO,VMP.KILOS_VENDIDOS,VMP.CANTIDAD_EMPAQUE,VMP.TOTAL_VENTA, "
                    + "                                        VMP.ID_TIPO_EMPAQUE_FK,TE.NOMBRE_EMPAQUE, VM.ID_CLIENTE_FK,C.NOMBRE ||' '||C.APELLIDO_PATERNO ||' '||C.APELLIDO_MATERNO AS NOMBRE_CLIENTE,VM.ID_VENDEDOR_FK,VM.FECHA_VENTA,VM.ID_STATUS_FK, "
                    + "                                        SV.NOMBRE_STATUS ,VM.ID_SUCURSAL_FK, "
                    + "                                        (SELECT NVL(SUM(EM.EMPAQUES_ENTREGADOS),0) FROM ENTREGA_MERCANCIA EM WHERE EM.ID_VP_MAYOREO_FK =VMP.ID_V_M_P_PK) AS EMPAQUES_ENTREGADOS, "
                    + "                                        (SELECT NVL(SUM(EM.KILOS_ENTREGADOS),0) FROM ENTREGA_MERCANCIA EM WHERE EM.ID_VP_MAYOREO_FK =VMP.ID_V_M_P_PK) AS KILOS_ENTREGADOS,VM.ID_TIPO_VENTA_FK,TV.NOMBRE_TIPO_VENTA "
                    + "                                        FROM VENTAMAYOREOPRODUCTO VMP "
                    + "                                        INNER JOIN SUBPRODUCTO SP ON SP.ID_SUBPRODUCTO_PK = VMP.ID_SUBPRODUCTO_FK "
                    + "                                        INNER JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK AND VM.VENTASUCURSAL =? AND VM.ID_SUCURSAL_FK = ? "
                    + "                                        INNER JOIN CLIENTE C ON C.ID_CLIENTE = VM.ID_CLIENTE_FK "
                    + "                                        INNER JOIN STATUS_VENTA SV ON SV.ID_STATUS_PK = VM.ID_STATUS_FK "
                    + "                                        INNER JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK =VMP.ID_TIPO_EMPAQUE_FK "
                    + "                                        INNER JOIN TIPO_VENTA TV ON TV.ID_TIPO_VENTA_PK = VM.ID_TIPO_VENTA_FK ");

            query.setParameter(1, idFolioSucursal);
            query.setParameter(2, idSucursal);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbEntregaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
