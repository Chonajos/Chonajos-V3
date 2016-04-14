/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Provedor;

import com.web.chon.core.service.PaginacionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author freddy
 */
public interface IfaceCatProvedores extends PaginacionService<Provedor,BigDecimal>       
{
    
    public ArrayList<Provedor> getProvedores();

    public List<Object[]> getProvedorlId(BigDecimal idProvedor);

    public int deleteProvedor(BigDecimal idProvedor);

    public int updateProvedor(Provedor pro);

    public int insertProvedor(Provedor pro);
    
    public int getNextVal ();
    
}
