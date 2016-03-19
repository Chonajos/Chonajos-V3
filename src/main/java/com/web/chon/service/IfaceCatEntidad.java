/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Entidad;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceCatEntidad 
{
    public ArrayList<Entidad> getEntidades();
    
    public Entidad getEntidadById(int idEntidad);
    
    public int deleteEntidad(int idEntidad);
    
    public int updateEntidad(Entidad enti);
    
    public int insertEntidad(Entidad enti);
    
}
