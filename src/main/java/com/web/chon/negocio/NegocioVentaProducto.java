package com.web.chon.negocio;

import com.web.chon.dominio.VentaProducto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioVentaProducto {
    
    public List<Object[]> getVentaProductoByIdVenta(BigDecimal idVentaFK);
    public List<Object[]> getProductosByIdVentaFK(BigDecimal idVentaFK);
    public List<Object[]> getReporteVenta(String fechaInicio,String fechaFin,BigDecimal idSucursal);
    public int insertarVentaProducto(VentaProducto ventaProducto,int idVenta);
    

}
