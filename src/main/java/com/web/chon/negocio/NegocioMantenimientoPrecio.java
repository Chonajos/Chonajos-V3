package com.web.chon.negocio;

import com.web.chon.dominio.MantenimientoPrecios;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioMantenimientoPrecio {

    public List<Object[]> getPrecioByIdEmpaqueAndIdProducto(String idProducto, int idEmpaqueint, int idSucursal);

    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);

    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);

    public List<Object[]> getAllByIdSucAndIdSubProducto(BigDecimal idSucursal,String idSubProducto, Date fechaMercado);

}
