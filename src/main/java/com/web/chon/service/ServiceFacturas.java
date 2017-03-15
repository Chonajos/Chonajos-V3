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
import com.web.chon.util.JsfUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(FacturaPDFDomain factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ArrayList<FacturaPDFDomain> getFacturasBy(BigDecimal idClienteFk, BigDecimal idSucursalFk, BigDecimal idFolioVentaFk, String fechaInicio, String fechaFin) {
        getEjb();
        ArrayList<FacturaPDFDomain> listaDatos = new ArrayList<FacturaPDFDomain>();
        List<Object[]> lstObject = ejb.getFacturasBy(idClienteFk, idSucursalFk, idFolioVentaFk, fechaInicio, fechaFin);
        for (Object[] obj : lstObject) {
            FacturaPDFDomain factura = new FacturaPDFDomain();
            factura.setIdFacturaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            factura.setNumeroFactura(obj[1] == null ? null : obj[1].toString());
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
                file = new DefaultStreamedContent(stream, "xml", "archivo.xml");
                factura.setFile(file);
            }

            listaDatos.add(factura);
        }
        return listaDatos;

    }

    @Override
    public int getNextVal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
