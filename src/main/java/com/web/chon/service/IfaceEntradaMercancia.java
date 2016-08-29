/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.EntradaMercancia;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author marcogante
 */
public interface IfaceEntradaMercancia extends PaginacionService<EntradaMercancia, BigDecimal> {

    public int insertEntradaMercancia(EntradaMercancia entrada);

    public int buscaMaxMovimiento(EntradaMercancia entrada);

    public int getNextVal();
    public int getCarroSucursal(BigDecimal idSucursal);

    /**
     * Obtiene una lista de la entrada de mercancia por medio de filtros de
     * fecha inicio, fin , id de sucursal y id de provedor
     *
     * @param fechaInicio
     * @param fechaFin
     * @param idSucursal
     * @param idProvedor
     * @return
     */
    public ArrayList<EntradaMercancia> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor);

    public ArrayList<EntradaMercancia> getSubEntradaByNombre(String nombre);
    
    public int deleteEntradaMercancia(EntradaMercancia entrada);
}
