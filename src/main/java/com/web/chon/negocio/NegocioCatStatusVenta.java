/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.StatusVenta;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatStatusVenta 
{
    public List<Object[]> getStatusVentas();

    public List<Object[]> getStatusVentaId(int idSucursal);

    public int deleteStatusVenta(int idSucursal);

    public int updateStatusVenta(StatusVenta st);

    public int insertStatusVenta(StatusVenta st);
    
    public int getNextVal ();
    
}
