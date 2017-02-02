package com.web.chon.negocio;

import com.web.chon.dominio.HorarioUsuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioHorarioUsuario {
    
    public int insert(HorarioUsuario horarioUsuario);
    
    public int update(HorarioUsuario horarioUsuario);
    
    public List<Object[]> getByIdUsuario(BigDecimal id);
}
