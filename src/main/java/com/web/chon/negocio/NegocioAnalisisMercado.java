/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.AnalisisMercado;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioAnalisisMercado 
{
    public int saveEntradaProductoCentral(AnalisisMercado entradaMercancia);
    
    public List<Object[]> getEntradaProductoByFiltroDay(String fechaInicio,String fechaFin,String idProducto);
    
    public List<Object[]> getEntradaProductoByFiltroWeek(String fechaInicio,String fechaFin,String idProducto);
    
    public List<Object[]> getEntradaProductoByFiltroMonth(String fechaInicio,String fechaFin,String idSubproducto);
    
    public List<Object[]> getEntradaProductoByFiltroYear(String fechaInicio,String fechaFin);
    
    public int update(AnalisisMercado entradaMercancia);
    
    public List<Object[]> getEntradaProductoByIdProducto(String idProducto);
    
    public BigDecimal getRemanente(String fechaInicio, String fechaFin,String idProducto);
    
    public int updateByIdProductoAndFecha(AnalisisMercado entradaMercancia) ;

    
}
