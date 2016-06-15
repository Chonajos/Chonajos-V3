/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.EntradaMenudeoProducto;

/**
 *
 * @author freddy
 */
public interface IfaceEntradaMenudeoProducto {
    public int insertEntradaMercanciaProducto(EntradaMenudeoProducto producto);
    public int getNextVal();
    
}
