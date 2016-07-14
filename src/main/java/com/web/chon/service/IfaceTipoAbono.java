/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.TipoAbono;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceTipoAbono {
    public ArrayList<TipoAbono> getAll();
   
    public TipoAbono getById(BigDecimal idtAbono);
    
    public int delete(BigDecimal idtAbono);
    
    public int update(TipoAbono tAbono);
    
    public int insert(TipoAbono tAbono);
}
