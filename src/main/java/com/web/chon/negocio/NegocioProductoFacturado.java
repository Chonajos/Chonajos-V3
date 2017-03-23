/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.ProductoFacturado;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NegocioProductoFacturado {
    public int insert(ProductoFacturado pf);
    public int update(ProductoFacturado pf);
    public int delete(ProductoFacturado pf);
    public int getNextVal();
    public List<Object[]> getByIdFacturaFk(BigDecimal idFacturaFk);
    public List<Object[]> getByIdPk(BigDecimal idPk);
    public List<Object[]> getByIdTipoFolioFk(BigDecimal idTipoFk,BigDecimal idVentaFk);
    public List<Object[]> getProductosNoFacturados(BigDecimal idTipoFk,BigDecimal idSucursalFk,String fechaInicio,String fechaFin);
    
    
}
