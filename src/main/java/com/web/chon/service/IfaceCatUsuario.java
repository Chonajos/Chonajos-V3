/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Producto;
import com.web.chon.dominio.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public interface IfaceCatUsuario {
    
    public ArrayList<Usuario> getUsuarios();
    
    public Usuario getUsuariosById(int idUsuario);
    
    public int deleteUsuarios(int idUsuario);
    
    public int updateUsuario(Usuario usuario );
    
    public int insertarUsuarios(Usuario usuario );
}
