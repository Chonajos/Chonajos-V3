/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.negocio.NegocioProductoFacturado;
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
 * @author jramirez
 */
@Stateless(mappedName = "ejbProductoFacturado")
public class EjbProductoFacturado implements NegocioProductoFacturado{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

     @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_PRODUCTO_FACTURADO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int insert(ProductoFacturado pf) {
        System.out.println("ProductoFacturado: "+pf.toString());
        try {

            Query query = em.createNativeQuery("INSERT INTO PRODUCTO_FACTURADO (ID_PRODUCTO_FACTURADO_PK,ID_TIPO_LLAVE_FK,ID_LLAVE_FK,ID_FACTURA_FK,IMPORTE,CANTIDAD,KILOS) VALUES(?,?,?,?,?,?,?)");
            query.setParameter(1,pf.getIdProductoFacturadoPk());
            query.setParameter(2,pf.getIdTipoLlaveFk());
            query.setParameter(3,pf.getIdLlaveFk());
            query.setParameter(4,pf.getIdFacturaFk());
            query.setParameter(5,pf.getImporte());
            query.setParameter(6,pf.getCantidad() == null ? new BigDecimal(0):pf.getCantidad());
            query.setParameter(7,pf.getKilos());
            
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbProductoFacturado.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(ProductoFacturado pf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(ProductoFacturado pf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdFacturaFk(BigDecimal idFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdPk(BigDecimal idPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getByIdTipoFolioFk(BigDecimal idTipoFk, BigDecimal idVentaFk) {
        try {

            Query query = em.createNativeQuery("SELECT  PF.ID_TIPO_LLAVE_FK,PF.ID_LLAVE_FK,SUM(PF.IMPORTE)AS IMPORTE,SUM(PF.CANTIDAD) AS CANTIDAD,SUM(PF.KILOS)AS KILOS FROM PRODUCTO_FACTURADO PF\n" +
"LEFT JOIN VENTAMAYOREOPRODUCTO VMP\n" +
"ON VMP.ID_V_M_P_PK = PF.ID_LLAVE_FK\n" +
"LEFT JOIN VENTA_MAYOREO VM\n" +
"ON VM.ID_VENTA_MAYOREO_PK = VMP.ID_VENTA_MAYOREO_FK\n" +
"LEFT JOIN VENTA_PRODUCTO VP ON VP.ID_VENTA_PRODUCTO_PK=PF.ID_LLAVE_FK\n" +
"LEFT JOIN VENTA V ON V.ID_VENTA_PK = VP.ID_VENTA_PRODUCTO_PK\n" +
"WHERE VM.VENTASUCURSAL = ?  AND PF.ID_TIPO_LLAVE_FK= ? \n" +
"group by PF.ID_TIPO_LLAVE_FK,PF.ID_LLAVE_FK");
            query.setParameter(1, idVentaFk);
            query.setParameter(2, idTipoFk);
            List<Object[]> resultList = null;
            resultList = query.getResultList();

            return resultList;

        } catch (Exception ex) 
        {
            Logger.getLogger(EjbProductoFacturado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    
    }
}
