package com.web.chon.service;

import com.web.chon.dominio.AjusteExistenciaMayoreo;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceAjusteExistenciaMayoreo {

    /**
     * Inserta los ajustes realizados en existencia de productos menudeo
     *
     * @param data
     * @return
     */
    public int insert(AjusteExistenciaMayoreo data);

    public int update(AjusteExistenciaMayoreo data);

    /**
     * Obtiene todos los ajustes que se an realizado en existencia de productos
     * menudeo
     *
     * @return
     */
    public List<AjusteExistenciaMayoreo> getAll();

    /**
     * Obtiene todos los ajustes que se an realizado en existencia de productos
     * menudeo de una sucursal
     *
     * @param idSucursal
     * @return
     */
    public List<AjusteExistenciaMayoreo> getAllByIdSucursal(BigDecimal idSucursal);

}
