/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntradaMenudeo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceEntradaMenudeo 
{
    public int insertEntradaMercancia(EntradaMenudeo entrada);
    public int buscaMaxMovimiento(EntradaMenudeo entrada);
    public int getNextVal();
    public int getFolio(BigDecimal idSucursal);
    public ArrayList<EntradaMenudeo> getEntradaProductoByIntervalDate(Date fechaFiltroInicio, Date fechaFiltroFin, BigDecimal idSucursal,String idSubproductoFK);
    
    public int cancelarEntrada(BigDecimal folioCancelar);

}
