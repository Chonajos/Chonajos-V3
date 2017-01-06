/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;


import com.web.chon.dominio.EntradaMenudeo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioEntradaMenudeo {
    
    public int insertEntradaMenudeo(EntradaMenudeo entrada);
    
    public int updateEntradaMenudeo(EntradaMenudeo entrada);

    public int getNextVal();
    
    public int getFolio(BigDecimal idSucursal);

    public int buscaMaxMovimiento(EntradaMenudeo entrada);
    
    public List<Object[]>  getEntradaById(BigDecimal id);
    
    public List<Object[]> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal,String idSubproductoPk);
    
    public int cancelarEntrada(BigDecimal folioCancelar);
}
