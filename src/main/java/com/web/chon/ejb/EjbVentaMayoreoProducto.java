/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreoProducto;
import com.web.chon.util.TiempoUtil;
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
 * @author freddy
 */
@Stateless(mappedName = "ejbVentaMayoreoProducto")
public class EjbVentaMayoreoProducto implements NegocioVentaMayoreoProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVentaProducto(VentaProductoMayoreo ventaproducto) {
        Query query = em.createNativeQuery("INSERT INTO VENTAMAYOREOPRODUCTO(ID_V_M_P_PK,ID_VENTA_MAYOREO_FK,ID_SUBPRODUCTO_FK,PRECIO_PRODUCTO,KILOS_VENDIDOS,CANTIDAD_EMPAQUE,TOTAL_VENTA,ID_TIPO_EMPAQUE_FK,ID_ENTRADA_MERCANCIA_FK,ID_EXISTENCIA_FK) VALUES(?,?,?,?,?,?,?,?,?,?)");
        
        query.setParameter(1, ventaproducto.getIdVentaMayProdPk());
        query.setParameter(2, ventaproducto.getIdVentaMayoreoFk());
        query.setParameter(3, ventaproducto.getIdSubProductofk());
        query.setParameter(4, ventaproducto.getPrecioProducto());
        query.setParameter(5, ventaproducto.getKilosVendidos());
        query.setParameter(6, ventaproducto.getCantidadEmpaque());
        query.setParameter(7, ventaproducto.getTotalVenta());
        query.setParameter(8, ventaproducto.getIdTipoEmpaqueFk());
        query.setParameter(9, ventaproducto.getIdEntradaMercanciaFk());
        query.setParameter(10, ventaproducto.getIdExistenciaFk());
        return query.executeUpdate();

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_VENTA_MAYOREO_PRODUCTO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Object[]> getProductos(BigDecimal idVmFk) {
        try {
            Query query = em.createNativeQuery("select em.CARROSUCURSAL,em.IDENTIFICADOR,sp.NOMBRE_SUBPRODUCTO,tem.NOMBRE_EMPAQUE,vmp.CANTIDAD_EMPAQUE,\n"
                    + "vmp.KILOS_VENDIDOS,vmp.PRECIO_PRODUCTO,vmp.TOTAL_VENTA, vmp.ID_V_M_P_PK,vmp.ID_VENTA_MAYOREO_FK from VENTAMAYOREOPRODUCTO vmp\n"
                    + "join VENTA_MAYOREO vm\n"
                    + "on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n"
                    + "INNER JOIN tipo_empaque tem\n"
                    + "on vmp.ID_TIPO_EMPAQUE_FK= tem.ID_TIPO_EMPAQUE_PK\n"
                    + "INNER JOIN subproducto sp\n"
                    + "on sp.id_subproducto_pk=vmp.ID_SUBPRODUCTO_FK\n"
                    + "INNER JOIN EXISTENCIA_PRODUCTO exp\n"
                    + "on exp.ID_EXP_PK = vmp.ID_EXISTENCIA_FK\n"
                    + "INNER JOIN ENTRADAMERCANCIAPRODUCTO emp\n"
                    + "on emp.ID_EMP_PK = exp.ID_EMP_FK\n"
                    + "INNER JOIN ENTRADAMERCANCIA em\n"
                    + "on em.ID_EM_PK = emp.ID_EM_FK\n"
                    + "where vmp.ID_VENTA_MAYOREO_FK =?");
            query.setParameter(1, idVmFk);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("-----------------------Entro a Error----------------");
            Logger.getLogger(EjbVentaMayoreoProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> buscaVentaCancelar(BigDecimal idVenta, BigDecimal idSucursal) {
        try {

            Query query = em.createNativeQuery("select vmp.ID_EXISTENCIA_FK,vmp.CANTIDAD_EMPAQUE,vmp.KILOS_VENDIDOS from VENTAMAYOREOPRODUCTO vmp\n"
                    + "INNER JOIN VENTA_MAYOREO vm\n"
                    + "on vm.ID_VENTA_MAYOREO_PK = vmp.ID_VENTA_MAYOREO_FK\n"
                    + "where vm.VENTASUCURSAL = ? and vm.ID_SUCURSAL_FK=?");
            query.setParameter(1, idVenta);
            query.setParameter(2, idSucursal);
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getVentaByIdSucursalAndCarro(BigDecimal idSucursal, BigDecimal carro, String fechaInicio, String fechaFin) {

        try {

            StringBuffer txtQuery = new StringBuffer("SELECT VMP.ID_SUBPRODUCTO_FK,SUM(VMP.CANTIDAD_EMPAQUE),SUM(VMP.KILOS_VENDIDOS),SUM(VMP.TOTAL_VENTA), "
                    + "EM.CARROSUCURSAL,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_EMPAQUE_FK FROM VENTAMAYOREOPRODUCTO VMP "
                    + "INNER JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
                    + "INNER JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EMP_PK = EXP.ID_EMP_FK "
                    + "INNER JOIN ENTRADAMERCANCIA EM ON EM.ID_EM_PK = EMP.ID_EM_FK "
                    + "INNER JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK "
                    + "WHERE EM.ID_SUCURSAL_FK = " + idSucursal + " AND VM.ID_STATUS_FK != 4 AND EM.CARROSUCURSAL =" + carro);

//            Query query = em.createNativeQuery("SELECT VMP.ID_SUBPRODUCTO_FK,SUM(VMP.CANTIDAD_EMPAQUE),SUM(VMP.KILOS_VENDIDOS),SUM(VMP.TOTAL_VENTA), "
//                    + "EM.CARROSUCURSAL,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_EMPAQUE_FK FROM VENTAMAYOREOPRODUCTO VMP "
//                    + "INNER JOIN EXISTENCIA_PRODUCTO EXP ON EXP.ID_EXP_PK = VMP.ID_EXISTENCIA_FK "
//                    + "INNER JOIN ENTRADAMERCANCIAPRODUCTO EMP ON EMP.ID_EMP_PK = EXP.ID_EMP_FK "
//                    + "INNER JOIN ENTRADAMERCANCIA EM ON EM.ID_EM_PK = EMP.ID_EM_FK "
//                    + "INNER JOIN VENTA_MAYOREO VM ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK "
//                    + "WHERE EM.ID_SUCURSAL_FK = ? AND VM.ID_STATUS_FK != 4 AND EM.CARROSUCURSAL = ? "
//                    + "GROUP BY EM.CARROSUCURSAL,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,VMP.ID_SUBPRODUCTO_FK "
//                    + ",EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_EMPAQUE_FK ORDER BY EM.CARROSUCURSAL ");
//            query.setParameter(1, idSucursal);
//            query.setParameter(2, carro);
            if (fechaInicio != null) {
                txtQuery.append(" AND TO_DATE(TO_CHAR(VM.FECHA_VENTA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
            }
            txtQuery.append("GROUP BY EM.CARROSUCURSAL,EMP.CONVENIO,EMP.ID_TIPO_CONVENIO_FK,VMP.ID_SUBPRODUCTO_FK "
                    + ",EMP.CANTIDAD_EMPACAQUE,EMP.KILOS_TOTALES,EMP.ID_TIPO_EMPAQUE_FK ORDER BY EM.CARROSUCURSAL ");

            Query query = em.createNativeQuery(txtQuery.toString());

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbBuscaVenta.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
