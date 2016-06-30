/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.negocio.NegocioPreciosCompetidores;
import com.web.chon.util.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServicePreciosCompetencias implements IfacePreciosCompetencias
{
    NegocioPreciosCompetidores ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioPreciosCompetidores) Utilidades.getEJBRemote("ejbPreciosCompetidores", NegocioPreciosCompetidores.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServicePreciosCompetencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getNextVal() {
        getEjb();
      return ejb.getNextVal();
    }

    @Override
    public int insertPreciosCompetencias(PreciosCompetencia pc) {
      getEjb();
      return ejb.insertPreciosCompetidores(pc);
    }
}
