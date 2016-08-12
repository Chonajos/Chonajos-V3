/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.CobroCheques;
import com.web.chon.service.IfaceCobroCheques;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanHistorialCheques implements Serializable{
    private static final long serialVersionUID = 1L;
    @Autowired private IfaceCobroCheques ifaceCobroCheques;
    private ArrayList<CobroCheques> listaChequesDeposito;
    private ArrayList<CobroCheques> listaChequesEfectivo;
    private ArrayList<CobroCheques> listaChequesNoCobrados;
    
    @PostConstruct
    public void init()
    {
        listaChequesDeposito = new ArrayList<CobroCheques>();
         listaChequesEfectivo = new ArrayList<CobroCheques>();
          listaChequesNoCobrados = new ArrayList<CobroCheques>();
        
        
    }

    public ArrayList<CobroCheques> getListaChequesDeposito() {
        return listaChequesDeposito;
    }

    public void setListaChequesDeposito(ArrayList<CobroCheques> listaChequesDeposito) {
        this.listaChequesDeposito = listaChequesDeposito;
    }

    public ArrayList<CobroCheques> getListaChequesEfectivo() {
        return listaChequesEfectivo;
    }

    public void setListaChequesEfectivo(ArrayList<CobroCheques> listaChequesEfectivo) {
        this.listaChequesEfectivo = listaChequesEfectivo;
    }

    public ArrayList<CobroCheques> getListaChequesNoCobrados() {
        return listaChequesNoCobrados;
    }

    public void setListaChequesNoCobrados(ArrayList<CobroCheques> listaChequesNoCobrados) {
        this.listaChequesNoCobrados = listaChequesNoCobrados;
    }

    
    
}
