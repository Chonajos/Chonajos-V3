/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.VentaProductoMayoreo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioVentaMayoreoProducto {

    public int insertarVentaProducto(VentaProductoMayoreo ventaproducto);

    public List<Object[]> getProductos(BigDecimal idVmFk);

    public List<Object[]> buscaVentaCancelar(BigDecimal idVenta, BigDecimal idSucursal);

    public int getNextVal();

    /**
     * Obtiene el total de ventas por producto por medio del id de sucursal y el carro y un intervalo de fechas
     * @param idSucursal
     * @param carro
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public List<Object[]> getVentaByIdSucursalAndCarro(BigDecimal idSucursal, BigDecimal carro,String fechaInicio,String fechaFin);

}
