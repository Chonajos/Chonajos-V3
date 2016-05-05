/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.ExistenciaProducto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;



/**
 *
 * @author freddy
 */
@Remote
public interface NegocioExistenciaProducto 
{
    public int insertaExistencia(ExistenciaProducto e);
    public int updateExistenciaProducto(ExistenciaProducto e);
    public List<Object[]> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor,String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio,BigDecimal idEmPK);
    public List<Object[]> getExistenciaProductoId(BigDecimal idSucursal,String idSubproductoFk,BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk);
    public List<Object[]> getExistenciasRepetidas(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idEMFK,BigDecimal idTipoConvenio);
    public int updatePrecio(ExistenciaProducto ep);
}
