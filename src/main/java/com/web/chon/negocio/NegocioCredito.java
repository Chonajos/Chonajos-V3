
package com.web.chon.negocio;

import com.web.chon.dominio.Credito;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioCredito 
{
    public int insert(Credito credito);
    
    public int update(Credito idCredito);
    
    public int delete(BigDecimal idCredito);
    
    public List<Object[]> getAll();
    
    public List<Object[]> getCreditosActivos(BigDecimal idCliente);
    public List<Object[]> getById(BigDecimal idCredito) ;
    
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus);

    
}
