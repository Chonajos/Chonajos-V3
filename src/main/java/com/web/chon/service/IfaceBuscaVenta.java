package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;

/**
 *
 * @author freddy
 */
public interface IfaceBuscaVenta 
{
    public BuscaVenta getVentaById(int idVenta);
    public int updateCliente(int idVenta);
}
