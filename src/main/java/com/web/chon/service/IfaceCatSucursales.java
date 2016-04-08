/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Sucursal;
import core.service.PaginacionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author freddy
 */
public interface IfaceCatSucursales  extends PaginacionService<Sucursal,BigDecimal>
{
    public ArrayList<Sucursal> getSucursales();

    public List<Object[]> getSucursalId(BigDecimal idSucursal);

    public int deleteSucursal(BigDecimal idSucursal);

    public int updateSucursal(Sucursal sucu);

    public int insertSucursal(Sucursal sucu);
    
    public int getNextVal ();
    
}
