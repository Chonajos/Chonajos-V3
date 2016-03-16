/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Subproducto;
import java.util.ArrayList;

/**
 *
 * Interface para el servicio del catalogo de productos
 * @author Juan de la Cruz
 */
public interface IfaceSubProducto {
    
    public ArrayList<Subproducto> getSubProductos();
    
    public Subproducto getSubProductoById(String idSubProducto);
    
    public int deleteSubProducto(String idSubProducto);
    
    public int updateSubProducto(Subproducto subProducto);
    
    public int insertarSubProducto(Subproducto subProducto);
    
    /**
     * Devuelve el siguiente id del producto a registrar por medio del id de categoria
     * @param idCategoria
     * @return 
     */
    public int getLastIdProducto(String idCategoria);
    
    /**
     * Devuelve una lista de proyectos por medio del nombre
     * @param nombre
     * @return 
     */
    public ArrayList<Subproducto> getSubProductoByNombre(String nombre);
}
