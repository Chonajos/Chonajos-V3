/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Cliente;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioBuscaVenta 
{
    public List<Object[]> getVentaById(int idVenta);
    public List<Object[]> getVentaMayoreoById(int idVenta,int idSucursal);
    public List<Object[]> buscaVentaCancelar(int idVenta,int idSucursal);
    public List<Object[]> getVentaMayoreoByIdBuscaVenta(int idVenta,int idSucursal);        
    public int updateVenta(int idVenta,int idUsuario);
    public int cancelarVenta(int idVenta, int idUsuario, String comentarios);
    public int cancelarVentaMayoreo(int idVenta, int idUsuario, String comentarios);
    public int updateStatusVentaMayoreo(int idVenta,int idUsuario);
    

}
