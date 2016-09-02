
package com.web.chon.service;

import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Juan de la Cruz
 */
public interface IfaceCredito {
    
    public ArrayList<Credito> getAll();
    
    public ArrayList<SaldosDeudas> getCreditosActivos(BigDecimal idCliente);
    
    public Credito getById(BigDecimal idCredito);
    
    public int delete(BigDecimal idCredito);
    
    public int update(Credito credito);
    
    public int updateACuenta(Credito credito);
    
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus);
    
    public Credito getTotalAbonado(BigDecimal idCredito);
    
    public int insert(Credito credito);
    
    /**
     * Obtiene creditos por medio de su estatus(Vencido = 3, Atrazado = 2,Preventivo =1)
     * @param estatus
     * @return ArrayList<SaldosDeudas>
     */
    public ArrayList<SaldosDeudas> getCreditosByEstatus(int estatus,int dias);
    
    /**
     * Obtiene el credito por medio del id de venta de menudeo
     * @param idVenta
     * @return 
     */
     public Credito getCreditosByIdVentaMenudeo(BigDecimal idVenta);
     
     public Credito getCreditosByIdVentaMayoreo(BigDecimal idVentaMayoreo);
    
    
    
}
