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
    public ArrayList<BuscaVenta> getVentaMayoreoById(int idVenta,int idSucursal);
    public ArrayList<BuscaVenta> buscaVentaCancelar(int idVenta,int idSucursal);
    public ArrayList<BuscaVenta> getVentaMayoreoByIdBuscaVenta(int idVenta,int idSucursal);
    public int updateVenta(int idVenta,int idUsusario);
    public int cancelarVenta(int idVenta,int idUsuario,String comentarios);
    public int cancelarVentaMayoreo(int idVenta,int idUsuario,String comentarios);
    public int updateStatusVentaMayoreo(int idVenta,int idUsuario);
    
    /**
     * Busca una venta de menudeo por folio de sucursal y un id de sucursal 
     * @param folioVenta
     * @param idSucursal
     * @return 
     */
    public ArrayList<BuscaVenta> getVentaByfolioAndIdSuc(int folioVenta, int idSucursal);
}
