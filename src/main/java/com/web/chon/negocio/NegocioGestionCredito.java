
package com.web.chon.negocio;

import com.web.chon.dominio.GestionCredito;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioGestionCredito 
{
    public int insert(GestionCredito gestionCredito);
    
    public int update(GestionCredito gestionCredito);
    
    public int delete(BigDecimal idGestionCredito);
    
    public List<Object[]> getAll();
    
    public List<Object[]> getById(BigDecimal idAGestionCredito) ;
    
    public int getNextVal();
    

    
}
