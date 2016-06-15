/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;


import com.web.chon.dominio.EntradaMenudeoProducto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioMenudeoProducto {
    public int insertEntradaMenudeoProducto(EntradaMenudeoProducto producto);
    public int getNextVal();
    public List<Object[]> getEntradaMenudeoProductoByIdEM(BigDecimal idEntradaMenudeo);

}
