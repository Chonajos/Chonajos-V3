/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ProductoFacturado;
import com.web.chon.dominio.VentaProductoMayoreo;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author jramirez
 */
public interface IfaceProductoFacturado {
    public int insert(ProductoFacturado pf);
    public int update(ProductoFacturado pf);
    public int delete(ProductoFacturado pf);
    public int deleteByIdFacturaFk(BigDecimal idFacturaFk);
    public int getNextVal();
    public ArrayList<ProductoFacturado> getByIdFacturaFk(BigDecimal idFacturaFk);
    public ProductoFacturado getByIdPk(BigDecimal idPk);
    public ArrayList<ProductoFacturado> getByIdTipoFolioFk(BigDecimal idTipoFk,BigDecimal idVentaFk);
    public ArrayList<VentaProductoMayoreo>  getProductosNoFacturados(BigDecimal idTipoFk,BigDecimal idSucursalFk,String fechaInicio,String fechaFin);
    public ArrayList<VentaProductoMayoreo>  getProductosNoFacturadosAbonos(BigDecimal idTipoFk, BigDecimal folioAbono);
}
