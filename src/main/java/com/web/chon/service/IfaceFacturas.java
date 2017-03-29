/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.FacturaPDFDomain;
import java.math.BigDecimal;
import java.util.ArrayList;


/**
 *
 * @author jramirez
 */
public interface IfaceFacturas {
    public int insert(FacturaPDFDomain factura);
    public int delete(FacturaPDFDomain factura);
    public int update(BigDecimal  idFacturaFk,BigDecimal idStatusFk);
    public FacturaPDFDomain getFacturaByIdPk(BigDecimal idFacturaPk);
    public FacturaPDFDomain getFacturaByIdNumeroFac(BigDecimal idFacturaPk);
    public ArrayList<FacturaPDFDomain> getFacturasBy(BigDecimal idClienteFk,BigDecimal idSucursalFk,BigDecimal idFolioVentaFk,String fechaInicio,String fechaFin);
    public int getNextVal();
    public int getLastNumeroFactura();
}
