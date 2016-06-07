/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.RegistroEntradaSalida;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioRegEntSal {
    public List<Object[]> getUsuarioByIdUsuario(BigDecimal idUsuarioFK,String fechaHoy);
    public List<Object[]> getALL(String fechaInicio, String fechaFin);
  
    public List<Object[]> getRegistros(BigDecimal idUsuarioFK, String fechaInicio, String fechaFin);
    public int updateSalidabyIdReg(RegistroEntradaSalida data);
    public int insertEntradabyIdReg(RegistroEntradaSalida data);
    public int getNextVal ();
}
