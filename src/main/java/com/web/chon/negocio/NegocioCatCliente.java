/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Cliente;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatCliente {
    
    public List<Object[]> getClientes();

    public List<Object[]> getClienteById(BigDecimal idCliente);
    
    public List<Object[]> getClienteCreditoById(BigDecimal idCliente);       

    public int deleteCliente(BigDecimal idCliente);

    public int updateCliente(Cliente clie);

    public int insertCliente(Cliente clie);

    public List<Object[]> getClienteByNombreCompleto(String nombreCliente);
    
    public int getNextVal ();
    
    public List<Object[]> getCreditoClienteByIdCliente(BigDecimal idCliente);  
    
    public List<Object[]> getClientesActivos();
    
    public List<Object[]> getClienteByIdSubProducto(String idSubProducto,BigDecimal idSucursal);
    
    public List<Object[]> getReporteClienteVentasUtilidad(BigDecimal idCliente,String fechaInicio,String fechaFin);
    
  
}
