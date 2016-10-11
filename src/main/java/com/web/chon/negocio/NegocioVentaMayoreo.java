/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.VentaMayoreo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioVentaMayoreo {

    public int insertarVenta(VentaMayoreo venta);

    public int getNextVal();

    public int getVentaSucursal(BigDecimal idSucursal);

    List<Object[]> getVentasByInterval(String fechaInicio, String fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, BigDecimal idTipoVenta);

    public int updateEstatusVentaByFolioSucursalAndIdSucursal(BigDecimal folioSucursal, BigDecimal idSucursal, BigDecimal estatusVenta);

    public int cancelarVentaMayoreo(BigDecimal idVenta, BigDecimal idUsuario,String comentarios);
    
    }
