
package com.web.chon.negocio;

import com.web.chon.dominio.AbonoCredito;
import java.math.BigDecimal;
import java.util.Date;
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
    
    /**
     * Obtiene todos los abonos por medio del id de credito
     * @param idAbonoCredito
     * @return 
     */
    public List<Object[]> getAbonosByIdCredito(BigDecimal idCredito) ;
    
    public List<Object[]> getHistorialAbonos(BigDecimal idClienteFk, BigDecimal idCajeroFk, String fechaInicio, String fechaFin, BigDecimal idTipoPagoFk, BigDecimal idAbonoPk, BigDecimal idCreditoFk,BigDecimal idSucursalFk,BigDecimal idCajaFk);
    
    public List<Object[]> getHistorialCrediticio(BigDecimal idClienteFk, String fechaInicio, String fechaFin);
    
    public BigDecimal  getTotalAbonos(BigDecimal idClienteFk,String fechaInicio);
    
}
