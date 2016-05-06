/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.negocio.NegocioVentaMayoreo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbVentaMayoreo")
public class EjbVentaMayoreo implements NegocioVentaMayoreo {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarVenta(VentaMayoreo venta) {
       Query query = em.createNativeQuery("INSERT INTO VENTA_MAYOREO(ID_VENTA_MAYOREO_PK,ID_CLIENTE_FK,ID_VENDEDOR_FK,FECHA_VENTA,ID_SUCURSAL_FK,ID_TIPO_VENTA_FK) VALUES(?,?,?,sysdate,?,?)");
        System.out.println("venta_mayoreo ejb :" + venta.toString());
        query.setParameter(1, venta.getIdVentaMayoreoPk());
        query.setParameter(2, venta.getIdClienteFk());
        query.setParameter(3, venta.getIdVendedorFK());
        query.setParameter(4, venta.getIdSucursalFk());
        query.setParameter(5, venta.getIdtipoVentaFk());
        return query.executeUpdate();
    }

    @Override
    public int getNextVal() {
       Query query = em.createNativeQuery("SELECT s_venta_Mayoreo.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }
    
}
