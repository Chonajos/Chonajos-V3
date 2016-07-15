/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.AbonoCredito;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceAbonoCredito {
    
    public int update(AbonoCredito abonoCredito);
    
    public int insert(AbonoCredito abonoCredito);
    
    public int delete(BigDecimal idAbonoCredito);
    
    public ArrayList<AbonoCredito> getAll();
    
    public AbonoCredito getById(BigDecimal idAbonoCredito);
    
    public int getNextVal();
    
    
}
