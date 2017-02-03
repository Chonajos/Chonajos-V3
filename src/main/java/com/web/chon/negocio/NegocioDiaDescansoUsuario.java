package com.web.chon.negocio;

import com.web.chon.dominio.DiaDescansoUsuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioDiaDescansoUsuario {
    
    
    public int insert(DiaDescansoUsuario diaDescansoUsuario);
    
    public int update(DiaDescansoUsuario diaDescansoUsuario);
    
    public List<Object[]> getByIdUsuario(BigDecimal id);
    
    
    
}
