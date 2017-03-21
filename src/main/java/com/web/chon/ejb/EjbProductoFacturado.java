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
        try {

            Query query = em.createNativeQuery("INSERT INTO PRODUCTO_FACTURADO (ID_PRODUCTO_FACTURADO_PK,ID_TIPO_LLAVE_FK,ID_LLAVE_FK,ID_FACTURA_FK,IMPORTE,CANTIDAD,KILOS) VALUES(?,?,?,?,?,?)");
            query.setParameter(1,pf.getIdProductoFacturadoPk());
            query.setParameter(2,pf.getIdTipoLlaveFk());
            query.setParameter(3,pf.getIdLlaveFk());
            query.setParameter(4,pf.getImporte());
            query.setParameter(5,pf.getCantidad());
            query.setParameter(6,pf.getKilos());
            
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
    public List<Object[]> getByIdVentaFk(BigDecimal idTipoFk, BigDecimal idVentaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
