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
        try {
            getEjb();
            int idCredito = ejb.nextVal();

            if (ejb.insert(credito, idCredito) == 0) {
                return 0;
            } else {
                return idCredito;
            }
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage().toString());
            return 0;
        }
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
            credito.setNumeroPagos(object[9] == null ? null : new BigDecimal(object[9].toString()));
            credito.setChequesPorCobrar(object[10] == null ? null : new BigDecimal(object[10].toString()));
            Date hoy = context.getFechaSistema();
            hoy.setHours(0);
            hoy.setMinutes(0);
            hoy.setSeconds(0);
            Date fechaVenta = credito.getFechaVenta();
            BigDecimal cantidadPorFecha = new BigDecimal(0);
            credito.setMontoAbonar(credito.getSaldoTotal().divide(credito.getNumeroPagos(), 2, RoundingMode.HALF_UP));
            //int contador = 0;
            int var = credito.getNumeroPagos().intValue();
            BigDecimal creditoAtrasado = new BigDecimal(0);
            int numeroAsumar = credito.getPlazo().intValue() / credito.getNumeroPagos().intValue();
            ArrayList<Date> fechas_pagos = new ArrayList<Date>();
            ArrayList<BigDecimal> pagos_por_fecha = new ArrayList<BigDecimal>();
            for (int i = 0; i < var; i++) {
                Date auxiliar = fechaVenta;
                auxiliar = TiempoUtil.fechaTextoDiaMesAnio(TiempoUtil.getFechaDDMMYYYY(TiempoUtil.sumarRestarDias(fechaVenta, numeroAsumar)));
                fechas_pagos.add(auxiliar);
                pagos_por_fecha.add(credito.getMontoAbonar().multiply(new BigDecimal(i + 1), MathContext.UNLIMITED));
                fechaVenta = auxiliar;
            }
            for (int j = 0; j < fechas_pagos.size(); j++) {
                System.out.println("Fecha: " + fechas_pagos.get(j) + "   Cantidad: " + pagos_por_fecha.get(j).toString());
            }
            int contador_periodos_atrasados = 0;
            BigDecimal deudas = new BigDecimal(0);
            System.out.println("=============================================================");
            boolean fechaPagoMayoraHoy = false;
            for (int x = 0; x < fechas_pagos.size(); x++) 
            {
                if (hoy.compareTo(fechas_pagos.get(x)) == 1) 
                {//si hoy es menor que la fecha 
                    
                    System.out.println("Hoy: " + hoy);
                    System.out.println("Fecha Prox: " + fechas_pagos.get(x));
                    //mientras hoy sea mayor primer fecha de pago entonces hacer calculos
                    System.out.println("Total Abonado: " + credito.getTotalAbonado() + "  Pago por Fecha: " + pagos_por_fecha.get(x));
                    if (credito.getTotalAbonado().compareTo(pagos_por_fecha.get(x)) == -1) 
                    {
                        //si lo abonado es menor a la cantidad de esa fecha incrementar el contador de periodos atrasados
                        contador_periodos_atrasados = contador_periodos_atrasados + 1;
                        deudas = new BigDecimal(Math.abs(Double.parseDouble((credito.getTotalAbonado().subtract(pagos_por_fecha.get(x), MathContext.UNLIMITED)).toString())));
                        credito.setFechaProximaAbonar(fechas_pagos.get(x));
                        credito.setStatusFechaProxima(new BigDecimal(2));

                    }
                } else 
                {
                    
                    if(x==0 && fechaPagoMayoraHoy==false)
                    {
                        fechaPagoMayoraHoy = true;
                        credito.setFechaProximaAbonar(fechas_pagos.get(0));
                        credito.setStatusFechaProxima(new BigDecimal(1));
                    }else 
                        if(fechaPagoMayoraHoy==false)
                    {
                        credito.setStatusFechaProxima(new BigDecimal(2));
                        credito.setFechaProximaAbonar(fechas_pagos.get(x));
                    }
                    
                    
                }

                credito.setSaldoLiquidar(credito.getSaldoTotal().subtract(credito.getTotalAbonado(), MathContext.UNLIMITED));
                credito.setPeriodosAtraso(new BigDecimal(contador_periodos_atrasados));
                credito.setSaldoAtrasado(deudas);
                if (credito.getFechaProximaAbonar().compareTo(hoy) == 1 || credito.getFechaProximaAbonar().compareTo(hoy) == 0) {
                    System.out.println("Fecha Proxima a Abonar: " + credito.getFechaProximaAbonar());
                    credito.setMinimoPago(deudas.add(credito.getMontoAbonar(), MathContext.UNLIMITED));
                } else {
                    credito.setMinimoPago(deudas);
                }

                System.out.println("Periodos Atrasados: " + contador_periodos_atrasados + "  Cantidad: " + deudas);
                System.out.println("---------------------------------------------------------------");
            }
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
