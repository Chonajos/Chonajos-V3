/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceOperacionesCuentas;
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
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanRecibirDeposito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    
    
    
    @Autowired
    private PlataformaSecurityContext context;
    
    @Autowired 
    private IfaceOperacionesCuentas ifaceOperacionesCuentas;
    private UsuarioDominio usuario;
    private OperacionesCuentas opcuenta;
    private OperacionesCaja opcajaOrigen;


    private ArrayList<OperacionesCaja> lstDespositosEntrantes;
    private OperacionesCaja data;

    private String title;
    private String viewEstate;

    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private BigDecimal idConceptoBean;
    private BigDecimal idTipoOperacionBean;
    private BigDecimal monto;
    private String comentarios;
    private BigDecimal idCajaDestinoBean;

    private static final BigDecimal entrada = new BigDecimal(1);
    private static final BigDecimal salida = new BigDecimal(2);
    private static final BigDecimal statusOperacion = new BigDecimal(1);
    private static final BigDecimal idConcepto = new BigDecimal(14);

    @PostConstruct
    public void init() 
    {
        usuario = context.getUsuarioAutenticado();
        setTitle("Recibir Depósitos de Cuentas");
        setViewEstate("init");
        data = new OperacionesCaja();
        opcuenta = new OperacionesCuentas();
        opcuenta.setIdUserFk(usuario.getIdUsuario());
        opcuenta.setIdStatusFk(statusOperacion);
        opcuenta.setEntradaSalida(entrada);
        opcuenta.setIdConceptoFk(idConcepto);
        lstDespositosEntrantes = new ArrayList<OperacionesCaja>();
        lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes();
    }
    public void aceptar() {

            opcuenta.setIdOperacionCuenta(new BigDecimal(ifaceOperacionesCuentas.getNextVal()));
            opcuenta.setMonto(data.getMonto());
            if (ifaceOperacionesCuentas.insertaOperacion(opcuenta) == 1) 
            {
                ifaceOperacionesCaja.updateStatus(data.getIdOperacionesCajaPk(), statusOperacion);
                lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes();
                JsfUtil.addSuccessMessageClean("Se ha recibido el Depósito Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al recibir el Depósito");
            }

    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
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

    public BigDecimal getIdCajaDestinoBean() {
        return idCajaDestinoBean;
    }

    public void setIdCajaDestinoBean(BigDecimal idCajaDestinoBean) {
        this.idCajaDestinoBean = idCajaDestinoBean;
    }

    public ArrayList<OperacionesCaja> getLstDespositosEntrantes() {
        return lstDespositosEntrantes;
    }

    public void setLstDespositosEntrantes(ArrayList<OperacionesCaja> lstDespositosEntrantes) {
        this.lstDespositosEntrantes = lstDespositosEntrantes;
    }

    public OperacionesCuentas getOpcuenta() {
        return opcuenta;
    }

    public void setOpcuenta(OperacionesCuentas opcuenta) {
        this.opcuenta = opcuenta;
    }

    public OperacionesCaja getOpcajaOrigen() {
        return opcajaOrigen;
    }

    public void setOpcajaOrigen(OperacionesCaja opcajaOrigen) {
        this.opcajaOrigen = opcajaOrigen;
    }

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
        this.data = data;
    }

   

    
}
