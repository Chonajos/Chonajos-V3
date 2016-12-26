/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Apartado;
import java.math.BigDecimal;

/**
 *
 * @author jramirez
 */
public interface IfaceApartado {
    public int insert(Apartado apartado);
    public int getNextVal();
    public BigDecimal montoApartado(BigDecimal idVentaFk,BigDecimal idTipoFk);
}
