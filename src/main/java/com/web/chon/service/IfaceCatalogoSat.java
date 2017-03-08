/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CatalogoSat;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author jramirez
 */
public interface IfaceCatalogoSat {
    
    public ArrayList<CatalogoSat> getCatalogo(BigDecimal idTipoFk);

}
