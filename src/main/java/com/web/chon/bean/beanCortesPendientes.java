/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCorteCaja;
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
public class beanCortesPendientes {
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    private CorteCaja corte;
    private Caja caja;
    private UsuarioDominio usuario;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private String title;
    private String viewEstate;
    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private ArrayList<CorteCaja> listaCortes;
    
    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = ifaceCatSucursales.getSucursales();
        idSucursalBean = new BigDecimal(usuario.getSucId());
        listaCortes = new ArrayList<CorteCaja>();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), new BigDecimal(1));
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()), new BigDecimal(1));
        idCajaBean = caja.getIdCajaPk();
        listaCortes = ifaceCorteCaja.getCortesByIdDestinoFk(caja.getIdCajaPk());
        setTitle("Cortes Pendientes");
        setViewEstate("init");
    }
    public void aceptar()
    {
        
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
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
    

    public CorteCaja getCorte() {
        return corte;
    }

    public void setCorte(CorteCaja corte) {
        this.corte = corte;
    }

    public ArrayList<CorteCaja> getListaCortes() {
        return listaCortes;
    }

    public void setListaCortes(ArrayList<CorteCaja> listaCortes) {
        this.listaCortes = listaCortes;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
    }
    
    
}
