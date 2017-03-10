/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.ConceptosES;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTipoAbono;
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
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanAjusteCaja implements Serializable {

    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;

    private ArrayList<ConceptosES> listaConceptos;

    private ArrayList<OperacionesCaja> listaOperaciones;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private ArrayList<TipoOperacion> listaTiposOperaciones;

    private String title;
    private String viewEstate;
    private BigDecimal idConceptoBean;
    private int filtroES;
    private UsuarioDominio usuario;

    private Caja caja;
    private OperacionesCaja opcaja;
    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private BigDecimal monto;
    private String comentarios;
    private BigDecimal idFormaPagoBean;
    private ArrayList<TipoAbono> listaAbonos;

    private static final BigDecimal CONCEPTO_AJUSTE = new BigDecimal(11);
    private static final BigDecimal OPERACION_AJUSTE = new BigDecimal(8);
    private static final BigDecimal STATUS_REALIZADA = new BigDecimal(1);

    private static final BigDecimal SALIDA = new BigDecimal(2);
    private static final BigDecimal ENTRADA = new BigDecimal(1);
    private BigDecimal idUsuarioCajaBean;
    private ArrayList<Usuario> listaResponsables;

    @PostConstruct
    public void init() {
        listaAbonos = new ArrayList<TipoAbono>();
        listaAbonos = ifaceTipoAbono.getAll();
        filtroES = 1;
        usuario = context.getUsuarioAutenticado();
        setTitle("Ajuste de Caja");
        setViewEstate("init");
        listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(new BigDecimal(1));
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuario.getIdUsuario());
        opcaja.setEntradaSalida(new BigDecimal(filtroES));
        opcaja.setIdStatusFk(STATUS_REALIZADA);
        opcaja.setIdConceptoFk(CONCEPTO_AJUSTE);
        opcaja.setIdTipoOperacionFk(OPERACION_AJUSTE);
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        listaResponsables = new ArrayList<Usuario>();
        listaCajas = ifaceCaja.getCajas();
    }

    public void buscarReponsables() {
        listaResponsables.clear();
        listaResponsables = ifaceOperacionesCaja.getResponsables(idCajaBean);
        if (idCajaBean != null) {
            if (!listaResponsables.isEmpty()) {
                idUsuarioCajaBean = listaResponsables.get(0).getIdUsuarioPk();
            }
        }
    }

    public void reset() {
        comentarios = null;
        idFormaPagoBean = null;
        idCajaBean = null;
        idUsuarioCajaBean = null;
        monto = null;

    }

    public void ajustar() {

        if ((validarSaldo(idFormaPagoBean.intValue(), monto, idCajaBean) && filtroES == 2) || filtroES == 1) {
            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcaja.setMonto(monto);
            comentarios = comentarios + "SISTEMA: Usuario " + usuario.getNombreCompleto();
            opcaja.setComentarios(comentarios);
            opcaja.setEntradaSalida(new BigDecimal(filtroES));
            opcaja.setIdFormaPago(idFormaPagoBean);
            opcaja.setIdCajaFk(idCajaBean);
            opcaja.setIdUserFk(idUsuarioCajaBean);

            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                JsfUtil.addSuccessMessageClean("Ajuste de Caja Registrado Correctamente");
                reset();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al reslizar el ajuste de Caja");
            }
        } else {
            JsfUtil.addErrorMessageClean("Ajuste en Caja Mayor al Monto a Ajustar");
        }

    }

    private boolean validarSaldo(int tipoValidar, BigDecimal monto, BigDecimal idCaja) {
        CorteCaja corteCaja = new CorteCaja();
        boolean valido = false;

        corteCaja = ifaceCorteCaja.getSaldoCajaByIdCaja(idCaja);
        switch (tipoValidar) {
            case 1:
                if (monto.compareTo(corteCaja.getSaldoNuevo()) <= 0) {
                    valido = true;
                } else {
                    valido = false;
                }
                break;

            case 3:
                if (monto.compareTo(corteCaja.getMontoChequesNuevos()) <= 0) {
                    valido = true;
                } else {
                    valido = false;
                }
                break;

            case 2:
            case 4:
                if (monto.compareTo(corteCaja.getMontoCuentaNuevo()) <= 0) {
                    valido = true;
                } else {
                    valido = false;
                }
                break;

        }

        return valido;

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

    public BigDecimal getIdConceptoBean() {
        return idConceptoBean;
    }

    public void setIdConceptoBean(BigDecimal idConceptoBean) {
        this.idConceptoBean = idConceptoBean;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public OperacionesCaja getOpcaja() {
        return opcaja;
    }

    public void setOpcaja(OperacionesCaja opcaja) {
        this.opcaja = opcaja;
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

    public ArrayList<TipoOperacion> getListaTiposOperaciones() {
        return listaTiposOperaciones;
    }

    public void setListaTiposOperaciones(ArrayList<TipoOperacion> listaTiposOperaciones) {
        this.listaTiposOperaciones = listaTiposOperaciones;
    }

    public int getFiltroES() {
        return filtroES;
    }

    public void setFiltroES(int filtroES) {
        this.filtroES = filtroES;
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

    public ArrayList<TipoAbono> getListaAbonos() {
        return listaAbonos;
    }

    public void setListaAbonos(ArrayList<TipoAbono> listaAbonos) {
        this.listaAbonos = listaAbonos;
    }

    public BigDecimal getIdFormaPagoBean() {
        return idFormaPagoBean;
    }

    public void setIdFormaPagoBean(BigDecimal idFormaPagoBean) {
        this.idFormaPagoBean = idFormaPagoBean;
    }

    public BigDecimal getIdUsuarioCajaBean() {
        return idUsuarioCajaBean;
    }

    public void setIdUsuarioCajaBean(BigDecimal idUsuarioCajaBean) {
        this.idUsuarioCajaBean = idUsuarioCajaBean;
    }

    public ArrayList<Usuario> getListaResponsables() {
        return listaResponsables;
    }

    public void setListaResponsables(ArrayList<Usuario> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

}
