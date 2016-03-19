/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Municipios;
import java.util.ArrayList;

/**
 *
 * @author marcogante
 */
public interface IfaceCatMunicipio {
    
    public ArrayList<Municipios> getMunicipios(int idMunicipio);
    
    public Municipios getMunicipioById(int idMunicipio);
    
    public int deleteMunicipio(int idMunicipio);
    
    public int updateMunicipios(Municipios muni);
    
    public int insertMunicipios(Municipios muni);
    
    
}
