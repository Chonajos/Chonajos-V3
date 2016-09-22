/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCuentasBancarias;

import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.util.JsfUtil;
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
public class BeanDepositoBancario {
    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired 
    private IfaceCuentasBancarias ifaceCuentasBancarias;
    
    private UsuarioDominio usuario;
    private Caja caja;
    
    private String title;
    private String viewEstate;
    
    private OperacionesCaja opcajaOrigen;
    private OperacionesCaja opcajaDestino;
    private ArrayList<CuentaBancaria> listaCuentas;
    
    private static final BigDecimal salida = new BigDecimal(2);
    private static final BigDecimal concepto = new BigDecimal(14);
    private static final BigDecimal statusOperacion = new BigDecimal(2);
    private BigDecimal monto;
    private String comentarios;
    private BigDecimal idCuentaDestinoBean;
    
    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Realizar Déposito Bancario");
        setViewEstate("init");
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        listaCuentas = ifaceCuentasBancarias.getCuentas();
        opcajaOrigen = new OperacionesCaja();
        opcajaOrigen.setIdCajaFk(caja.getIdCajaPk());
        opcajaOrigen.setIdUserFk(usuario.getIdUsuario());
        opcajaOrigen.setEntradaSalida(salida);
        opcajaOrigen.setIdStatusFk(statusOperacion);
        
    }
    public void depositar() {
        opcajaOrigen.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcajaOrigen.setMonto(monto);
        opcajaOrigen.setComentarios(comentarios);
        opcajaOrigen.setIdConceptoFk(concepto);
        opcajaOrigen.setIdCuentaDestinoFk(idCuentaDestinoBean);

        if (caja.getIdCajaPk() != null) {
            if (ifaceOperacionesCaja.insertaOperacion(opcajaOrigen) == 1) 
            {
                JsfUtil.addSuccessMessageClean("Depósito Registrado Correctamente");
                monto = null;
                comentarios=null;
                idCuentaDestinoBean = null;
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el depósito");
            }
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
        }
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

    public OperacionesCaja getOpcajaOrigen() {
        return opcajaOrigen;
    }

    public void setOpcajaOrigen(OperacionesCaja opcajaOrigen) {
        this.opcajaOrigen = opcajaOrigen;
    }

    public OperacionesCaja getOpcajaDestino() {
        return opcajaDestino;
    }

    public void setOpcajaDestino(OperacionesCaja opcajaDestino) {
        this.opcajaDestino = opcajaDestino;
    }

    public ArrayList<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<CuentaBancaria> listaCuentas) {
        this.listaCuentas = listaCuentas;
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

    public BigDecimal getIdCuentaDestinoBean() {
        return idCuentaDestinoBean;
    }

    public void setIdCuentaDestinoBean(BigDecimal idCuentaDestinoBean) {
        this.idCuentaDestinoBean = idCuentaDestinoBean;
    }
    
    
    
    
}
