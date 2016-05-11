/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.VentaProductoMayoreo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marcogante
 */
@Remote
public interface NegocioVentaMayoreoProducto {
    public int insertarVentaProducto(VentaProductoMayoreo ventaproducto);
    public int getNextVal();
    
}
