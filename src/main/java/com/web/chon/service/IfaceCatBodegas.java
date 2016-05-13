/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.Bodega;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface IfaceCatBodegas extends PaginacionService<Bodega, BigDecimal>{
     public ArrayList<Bodega> getBodegas();
    
    public List<Bodega[]> getBodegaById(int idBodega);
    public ArrayList<Bodega> getBodegaByIdSucursal(BigDecimal idSucursal);

    public int getNextVal ();
}
