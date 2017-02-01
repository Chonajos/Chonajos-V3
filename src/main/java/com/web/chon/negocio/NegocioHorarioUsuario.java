package com.web.chon.negocio;

import com.web.chon.dominio.HorarioUsuario;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioHorarioUsuario {
    
    public int insert(HorarioUsuario horarioUsuario);
    
    public int update(HorarioUsuario horarioUsuario);
}
