package com.web.chon.service;

import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.negocio.NegocioCredito;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.util.TiempoUtil;
import java.math.RoundingMode;

/**
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceCredito implements IfaceCredito {

    NegocioCredito ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCredito) Utilidades.getEJBRemote("ejbCredito", NegocioCredito.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Credito> getAll() {
        getEjb();
        ArrayList<Credito> lstCredito = new ArrayList<Credito>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        for (Object[] object : lstObject) {
            Credito credito = new Credito();

            credito.setIdCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            credito.setIdClienteFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            credito.setIdVentaMenudeo(object[2] == null ? null : new BigDecimal(object[2].toString()));
            credito.setIdVentaMayoreo(object[3] == null ? null : new BigDecimal(object[3].toString()));
            credito.setIdUsuarioCredito(object[4] == null ? null : new BigDecimal(object[4].toString()));
            credito.setIdTipoCreditoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setEstatusCredito(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setNumeroPromesaPago(object[7] == null ? null : new BigDecimal(object[7].toString()));
            credito.setFechaInicioCredito(object[8] == null ? null : (Date) object[8]);
            credito.setFechaFinCredito(object[9] == null ? null : (Date) object[9]);
            credito.setFechaPromesaPago(object[10] == null ? null : (Date) object[10]);
            credito.setTazaInteres(object[11] == null ? null : new BigDecimal(object[11].toString()));
            credito.setTazaInteres(object[12] == null ? null : new BigDecimal(object[12].toString()));

            lstCredito.add(credito);
        }

        return lstCredito;
    }

    @Override
    public Credito getById(BigDecimal idCredito) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getAll();
        Credito credito = new Credito();
        for (Object[] object : lstObject) {
            

            credito.setIdCreditoPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            credito.setIdClienteFk(object[1] == null ? null : new BigDecimal(object[1].toString()));
            credito.setIdVentaMenudeo(object[2] == null ? null : new BigDecimal(object[2].toString()));
            credito.setIdVentaMayoreo(object[3] == null ? null : new BigDecimal(object[3].toString()));
            credito.setIdUsuarioCredito(object[4] == null ? null : new BigDecimal(object[4].toString()));
            credito.setIdTipoCreditoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setEstatusCredito(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setNumeroPromesaPago(object[7] == null ? null : new BigDecimal(object[7].toString()));
            credito.setFechaInicioCredito(object[8] == null ? null : (Date) object[8]);
            credito.setFechaFinCredito(object[9] == null ? null : (Date) object[9]);
            credito.setFechaPromesaPago(object[10] == null ? null : (Date) object[10]);
            credito.setTazaInteres(object[11] == null ? null : new BigDecimal(object[11].toString()));
            credito.setTazaInteres(object[12] == null ? null : new BigDecimal(object[12].toString()));
        }

        return credito;
    }

    @Override
    public int delete(BigDecimal idCredito) {
        getEjb();
        return ejb.delete(idCredito);
    }

    @Override
    public int update(Credito credito) {
        getEjb();
        return ejb.update(credito);
    }

    @Override
    public int insert(Credito credito) {
        getEjb();
        return ejb.insert(credito);
    }

    @Override
    public ArrayList<SaldosDeudas> getCreditosActivos(BigDecimal idCliente) {
        getEjb();
        ArrayList<SaldosDeudas> lstCreditos = new ArrayList<SaldosDeudas>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCreditosActivos(idCliente);
        for (Object[] object : lstObject) {
            SaldosDeudas credito = new SaldosDeudas();

            credito.setFolioCredito(object[0] == null ? null : new BigDecimal(object[0].toString()));
            credito.setNombreStatus(object[1] == null ? null : object[1].toString());
            credito.setFechaVenta(object[2] == null ? null : (Date) object[2]);
            credito.setFolioVenta(object[3] == null ? null : new BigDecimal(object[3].toString()));
            credito.setPeriodo(object[4] == null ? null : object[4].toString());
            credito.setPlazo(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setSaldoTotal(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setTotalAbonado(object[7] == null ? null : new BigDecimal(object[7].toString()));
            credito.setTipoCredito(object[8] == null ? null : new BigDecimal(object[8].toString()));
            credito.setIdEstatus(object[9] == null ? null : new BigDecimal(object[9].toString()));
            Date hoy = new Date();
            Date temporal = credito.getFechaVenta();
            ArrayList<Date> fechasDePago  = new ArrayList<Date>();
            for (int i = 0; i<credito.getPlazo().intValue();i++)
            {
                Date auxiliar = new Date(TiempoUtil.getFechaDDMMYYYY(TiempoUtil.sumarRestarDias(temporal, credito.getTipoCredito().intValue())));
                fechasDePago.add(auxiliar);
                temporal = auxiliar;
                System.out.println("Fecha: N°: "+i + " ==== " + temporal);
            }
            for(Date item : fechasDePago)
            {
                Date temp = TiempoUtil.
            }
            
            
            credito.setMontoAbonar(credito.getSaldoTotal().divide(credito.getPlazo(),2,RoundingMode.HALF_UP));
            
//            credito.setNumeroPromesaPago(object[7] == null ? null : new BigDecimal(object[7].toString()));
//            credito.setFechaInicioCredito(object[8] == null ? null : (Date) object[8]);
//            credito.setFechaFinCredito(object[9] == null ? null : (Date) object[9]);
//            credito.setFechaPromesaPago(object[10] == null ? null : (Date) object[10]);
//            credito.setTazaInteres(object[11] == null ? null : new BigDecimal(object[11].toString()));
//            credito.setTazaInteres(object[12] == null ? null : new BigDecimal(object[12].toString()));

            lstCreditos.add(credito);
        }

        return lstCreditos;
        
    }

    @Override
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus) {
       getEjb();
        return ejb.updateStatus(idCreditoPk,estatus);
    
    }

}
