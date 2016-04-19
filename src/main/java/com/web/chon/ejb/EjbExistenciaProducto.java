/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.negocio.NegocioExistenciaProducto;
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
 * @author marcogante
 */
@Stateless(mappedName = "ejbExistenciaProducto")
public class EjbExistenciaProducto implements NegocioExistenciaProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaExistencia(ExistenciaProducto e) {

        System.out.println("EJB-INSERTA-EXISTENCIA-NUEVA");

        try {

            Query querySel = em.createNativeQuery("select * from existencia_producto where ID_SUCURSAL_FK = '"+e.getIdSucursalFk()+"' and ID_SUBPRODUCTO_FK='"+e.getIdSubProductoFk()+"' and  ID_TIPO_EMPAQUE='"+e.getIdTipoEmpaque()+"' and ID_BODEGA_FK='"+e.getIdBodegaFk()+"'");
            List<Object[]> resultList = null;
            resultList = querySel.getResultList();

            if (resultList.isEmpty()) 
            {
                System.out.println("Entrada_Porducto: " + e);
                Query query = em.createNativeQuery("INSERT INTO EXISTENCIA_PRODUCTO (ID_EXISTENCIA_PRODUCTO_PK,ID_SUCURSAL_FK,ID_SUBPRODUCTO_FK,KILOS_EXISTENCIA,CANTIDAD_EMPAQUE,ID_TIPO_EMPAQUE,KILOS_EMPAQUE,ID_BODEGA_FK,ID_PROVEDOR_FK)VALUES (S_EXISTENCIA_PRODUCTO.NextVal,?,?,?,?,?,?,?,?)");
                query.setParameter(1, e.getIdSucursalFk());
                query.setParameter(2, e.getIdSubProductoFk());
                query.setParameter(3, e.getKilosExistencia());
                query.setParameter(4, e.getCantidadEmpaque());
                query.setParameter(5, e.getIdTipoEmpaque());
                query.setParameter(6, e.getKilosEmpaque());
                query.setParameter(7, e.getIdBodegaFk());
                query.setParameter(8, e.getIdProvedorFk());
                return query.executeUpdate();
            } else {
                System.out.println("Ya encontro ese registro siguiente paso aumentar cantidad");
                return 2;
            }

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getExistenciaProductoId(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk) 
    {
        try {
             Query query = em.createNativeQuery("select * from existencia_producto where ID_SUCURSAL_FK = '"+idSucursal+"' and ID_SUBPRODUCTO_FK='"+idSubproductoFk+"' and  ID_TIPO_EMPAQUE='"+idTipoEmpaqueFk+"' and ID_BODEGA_FK='"+idBodegaFk+"'");
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateExistenciaProducto(ExistenciaProducto e) {
       
        try {
                System.out.println("Entrada_Porducto_update: " + e);
                Query query = em.createNativeQuery("UPDATE EXISTENCIA_PRODUCTO SET KILOS_EXISTENCIA='"+e.getKilosExistencia()+"',CANTIDAD_EMPAQUE='"+e.getCantidadEmpaque()+"',KILOS_EMPAQUE='"+e.getKilosEmpaque()+"' where ID_EXISTENCIA_PRODUCTO_PK='"+e.getIdExistenciaProductoPk()+"'");
                query.setParameter(1, e.getKilosExistencia());
                query.setParameter(2, e.getCantidadEmpaque());
                query.setParameter(3, e.getKilosEmpaque());
                query.setParameter(4, e.getKilosEmpaque());
                
                
                return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
