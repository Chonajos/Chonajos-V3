/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.DatosFacturacion;
import java.math.BigDecimal;
import java.util.ArrayList;


/**
 *
 * @author jramirez
 */
public interface IfaceFacturacion {
    public DatosFacturacion getDatosFacturacionByIdCliente(BigDecimal idClienteFk);
    public ArrayList<DatosFacturacion> getDatosFacturacionByIdSucursal(BigDecimal idSucursalFk);
    public int deleteDatosFacturacion(String idProducto);
    public int insertarDatosFacturacion(DatosFacturacion df);
    public int updateDatosFacturacion(DatosFacturacion df);
    public int getNextVal();
}
