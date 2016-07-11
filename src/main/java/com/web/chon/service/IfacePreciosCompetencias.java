/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.PreciosCompetencia;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfacePreciosCompetencias {
    public int getNextVal();
    public int deletePrecioCompetencia(PreciosCompetencia pc);
    public int updateCompetencia(PreciosCompetencia pc);
    public int insertPreciosCompetencias(PreciosCompetencia pc);
    public PreciosCompetencia getPreciosCompetenciasByCompetidorProducto(PreciosCompetencia pc);
    public ArrayList<PreciosCompetencia> getPreciosCompetencias(String fecha);
  
    
}
