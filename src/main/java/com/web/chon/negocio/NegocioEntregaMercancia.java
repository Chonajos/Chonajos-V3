package com.web.chon.negocio;

import com.web.chon.dominio.EntregaMercancia;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioEntregaMercancia {

    /**
     * Inserta una entrada de mercancia
     * @param entregaMercancia
     * @return 
     */
    public int insertar(EntregaMercancia entregaMercancia);

    /**
     * Regresa el siguiente id a guardar para la tabla de entrada de mercancia
     *
     * @return
     */
    public int getNextVal();

    /**
     * Regresa la lista de productos vendidos y los entregados por folio de sucursal
     *
     * @return List<Object[]>
     */
    public List<Object[]> getByIdSucursalAndIdFolioSucursal(BigDecimal idSucursal,BigDecimal idFolioSucursal);



}
