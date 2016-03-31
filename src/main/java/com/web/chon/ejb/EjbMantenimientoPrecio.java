package com.web.chon.ejb;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioMantenimientoPrecio;
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
    public List<Object[]> getPrecioByIdEmpaqueAndIdProducto(String idProducto, int idEmpaque) {
        Query query = em.createNativeQuery("SELECT * FROM MANTENIMIENTO_PRECIO WHERE TRIM(ID_SUBPRODUCTO_FK) = ? AND ID_TIPO_EMPAQUE_FK = ?");
        query.setParameter(1, idProducto);
        query.setParameter(2, idEmpaque);

        return query.getResultList();
    }

    @Override
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {
            System.out.println("data ejb insert:"+mantenimientoPrecios.toString());
            Query query = em.createNativeQuery("INSERT INTO MANTENIMIENTO_PRECIO (ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,PRECIO_VENTA,PRECIO_MINIMO,PRECIO_MAXIMO) values(?,?,?,?,?)");
            query.setParameter(1, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(2, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(3, mantenimientoPrecios.getPrecioVenta());
            query.setParameter(4, mantenimientoPrecios.getPrecioMinimo());
            query.setParameter(5, mantenimientoPrecios.getPrecioMaximo());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {

            Query query = em.createNativeQuery("UPDATE MANTENIMIENTO_PRECIO SET ID_SUBPRODUCTO_FK = ? ,ID_TIPO_EMPAQUE_FK =? ,PRECIO_VENTA = ?, PRECIO_MINIMO = ? ,PRECIO_MAXIMO = ?  WHERE TRIM(ID_SUBPRODUCTO_FK) = ? AND ID_TIPO_EMPAQUE_FK = ?");
            query.setParameter(1, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(2, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(3, mantenimientoPrecios.getPrecioVenta());
            query.setParameter(4, mantenimientoPrecios.getPrecioMinimo());
            query.setParameter(5, mantenimientoPrecios.getPrecioMaximo());
            query.setParameter(6, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(7, mantenimientoPrecios.getIdTipoEmpaquePk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}