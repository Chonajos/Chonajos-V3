/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.negocio.NegocioRegEntSal;
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
 * @author freddy
 */
@Service
public class ServiceRegEntSal implements  IfaceRegistroEntradaSalida{

    NegocioRegEntSal ejb;

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
            for (Object[] obj : lstObject) 
            {
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
                reg.setNombre(obj[9] == null ? null:obj[9].toString());
                reg.setApMaterno(obj[10] == null ? null:obj[10].toString());
                reg.setApMaterno(obj[11] == null ? null:obj[11].toString());
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
            ArrayList<RegistroEntradaSalida> lstTop = new ArrayList<RegistroEntradaSalida>();
            List<Object[]> lstObject = ejb.getRegistros(idUsuarioFK, TiempoUtil.getFechaDDMMYY(TiempoUtil.getDayOneOfMonth(fechaInicio)),TiempoUtil.getFechaDDMMYYYY(fechaFin));
            for (Object[] obj : lstObject) 
            {
                RegistroEntradaSalida reg = new RegistroEntradaSalida();
                reg.setFecha(obj[0] == null ? null : (Date) obj[0]);
                
                reg.setHoraEntrada(obj[2] == null ? "---": obj[2].toString());
                reg.setHoraSalida(obj[4] == null ? "---": obj[4].toString());
                reg.setLatitudEntrada(obj[5] == null ? Double.valueOf(0) : Double.valueOf(obj[5].toString()));
                reg.setLatitudSalida(obj[6] == null ? Double.valueOf(0) : Double.valueOf(obj[6].toString()));
                reg.setLongitudEntrada(obj[7] == null ? Double.valueOf(0) : Double.valueOf(obj[7].toString()));
                reg.setLatitudSalida(obj[8] == null ? Double.valueOf(0) : Double.valueOf(obj[8].toString()));

                lstTop.add(reg);
            }
            return lstTop;
        } catch (Exception ex) {
            Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }

    @Override
    public ArrayList<RegistroEntradaSalida> getALL(Date fechaInicio, Date fechaFin) {
       getEjb();
        
        try {
            ArrayList<RegistroEntradaSalida> lstTop = new ArrayList<RegistroEntradaSalida>();
            List<Object[]> lstObject = ejb.getALL( TiempoUtil.getFechaDDMMYYYY(fechaInicio),TiempoUtil.getFechaDDMMYYYY(fechaFin));
            for (Object[] obj : lstObject) 
            {
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
                reg.setNombre(obj[9] == null ? null:obj[9].toString());
                reg.setApMaterno(obj[10] == null ? null:obj[10].toString());
                reg.setApMaterno(obj[11] == null ? null:obj[11].toString());
                lstTop.add(reg);
                System.out.println("REG: "+reg);
            }
            return lstTop;
        } catch (Exception ex) {
            Logger.getLogger(ServiceRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
}
