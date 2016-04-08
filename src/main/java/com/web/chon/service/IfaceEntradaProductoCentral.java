package com.web.chon.service;

import com.web.chon.dominio.EntradaMercancia;
import core.service.PaginacionService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceEntradaProductoCentral extends PaginacionService<EntradaMercancia, BigDecimal> {

    /**
     * Metodo para guardar el producto que entra a la central
     *
     * @param entradaMercancia
     * @return
     */
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia);

    /**
     * Obtiene una lista de por medio de filtros dia, mes , año e intervalo de
     * fechas
     *
     * @param filtro
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public ArrayList<EntradaMercancia> getEntradaMercanciaByFiltro(int filtro, Date fechaInicio, Date fechaFin);
}
