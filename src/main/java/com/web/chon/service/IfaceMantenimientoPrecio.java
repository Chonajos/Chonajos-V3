/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.MantenimientoPrecios;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Interface para el servicio del catalogo de productos
 * @author Juan de la Cruz
 */
public interface IfaceMantenimientoPrecio {
    
    public ArrayList<MantenimientoPrecios> getMantenimientoPrecio();
    
    public MantenimientoPrecios getMantenimientoPrecioById(String idSubProducto,int idEmpaque,int idSucursal);
    
    public int deleteMantenimientoPrecio(String idSubProducto,int idEmpaque);
    
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);
    
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);
    
    public ArrayList<MantenimientoPrecios> getMantenimientoPrecioByIdSucAndIdSubProducto(BigDecimal idSucursal, String idSubProducto, Date fechaMercado);
    
    
}
