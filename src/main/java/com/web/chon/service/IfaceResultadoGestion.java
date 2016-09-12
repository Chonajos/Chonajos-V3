
package com.web.chon.service;

import com.web.chon.dominio.ResultadoGestion;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceResultadoGestion {
    
    public int update(ResultadoGestion resultadoGestion);
    
    public int insert(ResultadoGestion resultadoGestion);
    
    public int delete(BigDecimal idResultadoGestion);
    
    public ArrayList<ResultadoGestion> getAll();
    
    public ResultadoGestion getById(BigDecimal idResultadoGestion);
    
    public int getNextVal();
    
    
}
