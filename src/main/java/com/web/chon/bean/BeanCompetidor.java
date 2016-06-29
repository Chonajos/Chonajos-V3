/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Competidor;
import com.web.chon.dominio.PreciosCompetencia;
import com.web.chon.dominio.Subproducto;
import com.web.chon.service.IfaceCompetidores;
import com.web.chon.service.IfaceSubProducto;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author marcogante
 */
@Component
@Scope("view")
public class BeanCompetidor implements Serializable{
    private static final long serialVersionUID = 1L;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private IfaceCompetidores ifaceCompetidores;
    
    private String title = "";
    private String viewEstate = "";
    
    private Subproducto subProducto;
    
    private PreciosCompetencia data;
    
    private ArrayList<Competidor> listaCompetidor;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<PreciosCompetencia> listaPrecios;
    
    
    
    @PostConstruct
    public void init() 
    {
        setTitle("Registro Precios de Venta Competencia");
        setViewEstate("init");
        listaCompetidor=ifaceCompetidores.getCometidores();
        data = new PreciosCompetencia();
        
    }
    public void registrar()
    {
        
    }
    
    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

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

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public PreciosCompetencia getData() {
        return data;
    }

    public void setData(PreciosCompetencia data) {
        this.data = data;
    }


    public ArrayList<Competidor> getListaCompetidor() {
        return listaCompetidor;
    }

    public void setListaCompetidor(ArrayList<Competidor> listaCompetidor) {
        this.listaCompetidor = listaCompetidor;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<PreciosCompetencia> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(ArrayList<PreciosCompetencia> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }
    
    
    
}
