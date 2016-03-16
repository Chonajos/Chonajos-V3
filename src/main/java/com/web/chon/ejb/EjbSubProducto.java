
package com.web.chon.ejb;

import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioSubProducto;
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
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbSubProducto")
public class EjbSubProducto implements NegocioSubProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getSubProductos() {
        try {

            Query query = em.createNativeQuery("SELECT * FROM SUBPRODUCTO SUB INNER JOIN PRODUCTO PRO ON PRO.ID_PRODUCTO_PK = SUB.ID_PRODUCTO_FK ORDER BY ID_PRODUCTO_FK,ID_SUBPRODUCTO_PK ASC");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteSubProducto(String idSubProducto) {
        try {

            Query query = em.createNativeQuery("DELETE SUBPRODUCTO where ID_SUBPRODUCTO_PK = ?");
            query.setParameter(1, idSubProducto);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int insertarSubProducto(Subproducto subProducto) {
        try {
            Query query = em.createNativeQuery("INSERT INTO SUBPRODUCTO (ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO,DESCRIPCION_SUBPRODUCTO,ID_PRODUCTO_FK,PRECIO_MINIMO,PRECIO_MAXIMO,PRECIO_VENTA) values(?,?,?,?,?,?,?)");
            query.setParameter(1, subProducto.getIdSubproductoPk());
            query.setParameter(2, subProducto.getNombreSubproducto());
            query.setParameter(3, subProducto.getDescripcionSubproducto());
            query.setParameter(4, subProducto.getIdProductoFk());
            query.setParameter(5, subProducto.getPrecioMinimo());
            query.setParameter(6, subProducto.getPrecioMaximo());
            query.setParameter(7, subProducto.getPrecioVenta());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int updateSubProducto(Subproducto subProducto) {
        try {

            
            Query query = em.createNativeQuery("UPDATE SUBPRODUCTO set NOMBRE_SUBPRODUCTO = ?,DESCRIPCION_SUBPRODUCTO =?,ID_PRODUCTO_FK = ?,PRECIO_MINIMO =?,PRECIO_MAXIMO =?,PRECIO_VENTA =?  where ID_SUBPRODUCTO_PK = ?");
            query.setParameter(1, subProducto.getNombreSubproducto());
            query.setParameter(2, subProducto.getDescripcionSubproducto());
            query.setParameter(3, subProducto.getIdProductoFk());
            query.setParameter(4, subProducto.getPrecioMinimo());
            query.setParameter(5, subProducto.getPrecioMaximo());
            query.setParameter(6, subProducto.getPrecioVenta());
            query.setParameter(7, subProducto.getIdSubproductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
     @Override
    public int getLastIdProducto(String idCategoria) {
        Query query = em.createNativeQuery("SELECT MAX(SUBSTR(ID_SUBPRODUCTO_PK,5,8)+1 ) ID_SUBPRODUCTO_PK from SUBPRODUCTO where ID_PRODUCTO_FK = ?");
        query.setParameter(1, idCategoria);
        String lastId = query.getSingleResult().toString();
        return Integer.parseInt(lastId);
    }

    @Override
    public List<Object[]> getProductoById(String idProducto) {
        Query query = em.createNativeQuery("SELECT * FROM SUBPRODUCTO WHERE ID_SUBPRODUCTO_PK = ?");
        query.setParameter(1, idProducto);
        
        return query.getResultList();
    }

    @Override
    public List<Object[]> getSubProductoByNombre(String idProducto) {
        Query query = em.createNativeQuery("SELECT ID_SUBPRODUCTO_PK,NOMBRE_SUBPRODUCTO FROM SUBPRODUCTO WHERE UPPER(NOMBRE_SUBPRODUCTO) LIKE '%"+idProducto+"%'");
        
        return query.getResultList();
    }

}
