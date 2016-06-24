package com.web.chon.service;

import com.web.chon.dominio.MantenimientoPrecios;
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
    public MantenimientoPrecios getMantenimientoPrecioById(String idSubProducto, int idEmpaque, int idSucursal) {
        try {
            MantenimientoPrecios mantenimientoPrecios = new MantenimientoPrecios();
            ejb = (NegocioMantenimientoPrecio) Utilidades.getEJBRemote("ejbMantenimientoPrecio", NegocioMantenimientoPrecio.class.getName());
            List<Object[]> object = ejb.getPrecioByIdEmpaqueAndIdProducto(idSubProducto.trim(), idEmpaque, idSucursal);
            for (Object[] obj : object) {

                mantenimientoPrecios.setIdSubproducto(obj[0] == null ? "" : obj[0].toString());
                mantenimientoPrecios.setIdTipoEmpaquePk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                mantenimientoPrecios.setPrecioMinimo(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                mantenimientoPrecios.setPrecioVenta(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                mantenimientoPrecios.setPrecioMaximo(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                mantenimientoPrecios.setIdSucursal(obj[5] == null ? null : Integer.valueOf(obj[5].toString()));
                mantenimientoPrecios.setCostoReal(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                mantenimientoPrecios.setCostoMerma(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                
                System.out.println("by id"+mantenimientoPrecios.toString());
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
    public ArrayList<MantenimientoPrecios> getMantenimientoPrecioByIdSucAndIdSubProducto(BigDecimal idSucursal, String idSubProducto) {

        try {
            ArrayList<MantenimientoPrecios> lstMantenimientoPrecios = new ArrayList<MantenimientoPrecios>();

            ejb = (NegocioMantenimientoPrecio) Utilidades.getEJBRemote("ejbMantenimientoPrecio", NegocioMantenimientoPrecio.class.getName());
            List<Object[]> object = ejb.getAllByIdSucAndIdSubProducto(idSucursal, idSubProducto);
            for (Object[] obj : object) {

                MantenimientoPrecios mantenimientoPrecios = new MantenimientoPrecios();

                mantenimientoPrecios.setIdSubproducto(obj[0] == null ? "" : obj[0].toString());
                mantenimientoPrecios.setNombreSubProducto(obj[1] == null ? null : obj[1].toString());
                mantenimientoPrecios.setPrecioMinimo(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                mantenimientoPrecios.setPrecioVenta(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
                mantenimientoPrecios.setPrecioMaximo(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                mantenimientoPrecios.setExistenciaKilos(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                mantenimientoPrecios.setCostoReal(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                mantenimientoPrecios.setCostoMerma(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                //Se suma el 30 % para sacar el precio sugerido
                mantenimientoPrecios.setPrecioSugerido(mantenimientoPrecios.getCostoReal().add(mantenimientoPrecios.getCostoReal().multiply(new BigDecimal(0.3)))) ;

                System.out.println("mantenimiento data " + mantenimientoPrecios.toString());
                lstMantenimientoPrecios.add(mantenimientoPrecios);
            }

            return lstMantenimientoPrecios;
        } catch (Exception ex) {
            Logger.getLogger(ServiceMantenimientoPrecio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public ArrayList<MantenimientoPrecios> getMantenimientoPrecio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
