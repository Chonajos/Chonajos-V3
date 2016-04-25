/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.EntradaMercanciaProducto;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioEntradaMercanciaProducto {
    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto);
    public int getNextVal();
}
