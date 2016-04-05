package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia;
import core.service.PaginacionService;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author freddy
 */
public interface IfaceEntradaProductoCentral extends PaginacionService<EntradaMercancia, BigDecimal>
{
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia);
}
