/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.MayoreoProductoEntradaProducto;
import com.web.chon.dominio.VentaProductoMayoreo;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author marcogante
 */
public interface IfaceVentaMayoreoProducto {
    public int insertarVentaMayoreoProducto(VentaProductoMayoreo venta);
    public ArrayList<VentaProductoMayoreo> getProductosbyIdVmFk(BigDecimal idVmFk);
    public int getNextVal();
    public ArrayList<VentaProductoMayoreo> buscaVentaCancelar(BigDecimal idVenta,BigDecimal idSucursal);
    
    /**
     * Obtiene las ventas de los productos por medio del id de sucursal y el numero del carro
     * @param idSucursal
     * @param carro
     * @param fechaInicio
     * @param fechaFin
     * @return ArrayList<MayoreoProductoEntradaProducto>
     */
    public ArrayList<MayoreoProductoEntradaProducto> getVentaByIdSucursalAndCarro(BigDecimal idSucursal, BigDecimal carro,String fechaInicio,String fechaFin);
    
    
    
}
