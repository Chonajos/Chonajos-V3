/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.VentaProductoMayoreo;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceEntradaMercanciaProducto extends PaginacionService<EntradaMercanciaProducto, BigDecimal> {

    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto);

    public int getNextVal();

    /**
     * Obtiene una lista de entrada mercancia de productos por medio del id de
     * entrada de producto
     *
     * @param idEntradaMercancia
     * @return
     */
    public ArrayList<EntradaMercanciaProducto> getEntradaProductoByIdEM(BigDecimal idEntradaMercancia);
    
    public int deleteEntradaMercanciaProducto(EntradaMercanciaProducto ep);
    
    public EntradaMercanciaProducto getEntradaMercanciaProductoByIdEmpPk(BigDecimal idEmpPk);
    
    public VentaProductoMayoreo getTotalVentasByIdEMP(BigDecimal idEmP);
    
    public int updateVideo(EntradaMercanciaProducto entradaMercanciaProducto);

}
