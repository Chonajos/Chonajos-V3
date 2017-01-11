
package com.web.chon.negocio;

import com.web.chon.dominio.ExistenciaProducto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioExistenciaProducto {

    public int insertaExistencia(ExistenciaProducto e);

    public int updateCantidadKilo(ExistenciaProducto e);

    public List<Object[]> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor, String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio, BigDecimal idEmpPK,BigDecimal carro,BigDecimal estatusCarro);

    public List<Object[]> getExistenciasCancelar(BigDecimal idExistencia);

    public List<Object[]> getExistenciaProductoId(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk);

    /**
     * Trae las existencias reptidas de una entrada de producto
     *
     * @param idSucursal
     * @param idSubproductoFk
     * @param idTipoEmpaqueFk
     * @param idBodegaFk
     * @param idProvedorFk
     * @param idTipoConvenio
     * @param idEMProducto
     * @return
     */
    public List<Object[]> getExistenciasRepetidas(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idTipoConvenio, BigDecimal idEMProducto);

    public int updatePrecio(ExistenciaProducto ep);

    public int update(ExistenciaProducto e);

    public List<Object[]> getExistenciaById(BigDecimal idExistencia);

    public int deleteExistenciaProducto(ExistenciaProducto exp);

    public List<Object[]> getExistenciaByIdEmpFk(BigDecimal idEmpFk);

    public int getNextVal();
    
    public List<Object[]> getExistenciaByBarCode(String idSubProducto, BigDecimal idTipoEmpaqueFk, BigDecimal idTipoConvenioFk, BigDecimal idCarro, BigDecimal idSucursalFk);
}
