/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaMenudeo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author freddy
 */
public interface IfaceExistenciaMenudeo {
    public int insertaExistenciaMenudeo(ExistenciaMenudeo em);
    public int updateExistenciaMenudeo(ExistenciaMenudeo em);
    public ArrayList<ExistenciaMenudeo> getExistenciasMenudeoByIdSucursal(BigDecimal idSucursal);
    public ExistenciaMenudeo getExistenciasMenudeoById(BigDecimal id);
    public ArrayList<ExistenciaMenudeo> getExistenciasMenudeo();

    
}