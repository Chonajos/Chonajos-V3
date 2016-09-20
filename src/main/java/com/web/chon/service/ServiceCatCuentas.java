/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.negocio.NegocioCatCuentas;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceCatCuentas implements IfaceCatalogoCuentas {
    NegocioCatCuentas ejb;
    
    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatCuentas) Utilidades.getEJBRemote("ejbCredito", NegocioCatCuentas.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    @Override
    public ArrayList<CuentaBancaria> getCuentas() {
         getEjb();
        ArrayList<CuentaBancaria> lstCuentas = new ArrayList<CuentaBancaria>();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getCuentas();
        for (Object[] object : lstObject) {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setIdCuentaBancariaPk(object[0] == null ? null : new BigDecimal(object[0].toString()));
            cuenta.setCuenta(object[1] == null ? null : new BigDecimal(object[1].toString()));
            cuenta.setNombreBanco(object[2] == null ? null : object[2].toString());
            lstCuentas.add(cuenta);
        }

        return lstCuentas;
    }
    
    
}
