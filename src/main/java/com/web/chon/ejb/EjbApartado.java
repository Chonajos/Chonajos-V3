/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Apartado;
import com.web.chon.negocio.NegocioApartado;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jramirez
 */
@Stateless(mappedName = "ejbApartado")
public class EjbApartado implements NegocioApartado{

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;
    
    @Override
    public int insert(Apartado apartado) {
        try {
            Query query = em.createNativeQuery("INSERT INTO  APARTADO_VENTA (ID_APARTADO_PK ,"
                    + "ID_VENTA_MAYOREO_FK "
                    + ",ID_VENTA_MENUDEO_FK ,FECHA ,MONTO,ID_CAJERO_FK,ID_STATUS) "
                    + " VALUES(?,?,?,sysdate,?,?,?)");
            query.setParameter(1, apartado.getIdApartadoPk());
            query.setParameter(2, apartado.getIdVentaMayoreoFk());
            query.setParameter(3, apartado.getIdVentaMenudeoFk());
            query.setParameter(4, apartado.getMonto());
            query.setParameter(5, apartado.getIdCajeroFk());
            query.setParameter(6, apartado.getIdStatus());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbApartado.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_APARTADO_VENTA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public BigDecimal montoApartado(BigDecimal idVentaFk,BigDecimal idTipoFk) 
    {
        Query query;
        if(idTipoFk.intValue()==1)
        {
            query = em.createNativeQuery("select nvl(sum(av.MONTO),0) as total from APARTADO_VENTA av where av.ID_STATUS=1 and av.ID_VENTA_MAYOREO_FK = ?");
        }
        else
        {
            query = em.createNativeQuery("select nvl(sum(av.MONTO),0) as total from APARTADO_VENTA av where av.ID_STATUS=1 and av.ID_VENTA_MENUDEO_FK = ?");
        }
        System.out.println("Query: "+query);
        System.out.println("IdVenta: "+idVentaFk);
        System.out.println("idTipo:"+idTipoFk);
        query.setParameter(1, idVentaFk);
        return new BigDecimal(query.getSingleResult().toString());
    }
    
}
