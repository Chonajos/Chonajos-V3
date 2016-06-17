/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.RelEntSalExAj;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceExistenciaMenudeo;
import com.web.chon.service.IfaceSubProducto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanRelaEntSalExAj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceExistenciaMenudeo ifaceExistenciaMenudeo;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;

    private String title;
    private String viewEstate;
    private BigDecimal idSucursal;
    private BigDecimal idSubProducto;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Subproducto> lstProducto;
    private RelEntSalExAj model;

    private Subproducto subProducto;

    @PostConstruct
    public void init() {
        setTitle("Relación de Operaciones");
        setViewEstate("init");
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        subProducto = new Subproducto();
    }
    public void search() {

        if (idSucursal != null && subProducto.getIdProductoFk() != null) {
            System.out.println("idSucursal: " + idSucursal);
            System.out.println("SubProducto: " + subProducto.getIdSubproductoPk());
            model = ifaceExistenciaMenudeo.getRelacion(idSucursal, subProducto.getIdSubproductoPk());
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdSubProducto() {
        return idSubProducto;
    }

    public void setIdSubProducto(BigDecimal idSubProducto) {
        this.idSubProducto = idSubProducto;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public RelEntSalExAj getModel() {
        return model;
    }

    public void setModel(RelEntSalExAj model) {
        this.model = model;
    }

   

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

}
