/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.EntradaSalida;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class BeanCaja implements Serializable {

    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private PlataformaSecurityContext context;

    private UsuarioDominio usuario;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private ArrayList<EntradaSalida> listaMovimientosSalidas;
    
    
    private String title;
    private String viewEstate;
    
    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()),new BigDecimal(1));
        idSucursalBean = new BigDecimal(usuario.getSucId());
        listaMovimientosSalidas= new ArrayList<EntradaSalida>();

        
        setTitle("Operaciones de Caja");
        setViewEstate("init");
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getIdSucursalBean() {
        return idSucursalBean;
    }

    public void setIdSucursalBean(BigDecimal idSucursalBean) {
        this.idSucursalBean = idSucursalBean;
    }

    public BigDecimal getIdCajaBean() {
        return idCajaBean;
    }

    public void setIdCajaBean(BigDecimal idCajaBean) {
        this.idCajaBean = idCajaBean;
    }

    public ArrayList<EntradaSalida> getListaMovimientosSalidas() {
        return listaMovimientosSalidas;
    }

    public void setListaMovimientosSalidas(ArrayList<EntradaSalida> listaMovimientosSalidas) {
        this.listaMovimientosSalidas = listaMovimientosSalidas;
    }
    
    
    

    
}
