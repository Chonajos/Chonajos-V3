package com.web.chon.service;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioMantenimientoPrecio;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * Servicio para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceMantenimientoPrecio implements IfaceMantenimientoPrecio {

    NegocioMantenimientoPrecio ejb;

    @Override
    public MantenimientoPrecios getMantenimientoPrecioById(String idSubProducto, int idEmpaque,int idSucursal) {
        try {
            MantenimientoPrecios mantenimientoPrecios = new MantenimientoPrecios();
            ejb = (NegocioMantenimientoPrecio) Utilidades.getEJBRemote("ejbMantenimientoPrecio", NegocioMantenimientoPrecio.class.getName());
            List<Object[]> object = ejb.getPrecioByIdEmpaqueAndIdProducto(idSubProducto.trim(), idEmpaque,idSucursal);
            for (Object[] obj : object) {

                mantenimientoPrecios.setIdSubproducto(obj[0] == null ? "" : obj[0].toString());
                mantenimientoPrecios.setIdTipoEmpaquePk(obj[1] == null ? new BigDecimal(-1) : new BigDecimal(obj[1].toString()));
                mantenimientoPrecios.setPrecioVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                mantenimientoPrecios.setPrecioMinimo(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                mantenimientoPrecios.setPrecioMaximo(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            }

            return mantenimientoPrecios;
        } catch (Exception ex) {
            Logger.getLogger(ServiceMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteMantenimientoPrecio(String idSubProducto, int idEmpaque) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        return ejb.updateMantenimientoPrecio(mantenimientoPrecios);
    }

    @Override
    public int insertarMantenimientoPrecio(MantenimientoPrecios mantenimientoPrecios) {
        return ejb.insertarMantenimientoPrecio(mantenimientoPrecios);
    }

    @Override
    public ArrayList<MantenimientoPrecios> getMantenimientoPrecio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
