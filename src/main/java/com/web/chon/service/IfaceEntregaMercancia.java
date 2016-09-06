/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.EntregaMercancia;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceEntregaMercancia {
   
    /**
     * Obtiene lista de productos de venta por medio de id de sucursal y el folio de venta
     * @param idSucursal
     * @param folioSucursal
     * @return 
     */
    public ArrayList<EntregaMercancia> getByIdSucursalAndFolioSucursal(BigDecimal idSucursal,BigDecimal folioSucursal);

    /**
     * Inserta una e
     * @param entregaMercancia
     * @return 
     */
    public int insert(EntregaMercancia entregaMercancia);
}
