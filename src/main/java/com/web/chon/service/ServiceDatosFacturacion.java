package com.web.chon.service;

import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.negocio.NegocioDatosFacturacion;
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
public class ServiceDatosFacturacion implements IfaceDatosFacturacion {

    NegocioDatosFacturacion ejb;

    private void getEjb() {
        try {
            if (ejb == null) {
                ejb = (NegocioDatosFacturacion) Utilidades.getEJBRemote("ejbDatosFacturacion", NegocioDatosFacturacion.class.getName());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServiceDatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DatosFacturacion getDatosFacturacionByIdCliente(BigDecimal idClienteFk) {

        try {
            DatosFacturacion df = new DatosFacturacion();
            getEjb();
            List<Object[]> object = ejb.getByIdCliente(idClienteFk);
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
                if (df.getCodigoPostal().length() < 5) {
                    df.setCodigoPostal("0" + df.getCodigoPostal());
                }
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
        ArrayList<DatosFacturacion> listaDatos = new ArrayList<DatosFacturacion>();
        List<Object[]> lstObject = ejb.getByIdSucursal(idSucursalFk);
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
            df.setRuta_llave_privada_cancel(obj[17] == null ? null : obj[17].toString());
            df.setRuta_certificado_cancel(obj[18] == null ? null : obj[18].toString());
            df.setClavePublica(obj[19] == null ? null : obj[19].toString());
            df.setCodigoPostal(obj[20] == null ? null : obj[20].toString());
            if (df.getCodigoPostal().length() < 5) {
                df.setCodigoPostal("0" + df.getCodigoPostal());
            }
            df.setColonia(obj[21] == null ? null : obj[21].toString());
            df.setMunicipio(obj[22] == null ? null : obj[22].toString());
            df.setEstado(obj[23] == null ? null : obj[23].toString());
            listaDatos.add(df);
        }
        return listaDatos;
    }

    @Override
    public int deleteDatosFacturacion(BigDecimal idDatosFacturacion) {
        getEjb();

        return ejb.delete(idDatosFacturacion);
    }

    @Override
    public int insertarDatosFacturacion(DatosFacturacion df) {
        getEjb();

        return ejb.insert(df);
    }

    @Override
    public int updateDatosFacturacion(DatosFacturacion df) {
        getEjb();

        return ejb.update(df);
    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<DatosFacturacion> getByIdSucursal(BigDecimal idSucursal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<DatosFacturacion> getAll() {
        getEjb();
        ArrayList<DatosFacturacion> lstDatosFacturacion = new ArrayList<DatosFacturacion>();
        List<Object[]> lstObject = ejb.getAll();
        for (Object[] obj : lstObject) {
            DatosFacturacion df = new DatosFacturacion();

            df.setIdDatosFacturacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            df.setIdSucursalFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            df.setRazonSocial(obj[2] == null ? null : obj[2].toString());
            df.setRfc(obj[3] == null ? null : obj[3].toString());
            df.setCalle(obj[4] == null ? null : obj[4].toString());
            df.setNumInt(obj[5] == null ? null : obj[5].toString());
            df.setNumExt(obj[6] == null ? null : obj[6].toString());
            df.setPais(obj[7] == null ? null : obj[7].toString());
            df.setLocalidad(obj[8] == null ? null : obj[8].toString());
            df.setIdCodigoPostalFk(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            df.setTelefono(obj[10] == null ? null : obj[10].toString());
            df.setCorreo(obj[11] == null ? null : obj[11].toString());
            df.setRegimen(obj[12] == null ? null : obj[12].toString());
            df.setField(obj[13] == null ? null : obj[13].toString());
            df.setRuta_llave_privada(obj[14] == null ? null : obj[14].toString());
            df.setRuta_certificado(obj[15] == null ? null : obj[15].toString());
            df.setRuta_llave_privada_cancel(obj[16] == null ? null : obj[16].toString());
            df.setRuta_certificado_cancel(obj[17] == null ? null : obj[17].toString());
            df.setClavePublica(obj[18] == null ? null : obj[18].toString());
            df.setCodigoPostal(obj[19] == null ? null : obj[19].toString());

            if (df.getCodigoPostal().length() < 5) {
                df.setCodigoPostal("0" + df.getCodigoPostal());
            }

            df.setColonia(obj[20] == null ? null : obj[20].toString());
            df.setMunicipio(obj[21] == null ? null : obj[21].toString());
            df.setEstado(obj[22] == null ? null : obj[22].toString());
            df.setIdMunicipio(obj[23] == null ? null : new BigDecimal(obj[23].toString()));
            df.setIdEntidad(obj[24] == null ? null : new BigDecimal(obj[24].toString()));

            lstDatosFacturacion.add(df);
        }

        return lstDatosFacturacion;
    }

    @Override
    public DatosFacturacion getByRfc(String rfc) {
        getEjb();
        List<Object[]> lstObject = ejb.getByRfc(rfc);
        DatosFacturacion df = new DatosFacturacion();
        for (Object[] obj : lstObject) {

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
            df.setRuta_llave_privada_cancel(obj[17] == null ? null : obj[17].toString());
            df.setRuta_certificado_cancel(obj[18] == null ? null : obj[18].toString());
            df.setClavePublica(obj[19] == null ? null : obj[19].toString());
            df.setCodigoPostal(obj[20] == null ? null : obj[20].toString());
            if (df.getCodigoPostal().length() < 5) {
                df.setCodigoPostal("0" + df.getCodigoPostal());
            }
            df.setColonia(obj[21] == null ? null : obj[21].toString());
            df.setMunicipio(obj[22] == null ? null : obj[22].toString());
            df.setEstado(obj[23] == null ? null : obj[23].toString());

        }
        return df;
    }

}
