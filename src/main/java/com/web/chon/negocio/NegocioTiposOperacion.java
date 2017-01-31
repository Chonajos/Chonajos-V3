/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
  */
@Remote
public interface NegocioTiposOperacion {
    public List<Object[]> getOperaciones();
    public List<Object[]> getOperacionesByIdCategoria(BigDecimal id);
}
