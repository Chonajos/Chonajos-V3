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
    public List<Object[]> getVentaMayoreoById(int idVenta);
    public int updateCliente(int idVenta);
    public int cancelarVenta(int idVenta);
    public int updateStatusVentaMayoreo(int idVenta);
    
}
