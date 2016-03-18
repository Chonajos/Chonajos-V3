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
        Query query = em.createNativeQuery("SELECT * FROM MANTENIMIENTO_PRECIO WHERE ID_SUBPRODUCTO_FK = ?");

        return query.getResultList();
    }

    @Override
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {
            Query query = em.createNativeQuery("INSERT INTO SUBPRODUCTO (ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO,DESCRIPCION_SUBPRODUCTO,ID_PRODUCTO_FK) values(?,?,?,?)");
//            query.setParameter(1, subProducto.getIdSubproductoPk());
//            query.setParameter(2, subProducto.getNombreSubproducto());
//            query.setParameter(3, subProducto.getDescripcionSubproducto());
//            query.setParameter(4, subProducto.getIdProductoFk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        try {

            Query query = em.createNativeQuery("UPDATE SUBPRODUCTO set NOMBRE_SUBPRODUCTO = ?,DESCRIPCION_SUBPRODUCTO =?,ID_PRODUCTO_FK = ? where ID_SUBPRODUCTO_PK = ?");
//            query.setParameter(1, subProducto.getNombreSubproducto());
//            query.setParameter(2, subProducto.getDescripcionSubproducto());
//            query.setParameter(3, subProducto.getIdProductoFk());
//            query.setParameter(7, subProducto.getIdSubproductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
