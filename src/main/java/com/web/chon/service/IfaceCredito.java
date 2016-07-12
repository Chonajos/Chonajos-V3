
package com.web.chon.service;

import com.web.chon.dominio.Credito;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceCredito {
    
    public ArrayList<Credito> getAll();
    
    public Credito getById(BigDecimal idCredito);
    
    public int delete(BigDecimal idCredito);
    
    public int update(Credito credito);
    
    public int insert(Credito credito);
    
    
}
