/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.EntradaMercancia2;
import java.math.BigDecimal;

/**
 *
 * @author marcogante
 */
public interface IfaceEntradaMercancia extends PaginacionService<EntradaMercancia2, BigDecimal>
{
     public int insertEntradaMercancia(EntradaMercancia2 entrada);
     public int buscaMaxMovimiento(EntradaMercancia2 entrada);
     public int getNextVal ();
    
}
