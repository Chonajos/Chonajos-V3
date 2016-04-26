
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaMercanciaProducto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author s
 */
@Remote
public interface NegocioEntradaMercanciaProducto {

    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto);

    public int getNextVal();

    public List<Object[]> getEntradaProductoByIdEM(BigDecimal idEntradaProducto);
}
