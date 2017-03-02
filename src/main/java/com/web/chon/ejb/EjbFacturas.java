/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.negocio.NeogocioFacturas;
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
@Stateless(mappedName = "ejbFacturas")
public class EjbFacturas implements NeogocioFacturas
{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(FacturaPDFDomain factura) {
        try {
            Query query = em.createNativeQuery("INSERT INTO FACTURAS () values ()");
            query.setParameter(1, factura.getIdFacturaPk());
            query.setParameter(2, factura.getNumeroFactura());
            query.setParameter(3, factura.getFechaCertificacion());
            query.setParameter(4, factura.getIdClienteFk());
            query.setParameter(5, factura.getIdSucursalFk());
            query.setParameter(6, factura.getIdLlaveFk());
            
            
            
            
            
            
            
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbFacturas.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public int delete(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturaByIdPk(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturaByIdNumeroFac(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getFacturasBy(BigDecimal idClienteFk, BigDecimal idSucursalFk, BigDecimal idFolioVentaFk, String fechaInicio, String fechaFin, BigDecimal idNumeroFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_Facturas.NextVal from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            return 0;
        }
    
    }
    
}
