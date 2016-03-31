/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Correos;
import com.web.chon.negocio.NegocioCatCorreos;
import com.web.chon.util.Utilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServiceCatCorreos implements IfaceCatCorreos {

    NegocioCatCorreos ejb;

    @Override
    public int insertCorreo(Correos co) {
        try {
            ejb = (NegocioCatCorreos) Utilidades.getEJBRemote("ejbCatCorreos", NegocioCatCorreos.class.getName());
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Correo Service: " + co.toString());
        return ejb.insertCorreo(co);
    }

    @Override
    public int deleteCorreos(Correos co) {
        try {

            ejb = (NegocioCatCorreos) Utilidades.getEJBRemote("ejbCatCorreos", NegocioCatCorreos.class.getName());
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("service delete correos");
        return ejb.deleteCorreos(co);
    }

    @Override
    public ArrayList<Correos> SearchCorreosbyidClientPk(int idClientepk) {
        try {
            ArrayList<Correos> lista_correos = new ArrayList<Correos>();
            ejb = (NegocioCatCorreos) Utilidades.getEJBRemote("ejbCatCorreos", NegocioCatCorreos.class.getName());
            List<Object[]> lstObject = ejb.SearchCorreosbyidClientPk(idClientepk);
            for (Object[] obj : lstObject) {
                Correos c = new Correos();
                c.setIdcorreo(Integer.parseInt(obj[0].toString()));
                c.setId_cliente_fk(Integer.parseInt(obj[1].toString()));
                c.setCorreo(obj[2] == null ? "" : obj[2].toString());
                c.setTipo(obj[3] == null ? "" : obj[3].toString());
                lista_correos.add(c);
            }

            return lista_correos;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCorreos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int updateCorreos(Correos co) 
    {
        if(co.getIdcorreo()==0)
        {
            System.out.println("No se actualiza, se inserta correo nuevo.");
            return ejb.insertCorreo(co);
        }
        System.out.println("service update correos:");
        return ejb.updateCorreos(co);
    }

}
