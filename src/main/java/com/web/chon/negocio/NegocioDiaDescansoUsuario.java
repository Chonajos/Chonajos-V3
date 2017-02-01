package com.web.chon.negocio;

import com.web.chon.dominio.DiaDescansoUsuario;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioDiaDescansoUsuario {
    
    
    public int insert(DiaDescansoUsuario diaDescansoUsuario);
    
    public int update(DiaDescansoUsuario diaDescansoUsuario);
    
    
    
}
