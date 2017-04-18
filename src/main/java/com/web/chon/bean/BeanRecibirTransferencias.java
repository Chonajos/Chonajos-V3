/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceOperacionesCaja;
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
public class BeanRecibirTransferencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;

    private UsuarioDominio usuario;

    private Caja caja;
    private OperacionesCaja opcaja;
    private OperacionesCaja opcajaDestino;
    private OperacionesCaja data;

    private ArrayList<OperacionesCaja> lstTransferenciasEntrantes;

    private String title;
    private String viewEstate;

    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private BigDecimal idConceptoBean;
    private BigDecimal idTipoOperacionBean;
    private BigDecimal monto;
    private String comentarios;
    private BigDecimal idCajaDestinoBean;

    private static final BigDecimal ENTRADA = new BigDecimal(1);
    private static final BigDecimal SALIDA = new BigDecimal(2);

    private static final BigDecimal STATUS_REALIZADA = new BigDecimal(1);
    private static final BigDecimal STATUS_PENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUS_RECHAZADA = new BigDecimal(3);
    private static final BigDecimal STATUS_CANCELADA = new BigDecimal(4);

    private static final BigDecimal EFECTIVO = new BigDecimal(1);
    private static final BigDecimal TRANSFERENCIA = new BigDecimal(2);
    private static final BigDecimal CHEQUE = new BigDecimal(3);
    private static final BigDecimal DEPOSITO = new BigDecimal(4);

    private static final BigDecimal CONCEPTO_TRANSFERENCIA = new BigDecimal(5);
    private static final BigDecimal OPERACION_TRANSFERENCIA = new BigDecimal(2);

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Recibir Transferencias de Caja");
        setViewEstate("init");
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuario.getIdUsuario());
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getSucId()));

        opcaja.setEntradaSalida(ENTRADA);
        data = new OperacionesCaja();
        lstTransferenciasEntrantes = new ArrayList<OperacionesCaja>();
        lstTransferenciasEntrantes = ifaceOperacionesCaja.getTransferenciasEntrantes(opcaja.getIdCajaFk());
    }

    public void aceptar() {
        
        if (caja.getIdCajaPk() != null) {

            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcaja.setMonto(data.getMonto());
            opcaja.setIdConceptoFk(data.getIdConceptoFk());
            opcaja.setIdTipoOperacionFk(data.getIdTipoOperacionFk());
            opcaja.setIdFormaPago(data.getIdFormaPago());
            opcaja.setComentarios("SISTEMA: TRANSFERENCIA: OC:" + data.getIdOperacionesCajaPk());

            opcaja.setIdStatusFk(STATUS_REALIZADA);
            
            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), STATUS_REALIZADA, data.getIdConceptoFk());
                lstTransferenciasEntrantes = ifaceOperacionesCaja.getTransferenciasEntrantes(opcaja.getIdCajaFk());
                JsfUtil.addSuccessMessageClean("Transferencia Recibida Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al recibir la transferencia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
        }
        init();

    }

    public void rechazarTransferencia() {
        if (caja.getIdCajaPk() != null) {
//            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
//            opcaja.setMonto(data.getMonto());
//            opcaja.setIdConceptoFk(data.getIdConceptoFk());
//            
//            opcaja.setIdStatusFk(STATUS_RECHAZADA);
//            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) 
//            {
            System.out.println("=======" + data.getIdOperacionesCajaPk());
            if (ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), STATUS_RECHAZADA, data.getIdConceptoFk()) == 1) {
                lstTransferenciasEntrantes = ifaceOperacionesCaja.getTransferenciasEntrantes(opcaja.getIdCajaFk());
                JsfUtil.addSuccessMessageClean("Transferencia Rechazada Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al rechazar la transferencia");
            }
        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar la operación");
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

    public OperacionesCaja getOpcaja() {
        return opcaja;
    }

    public void setOpcaja(OperacionesCaja opcaja) {
        this.opcaja = opcaja;
    }

    public OperacionesCaja getOpcajaDestino() {
        return opcajaDestino;
    }

    public void setOpcajaDestino(OperacionesCaja opcajaDestino) {
        this.opcajaDestino = opcajaDestino;
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

    public ArrayList<OperacionesCaja> getLstTransferenciasEntrantes() {
        return lstTransferenciasEntrantes;
    }

    public void setLstTransferenciasEntrantes(ArrayList<OperacionesCaja> lstTransferenciasEntrantes) {
        this.lstTransferenciasEntrantes = lstTransferenciasEntrantes;
    }

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
        this.data = data;
    }

}
