/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Apartado;
import com.web.chon.negocio.NegocioApartado;

import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author jramirez
 */
@Service
public class ServiceApartado implements IfaceApartado{
    NegocioApartado ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioApartado) Utilidades.getEJBRemote("ejbApartado", NegocioApartado.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceApartado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insert(Apartado apartado) {
        getEjb();
        return ejb.insert(apartado);
    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public BigDecimal montoApartado(BigDecimal idVentaFk, BigDecimal idTipoFk) {
       getEjb();
        return ejb.montoApartado(idVentaFk, idTipoFk);
    }
    
}
