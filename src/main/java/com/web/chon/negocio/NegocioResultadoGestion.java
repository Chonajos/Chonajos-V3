
package com.web.chon.negocio;

import com.web.chon.dominio.GestionCredito;
import com.web.chon.dominio.ResultadoGestion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioResultadoGestion 
{
    public int insert(ResultadoGestion resultadoGestion);
    
    public int update(ResultadoGestion resultadoGestion);
    
    public int delete(BigDecimal idResultadoGestion);
    
    public List<Object[]> getAll();
    
    public int getNextVal();
    

    
}
