/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoCovenio;
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
public class beanExistencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired
    private IfaceTipoCovenio ifaceCovenio;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceCatProvedores ifaceCatProvedores;
    private ArrayList<Provedor> listaProvedores;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCatBodegas ifaceCatBodegas;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Bodega> listaBodegas;
    private String title = "";
    private String viewEstate = "";
    private ArrayList<ExistenciaProducto> model;
    private ExistenciaProducto data;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    private Subproducto subProducto;
    private EntradaMercancia2 entradaMercancia;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<EntradaMercancia2> lstEntradaMercancia;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TipoConvenio> listaTiposConvenio;

    @PostConstruct
    public void init() {
        
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = new ArrayList<Provedor>();
        listaProvedores = ifaceCatProvedores.getProvedores();
        model = ifaceNegocioExistencia.getExistencias(null,null,null,null,null,null,null);
        listaBodegas = new ArrayList<Bodega>();
        listaBodegas = ifaceCatBodegas.getBodegas();
        data = new ExistenciaProducto();
        setTitle("Existencias");
        setViewEstate("init");
        subProducto = new Subproducto();
        entradaMercancia = new EntradaMercancia2();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        listaTiposConvenio = new ArrayList<TipoConvenio>();
        listaTiposConvenio = ifaceCovenio.getTipos();
    }

    public void buscaExistencias() 
    {
        BigDecimal idEntrada;
        if(entradaMercancia == null)
        {
            idEntrada =  null;
        }
        else
        {
            idEntrada = entradaMercancia.getIdEmPK();
            //data.reset();
        }
        
        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        model = ifaceNegocioExistencia.getExistencias(data.getIdSucursal(),data.getIdBodegaFK(),data.getIdProvedor(),idproductito,data.getIdTipoEmpaqueFK(),data.getIdTipoConvenio(),idEntrada);
        
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public ArrayList<TipoConvenio> getListaTiposConvenio() {
        return listaTiposConvenio;
    }

    public void setListaTiposConvenio(ArrayList<TipoConvenio> listaTiposConvenio) {
        this.listaTiposConvenio = listaTiposConvenio;
    }

    public ArrayList<EntradaMercancia2> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia2> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }
    

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }
    
    public ArrayList<EntradaMercancia2> autoCompleteMercancia(String clave) {
        System.out.println("Letra: "+clave);
        lstEntradaMercancia = ifaceEntradaMercancia.getSubEntradaByNombre(clave.toUpperCase());
        return lstEntradaMercancia;

    }
    
    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public EntradaMercancia2 getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(EntradaMercancia2 entradaMercancia) {
        this.entradaMercancia = entradaMercancia;
    }

    
    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    
    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
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

    public ArrayList<ExistenciaProducto> getModel() {
        return model;
    }

    public void setModel(ArrayList<ExistenciaProducto> model) {
        this.model = model;
    }

    public ExistenciaProducto getData() {
        return data;
    }

    public void setData(ExistenciaProducto data) {
        this.data = data;
    }

    public IfaceNegocioExistencia getIfaceNegocioExistencia() {
        return ifaceNegocioExistencia;
    }

    public void setIfaceNegocioExistencia(IfaceNegocioExistencia ifaceNegocioExistencia) {
        this.ifaceNegocioExistencia = ifaceNegocioExistencia;
    }

    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

}
