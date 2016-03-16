/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Producto;
import java.util.ArrayList;

/**
 *
 * Interface para el servicio del catalogo de Categorias
 * @author Juan de la Cruz
 */
public interface IfaceProducto {
    
    public ArrayList<Producto> getProductos();
    
    public Producto getProductoById(String idProducto);
    
    public int deleteProducto(String idProducto);
    
    public int updateProducto(Producto producto );
    
    /**
     * Obtiene el ultimo id de categoria y lo devuelve como entero
     * @return 
     */
    public int getLastIdCategoria();
    
    public int insertarProducto(Producto producto );
}
