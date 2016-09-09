/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.CorteCaja;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioCorteCaja {
    public  List<Object[]> getCorteBy(BigDecimal idCajaFk, String fechaInicio, String fechaFin, BigDecimal idUserFk, BigDecimal idStatusFk);
    public int insertCorte(CorteCaja cc);
    public int updateCorte(CorteCaja cc);
    public List<Object[]> getCortesByIdPk(BigDecimal idDestinoFK);
    public int getNextVal();
    
}
