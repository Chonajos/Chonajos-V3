/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Documento;
import com.web.chon.negocio.NegocioDocumentos;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceDocumentos implements IfaceDocumentos {

    NegocioDocumentos ejb;
     @Autowired  IfaceAbonoDocumentos ifaceAbonoDocumentos;
    
    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioDocumentos) Utilidades.getEJBRemote("ejbDocumentos", NegocioDocumentos.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertarDocumento(Documento documento) {
       getEjb();
       return ejb.insertarDocumento(documento);
    }
    
    @Override
    public int getNextVal() {
        getEjb();
        
        return ejb.nextVal();
    }

    @Override
    public Documento getDocumentoByIdDocumentoPk(BigDecimal idDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Documento getDocumentoByIdAbonoFk(BigDecimal idAbonoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Documento> getDocumentosByIdClienteFk(BigDecimal idClienteFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Documento> getDocumentosByIdStatusFk(BigDecimal idStatusFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateDocumentoById(Documento dc) {
       getEjb();
       return ejb.updateDocumento(dc);
    
    }

    @Override
    public ArrayList<Documento> getDocumentos(Date fechaInicio, Date fechaFin, BigDecimal idSucursalFk, BigDecimal idClienteFk, BigDecimal idFormaPagoFk, BigDecimal idStatusFk,BigDecimal filtroFecha) {
        getEjb();
        ArrayList<Documento> lstDocumentos = new ArrayList<Documento>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getDocumentos(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin),idSucursalFk,idClienteFk,idFormaPagoFk,idStatusFk,filtroFecha);
        for (Object[] object : lstObject) 
        {
            Documento documento = new Documento();
            documento.setIdDocumentoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            documento.setIdTipoDocumento(object[1] == null ? null : new BigDecimal(object[1].toString()));
            //documento.setIdAbonoFk(object[2] == null ? null : new BigDecimal(object[2].toString()));
            documento.setIdClienteFk(object[2] == null ? null : new BigDecimal(object[2].toString()));
            documento.setIdStatusFk(object[3] == null ? null : new BigDecimal(object[3].toString()));
            documento.setComentario(object[4] == null ? "" : object[4].toString());
            documento.setMonto(object[5] == null ? null : new BigDecimal(object[5].toString()));
            //documento.setIdAbonoDocumentoFk(object[7] == null ? null : new BigDecimal(object[7].toString()));
            documento.setNumeroCheque(object[6] == null ? null : new BigDecimal(object[6].toString()));
            documento.setFactura(object[7] == null ? "" : object[7].toString());
            documento.setBanco(object[8] == null ? "" : object[8].toString());
            documento.setLibrador(object[9] == null ? "" : object[9].toString());
            documento.setFechaCobro(object[10] == null ? null : (Date) object[10]);
            documento.setIdFormaCobroFk(object[11] == null ? null : new BigDecimal(object[11].toString()));
            documento.setIdDocumentoPadreFk(object[12] == null ? null : new BigDecimal(object[12].toString()));
            documento.setNombreCliente(object[13] == null ? "" : object[13].toString());
            documento.setNombreFormaCobro(object[14] == null ? "" : object[14].toString());
            documento.setNombreStatus(object[15] == null ? "" : object[15].toString());
            documento.setTotalAbonado(ifaceAbonoDocumentos.getTotalAbonadoByIdDocumento(documento.getIdDocumentoPk()));
            lstDocumentos.add(documento);
        }
        return lstDocumentos;
    
    
    }

    @Override
    public int cambiarFormaPago(Documento d) {
        getEjb();
       return ejb.cambiarFormaPago(d);
    
    }

}
