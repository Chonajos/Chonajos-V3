/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.ExistenciaMenudeo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioExistenciaMenudeo {
    public int insertaExistenciaMenudeo(ExistenciaMenudeo em);
    public int updateExistenciaMenudeo(ExistenciaMenudeo em);
    public List<Object[]> getExistenciasMenudeoByIdSucursal(BigDecimal idSucursal);
    public List<Object[]> getExistenciasMenudeoById(BigDecimal id);
    public List<Object[]> getExistenciasMenudeo(BigDecimal id);
}
