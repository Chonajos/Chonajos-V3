/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.DatosFacturacion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NegocioDatosFacturacion {
    
    public List<Object[]> getByIdCliente(BigDecimal idClienteFk);
    public List<Object[]> getByIdSucursal(BigDecimal idSucursalFk);
    public int delete(BigDecimal idDatosFacturacion);
    public int insert(DatosFacturacion df);
    public int update(DatosFacturacion df);
    public int getNextVal();
    public List<Object[]> getAll();
    public List<Object[]> getByRfc(String rfc);
    
}
