package com.web.chon.service;

import com.web.chon.dominio.HorarioUsuario;
import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public interface IfaceHorarioUsuario {
    
    public int insert(HorarioUsuario horarioUsuario);
    
    public int update(HorarioUsuario horarioUsuario);
    
    public HorarioUsuario getByIdUsuario(BigDecimal id);
    
}
