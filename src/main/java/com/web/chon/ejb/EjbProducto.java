
package com.web.chon.ejb;



import com.web.chon.dominio.Producto;
import com.web.chon.negocio.NegocioProducto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Ejb para el catalogo de categorias
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbProducto")
public class EjbProducto implements NegocioProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
 

    @Override
    public List<Object[]> getProductos() {
        try {

            Query query = em.createNativeQuery("SELECT * FROM PRODUCTO ORDER BY ID_PRODUCTO_PK ASC");
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteProducto(String idProducto) {
        try {
            Query query = em.createNativeQuery("delete Producto where ID_PRODUCTO_PK = ?");
            query.setParameter(1, idProducto);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int insertarProducto(Producto producto) {
        try {
            System.out.println("insert : " + producto.getNombreProducto() + " " + producto.getDescripcionProducto());
            Query query = em.createNativeQuery("insert into PRODUCTO (ID_PRODUCTO_PK,NOMBRE_PRODUCTO,DESCRIPCION_PRODUCTO) values(?,?,?)");
            query.setParameter(1, producto.getIdProductoPk());
            query.setParameter(2, producto.getNombreProducto());
            query.setParameter(3, producto.getDescripcionProducto());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public int updateProducto(Producto producto) {
        try {

            System.out.println("producto a modificar :" + producto.getNombreProducto() + " desc " + producto.getDescripcionProducto());
            Query query = em.createNativeQuery("update Producto set NOMBRE_PRODUCTO = ?,DESCRIPCION_PRODUCTO = ?  where ID_PRODUCTO_PK = ?");
            query.setParameter(1, producto.getNombreProducto());
            query.setParameter(2, producto.getDescripcionProducto());
            query.setParameter(3, producto.getIdProductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getLastIdCategoria() {
        Query query = em.createNativeQuery("SELECT MAX(ID_PRODUCTO_PK)+1 ID_PRODUCTO_PK FROM PRODUCTO");
        String lastId = query.getSingleResult().toString();
        return Integer.parseInt(lastId);
    }
    
    
    
}
