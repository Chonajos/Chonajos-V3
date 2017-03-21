/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.negocio.NegocioProductoFacturado;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceProductoFacturado implements IfaceProductoFacturado {
    
    NegocioProductoFacturado ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioProductoFacturado) Utilidades.getEJBRemote("ejbProductoFacturado", NegocioProductoFacturado.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceProductoFacturado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insert(ProductoFacturado pf) {
        getEjb();
        return ejb.insert(pf);
    }

    @Override
    public int update(ProductoFacturado pf) {
        getEjb();
        return ejb.update(pf);
    }

    @Override
    public int delete(ProductoFacturado pf) {
       getEjb();
        return ejb.delete(pf);
    }

    @Override
    public int getNextVal() {
         getEjb();
        return ejb.getNextVal();
    }

    @Override
    public ArrayList<ProductoFacturado> getByIdFacturaFk(BigDecimal idFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoFacturado getByIdPk(BigDecimal idPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ProductoFacturado> getByIdTipoFolioFk(BigDecimal idTipoFk, BigDecimal idVentaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
