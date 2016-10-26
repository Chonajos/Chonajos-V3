/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaMercancia;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioEntradaMercancia {

    public int insertEntradaMercancia(EntradaMercancia entrada);
    
    public int updateEntradaMercancia(EntradaMercancia entrada);

    public int getNextVal();
    
    public int getCarroSucursal(BigDecimal idSucursal);

    public int buscaMaxMovimiento(EntradaMercancia entrada);

    public List<Object[]> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor);

    public List<Object[]> getSubEntradaByNombre(String clave);
    
    public List<Object[]>  getEntradaById(BigDecimal id);
    
    public int deleteEntradaMercancia(EntradaMercancia em);
    
    public List<Object[]> getEntradaByIdEmPFk(BigDecimal idEmPFk);
    
    public List<Object[]> getEntradaByIdPk(BigDecimal idPk);
    
    /**
     * Obtiene los carros de las entradas de mercancia por medio del id de sucursal
     * @param idPk
     * @return 
     */
    public List<Object[]> getCarrosByIdSucursalAndIdProvedor(BigDecimal idSucursal,BigDecimal idProvedor,BigDecimal carro);
    
    
}
