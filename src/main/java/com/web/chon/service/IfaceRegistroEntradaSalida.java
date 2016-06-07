/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.RegistroEntradaSalida;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceRegistroEntradaSalida 
{
    public ArrayList<RegistroEntradaSalida> getUsuarioByIdUsuario(BigDecimal idUsuarioFK,Date fechaHoy);
    public ArrayList<RegistroEntradaSalida> getRegistros(BigDecimal idUsuarioFK,Date fechaInicio, Date fechaFin);
    public ArrayList<RegistroEntradaSalida> getALL(Date fechaInicio, Date fechaFin);
    
    public int updateSalidabyIdReg(RegistroEntradaSalida data);
    public int insertEntradabyIdReg(RegistroEntradaSalida data);
    public int getNextVal ();
    
}
