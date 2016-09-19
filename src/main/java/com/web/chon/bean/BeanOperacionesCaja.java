/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.ConceptosES;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTiposOperacion;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class BeanOperacionesCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;
    @Autowired
    private IfaceConceptos ifaceConceptos;

    private OperacionesCaja data;
    private UsuarioDominio usuario;

    private ArrayList<OperacionesCaja> listaOperaciones;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private ArrayList<ConceptosES> listaConceptos;
    private ArrayList<TipoOperacion> listaTiposOperaciones;

    private String title;
    private String viewEstate;

    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private int filtro;
    private int filtroStatus;
    private int filtroES;
    private boolean enableCalendar;

    private BigDecimal idSucursalBean;

    private BigDecimal idCajaBean;
    private BigDecimal idConceptoBean;
    private BigDecimal idTipoOperacionBean;
    private BigDecimal monto;
    private String comentarios;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Relación de Operaciones de Caja");
        setViewEstate("init");
        fechaFiltroInicio = new Date();
        fechaFiltroFin = new Date();
        listaSucursales = ifaceCatSucursales.getSucursales();
        idSucursalBean = new BigDecimal(usuario.getSucId());
        listaCajas = ifaceCaja.getCajas();
        listaOperaciones = new ArrayList<OperacionesCaja>();
        Caja c = new Caja();
        c = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        idCajaBean = c.getIdCajaPk();
        listaConceptos = ifaceConceptos.getConceptos();
        listaTiposOperaciones = ifaceTiposOperacion.getOperaciones();
        listaOperaciones = ifaceOperacionesCaja.getOperacionesBy(idCajaBean, idTipoOperacionBean, idConceptoBean, TiempoUtil.getFechaDDMMYYYY(fechaFiltroInicio), TiempoUtil.getFechaDDMMYYYY(fechaFiltroFin), idCajaBean, usuario.getIdUsuario());

    }

    public void buscar() {

    }

    public void verificarCombo() {
        if (filtro == -1) {
            //se habilitan los calendarios.
            fechaFiltroInicio = null;
            fechaFiltroFin = null;
            enableCalendar = false;
        } else {
            switch (filtro) {
                case 1:
                    fechaFiltroInicio = new Date();
                    fechaFiltroFin = new Date();
                    break;

                case 2:
                    fechaFiltroInicio = TiempoUtil.getDayOneOfMonth(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndOfMonth(new Date());

                    break;
                case 3:
                    fechaFiltroInicio = TiempoUtil.getDayOneYear(new Date());
                    fechaFiltroFin = TiempoUtil.getDayEndYear(new Date());
                    break;
                default:
                    fechaFiltroInicio = null;
                    fechaFiltroFin = null;
                    break;
            }
            enableCalendar = true;
        }
    }

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
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

    public ArrayList<OperacionesCaja> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(ArrayList<OperacionesCaja> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public Date getFechaFiltroInicio() {
        return fechaFiltroInicio;
    }

    public void setFechaFiltroInicio(Date fechaFiltroInicio) {
        this.fechaFiltroInicio = fechaFiltroInicio;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public ArrayList<ConceptosES> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaConceptos(ArrayList<ConceptosES> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public ArrayList<TipoOperacion> getListaTiposOperaciones() {
        return listaTiposOperaciones;
    }

    public void setListaTiposOperaciones(ArrayList<TipoOperacion> listaTiposOperaciones) {
        this.listaTiposOperaciones = listaTiposOperaciones;
    }

    public BigDecimal getIdConceptoBean() {
        return idConceptoBean;
    }

    public void setIdConceptoBean(BigDecimal idConceptoBean) {
        this.idConceptoBean = idConceptoBean;
    }

    public BigDecimal getIdTipoOperacionBean() {
        return idTipoOperacionBean;
    }

    public void setIdTipoOperacionBean(BigDecimal idTipoOperacionBean) {
        this.idTipoOperacionBean = idTipoOperacionBean;
    }

    public int getFiltroStatus() {
        return filtroStatus;
    }

    public void setFiltroStatus(int filtroStatus) {
        this.filtroStatus = filtroStatus;
    }

    public int getFiltroES() {
        return filtroES;
    }

    public void setFiltroES(int filtroES) {
        this.filtroES = filtroES;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

}
