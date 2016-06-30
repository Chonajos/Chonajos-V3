/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.PreciosCompetencia;

/**
 *
 * @author freddy
 */
public interface IfacePreciosCompetencias {
    public int getNextVal();
    public int insertPreciosCompetencias(PreciosCompetencia pc);
  
    
}
