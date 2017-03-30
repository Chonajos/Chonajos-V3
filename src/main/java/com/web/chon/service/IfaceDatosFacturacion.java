package com.web.chon.service;

import com.web.chon.dominio.DatosFacturacion;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author jramirez
 */
public interface IfaceDatosFacturacion {
    
    public DatosFacturacion getDatosFacturacionByIdCliente(BigDecimal idClienteFk);
    public ArrayList<DatosFacturacion> getDatosFacturacionByIdSucursal(BigDecimal idSucursalFk);
    public int deleteDatosFacturacion(BigDecimal idDatosFacturacion);
    public int insertarDatosFacturacion(DatosFacturacion df);
    public int updateDatosFacturacion(DatosFacturacion df);
    public int getNextVal();
    public ArrayList<DatosFacturacion> getByIdSucursal(BigDecimal idSucursal);
    public DatosFacturacion getByRfc(String rfc);
    
    
    /**
     * Obtine todas las razones sociales
     * @return 
     */
    public ArrayList<DatosFacturacion> getAll();
    
    
    
}
