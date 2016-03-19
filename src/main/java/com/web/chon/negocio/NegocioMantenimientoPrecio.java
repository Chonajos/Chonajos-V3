package com.web.chon.negocio;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioMantenimientoPrecio {
    
   
    public List<Object[]> getPrecioByIdEmpaqueAndIdProducto(String idProducto, int idEmpaque);
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios);
    

}
