package com.web.chon.negocio;

import com.web.chon.dominio.Credito;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan
 */
@Remote
public interface NegocioCredito {

    public int insert(Credito credito, int idCredito);

    public int update(Credito idCredito);

    public int updateACuenta(Credito credito);

    public int delete(BigDecimal idCredito);

    public List<Object[]> getAll();
     public int activarCredito(Credito credito);

    /**
     * Obtiene todos los creditos activos por id de Cliente
     * @param idCliente
     * @return 
     */
    public List<Object[]> getCreditosActivos(BigDecimal idCliente,BigDecimal idAbonoPk,BigDecimal idSucursalFk);

    public List<Object[]> getById(BigDecimal idCredito);

    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus);

    public int nextVal();

    public List<Object[]> getTotalAbonado(BigDecimal idCredito);

    /**
     * Obtiene todos los creditos activos
     *
     */
    public List<Object[]> getAllCreditosActivos(BigDecimal idSucursal);
    
    
    public List<Object[]> getCreditosByIdVentaMenudeo(BigDecimal idVenta);
    
    public List<Object[]> getCreditosByIdVentaMayoreo(BigDecimal idVentaMayoreo);
    
    public int eliminarCreditoByIdCreditoPk(BigDecimal idCreditoPk);
    

}
