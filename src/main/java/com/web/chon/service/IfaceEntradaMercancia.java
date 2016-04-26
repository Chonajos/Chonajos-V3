/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.EntradaMercancia2;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author marcogante
 */
public interface IfaceEntradaMercancia extends PaginacionService<EntradaMercancia2, BigDecimal> {

    public int insertEntradaMercancia(EntradaMercancia2 entrada);

    public int buscaMaxMovimiento(EntradaMercancia2 entrada);

    public int getNextVal();

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
    public ArrayList<EntradaMercancia2> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor);

}
