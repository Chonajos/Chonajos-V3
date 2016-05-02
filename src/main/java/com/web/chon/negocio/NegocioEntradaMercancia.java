/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaMercancia2;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioEntradaMercancia {

    public int insertEntradaMercancia(EntradaMercancia2 entrada);

    public int getNextVal();

    public int buscaMaxMovimiento(EntradaMercancia2 entrada);

    public List<Object[]> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor);

    public List<Object[]> getSubEntradaByNombre(String clave);
    
    
}
