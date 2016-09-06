package com.web.chon.negocio;

import com.web.chon.dominio.Venta;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioVenta {

    public int insertarVenta(Venta venta,int folioVenta);

    /**
     * Regresa el siguiente id a guardar para la tabla de venta
     *
     * @return
     */
    public int getNextVal();

    /**
     * Obtiene las ventas realizadas por un intervalo de fechas
     *
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta,String idProducto); //default todo

    /**
     * Regresa el siguiente numero de folio de venta de la sucursal
     *
     * @return
     */
    public int getFolioByIdSucursal(int idSucursal);
    
    public int cancelarVenta(int idVenta, int idUsuario, String comentarios);
    
    public BigDecimal getTotalVentasByDay(String fecha);



}
