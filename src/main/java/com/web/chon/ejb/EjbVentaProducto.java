
package com.web.chon.ejb;

import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.negocio.NegocioSubProducto;
import com.web.chon.negocio.NegocioVentaProducto;
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
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbVentaProducto")
public class EjbVentaProducto implements NegocioVentaProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getVentaProductoByIdVenta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertarVentaProducto(VentaProducto ventaProducto, int idVenta) {
        Query query = em.createNativeQuery("INSERT INTO VENTA_PRODUCTO(ID_VENTA_PRODUCTO_PK,ID_SUBPRODUCTO_FK,PRECIO_PRODUCTO,KILOS_VENDIDOS,CANTIDAD_EMPAQUE,TOTAL_VENTA,ID_TIPO_EMPAQUE_FK,ID_VENTA_FK)"
                + "VALUES(S_VENTA_PRODUCTO.nextVal,?,?,?,?,?,?,?)");
        query.setParameter(1, ventaProducto.getIdProductoFk());
        query.setParameter(2, ventaProducto.getPrecioProducto());
        query.setParameter(3, ventaProducto.getKilosVenta());
        query.setParameter(4, ventaProducto.getCantidadEmpaque());
        query.setParameter(5, ventaProducto.getTotal());
        query.setParameter(6, ventaProducto.getIdTipoEmpaqueFk().equals(new BigDecimal(-1)) ? null:ventaProducto.getIdTipoEmpaqueFk());
        query.setParameter(7, new BigDecimal(idVenta));
        
        return query.executeUpdate();
    }

   

}
