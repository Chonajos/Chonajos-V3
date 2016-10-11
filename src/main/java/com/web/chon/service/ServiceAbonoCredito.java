package com.web.chon.service;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.negocio.NegocioAbonoCredito;
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
 * @author Juan de la Cruz
 */
@Service
public class ServiceAbonoCredito implements IfaceAbonoCredito {

    NegocioAbonoCredito ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioAbonoCredito) Utilidades.getEJBRemote("ejbAbonoCredito", NegocioAbonoCredito.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int update(AbonoCredito abonoCredito) {
        getEjb();
        return ejb.update(abonoCredito);
    }

    @Override
    public int insert(AbonoCredito abonoCredito) {
        getEjb();
        return ejb.insert(abonoCredito);
    }

    @Override
    public int delete(BigDecimal idAbonoCredito) {
        getEjb();
        return ejb.delete(idAbonoCredito);
    }

    @Override
    public ArrayList<AbonoCredito> getAll() {
        getEjb();
        ArrayList<AbonoCredito> lstAbonoCredito = new ArrayList<AbonoCredito>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            AbonoCredito abonoCredito = new AbonoCredito();

            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));

            lstAbonoCredito.add(abonoCredito);
        }

        return lstAbonoCredito;

    }

    @Override
    public AbonoCredito getById(BigDecimal idAbonoCredito) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        AbonoCredito abonoCredito = new AbonoCredito();
        for (Object[] object : lstObject) {

            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));

        }

        return abonoCredito;
    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceAbonoCredito.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public ArrayList<AbonoCredito> getByIdCredito(BigDecimal idCreditoFk) {
       getEjb();
        ArrayList<AbonoCredito> lstAbonoCredito = new ArrayList<AbonoCredito>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getByIdCredito(idCreditoFk);
        for (Object[] object : lstObject) 
        {
            AbonoCredito abonoCredito = new AbonoCredito();
            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));
            abonoCredito.setIdtipoAbonoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            abonoCredito.setEstatusAbono(object[6] == null ? null : new BigDecimal(object[6].toString()));
            abonoCredito.setNumeroCheque(object[7] == null ? null : new BigDecimal(object[7].toString()));
            abonoCredito.setLibrador(object[8] == null ? "" : object[8].toString());
            abonoCredito.setFechaCobro(object[9] == null ? null : (Date) object[9]);
            abonoCredito.setBanco(object[10] == null ? "" : object[10].toString());
            abonoCredito.setFactura(object[11] == null ? "" : object[11].toString());
            abonoCredito.setReferencia(object[12] == null ? "" : object[12].toString());
            abonoCredito.setConcepto(object[13] == null ? "" : object[13].toString());
            abonoCredito.setFechaTransferencia(object[14] == null ? null : (Date) object[14]);
            
            lstAbonoCredito.add(abonoCredito);
        }
        return lstAbonoCredito;
    }

    @Override
    public ArrayList<AbonoCredito> getChequesPendientes(Date fechaInicio, Date fechaFin,BigDecimal idSucursal,BigDecimal idClienteFk,BigDecimal filtro,BigDecimal filtroStatus) {
       getEjb();
        ArrayList<AbonoCredito> lstAbonoCredito = new ArrayList<AbonoCredito>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getChequesPendientes(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin),idSucursal,idClienteFk,filtro,filtroStatus);
        for (Object[] object : lstObject) 
        {
            AbonoCredito abonoCredito = new AbonoCredito();
            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));
            abonoCredito.setIdtipoAbonoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            abonoCredito.setEstatusAbono(object[6] == null ? null : new BigDecimal(object[6].toString()));
            abonoCredito.setNumeroCheque(object[7] == null ? null : new BigDecimal(object[7].toString()));
            abonoCredito.setLibrador(object[8] == null ? "" : object[8].toString());
            abonoCredito.setFechaCobro(object[9] == null ? null : (Date) object[9]);
            abonoCredito.setBanco(object[10] == null ? "" : object[10].toString());
            abonoCredito.setFactura(object[11] == null ? "" : object[11].toString());
            abonoCredito.setReferencia(object[12] == null ? "" : object[12].toString());
            abonoCredito.setConcepto(object[13] == null ? "" : object[13].toString());
            abonoCredito.setFechaTransferencia(object[14] == null ? null : (Date) object[14]);
            abonoCredito.setIdDocumentoPk(object[15] == null ? null : new BigDecimal(object[15].toString()));
            abonoCredito.setNombreCliente(object[16] == null ? "" : object[16].toString());
            abonoCredito.setNombreStatus(object[17] == null ? "" : object[17].toString());
            abonoCredito.setIdStatusDocumentoFk(object[18] == null ? null : new BigDecimal(object[18].toString()));
            abonoCredito.setIdClienteFk(object[19] == null ? null : new BigDecimal(object[19].toString()));
            lstAbonoCredito.add(abonoCredito);
        }
        return lstAbonoCredito;
    
    }

    @Override
    public AbonoCredito getByIdVentaMayoreoFk(BigDecimal idVentaMayoreoFk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getByIdVentaMayoreoFk(idVentaMayoreoFk);
        AbonoCredito abonoCredito = new AbonoCredito();
        for (Object[] object : lstObject) {

            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));

        }

        return abonoCredito;
    }

    @Override
    public AbonoCredito getByIdVentaMenudeoFk(BigDecimal idVentaMenudeoFk) {
       getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getByIdVentaMenudeoFk(idVentaMenudeoFk);
        AbonoCredito abonoCredito = new AbonoCredito();
        for (Object[] object : lstObject) {

            abonoCredito.setIdAbonoCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            abonoCredito.setIdCreditoFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            abonoCredito.setMontoAbono(object[2] == null ? null : new BigDecimal(object[2].toString()));
            abonoCredito.setFechaAbono(object[3] == null ? null : (Date) object[3]);
            abonoCredito.setIdUsuarioFk(object[4] == null ? null : new BigDecimal(object[4].toString()));

        }

        return abonoCredito;
    
    }
    

}
