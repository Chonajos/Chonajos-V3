package com.web.chon.service;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.core.service.PaginacionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceAnalisisMercado extends PaginacionService<AnalisisMercado, BigDecimal> {

    /**
     * Metodo para guardar el producto que entra a la central
     *
     * @param entradaMercancia
     * @return
     */
    public int saveEntradaProductoCentral(AnalisisMercado entradaMercancia);

    /**
     * Obtiene una lista de por medio de filtros dia, mes , año e intervalo de
     * fechas
     *
     * @param filtro
     * @param fechaInicio
     * @return
     */
    public ArrayList<AnalisisMercado> getEntradaMercanciaByFiltro(int numRegistros,int filtro, Date fechaInicio,String idProducto);
    
    /**
     * Obtiene un dominio entradaMercancia por medio del id de producto y la feha actual del sistema
     * @param idProducto
     * @return 
     */
    public AnalisisMercado getEntradaProductoByIdProducto(String idProducto,String fecha);
    
    /**
     * Obtiene el Remanente por semana
     * @param fechaRemanente
     * @return 
     */
    public BigDecimal getRemanente(Date fechaRemanente,String idProducto);
    
    /**
     * Ingresa el remanente por semana del dia selecionado
     * @param analisiMercado
     * @return 
     */
    public int updateByIdProductoAndFecha(AnalisisMercado analisiMercado);
    
    /**
     * Regresa una lista de todos los dias del año por dia de la semana
     * @param fechaInicio
     * @param fechaFin
     * @param idProducto
     * @param nombreDia
     * @return ArrayList<AnalisisMercado>
     */
    public ArrayList<AnalisisMercado> getAnalisMercadoByNameDayOfYear(String fechaInicio, String fechaFin,String idProducto,String nombreDia);
}
