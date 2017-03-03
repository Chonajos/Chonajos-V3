/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.OperacionesCuentas;
import com.web.chon.dominio.PagosBancarios;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceOperacionesCuentas;
import com.web.chon.service.IfacePagosBancarios;
import com.web.chon.util.JsfUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("session")
public class BeanRecibirDeposito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfacePagosBancarios ifacePagosBancarios;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
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
    private String viewEstateCombo;

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

    //---Status Pagos Bancarios---//
    private static final BigDecimal STATUS_APROBADA = new BigDecimal(3);

    //
    private BigDecimal idCombo;

    private OperacionesCaja opcaja;
    private ArrayList<Sucursal> listaSucursales;
    private BigDecimal idSucursalFK;
    private StreamedContent variable;

    @PostConstruct
    public void init() {
        idCombo = new BigDecimal(1);
        usuario = context.getUsuarioAutenticado();
        setTitle("Autorizar Depósitos y Transferencias Bancarias");
        setViewEstate("init");
        data = new OperacionesCaja();
        opcuenta = new OperacionesCuentas();
        opcuenta.setIdUserFk(usuario.getIdUsuario());
        listaDepositosTransferencias = new ArrayList<PagosBancarios>();
        opcuenta.setEntradaSalida(ENTRADA);
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();

        lstDespositosEntrantes = new ArrayList<OperacionesCaja>();

        //caja = ifaceCaja.getCajaByIdUsuarioPk(usuarioDominio.getIdUsuario());
        opcaja = new OperacionesCaja();

        opcaja.setEntradaSalida(new BigDecimal(2));
        opcaja.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        idSucursalFK = opcaja.getIdSucursalFk();
        changeTable();
        buscar();
    }

    public void changeTable() {
        buscar();
        if (idCombo != null) 
        {
            if (idCombo.intValue() == 1) {
                viewEstateCombo = "1";
            } else {
                viewEstateCombo = "2";
            }
        }
        else{viewEstateCombo = null;}
    }

    public void buscar() {
        lstDespositosEntrantes.clear();
        listaDepositosTransferencias.clear();
        lstDespositosEntrantes = ifaceOperacionesCaja.getDepositosEntrantes(idSucursalFK);

        listaDepositosTransferencias = ifacePagosBancarios.getPagosPendientes(idSucursalFK);
    }

    public StreamedContent getProductImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        String imageType = "image/jpg";
        if (data.getFichero() == null) {
            JsfUtil.addErrorMessageClean("Esta operación no cuenta con comprobante");
            return variable;
        } else {
            variable = null;
            System.out.println("Data:" + data.toString());
            byte[] image = data.getFichero();
            variable = new DefaultStreamedContent(new ByteArrayInputStream(image), imageType, data.getIdOperacionesCajaPk().toString());
            return variable;
        }

    }

    public void aceptarDeposito() {
        System.out.println("Data 1: " + data1);
        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcaja.setIdCajaFk(data1.getIdCajaFk());
        opcaja.setIdConceptoFk(data1.getIdConceptoFk());
        opcaja.setIdStatusFk(STATUS_REALIZADA);
        opcaja.setIdUserFk(data1.getIdUserFk());
        opcaja.setMonto(data1.getMonto());
        opcaja.setEntradaSalida(SALIDA);
        opcaja.setComentarios("SISTEMA: Pago Bancario : OC: " + data1.getIdOperacionCajaFk());
        OperacionesCaja opTemporal = new OperacionesCaja();
        opTemporal = ifaceOperacionesCaja.getOperacionByIdPk(data1.getIdOperacionCajaFk());

        opcaja.setIdTipoOperacionFk(opTemporal.getIdTipoOperacionFk());
        opcaja.setIdFormaPago(opTemporal.getIdFormaPago());
        //opcaja.setIdSucursalFk();
        //opcaja.setIdConceptoFk(idConceptoTransAprobada);

        if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
            System.out.println("Se cambio concepto de operacion de caja");
            ifaceOperacionesCaja.updateStatusConcepto(data1.getIdOperacionCajaFk(), STATUS_REALIZADA, data1.getIdConceptoFk());
            data1.setIdStatusFk(STATUS_APROBADA);
            if (ifacePagosBancarios.updatePagoBancario(data1) == 1) {
                System.out.println("Se actualizo el pago bancario.");

                JsfUtil.addSuccessMessageClean("Transacción Recibida Correctamente");
            } else {
                JsfUtil.addErrorMessageClean("3-Ocurrió un error al recibir la transferencia o depósito bancario");
            }
        } else {
            JsfUtil.addErrorMessageClean("1-Ocurrió un error al recibir la transferencia o depósito bancario");
        }
        buscar();
    }

    public void aceptar() {
        opcuenta.setIdOperacionCuenta(new BigDecimal(ifaceOperacionesCuentas.getNextVal()));
        opcuenta.setMonto(data.getMonto());
        opcuenta.setIdStatusFk(STATUS_REALIZADA);
        opcuenta.setIdConceptoFk(data.getIdConceptoFk());
        System.out.println("Data :" + data.toString());
        if (ifaceOperacionesCuentas.insertaOperacion(opcuenta) == 1) {
            if (ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), STATUS_REALIZADA, data.getIdConceptoFk()) == 1) {
                JsfUtil.addSuccessMessageClean("Se ha recibido el Depósito Correctamente");
                buscar();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al cambiar de estatus la operacion");
            }
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error al recibir el Depósito");
        }

    }

    public void rechazarDeposito() {
        opcuenta.setIdOperacionCuenta(data1.getIdTransBancariasPk());
        opcuenta.setMonto(data1.getMonto());
        opcuenta.setIdStatusFk(STATUS_CANCELADA);
        opcuenta.setIdConceptoFk(data1.getIdConceptoFk());
        if (ifaceOperacionesCuentas.updateOperacion(opcuenta) == 1) {
            ifaceOperacionesCaja.updateStatusConcepto(data1.getIdOperacionCajaFk(), STATUS_RECHAZADA, data1.getIdConceptoFk());
            buscar();
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public BigDecimal getIdSucursalFK() {
        return idSucursalFK;
    }

    public void setIdSucursalFK(BigDecimal idSucursalFK) {
        this.idSucursalFK = idSucursalFK;
    }

    public StreamedContent getVariable() {
        return variable;
    }

    public void setVariable(StreamedContent variable) {
        this.variable = variable;
    }

    public BigDecimal getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(BigDecimal idCombo) {
        this.idCombo = idCombo;
    }

    public String getViewEstateCombo() {
        return viewEstateCombo;
    }

    public void setViewEstateCombo(String viewEstateCombo) {
        this.viewEstateCombo = viewEstateCombo;
    }

}
