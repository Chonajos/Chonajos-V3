/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.AccesMenu;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface IfaceAccesMenu extends PaginacionService<AccesMenu, BigDecimal>{
    
    public List<AccesMenu> getAccesosMenuPorIdRol(BigDecimal idRol);
    
}
