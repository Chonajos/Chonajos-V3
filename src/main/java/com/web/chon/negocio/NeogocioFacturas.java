/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.FacturaPDFDomain;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jramirez
 */
public interface NeogocioFacturas {
    public int insert(FacturaPDFDomain factura);
    public int delete(FacturaPDFDomain factura);
    public int update(FacturaPDFDomain factura);
    public List<Object[]> getFacturaByIdPk(BigDecimal idFacturaPk);
    public List<Object[]> getFacturaByIdNumeroFac(BigDecimal idFacturaPk);
    public List<Object[]> getFacturasBy(BigDecimal idClienteFk,BigDecimal idSucursalFk,BigDecimal idFolioVentaFk,String fechaInicio,String fechaFin,BigDecimal idNumeroFacturaFk);
    public int getNextVal();
}
