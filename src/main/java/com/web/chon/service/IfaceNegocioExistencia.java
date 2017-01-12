
package com.web.chon.service;

import com.web.chon.dominio.ExistenciaProducto;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author freddy
 */
public interface IfaceNegocioExistencia {

    public int insertExistenciaProducto(ExistenciaProducto ep);

    public ArrayList<ExistenciaProducto> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor, String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio, BigDecimal idEmpPK,BigDecimal carro,BigDecimal estatusCarro);

    public ArrayList<ExistenciaProducto> getExistenciasCancelar(BigDecimal idExistencia);

    public ArrayList<ExistenciaProducto> getExistenciasbyIdSubProducto(String idSubproductoFk);

    /**
     * Trae las existencias repetidas con la misma sucursal, subproducto, tipo
     * de empaque, bodega, provedor, convenio, id de entrada producto
     *
     * @param idSucursal
     * @param idSubproductoFk
     * @param idTipoEmpaqueFk
     * @param idBodegaFk
     * @param idProvedorFk
     * @param idTipoConvenio
     * @return
     */
    public ArrayList<ExistenciaProducto> getExistenciaProductoRepetidos(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idTipoConvenio, BigDecimal idEMProducto);

    public int updateCantidadKilo(ExistenciaProducto ep);

    public int update(ExistenciaProducto ep);

    public int updatePrecio(ExistenciaProducto ep);

    public ExistenciaProducto getExistenciaById(BigDecimal idExistencia);
    
    public ArrayList<ExistenciaProducto> getExistenciaByBarCode(String idSubProducto,BigDecimal idTipoEmpaqueFk,BigDecimal idTipoConvenioFk, BigDecimal idCarro,BigDecimal idSucursalFk);

    public int deleteExistenciaProducto(ExistenciaProducto ep);

    public ExistenciaProducto getExistenciaByIdEmpFk(BigDecimal idEmpFk);

    public int getNextVal();

}
