
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.Rol;
import java.math.BigDecimal;


/**
 *
 * @author Juan
 */
public interface IfaceCatRol extends PaginacionService<Rol, BigDecimal>{
 
    public int getNextVal ();
}
