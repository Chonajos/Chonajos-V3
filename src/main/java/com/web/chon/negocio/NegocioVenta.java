package com.web.chon.negocio;

import com.web.chon.dominio.Venta;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioVenta {

    public int insertarVenta(Venta venta);
    
    /**
     * Regresa el siguiente id a guardar para la tabla de venta
     * @return 
     */
    public int getNextVal();

}
