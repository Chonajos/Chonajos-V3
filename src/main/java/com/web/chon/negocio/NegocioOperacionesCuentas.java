/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.OperacionesCuentas;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
public interface NegocioOperacionesCuentas {
    public int insertaOperacion(OperacionesCuentas es);
    public int updateOperacion(OperacionesCuentas es);
    public List<Object[]> getOperacionesByIdCuenta(BigDecimal idCuentaFk);
    public int getNextVal();
}
