
package com.web.chon.service;

import com.web.chon.dominio.AcionGestion;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceAcionGestion {
    
    public int update(AcionGestion AcionGestion);
    
    public int insert(AcionGestion AcionGestion);
    
    public int delete(BigDecimal idAcionGestion);
    
    public ArrayList<AcionGestion> getAll();
    
    public ArrayList<AcionGestion> getByIdResultadoGestion(BigDecimal idResultadoGestion);
    
    public AcionGestion getById(BigDecimal idAcionGestion);
    
    public int getNextVal();
    
    
}
