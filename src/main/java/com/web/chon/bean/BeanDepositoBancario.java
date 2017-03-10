/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.CuentaBancaria;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceComprobantes;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceCuentasBancarias;

import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.util.JsfUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanDepositoBancario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCuentasBancarias ifaceCuentasBancarias;
    @Autowired
    private IfaceComprobantes ifaceComprobantes;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;

    private UsuarioDominio usuario;
    private Caja caja;

    private String title;
    private String viewEstate;

    private OperacionesCaja opcajaOrigen;
    private OperacionesCaja opcajaDestino;
    private ArrayList<CuentaBancaria> listaCuentas;

    private static final BigDecimal SALIDA = new BigDecimal(2);
    private static final BigDecimal CONCEPTO = new BigDecimal(10);
    private static final BigDecimal STATUS_PENDIENTE = new BigDecimal(2);
    private static final BigDecimal OPERACION = new BigDecimal(7);
    private static final BigDecimal EFECTIVO = new BigDecimal(1);

    private static final BigDecimal IMAGEN_TIPO_DEPOSITO = new BigDecimal(4);

    private BigDecimal monto;
    private String comentarios;
    private BigDecimal idCuentaDestinoBean;
    private byte[] bytes;
    ComprobantesDigitales cd;

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
        opcajaOrigen.setEntradaSalida(SALIDA);
        opcajaOrigen.setIdStatusFk(STATUS_PENDIENTE);
        opcajaOrigen.setIdSucursalFk(new BigDecimal(usuario.getSucId()));
        cd = new ComprobantesDigitales();

    }

    public void depositar() throws SQLException {

        if (validarSaldo(1, monto, caja.getIdCajaPk())) {

            opcajaOrigen.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcajaOrigen.setMonto(monto);
            opcajaOrigen.setComentarios(comentarios);
            opcajaOrigen.setIdConceptoFk(CONCEPTO);
            opcajaOrigen.setIdTipoOperacionFk(OPERACION);
            opcajaOrigen.setIdFormaPago(EFECTIVO);

            opcajaOrigen.setIdCuentaDestinoFk(idCuentaDestinoBean);

            if (caja.getIdCajaPk() != null) {
                if (ifaceOperacionesCaja.insertaOperacion(opcajaOrigen) == 1) {
                    cd.setIdTipoFk(IMAGEN_TIPO_DEPOSITO);
                    cd.setIdLlaveFk(opcajaOrigen.getIdOperacionesCajaPk());
                    //Primero verificamos que ya exista imagen para ese folio
                    ComprobantesDigitales cdi = new ComprobantesDigitales();
                    cdi = ifaceComprobantes.getComprobanteByIdTipoLlave(cd.getIdTipoFk(), cd.getIdLlaveFk());
                    System.out.println("CDI: " + cdi.toString());
                    if (cdi == null || cdi.getIdComprobantesDigitalesPk() == null) {
                        cd.setIdComprobantesDigitalesPk(new BigDecimal(ifaceComprobantes.getNextVal()));
                        if (cd.getFichero() != null) {
                            if (ifaceComprobantes.insertaComprobante(cd) == 1) {

                                if (ifaceComprobantes.insertarImagen(cd.getIdComprobantesDigitalesPk(), cd.getFichero()) == 1) {
                                    System.out.println("Se inserto la imagen correctamente");
                                } else {
                                    JsfUtil.addErrorMessageClean("1.- Ha ocurrido un error al subir la imagen");
                                }

                            }

                            monto = null;
                            comentarios = null;
                            idCuentaDestinoBean = null;
                            JsfUtil.addSuccessMessageClean("Depósito Registrado Correctamente");

                        } else {
                            JsfUtil.addErrorMessageClean("Ingresa un comprobante digital");
                        }
                    } else {
                        JsfUtil.addErrorMessageClean("Error, ya se ha subido imagen para este número de folio");
                    }

                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el depósito");
                }
            } else {
                JsfUtil.addErrorMessageClean("No cuenta con caja");
            }

        } else {
            JsfUtil.addErrorMessageClean("Saldo a Depositar es Mayor al Saldo en Caja.");
        }

    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        InputStream inputStr = null;
        try {

            inputStr = uploadedFile.getInputstream();
        } catch (IOException e) {
            JsfUtil.addErrorMessage("No se permite guardar valores nulos.");
            manageException(e);
        }

        try {
            bytes = IOUtils.toByteArray(inputStr);
            cd.setFichero(bytes);
            JsfUtil.addSuccessMessageClean("El archivo " + event.getFile().getFileName().trim() + "fue cargado con éxito");
        } catch (IOException e) {
            JsfUtil.addErrorMessageClean("Ocurrio un error al cargar el archivo");
            e.printStackTrace();
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

        }

        return valido;

    }

    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
