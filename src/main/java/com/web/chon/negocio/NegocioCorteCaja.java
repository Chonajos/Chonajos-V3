/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.CorteCaja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioCorteCaja {
    public int insertCorte(CorteCaja cc);
    public int updateCorte(CorteCaja cc);
    public int getNextVal();
    public List<Object[]>  getCortesByIdCajaFk(BigDecimal idCajaFK,String fechaIni,String fechaFin);
    public  List<Object[]>  getCorteByidPk(BigDecimal idPk);
    public  List<Object[]>  getLastCorteByCaja(BigDecimal idCajaPk);
    public List<Object[]> getLastCorteByCajaHistorial(BigDecimal idCajaPk,BigDecimal idCorteFk);
    public List<Object[]> getCortesByFechaCajaUsuario(BigDecimal idCajaFk,BigDecimal idUsuarioFk,String fecha);
    
}
