
package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.Pagina;

import com.web.chon.negocio.NegocioEntradaMercancia;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercancia implements IfaceEntradaMercancia{
    NegocioEntradaMercancia ejb;
    
    public void getEjb() 
    {
        if (ejb == null) 
        {
            try {
                ejb = (NegocioEntradaMercancia) Utilidades.getEJBRemote("ejbEntradaMercancia", NegocioEntradaMercancia.class.getName());
            } catch (Exception ex) 
            {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercancia2 entrada) 
    {
       
        getEjb();
        return ejb.insertEntradaMercancia(entrada);
        
        
    }

    @Override
    public int buscaMaxMovimiento(EntradaMercancia2 entrada)
    {
        getEjb();
        try {
            System.out.println("Entrada Service: " + entrada.toString());
        return ejb.buscaMaxMovimiento(entrada);
        }
         catch (Exception ex) {
            //Logger.getLogger(NegocioEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    @Override
    public int getNextVal() {
        getEjb();
        try 
        {
           return ejb.getNextVal();
           
        } catch (Exception ex) 
        {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public Pagina<EntradaMercancia2> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercancia2> findAllDominio(EntradaMercancia2 filters, int first, int pageSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia2 getById(BigDecimal dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia2 getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(EntradaMercancia2 dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(EntradaMercancia2 dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EntradaMercancia2> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
