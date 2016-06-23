package com.web.chon.ejb;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioMantenimientoPrecio;
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
 * Ejb para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbMantenimientoPrecio")
public class EjbMantenimientoPrecio implements NegocioMantenimientoPrecio {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getPrecioByIdEmpaqueAndIdProducto(String idProducto, int idEmpaque, int idSucursal) {
        Query query = em.createNativeQuery("SELECT * FROM MANTENIMIENTO_PRECIO WHERE TRIM(ID_SUBPRODUCTO_FK) = ? AND ID_TIPO_EMPAQUE_FK = ? AND ID_SUCURSAL_FK = ? ");
        query.setParameter(1, idProducto);
        query.setParameter(2, idEmpaque);
        query.setParameter(3, idSucursal);

        return query.getResultList();
    }

    @Override
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {
            System.out.println("data ejb insert:" + mantenimientoPrecios.toString());
            Query query = em.createNativeQuery("INSERT INTO MANTENIMIENTO_PRECIO (ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,PRECIO_VENTA,PRECIO_MINIMO,PRECIO_MAXIMO,ID_SUCURSAL_FK,COSTOREAL,COSTOMERMA) values(?,?,?,?,?,?,?,?)");
            query.setParameter(1, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(2, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(3, mantenimientoPrecios.getPrecioVenta());
            query.setParameter(4, mantenimientoPrecios.getPrecioMinimo());
            query.setParameter(5, mantenimientoPrecios.getPrecioMaximo());
            query.setParameter(6, mantenimientoPrecios.getIdSucursal());
            query.setParameter(7, mantenimientoPrecios.getCostoReal());
            query.setParameter(8, mantenimientoPrecios.getCostoMerma());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {

            Query query = em.createNativeQuery("UPDATE MANTENIMIENTO_PRECIO SET ID_SUBPRODUCTO_FK = ? ,ID_TIPO_EMPAQUE_FK =? ,PRECIO_VENTA = ?, PRECIO_MINIMO = ? ,PRECIO_MAXIMO = ?,COSTOREAL = ?,COSTOMERMA = ?  WHERE TRIM(ID_SUBPRODUCTO_FK) = ? AND ID_TIPO_EMPAQUE_FK = ?");
            query.setParameter(1, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(2, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(3, mantenimientoPrecios.getPrecioVenta());
            query.setParameter(4, mantenimientoPrecios.getPrecioMinimo());
            query.setParameter(5, mantenimientoPrecios.getPrecioMaximo());
            query.setParameter(6, mantenimientoPrecios.getCostoReal());
            query.setParameter(7, mantenimientoPrecios.getCostoMerma());
            query.setParameter(8, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(9, mantenimientoPrecios.getIdTipoEmpaquePk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAllByIdSucAndIdSubProducto(BigDecimal idSucursal, String idSubProducto) {

        try {
            StringBuilder queryStr;

            queryStr = new StringBuilder("SELECT SP.ID_SUBPRODUCTO_PK, SP.NOMBRE_SUBPRODUCTO, MP.ID_SUCURSAL_FK,MP.ID_TIPO_EMPAQUE_FK,MP.PRECIO_MINIMO,MP.PRECIO_VENTA,MP.PRECIO_MAXIMO, "
                    + "EXM.KILOS,MP.COSTOREAL,MP.COSTOMERMA FROM SUBPRODUCTO SP  "
                    + "LEFT JOIN MANTENIMIENTO_PRECIO MP ON SP.ID_SUBPRODUCTO_PK = MP.ID_SUBPRODUCTO_FK AND MP.ID_SUCURSAL_FK = " + idSucursal + " "
                    + "LEFT JOIN EXISTENCIAMENUDEO EXM ON EXM.ID_SUBPRODUCTO_FK = SP.ID_SUBPRODUCTO_PK AND EXM.ID_SUCURSAL_FK = " + idSucursal + " ");

            if (idSubProducto != null) {
                queryStr.append("WHERE SP.ID_SUBPRODUCTO_PK =" + idSubProducto.trim() + "");
            }

            queryStr.append(" ORDER by SP.ID_SUBPRODUCTO_PK ");
            Query query = em.createNativeQuery(queryStr.toString());

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
