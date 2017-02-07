/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.negocio.NegocioRegEntSal;
import com.web.chon.security.service.PlataformaSecurityContext;
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
 * @author freddy
 */
@Service
public class ServiceRegEntSal implements IfaceRegistroEntradaSalida {

    NegocioRegEntSal ejb;
    @Autowired
    PlataformaSecurityContext context;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioRegEntSal) Utilidades.getEJBRemote("ejbRegEntSal", NegocioRegEntSal.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<RegistroEntradaSalida> getUsuarioByIdUsuario(BigDecimal idUsuarioFK, Date fechaHoy) {

        getEjb();
        try {
            ArrayList<RegistroEntradaSalida> lstTop = new ArrayList<RegistroEntradaSalida>();
            List<Object[]> lstObject = ejb.getUsuarioByIdUsuario(idUsuarioFK, TiempoUtil.getFechaDDMMYYYY(fechaHoy));
            for (Object[] obj : lstObject) {
                RegistroEntradaSalida reg = new RegistroEntradaSalida();
                reg.setIdRegEntSalPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                reg.setFechaEntrada((Date) obj[1]);
                reg.setFechaSalida((Date) obj[2]);
                reg.setLatitudEntrada(obj[3] == null ? null : Double.valueOf(obj[3].toString()));
                reg.setLongitudEntrada(obj[4] == null ? null : Double.valueOf(obj[4].toString()));
                reg.setLongitudSalida(obj[6] == null ? null : Double.valueOf(obj[6].toString()));
                reg.setLatitudSalida(obj[5] == null ? null : Double.valueOf(obj[5].toString()));
                reg.setIdUsuarioFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                reg.setIdSucursalFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                reg.setNombre(obj[9] == null ? null : obj[9].toString());
                reg.setApMaterno(obj[10] == null ? null : obj[10].toString());
                reg.setApMaterno(obj[11] == null ? null : obj[11].toString());
                lstTop.add(reg);
            }
            return lstTop;
        } catch (Exception ex) {
            Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int updateSalidabyIdReg(RegistroEntradaSalida data) {
        getEjb();
        return ejb.updateSalidabyIdReg(data);
    }

    @Override
    public int insertEntradabyIdReg(RegistroEntradaSalida data) {
        getEjb();
        return ejb.insertEntradabyIdReg(data);

    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();

    }

    @Override
    public ArrayList<RegistroEntradaSalida> getRegistros(BigDecimal idUsuarioFK, Date fechaInicio, Date fechaFin) {
        getEjb();

        try {
            Date fechaActual = context.getFechaSistema();

            ArrayList<RegistroEntradaSalida> lstTop = new ArrayList<RegistroEntradaSalida>();
            List<Object[]> lstObject = ejb.getRegistros(idUsuarioFK, TiempoUtil.getFechaDDMMYYYY(TiempoUtil.getDayOneOfMonth(fechaInicio)), TiempoUtil.getFechaDDMMYYYY(fechaFin));

            final int RETARDO_MINUTOS = 15;
            final String SIN_REGISTOR = "--";

            for (Object[] obj : lstObject) {
                RegistroEntradaSalida reg = new RegistroEntradaSalida();
                reg.setFecha(obj[0] == null ? null : (Date) obj[0]);
                reg.setDia(TiempoUtil.nombreDia(reg.getFecha()).substring(0, 1).trim());
                reg.setHoraEntrada(obj[2] == null ? SIN_REGISTOR : obj[2].toString());
                reg.setHoraSalida(obj[4] == null ? SIN_REGISTOR : obj[4].toString());
                reg.setHorarioEntrada(obj[9] == null ? SIN_REGISTOR : obj[9].toString());
                reg.setHorarioSalida(obj[10] == null ? SIN_REGISTOR : obj[10].toString());
                reg.setLatitudEntrada(obj[5] == null ? Double.valueOf(0) : Double.valueOf(obj[5].toString()));
                reg.setLatitudSalida(obj[6] == null ? Double.valueOf(0) : Double.valueOf(obj[6].toString()));
                reg.setLongitudEntrada(obj[7] == null ? Double.valueOf(0) : Double.valueOf(obj[7].toString()));
                reg.setLatitudSalida(obj[8] == null ? Double.valueOf(0) : Double.valueOf(obj[8].toString()));
                reg.setDiasTrabajdosDescanso(new BigDecimal(0));
                reg.setHorasAtrabajar(new BigDecimal(0));
                reg.setHorasTrabajada(new BigDecimal(0));

                if (obj[11] != null) {

                    String[] diasDescanso = obj[11].toString().split(",");
                    reg.setDiasDescanso(diasDescanso);

                    int dia = TiempoUtil.getNumberDayForWeek(reg.getFecha());

                    for (int i = 0; i < reg.getDiasDescanso().length; i++) {
                        if (dia == Integer.parseInt(reg.getDiasDescanso()[i])) {
                            reg.setDiaDescanso(true);
                            break;
                        } else {
                            reg.setDiaDescanso(false);

                        }
                    }

                }

                if (!reg.isDiaDescanso()) {
                    if (reg.getHoraEntrada().equals(SIN_REGISTOR)) {
                        if (TiempoUtil.getFechaDDMMYYYYDate(fechaActual).compareTo(TiempoUtil.getFechaDDMMYYYYDate(reg.getFecha())) > 0) {
                            reg.setFalta(true);
                        }

                    } else if (!reg.getHorarioEntrada().equals(SIN_REGISTOR)) {
                        int minutosDiferencia = TiempoUtil.getMinutesBetweenTwoHour(reg.getHoraEntrada(), reg.getHorarioEntrada());
                        if (minutosDiferencia > RETARDO_MINUTOS) {
                            reg.setRetardo(true);
                        } else {
                            reg.setRetardo(false);
                        }
                    }
                }else{
                     if (!reg.getHoraEntrada().equals(SIN_REGISTOR)) {
                         reg.setDiasTrabajdosDescanso(reg.getDiasTrabajdosDescanso().add(new BigDecimal(1)));
                         if(reg.getHoraSalida().equals(SIN_REGISTOR)){
                             reg.setHoraSalida(reg.getHorarioSalida());
                             
                         }
                         int minutosTrabajdos = TiempoUtil.getMinutesBetweenTwoHour(reg.getHoraEntrada(), reg.getHoraSalida());
                         reg.setHorasTrabajada(reg.getHorasAtrabajar().add(new BigDecimal(minutosTrabajdos)));
                         
                     }
                }

                lstTop.add(reg);
            }

            return lstTop;
        } catch (Exception ex) {
            Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public ArrayList<RegistroEntradaSalida> getALL(Date fechaInicio, Date fechaFin
    ) {
        getEjb();

        try {
            ArrayList<RegistroEntradaSalida> lstTop = new ArrayList<RegistroEntradaSalida>();
            List<Object[]> lstObject = ejb.getALL(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin));
            for (Object[] obj : lstObject) {
                RegistroEntradaSalida reg = new RegistroEntradaSalida();
                reg.setIdRegEntSalPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                reg.setFechaEntrada((Date) obj[1]);
                reg.setFechaSalida((Date) obj[2]);
                reg.setLatitudEntrada(obj[3] == null ? null : Double.valueOf(obj[3].toString()));
                reg.setLongitudEntrada(obj[4] == null ? null : Double.valueOf(obj[4].toString()));
                reg.setLongitudSalida(obj[6] == null ? null : Double.valueOf(obj[6].toString()));
                reg.setLatitudSalida(obj[5] == null ? null : Double.valueOf(obj[5].toString()));
                reg.setIdUsuarioFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                reg.setIdSucursalFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                reg.setNombre(obj[9] == null ? null : obj[9].toString());
                reg.setApMaterno(obj[10] == null ? null : obj[10].toString());
                reg.setApMaterno(obj[11] == null ? null : obj[11].toString());
                lstTop.add(reg);
                System.out.println("REG: " + reg);
            }
            return lstTop;
        } catch (Exception ex) {
            Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
