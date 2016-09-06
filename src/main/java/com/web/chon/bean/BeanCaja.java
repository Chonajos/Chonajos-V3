/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.ConceptosES;
import com.web.chon.dominio.EntradaSalida;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceEntradaSalida;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
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
    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    IfaceEntradaSalida ifaceEntradaSalida;

    private UsuarioDominio usuario;
    private EntradaSalida data;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private ArrayList<EntradaSalida> listaMovimientosSalidas;
    private ArrayList<ConceptosES> listaConceptos;

    private String title;
    private String viewEstate;

    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()), new BigDecimal(1));
        idSucursalBean = new BigDecimal(usuario.getSucId());
        listaMovimientosSalidas = new ArrayList<EntradaSalida>();
        listaMovimientosSalidas = ifaceEntradaSalida.getMovimientosByIdCaja(new BigDecimal(1), title, title);
        listaConceptos = new ArrayList<ConceptosES>();
        listaConceptos = ifaceConceptos.getConceptos();
        data = new EntradaSalida();
        idCajaBean = new BigDecimal(1);

        setTitle("Operaciones de Caja");
        setViewEstate("init");
    }

    public void getMovimientos() {
        listaMovimientosSalidas = ifaceEntradaSalida.getMovimientosByIdCaja(new BigDecimal(1), title, title);
    }

    public void generarSalida() 
    {
        data.setIdEntradaSalidaPk(new BigDecimal(ifaceEntradaSalida.getNextVal()));
        data.setIdCajaFk(idCajaBean);
        Caja c = new Caja();
        c = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), idCajaBean);
        c.setMonto(c.getMonto().subtract(data.getMonto(), MathContext.UNLIMITED));
        if (ifaceCaja.updateMontoCaja(c) == 1) {
            System.out.println("Entrada Salida: " + data.toString());
            if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                getMovimientos();
                data.reset();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
            }
        }else
        {
            JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
        }

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

    public EntradaSalida getData() {
        return data;
    }

    public void setData(EntradaSalida data) {
        this.data = data;
    }

    public ArrayList<ConceptosES> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaConceptos(ArrayList<ConceptosES> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

}
