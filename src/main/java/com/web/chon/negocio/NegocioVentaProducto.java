package com.web.chon.negocio;

import com.web.chon.dominio.VentaProducto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioVentaProducto {
    
    public List<Object[]> getVentaProductoByIdVenta();
   
    public int insertarVentaProducto(VentaProducto ventaProducto,int idVenta);
    

}
