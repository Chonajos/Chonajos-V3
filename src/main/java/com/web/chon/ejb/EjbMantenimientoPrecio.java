package com.web.chon.ejb;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioMantenimientoPrecio;
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;
import java.util.Date;
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

            Query query = em.createNativeQuery("UPDATE MANTENIMIENTO_PRECIO SET ID_SUBPRODUCTO_FK = ? ,ID_TIPO_EMPAQUE_FK =? ,PRECIO_VENTA = ?, PRECIO_MINIMO = ? ,PRECIO_MAXIMO = ?,COSTOREAL = ?,COSTOMERMA = ?  WHERE TRIM(ID_SUBPRODUCTO_FK) = ? AND ID_TIPO_EMPAQUE_FK = ? AND ID_SUCURSAL_FK =? ");
            query.setParameter(1, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(2, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(3, mantenimientoPrecios.getPrecioVenta());
            query.setParameter(4, mantenimientoPrecios.getPrecioMinimo());
            query.setParameter(5, mantenimientoPrecios.getPrecioMaximo());
            query.setParameter(6, mantenimientoPrecios.getCostoReal());
            query.setParameter(7, mantenimientoPrecios.getCostoMerma());
            query.setParameter(8, mantenimientoPrecios.getIdSubproducto());
            query.setParameter(9, mantenimientoPrecios.getIdTipoEmpaquePk());
            query.setParameter(10, mantenimientoPrecios.getIdSucursal());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getAllByIdSucAndIdSubProducto(BigDecimal idSucursal, String idSubProducto, Date fechaMercado) {

        String fechaTemporalFin = TiempoUtil.getFechaDDMMYYYY(fechaMercado);
        String fechaTemporalInicio = TiempoUtil.getFechaDDMMYYYY(TiempoUtil.sumarRestarDias(fechaMercado, -7));
        System.out.println("Fecha Inicio: "+fechaTemporalInicio);
        System.out.println("Fecha Fin: "+fechaTemporalFin);
        try {
            StringBuilder queryStr;

             queryStr = new StringBuilder("SELECT SP.ID_SUBPRODUCTO_PK, SP.NOMBRE_SUBPRODUCTO, MP.ID_SUCURSAL_FK,\n" +
"MP.ID_TIPO_EMPAQUE_FK,MP.PRECIO_MINIMO,MP.PRECIO_VENTA,MP.PRECIO_MAXIMO, \n" +
"EXM.KILOS,MP.COSTOREAL,MP.COSTOMERMA,prc.PRECIO_VENTA as PRECIO_MERCADO FROM SUBPRODUCTO SP  \n" +
"RIGHT JOIN MANTENIMIENTO_PRECIO MP ON SP.ID_SUBPRODUCTO_PK = MP.ID_SUBPRODUCTO_FK \n" +
"AND MP.ID_SUCURSAL_FK = " + idSucursal + " \n" +
"RIGHT JOIN EXISTENCIAMENUDEO EXM ON EXM.ID_SUBPRODUCTO_FK = SP.ID_SUBPRODUCTO_PK \n" +
"AND EXM.ID_SUCURSAL_FK = " + idSucursal + " \n" +
"LEFT join (SELECT  AVG( PRECIO_VENTA) PRECIO_VENTA, ID_SUBPRODUCTO_FK FROM PRECIOSCOMPETENCIA WHERE TO_DATE(TO_CHAR(FECHA_REGISTRO,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaTemporalInicio + "' and '" + fechaTemporalFin + "' \n" +
"group by ID_SUBPRODUCTO_FK) prc\n" +
"on   prc.ID_SUBPRODUCTO_FK = SP.ID_SUBPRODUCTO_PK");
            //Para mostrar todos los productos que estan registrados cambiar RIGHT por left
            if (idSubProducto != null) 
            {
                queryStr.append(" WHERE SP.ID_SUBPRODUCTO_PK ='" + idSubProducto.trim() + "' and  EXM.ID_SUCURSAL_FK ='" + idSucursal + "'");
            }
            else
            {
                queryStr.append(" WHERE EXM.ID_SUCURSAL_FK = '" + idSucursal + "'");
            }

            queryStr.append(" order by SP.NOMBRE_SUBPRODUCTO ");
            
            System.out.println("Query==============:" + queryStr);
            Query query = em.createNativeQuery(queryStr.toString());

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
