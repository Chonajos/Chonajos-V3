/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Competidor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author freddy
 */
public interface IfaceCompetidores {
    public ArrayList<Competidor> getCometidores();
    public int getNextVal();
    public int insertCompetidor(Competidor c);
    public int updateCompetidor(Competidor c);
    public Competidor getCometidoresById(BigDecimal idCompetidor);
}
