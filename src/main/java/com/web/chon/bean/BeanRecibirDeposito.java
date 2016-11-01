/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceOperacionesCuentas;
import com.web.chon.service.IfacePagosBancarios;
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
    private IfacePagosBancarios ifacePagosBancarios;
    
    
    @Autowired
    private PlataformaSecurityContext context;
    
    @Autowired 
    private IfaceOperacionesCuentas ifaceOperacionesCuentas;
    private UsuarioDominio usuario;
    private OperacionesCuentas opcuenta;
    private OperacionesCaja opcajaOrigen;


    private ArrayList<OperacionesCaja> lstDespositosEntrantes;
    private ArrayList<PagosBancarios> listaDepositosTransferencias;
    private OperacionesCaja data;
    private PagosBancarios data1;

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
    private static final BigDecimal statusAprobada = new BigDecimal(1);
    private static final BigDecimal statusRechazada = new BigDecimal(2);
    
    private OperacionesCaja opcaja;


    @PostConstruct
    public void init() 
    {
        
        
        usuario = context.getUsuarioAutenticado();
        setTitle("Recibir Depósitos de Cuentas");
        setViewEstate("init");
        data = new OperacionesCaja();
        opcuenta = new OperacionesCuentas();
        opcuenta.setIdUserFk(usuario.getIdUsuario());
        listaDepositosTransferencias = new ArrayList<PagosBancarios>();
        opcuenta.setEntradaSalida(entrada);
        
        lstDespositosEntrantes = new ArrayList<OperacionesCaja>();
        lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes();
        listaDepositosTransferencias = ifacePagosBancarios.getPagosPendientes();
        //caja = ifaceCaja.getCajaByIdUsuarioPk(usuarioDominio.getIdUsuario());
        opcaja = new OperacionesCaja();
        
        opcaja.setEntradaSalida(new BigDecimal(2));
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
    }
    public void aceptarDeposito()
    {
            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcaja.setMonto(data1.getMonto());
            opcaja.setIdConceptoFk(data1.getIdConceptoFk());
            opcaja.setIdCajaFk(data1.getIdCajaFk());
            opcaja.setIdUserFk(data1.getIdUserFk());
            //opcaja.setIdConceptoFk(idConceptoTransAprobada);
            
            opcaja.setIdStatusFk(statusAprobada);
            
            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) 
            {
                ifaceOperacionesCaja.updateStatusConcepto(data1.getIdOperacionCajaFk(), statusAprobada,data1.getIdConceptoFk());
                JsfUtil.addSuccessMessageClean("Transferencia Recibida Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al recibir la transferencia");
            }
        
    }
    
    public void aceptar() 
    {
            opcuenta.setIdOperacionCuenta(new BigDecimal(ifaceOperacionesCuentas.getNextVal()));
            opcuenta.setMonto(data.getMonto());
            opcuenta.setIdStatusFk(statusAprobada);
            opcuenta.setIdConceptoFk(data.getIdConceptoFk());
            if (ifaceOperacionesCuentas.insertaOperacion(opcuenta) == 1) 
            {
                ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), statusAprobada,data1.getIdConceptoFk());
                lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes();
                JsfUtil.addSuccessMessageClean("Se ha recibido el Depósito Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al recibir el Depósito");
            }

    }
    public void rechazarDeposito()
    {
        opcuenta.setIdOperacionCuenta(new BigDecimal(ifaceOperacionesCuentas.getNextVal()));
            opcuenta.setMonto(data.getMonto());
            opcuenta.setIdStatusFk(statusRechazada);
            opcuenta.setIdConceptoFk(data.getIdConceptoFk());
            if (ifaceOperacionesCuentas.insertaOperacion(opcuenta) == 1) 
            {
                ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), statusRechazada,data.getIdConceptoFk());
                lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes();
                JsfUtil.addSuccessMessageClean("Se ha rechazado el Depósito Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Ocurrió un error al rechazar el Depósito");
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

    public ArrayList<PagosBancarios> getListaDepositosTransferencias() {
        return listaDepositosTransferencias;
    }

    public void setListaDepositosTransferencias(ArrayList<PagosBancarios> listaDepositosTransferencias) {
        this.listaDepositosTransferencias = listaDepositosTransferencias;
    }

    public PagosBancarios getData1() {
        return data1;
    }

    public void setData1(PagosBancarios data1) {
        this.data1 = data1;
    }
    

    
    
    
    
   

    
}
