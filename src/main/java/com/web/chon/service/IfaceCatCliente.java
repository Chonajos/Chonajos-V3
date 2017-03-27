package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.ReporteClienteVentas;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceCatCliente 
{
    public ArrayList<Cliente> getClientes();
    
    public Cliente getClienteById(BigDecimal idCliente);
    
    public Cliente getClienteCreditoById(BigDecimal idCliente);
    
    public int deleteCliente(BigDecimal idCliente);
    
    public int updateCliente(Cliente cliente);
    
    public int insertCliente(Cliente cliente);
 
    public ArrayList<Cliente> getClienteByNombreCompleto(String nombre);
    
    public int getNextVal ();
    
    /**
     * Devuelve el limite de credito, credito disponible , credito mayoreo, credito menudeo por medio del id del cliente
     * @param idCliente
     * @return objecto de tipo cliente
     */
    public Cliente getCreditoClienteByIdCliente(BigDecimal idCliente);
    
    public ArrayList<Cliente> getClientesActivos();
    
    /**
     * Obtiene los clientes que compran un determinado producto
     * @param idSubProducto
     * @return cliente
     */
    public ArrayList<Cliente> getClienteByIdSubProducto(String idSubProducto,BigDecimal idSucursal);
    
    /**
     * Obtiene los datos de compra de un cliente(total comprado Menudeo, total de comprado mayoreo, utilidad de las compras, monto recuperado) por medio de su id de cliente
     * @param idCliente
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public ArrayList<ReporteClienteVentas> getReporteClienteVentasUtilidad(BigDecimal idCliente,String fechaInicio,String fechaFin);
    
    
}
