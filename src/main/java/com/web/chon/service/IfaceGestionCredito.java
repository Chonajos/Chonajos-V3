
package com.web.chon.service;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.GestionCredito;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceGestionCredito {
    
    public int update(GestionCredito gestionCredito);
    
    public int insert(GestionCredito gestionCredito);
    
    public int delete(BigDecimal idGestionCredito);
    
    public ArrayList<GestionCredito> getAll();
   
    public GestionCredito getById(BigDecimal idGestionCredito);
    
    public int getNextVal();
    
    
}
