/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.EntradaMercanciaProducto;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author marcogante
 */
public interface IfaceEntradaMercanciaProducto extends PaginacionService<EntradaMercanciaProducto, BigDecimal> {

    public int insertEntradaMercancia(EntradaMercanciaProducto producto);

    public int getNextVal();

    /**
     * Obtiene una lista de entrada mercancia de productos por medio del id de
     * entrada de producto
     *
     * @param idEntradaMercancia
     * @return
     */
    public ArrayList<EntradaMercanciaProducto> getEntradaProductoByIdEM(BigDecimal idEntradaMercancia);

}
