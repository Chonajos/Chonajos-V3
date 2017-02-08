
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
    
    public int deleteEntradaProducto(EntradaMercanciaProducto ep);
    
    public List<Object[]> getTotalVentasByIdEMP(BigDecimal idEmP);
    
    public List<Object[]> getEntradaMercanciaProductoByIdEmpPk(BigDecimal idEmpPk);
    
    public int updateEntradaMercanciaProducto(EntradaMercanciaProducto producto);
    
    public int updateVideo(BigDecimal id, byte[] fichero,String url);
    
    public List<Object[]> getById(BigDecimal idEMP);
}
