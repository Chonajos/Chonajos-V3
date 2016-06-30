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
import com.web.chon.service.IfacePreciosCompetencias;
import com.web.chon.service.IfaceSubProducto;
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
 * @author marcogante
 */
@Component
@Scope("view")
public class BeanCompetidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceCompetidores ifaceCompetidores;
    @Autowired
    private IfacePreciosCompetencias ifacePreciosCompetencias;

    private String title = "";
    private String viewEstate = "";

    private Subproducto subProducto;

    private PreciosCompetencia data;

    private Competidor dataCompetidor;

    private ArrayList<Competidor> listaCompetidor;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<PreciosCompetencia> listaPrecios;

    @PostConstruct
    public void init() {
        setTitle("Registro Precios de Venta Competencia");
        setViewEstate("init");
        listaCompetidor = ifaceCompetidores.getCometidores();
        data = new PreciosCompetencia();
        dataCompetidor = new Competidor();
        subProducto = new Subproducto();

    }

    public void nuevoCompetidor() {
        BigDecimal idCompetidor = new BigDecimal(ifaceCompetidores.getNextVal());
        dataCompetidor.setIdCompetidorPk(idCompetidor);
        System.out.println("Bean====> " + dataCompetidor.toString());
        if (dataCompetidor.getNombreCompetidor().equals("")) {
            JsfUtil.addErrorMessageClean("Agregar un nombre");
        } else if (ifaceCompetidores.insertCompetidor(dataCompetidor) != 0) {
            listaCompetidor = ifaceCompetidores.getCometidores();
            JsfUtil.addSuccessMessageClean("Competidor Registrado Correctamente");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un problema");
        }

    }

    public void registrar() {
        if (data.getPrecioVenta()==null || data.getIdCompetidorFk()==null) {
            JsfUtil.addErrorMessageClean("Por favor Ingresar los datos");
        } else {
            BigDecimal idPrecioCompetidor = new BigDecimal(ifacePreciosCompetencias.getNextVal());
            data.setIdPcPk(idPrecioCompetidor);
            data.setIdSubProductoPk(subProducto.getIdSubproductoPk());
            System.out.println("Bean ==============>" + data.toString());
            if (ifacePreciosCompetencias.insertPreciosCompetencias(data) != 0) {
                data.reset();
                subProducto = new Subproducto();
                JsfUtil.addSuccessMessageClean("Precio de Venta Registrado Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un problema");
            }
        }
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

    public Competidor getDataCompetidor() {
        return dataCompetidor;
    }

    public void setDataCompetidor(Competidor dataCompetidor) {
        this.dataCompetidor = dataCompetidor;
    }

}
