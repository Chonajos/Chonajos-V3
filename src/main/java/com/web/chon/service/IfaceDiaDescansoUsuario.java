package com.web.chon.service;

import com.web.chon.dominio.DiaDescansoUsuario;
import java.math.BigDecimal;

/**
 *
 * @author Juan
 */
public interface IfaceDiaDescansoUsuario {

public int insert(DiaDescansoUsuario diaDescansoUsuario);

public int update(DiaDescansoUsuario diaDescansoUsuario);

public DiaDescansoUsuario getByIdUsuario(BigDecimal id);
    
}
