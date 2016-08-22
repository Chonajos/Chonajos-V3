/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.AbonoDocumentos;
import com.web.chon.negocio.NegocioAbonoDocumento;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceAbonoDocumentos implements IfaceAbonoDocumentos{

    NegocioAbonoDocumento ejb;
    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioAbonoDocumento) Utilidades.getEJBRemote("ejbAbonoDocumento", NegocioAbonoDocumento.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public int update(AbonoDocumentos abonoDocumentos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal idAbonoDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbonoDocumentos> getById(BigDecimal idAbonoDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbonoDocumentos> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNextVal() {
       getEjb();
       return ejb.getNextVal();
    }

    @Override
    public int insert(AbonoDocumentos abonoDocumentos) {
        getEjb();
       return ejb.insert(abonoDocumentos);
    }

    @Override
    public ArrayList<AbonoDocumentos> getCheques(Date fechaInicio, Date fechaFin, BigDecimal idSucursalFk, BigDecimal idClienteFk, BigDecimal filtro, BigDecimal filtroStatus) {
        getEjb();
        ArrayList<AbonoDocumentos> lstAbonoCredito = new ArrayList<AbonoDocumentos>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getChequesPendientes(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin),idSucursalFk,idClienteFk,filtro,filtroStatus);
        for (Object[] object : lstObject) 
        {
            AbonoDocumentos abonoDocumento = new AbonoDocumentos();
            abonoDocumento.setIdAbonoDocumentoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoDocumento.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoDocumento.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoDocumento.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));
            abonoDocumento.setIdTipoAbonoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            abonoDocumento.setEstatus(object[6] == null ? null : new BigDecimal(object[6].toString()));
            abonoDocumento.setNumeroCheque(object[7] == null ? null : new BigDecimal(object[7].toString()));
            abonoDocumento.setLibrador(object[8] == null ? "" : object[8].toString());
            abonoDocumento.setFechaCobro(object[9] == null ? null : (Date) object[9]);
            abonoDocumento.setBanco(object[10] == null ? "" : object[10].toString());
            abonoDocumento.setNumeroFactura(object[11] == null ? "" : object[11].toString());
            abonoDocumento.setReferencia(object[7] == null ? null : new BigDecimal(object[7].toString()));
            abonoDocumento.setConcepto(object[13] == null ? "" : object[13].toString());
            abonoDocumento.setFechaTransferencia(object[14] == null ? null : (Date) object[14]);
            abonoDocumento.setIdDocumentoFk(object[15] == null ? null : new BigDecimal(object[15].toString()));
            abonoDocumento.setNombreCliente(object[16] == null ? "" : object[16].toString());
            abonoDocumento.setNombreStatus(object[17] == null ? "" : object[17].toString());
            abonoDocumento.setIdStatusDocumento(object[18] == null ? null : new BigDecimal(object[18].toString()));
            lstAbonoCredito.add(abonoDocumento);
        }
        return lstAbonoCredito;
    
    }

    @Override
    public BigDecimal getTotalAbonadoByIdDocumento(BigDecimal idDocumentoFk) {
        getEjb();
        return ejb.getTotalAbonadoByIdDocumento(idDocumentoFk);
    
    }
    
}
