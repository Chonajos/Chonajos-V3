/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CuentaBancaria;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceCatCuentas implements IfaceCatalogoCuentas {

    @Override
    public ArrayList<CuentaBancaria> getCuentas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
