/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Sucursal;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author fredy
 */
@Component
@Scope("view")
public class BeanEntradaMercancia2 implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    private ArrayList<Sucursal> listaSucursales;
    
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    private ArrayList<Provedor> listaProvedores;
    private EntradaMercancia2 data;
    
    private String title = "";
    private String viewEstate = "";
    
    public void init()
    {
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores =  new ArrayList<Provedor>();
        listaProvedores  = ifaceCatProvedores.getProvedores();
        data = new EntradaMercancia2();
        setTitle("Mantenimiento de Precios.");
        setViewEstate("init");
    }
    
    
    
    
    
    
    //Getters y Setters
    

    public IfaceCatSucursales getIfaceCatSucursales() {
        return ifaceCatSucursales;
    }

    public void setIfaceCatSucursales(IfaceCatSucursales ifaceCatSucursales) {
        this.ifaceCatSucursales = ifaceCatSucursales;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public IfaceCatProvedores getIfaceCatProvedores() {
        return ifaceCatProvedores;
    }

    public void setIfaceCatProvedores(IfaceCatProvedores ifaceCatProvedores) {
        this.ifaceCatProvedores = ifaceCatProvedores;
    }

    public ArrayList<Provedor> getListaProvedores() {
        return listaProvedores;
    }

    public void setListaProvedores(ArrayList<Provedor> listaProvedores) {
        this.listaProvedores = listaProvedores;
    }

    public EntradaMercancia2 getData() {
        return data;
    }

    public void setData(EntradaMercancia2 data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewEstate() {
        return viewEstate;
    }

    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }
    
    
}
