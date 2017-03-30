/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.FacturaPDFDomain;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NeogocioFacturas;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceFacturas implements IfaceFacturas {

    NeogocioFacturas ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NeogocioFacturas) Utilidades.getEJBRemote("ejbFacturas", NeogocioFacturas.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insert(FacturaPDFDomain factura) {
        getEjb();
        if (ejb.insert(factura) == 1) {
            if (factura.getFichero() != null) {
                int ba = 0;
                try {

                    byte[] fichero = factura.getFichero();
                    if (ejb.insertarDocumento(factura.getIdFacturaPk(), fichero) == 1) {
                        ba = 1;
                    } else {
                        ba = 0;
                    }
                } catch (SQLException ex) {

                    Logger.getLogger(ServiceFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
                return ba;
            } else {
                return 1;
            }

        } else {
            return 0;
        }

    }

    @Override
    public int delete(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(BigDecimal  idFacturaFk,BigDecimal idStatusFk) {
 ;
        try {
            getEjb();
            return ejb.update(idFacturaFk,idStatusFk);
        } catch (Exception e) {
            System.out.println("Error Service: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public FacturaPDFDomain getFacturaByIdPk(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacturaPDFDomain getFacturaByIdNumeroFac(BigDecimal idFacturaPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FacturaPDFDomain> getFacturasBy(BigDecimal idClienteFk, BigDecimal idSucursalFk, BigDecimal folioFactura, String fechaInicio, String fechaFin,BigDecimal idStatusFk) {
        getEjb();
        ArrayList<FacturaPDFDomain> listaDatos = new ArrayList<FacturaPDFDomain>();
        List<Object[]> lstObject = ejb.getFacturasBy(idClienteFk, idSucursalFk, folioFactura, fechaInicio, fechaFin,idStatusFk);
        for (Object[] obj : lstObject) {
            FacturaPDFDomain factura = new FacturaPDFDomain();
            factura.setIdFacturaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            String nombreFactura = obj[1] == null ? null : obj[1].toString();
            factura.setNumeroFactura("B-" + nombreFactura);
            factura.setFechaCertificacion(obj[2] == null ? null : (Date) obj[2]);
            factura.setIdClienteFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            factura.setIdSucursalFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            factura.setIdLlaveFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            factura.setComentarios(obj[6] == null ? null : obj[6].toString());
            factura.setFechaEmision(obj[7] == null ? null : (Date) obj[7]);
            factura.setFichero((obj[8] == null ? null : (byte[]) (obj[8])));
            factura.setIdUsuarioFk(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            factura.setNombreCliente(obj[10] == null ? null : obj[10].toString());
            factura.setNombreSucursal(obj[11] == null ? null : obj[11].toString());
            factura.setIdStatusFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            factura.setNombreArchivoTimbrado(obj[13] == null ? null : obj[13].toString());
            factura.setRfcEmisor(obj[14] == null ? null : obj[14].toString());

            byte[] cad = (byte[]) obj[15];
            String cadena = new String(cad);
            factura.setCadena(cadena);
            //factura.setRfcEmisor(cadena);

            factura.setImporte(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            factura.setDescuento(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
            factura.setIva1(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
            factura.setUuid(obj[19] == null ? null : obj[19].toString());

            //para cancelar
            factura.setCertificadoCancelar(obj[20] == null ? null : obj[20].toString());
            factura.setKeyCancelar(obj[21] == null ? null : obj[21].toString());

            factura.setRfcCliente(obj[22] == null ? null : obj[22].toString());

            if (factura.getIdStatusFk().intValue() == 1) {
                factura.setNombreEstatus("EMITIDA");
            } else {
                factura.setNombreEstatus("CANCELADA");
            }
            StreamedContent file = null;

            if (factura.getFichero() == null) {
                factura.setFile(file);
            } else {
                byte[] datos = factura.getFichero();
                InputStream stream = new ByteArrayInputStream(datos);
                file = new DefaultStreamedContent(stream, "xml", "" + factura.getRfcCliente() + "_" + factura.getNumeroFactura() + ".xml");
                factura.setFile(file);
            }

            listaDatos.add(factura);
        }
        return listaDatos;

    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public int getLastNumeroFactura() {
        getEjb();
        return ejb.getLastNumeroFactura();
    }

}
