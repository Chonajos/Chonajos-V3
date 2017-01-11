
package com.web.chon.service;

import com.web.chon.core.service.PaginacionService;
import com.web.chon.dominio.CarroDetalleGeneral;
import com.web.chon.dominio.EntradaMercancia;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author marcogante
 */
public interface IfaceEntradaMercancia extends PaginacionService<EntradaMercancia, BigDecimal> {

    public int insertEntradaMercancia(EntradaMercancia entrada);
    
    public int updateEntradaMercancia(EntradaMercancia entrada);
    
    public EntradaMercancia getEntradaByIdEmPFk(BigDecimal idEmPFk);
    
    public EntradaMercancia getEntradaByIdPk(BigDecimal idPk);

    public int buscaMaxMovimiento(EntradaMercancia entrada);

    public int getNextVal();
    public int getCarroSucursal(BigDecimal idSucursal);

    /**
     * Obtiene una lista de la entrada de mercancia por medio de filtros de
     * fecha inicio, fin , id de sucursal y id de provedor
     *
     * @param fechaInicio
     * @param fechaFin
     * @param idSucursal
     * @param idProvedor
     * @return
     */
    public ArrayList<EntradaMercancia> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor,BigDecimal carro);

    public ArrayList<EntradaMercancia> getSubEntradaByNombre(String nombre);
    
    public int deleteEntradaMercancia(EntradaMercancia entrada);
    
    /**
     * Obtiene una lista de los carros por medio del id de la sucursal y/o id del provedor
     * @param idSucursal
     * @param idProvedor
     * @return 
     */
    public ArrayList<EntradaMercancia> getCarrosByIdSucursalAnsIdProvedor(BigDecimal idSucursal ,BigDecimal idProvedor);
    
    /**
     * Obtiene una lista de los carros y sus datos generales por medio del id de la sucursal y/o id del provedor
     * @param idSucursal
     * @param idProvedor
     * @return 
     */
    public ArrayList<CarroDetalleGeneral> getReporteGeneralCarro(BigDecimal idSucursal ,BigDecimal idProvedor,BigDecimal carro,String fechaInicio,String fechaFin);
    
    /**
     * Cambia el estatus del carro a cerrado
     * @param idEntradaMercancia
     * @return 
     */
    public int cerrarCarro(BigDecimal idEntradaMercancia);
}
