/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.RelacionOperacionesMayoreo;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author freddy
 */
public interface IfaceVentaMayoreo {

    public int insertarVenta(VentaMayoreo venta);

    public int getNextVal();

    public ArrayList<VentaMayoreo> getVentasByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idStatusVenta, BigDecimal idTipoVenta,String idSubproductoFk);
    
    public VentaMayoreo getVentaMayoreoByFolioidSucursalFk(BigDecimal idFolio,BigDecimal idSucursal);

    public int getVentaSucursal(BigDecimal idSucursal);

    /**
     * Modifica el estatus de la venta por medio del folio de la sucursal y el id de sucursal
     * @param idSucursal
     * @return 
     */
    public int updateEstatusVentaByFolioSucursalAndIdSucursal(BigDecimal folioSucursal, BigDecimal idSucursal, BigDecimal estatusVenta);

    public int cancelarVentaMayoreo(BigDecimal idVenta,BigDecimal idUsuario,String comentarios);
    

}
