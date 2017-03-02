/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.negocio.NeogocioFacturas;
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
public class ServiceFacturas implements IfaceFacturas
{
    NeogocioFacturas ejb;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NeogocioFacturas) Utilidades.getEJBRemote("ejbExistenciaMenudeo", NeogocioFacturas.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int insert(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public FacturaPDFDomain getFacturaByIdPk(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacturaPDFDomain getFacturaByIdNumeroFac(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FacturaPDFDomain> getFacturasBy(BigDecimal idClienteFk, BigDecimal idSucursalFk, BigDecimal idFolioVentaFk, String fechaInicio, String fechaFin, BigDecimal idNumeroFacturaFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
