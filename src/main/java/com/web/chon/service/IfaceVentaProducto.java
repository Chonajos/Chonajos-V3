package com.web.chon.service;

import com.web.chon.dominio.VentaProducto;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * Interface para el servicio Venta de Productos
 * @author Juan de la Cruz
 */
public interface IfaceVentaProducto {
    
    /**
     * Obtiene una lista de los productos vendidos por medio del id de venta
     * @param idVenta
     * @return 
     */
    public ArrayList<VentaProducto> getVentasProductoByIdVenta(BigDecimal idVenta);
    /**
     * Inserta los productos de la venta
     * @param ventaProducto  
     * @param idVenta
     * @return 
     */
    public int insertarVentaProducto(VentaProducto ventaProducto, int idVenta);
    
    /**
     * Obtiene el reporte de ventas de menudeo 
     * @param fechaInicio
     * @param fechaFin
     * @param idSucursal
     * @return 
     */
    public ArrayList<VentaProducto> getReporteVenta(String fechaInicio,String fechaFin,BigDecimal idSucursal);
    
   
}
