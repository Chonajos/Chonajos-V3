/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Apartado;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NegocioApartado {
    public int insert(Apartado apartado);
    public int getNextVal();
    public BigDecimal montoApartado(BigDecimal idVentaFk,BigDecimal idTipoFk);
    
}
