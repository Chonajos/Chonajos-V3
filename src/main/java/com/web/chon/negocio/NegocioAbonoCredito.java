
package com.web.chon.negocio;

import com.web.chon.dominio.AbonoCredito;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioAbonoCredito 
{
    public int insert(AbonoCredito abonoCredito);
    
    public int update(AbonoCredito abonoCredito);
    
    public int delete(BigDecimal idAbonoCredito);
    
    public List<Object[]> getAll();
    
    public List<Object[]> getById(BigDecimal idAbonoCredito) ;
    
    public List<Object[]> getByIdCredito(BigDecimal idAbonoCredito) ;
    
    public List<Object[]> getChequesPendientes(String fechaInicio, String fechaFin,BigDecimal idSucursal,BigDecimal idClienteFk,BigDecimal filtro,BigDecimal filtroStatus);
    
    public int getNextVal();
    
    public List<Object[]>  getByIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk);
    
    public List<Object[]>  getByIdVentaMenudeoFk(BigDecimal idVentaMenudeoFk);
    

    
}
