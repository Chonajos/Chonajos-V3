/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Sucursal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatSucursales 
{
    public List<Object[]> getSucursales();

    public List<Object[]> getSucursalId(int idSucursal);

    public int deleteSucursal(int idSucursal);

    public int updateSucursal(Sucursal sucu);

    public int insertSucursal(Sucursal sucu);
    
    public int getNextVal ();
    
}
