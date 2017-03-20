/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.ConceptosES;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceTiposOperacion;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jramirez
 */
@Component
@Scope("view")
public class BeanCatalogosOperaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;
    @Autowired
    private IfaceConceptos ifaceConceptos;
    
    private ArrayList<ConceptosES> listaConceptos;
    private ArrayList<TipoOperacion> listaCategorias;
    
    private ConceptosES data;
    
    private String title = "";
    public String viewEstate = "";
    
    
    @PostConstruct
    public void init() 
    {
        viewEstate="init";
        title="Catalógo de Operaciones";
        listaConceptos = new ArrayList<ConceptosES>();
        listaConceptos = ifaceConceptos.getConceptos();
        listaCategorias = new ArrayList<TipoOperacion>();
        listaCategorias = ifaceTiposOperacion.getOperaciones();
        
    }
    public void insert()
    {
        viewEstate="new";
        data.setIdConceptoPk(new BigDecimal(ifaceConceptos.getNextVal()));
        if(ifaceConceptos.insertConcepto(data)==1)
        {
            JsfUtil.addSuccessMessageClean("Se ha ingresado el concepto existosamente");
        }
        else
        {
            JsfUtil.addErrorMessageClean("Ha ocurrido un error al ingresar el concepto");
        }
    }
    public void update()
    {
      
        if(ifaceConceptos.updateConcepto(data)==1)
        {
            JsfUtil.addSuccessMessageClean("Se han actualizado los datos exitosamente");
        }
        else
        {
            JsfUtil.addErrorMessageClean("Ha ocurrido un error al actualizar los datos");
        }
        
        
       backView(); 
    }
    
    public void backView()
    {
        
        data.reset();
        setTitle("Catalógo de Operaciones");
        setViewEstate("init");
    }
    public void changeViewNew()
    {
        data = new ConceptosES();
        setTitle("Alta de Conceptos");
        setViewEstate("new");
    }
    public void changeViewEditar()
    {
        
        setTitle("Actualizar Conceptos");
        setViewEstate("searchById");
    }

    public ArrayList<TipoOperacion> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(ArrayList<TipoOperacion> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    

    public ArrayList<ConceptosES> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaConceptos(ArrayList<ConceptosES> listaConceptos) {
        this.listaConceptos = listaConceptos;
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

    public ConceptosES getData() {
        return data;
    }

    public void setData(ConceptosES data) {
        this.data = data;
    }
    
}
