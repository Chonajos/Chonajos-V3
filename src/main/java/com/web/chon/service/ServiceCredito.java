package com.web.chon.service;

import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.negocio.NegocioCredito;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.util.TiempoUtil;
import java.math.MathContext;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceCredito implements IfaceCredito {

    @Autowired
    private PlataformaSecurityContext context;
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
//            credito.setIdTipoCreditoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setEstatusCredito(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setNumeroPromesaPago(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setFechaInicioCredito(object[7] == null ? null : (Date) object[7]);
            credito.setFechaFinCredito(object[8] == null ? null : (Date) object[8]);
            credito.setFechaPromesaPago(object[9] == null ? null : (Date) object[9]);
            credito.setTazaInteres(object[10] == null ? null : new BigDecimal(object[10].toString()));
            credito.setTazaInteres(object[11] == null ? null : new BigDecimal(object[11].toString()));

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
//            credito.setIdTipoCreditoFk(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setEstatusCredito(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setNumeroPromesaPago(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setFechaInicioCredito(object[7] == null ? null : (Date) object[7]);
            credito.setFechaFinCredito(object[8] == null ? null : (Date) object[8]);
            credito.setFechaPromesaPago(object[9] == null ? null : (Date) object[9]);
            credito.setTazaInteres(object[10] == null ? null : new BigDecimal(object[10].toString()));
            credito.setTazaInteres(object[11] == null ? null : new BigDecimal(object[11].toString()));
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
            credito.setPlazo(object[3] == null ? null : new BigDecimal(object[3].toString()));
            credito.setSaldoTotal(object[4] == null ? null : new BigDecimal(object[4].toString()));
            credito.setTotalAbonado(object[5] == null ? null : new BigDecimal(object[5].toString()));
            credito.setIdEstatus(object[6] == null ? null : new BigDecimal(object[6].toString()));
            credito.setSaldoACuenta(object[7] == null ? null : new BigDecimal(object[7].toString()));
            credito.setStatusAcuenta(object[8] == null ? null : new BigDecimal(object[8].toString()));

            Date hoy = context.getFechaSistema();
            Date fechaVenta = credito.getFechaVenta();
//            Date temporal = fechaVenta;
//
//            while (temporal.compareTo(hoy) == -1) {
//                temporal = TiempoUtil.fechaTextoDiaMesAnio(TiempoUtil.getFechaDDMMYYYY(TiempoUtil.sumarRestarDias(temporal, 7)));
//            }
//            
//            System.out.println("fecha de siguiente pago ---------------- " + temporal);

            BigDecimal cantidadPorFecha = new BigDecimal(0);
            credito.setMontoAbonar(credito.getSaldoTotal().divide(credito.getPlazo(), 2, RoundingMode.HALF_UP));
            //int contador = 0;
            int var = credito.getNumeroPagos().intValue();
            for (int i = 0; i <= var; i++) {
                Date auxiliar = fechaVenta;
                if (auxiliar.compareTo(hoy) == -1) //si auxiliar es menor entra
                {
                    cantidadPorFecha = cantidadPorFecha.add(credito.getMontoAbonar(), MathContext.UNLIMITED);
                    System.out.println("Cantidad por Fechar: "+cantidadPorFecha);
                    System.out.println("credito total abonado: "+credito.getTotalAbonado());
                    System.out.println("Resta: " +cantidadPorFecha.subtract(credito.getTotalAbonado(), MathContext.UNLIMITED));

                    auxiliar = TiempoUtil.fechaTextoDiaMesAnio(TiempoUtil.getFechaDDMMYYYY(TiempoUtil.sumarRestarDias(fechaVenta, credito.getPlazo().intValue())));
                    BigDecimal plazoTemp = (cantidadPorFecha.subtract(credito.getTotalAbonado(), MathContext.UNLIMITED)).divide(credito.getMontoAbonar(), 2, RoundingMode.UP);
    
                    double t= Double.parseDouble(((plazoTemp).subtract(new BigDecimal(1), MathContext.UNLIMITED)).toString());
                    credito.setPeriodosAtraso(new BigDecimal(Math.ceil(t)));
                    credito.setFechaProximaAbonar(auxiliar);
                    credito.setMinimoPago((cantidadPorFecha.subtract(credito.getTotalAbonado(), MathContext.UNLIMITED)));
                    credito.setSaldoAtrasado(credito.getMinimoPago().subtract(credito.getMontoAbonar(), MathContext.UNLIMITED));
                }
                System.out.println("========================================================================");

                fechaVenta = auxiliar;
            }

            credito.setSaldoLiquidar(credito.getSaldoTotal().subtract(credito.getTotalAbonado(), MathContext.UNLIMITED));
            lstCreditos.add(credito);
        }

        return lstCreditos;

    }

    @Override
    public int updateStatus(BigDecimal idCreditoPk, BigDecimal estatus) {
        getEjb();
        return ejb.updateStatus(idCreditoPk, estatus);

    }

    @Override
    public int updateACuenta(Credito credito) {
        getEjb();
        return ejb.updateACuenta(credito);

    }

}
