/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.negocio.NegocioFacturacion;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceFacturacion implements IfaceFacturacion {

    NegocioFacturacion ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioFacturacion) Utilidades.getEJBRemote("ejbDatosFacturacion", NegocioFacturacion.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();

    }

    @Override
    public DatosFacturacion getDatosFacturacionByIdCliente(BigDecimal idClienteFk) {

        try {
            DatosFacturacion df = new DatosFacturacion();
            getEjb();
            List<Object[]> object = ejb.getDatosFacturacionByIdCliente(idClienteFk);
            for (Object[] obj : object) {
                df.setIdDatosFacturacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                df.setIdClienteFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                df.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                df.setRazonSocial(obj[3] == null ? null : obj[3].toString());
                df.setRfc(obj[4] == null ? null : obj[4].toString());
                df.setCalle(obj[5] == null ? null : obj[5].toString());
                df.setNumInt(obj[6] == null ? null : obj[6].toString());
                df.setNumExt(obj[7] == null ? null : obj[7].toString());
                df.setPais(obj[8] == null ? null : obj[8].toString());
                df.setLocalidad(obj[9] == null ? null : obj[9].toString());
                df.setIdCodigoPostalFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                df.setTelefono(obj[11] == null ? null : obj[11].toString());
                df.setCorreo(obj[12] == null ? null : obj[12].toString());
                df.setRegimen(obj[13] == null ? null : obj[13].toString());
                df.setField(obj[14] == null ? null : obj[14].toString());
                df.setRuta_llave_privada(obj[15] == null ? null : obj[15].toString());
                df.setRuta_certificado(obj[16] == null ? null : obj[16].toString());
                df.setRuta_llave_privada(obj[17] == null ? null : obj[17].toString());
                df.setRuta_certificado_cancel(obj[18] == null ? null : obj[18].toString());
                df.setNombre(obj[19] == null ? null : obj[19].toString());
                df.setCodigoPostal(obj[20] == null ? null : obj[20].toString());
                df.setColonia(obj[21] == null ? null : obj[21].toString());
                df.setMunicipio(obj[22] == null ? null : obj[22].toString());
                df.setEstado(obj[23] == null ? null : obj[23].toString());
            }

            return df;
        } catch (Exception ex) {
            Logger.getLogger(ServiceOperacionesCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<DatosFacturacion> getDatosFacturacionByIdSucursal(BigDecimal idSucursalFk) {
        getEjb();
        int i = 1;
        ArrayList<DatosFacturacion> listaDatos = new ArrayList<DatosFacturacion>();
        List<Object[]> lstObject = ejb.getDatosFacturacionByIdSucursal(idSucursalFk);
        for (Object[] obj : lstObject) {
            DatosFacturacion df = new DatosFacturacion();
            df.setIdDatosFacturacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            df.setIdClienteFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            df.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            df.setRazonSocial(obj[3] == null ? null : obj[3].toString());
            df.setRfc(obj[4] == null ? null : obj[4].toString());
            df.setCalle(obj[5] == null ? null : obj[5].toString());
            df.setNumInt(obj[6] == null ? null : obj[6].toString());
            df.setNumExt(obj[7] == null ? null : obj[7].toString());
            df.setPais(obj[8] == null ? null : obj[8].toString());
            df.setLocalidad(obj[9] == null ? null : obj[9].toString());
            df.setIdCodigoPostalFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            df.setTelefono(obj[11] == null ? null : obj[11].toString());
            df.setCorreo(obj[12] == null ? null : obj[12].toString());
            df.setRegimen(obj[13] == null ? null : obj[13].toString());
            df.setField(obj[14] == null ? null : obj[14].toString());
            df.setRuta_llave_privada(obj[15] == null ? null : obj[15].toString());
            df.setRuta_certificado(obj[16] == null ? null : obj[16].toString());
            df.setRuta_llave_privada(obj[17] == null ? null : obj[17].toString());
            df.setRuta_certificado_cancel(obj[18] == null ? null : obj[18].toString());
            listaDatos.add(df);
        }
        return listaDatos;
    }

    @Override
    public int deleteDatosFacturacion(String idProducto) {
        getEjb();
        return ejb.deleteDatosFacturacion(idProducto);
    }

    @Override
    public int insertarDatosFacturacion(DatosFacturacion df) {
        getEjb();
        return ejb.insertarDatosFacturacion(df);
    }

    @Override
    public int updateDatosFacturacion(DatosFacturacion df) {
        getEjb();
        return ejb.updateDatosFacturacion(df);
    }

}
