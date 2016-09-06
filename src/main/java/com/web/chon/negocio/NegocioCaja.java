/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Caja;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioCaja 
{
    public int insertCaja(Caja c);
    public int updateCaja(Caja c);
    public int updateMontoCaja(Caja c);
    public List<Object[]> getCajas(BigDecimal idSucursalFk,BigDecimal tipo);
    public List<Object[]> getCajaByIdPk(BigDecimal idCajaPk);
    public List<Object[]> getCajaByIdUsuarioPk(BigDecimal idUsuarioPk,BigDecimal tipo);
    
}
