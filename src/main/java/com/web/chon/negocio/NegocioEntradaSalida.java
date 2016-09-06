/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaSalida;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioEntradaSalida {
    public List<Object[]> getMovimientosByIdCaja(BigDecimal idCaja, String fechaInicio,String fechaFin);
    public int insertaMovimiento(EntradaSalida es);
    public int getNextVal();
    
}
