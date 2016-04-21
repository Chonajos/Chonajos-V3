/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaProducto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author f
 */
public interface IfaceNegocioExistencia {
    public int insertExistenciaProducto(ExistenciaProducto ep);
    public ArrayList<ExistenciaProducto> getExistenciaProductoId(BigDecimal idSucursal,String idSubproductoFk,BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk);
    public int updateExistenciaProducto(ExistenciaProducto ep);
    }
