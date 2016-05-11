package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceBuscaVenta 
{
    public ArrayList<BuscaVenta> getVentaById(int idVenta);
    public ArrayList<BuscaVenta> getVentaMayoreoById(int idVenta);
    public int updateCliente(int idVenta);
}
