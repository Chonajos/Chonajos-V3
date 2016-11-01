/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CorteCaja;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface IfaceCorteCaja {
    public int insertCorte(CorteCaja cc);
    public int updateCorte(CorteCaja cc);
    public int getNextVal();
    public ArrayList<CorteCaja> getCortesByIdCajaFk(BigDecimal idDestinoFK,String fechaIni,String fechaFin);
    public  CorteCaja getCorteByidPk(BigDecimal idPk);
    public CorteCaja getLastCorteByCaja(BigDecimal idCajaPk);
    public CorteCaja getLastCorteByCajaHistorial(BigDecimal idCajaPk,BigDecimal idCorteFk);
    public ArrayList<CorteCaja> getCortesByFechaCajaUsuario(BigDecimal idCajaFk,BigDecimal idUsuarioFk,String fecha);
    
}
