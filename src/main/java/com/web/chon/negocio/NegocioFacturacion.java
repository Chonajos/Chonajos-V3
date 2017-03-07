/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.DatosFacturacion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jramirez
 */
@Remote
public interface NegocioFacturacion {
    public List<Object[]> getDatosFacturacionByIdCliente(BigDecimal idClienteFk);
    public List<Object[]> getDatosFacturacionByIdSucursal(BigDecimal idSucursalFk);
    public int deleteDatosFacturacion(String idProducto);
    public int insertarDatosFacturacion(DatosFacturacion df);
    public int updateDatosFacturacion(DatosFacturacion df);
    public int getNextVal();
}
