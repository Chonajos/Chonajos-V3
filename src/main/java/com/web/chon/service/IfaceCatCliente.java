/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Correos;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceCatCliente 
{
    public ArrayList<Cliente> getClientes();
    
    public Cliente getClienteById(int idCliente);
    
    public Cliente getClienteCreditoById(int idCliente);
    
    public int deleteCliente(int idCliente);
    
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
    
    
}
