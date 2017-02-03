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
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTiposOperacion;
import com.web.chon.util.Constantes;
import com.web.chon.util.FileUtils;
import com.web.chon.util.JsfUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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
public class BeanPagoServicios implements Serializable {

    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;

    private ArrayList<ConceptosES> listaConceptos;
    //--Variables para Verificar Maximo en Caja --//
    private ArrayList<TipoOperacion> lstOperaciones;
    private ArrayList<Caja> listaSucursales;

    private String title;
    private String viewEstate;
    private String referencia;
    private String comentarios;

    private UsuarioDominio usuario;
    private Caja caja;
    private OperacionesCaja opcaja;
    private CorteCaja corteAnterior;

    private BigDecimal saldoAnterior;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;
    private BigDecimal totalServicio;
    private BigDecimal idConceptoBean;
    private BigDecimal idSucursalFk;
    private BigDecimal idOperacionBean;

    private static final BigDecimal ENTRADA = new BigDecimal(1);
    private static final BigDecimal SALIDA = new BigDecimal(2);
    private static final BigDecimal STATUS_REALIZADA = new BigDecimal(1);
    private static final BigDecimal STATUS_PENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUS_RECHAZADA = new BigDecimal(3);
    private static final BigDecimal STATUS_CANCELADA = new BigDecimal(4);

    private static final BigDecimal CERO = new BigDecimal(0).setScale(2, RoundingMode.CEILING);


    private byte[] bytes;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Pago de Servicios");
        setViewEstate("init");
        lstOperaciones = new ArrayList<TipoOperacion>();
        lstOperaciones = ifaceTiposOperacion.getOperacionesByIdCategoria(new BigDecimal(1));

        listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(idOperacionBean);

        //listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(new BigDecimal(1));
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuario.getIdUsuario());
        opcaja.setEntradaSalida(SALIDA);
        opcaja.setIdStatusFk(STATUS_PENDIENTE);

        //--Maximo Pago--//
       

        listaSucursales = new ArrayList<Caja>();
        listaSucursales = ifaceCaja.getSucursalesByIdCaja(caja.getIdCajaPk());
        

       

        
    }
    
    public void buscaConceptos()
    {
        System.out.println("Operacion: "+idOperacionBean);
          listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(idOperacionBean);
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
            opcaja.setFichero(bytes);
            JsfUtil.addSuccessMessageClean("El archivo " + event.getFile().getFileName().trim() + "fue cargado con éxito");
        } catch (IOException e) {
            JsfUtil.addErrorMessageClean("Ocurrio un error al cargar el archivo");
            e.printStackTrace();
        }

    }

    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void reset() {
        totalServicio = null;
        referencia = null;
        comentarios = null;
        idConceptoBean = null;
    }

    
    public void pagar() {
        //verificarDinero();

        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcaja.setMonto(totalServicio);
        opcaja.setComentarios(comentarios);
        opcaja.setIdConceptoFk(idConceptoBean);
        opcaja.setIdTipoOperacionFk(idOperacionBean);
        opcaja.setIdFormaPago(new BigDecimal(1));
        opcaja.setIdSucursalFk(idSucursalFk);

        if (caja.getIdCajaPk() != null) {
            if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {
                JsfUtil.addSuccessMessageClean("Pago de servicio registrado correctamente");
                reset();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al registrar el pago de servicio, contactar al administrador");
            }

        } else {
            JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
        }

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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getIdConceptoBean() {
        return idConceptoBean;
    }

    public void setIdConceptoBean(BigDecimal idConceptoBean) {
        this.idConceptoBean = idConceptoBean;
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

    public BigDecimal getTotalServicio() {
        return totalServicio;
    }

    public void setTotalServicio(BigDecimal totalServicio) {
        this.totalServicio = totalServicio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    
    public BigDecimal getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(BigDecimal totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public BigDecimal getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(BigDecimal totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public ArrayList<TipoOperacion> getLstOperaciones() {
        return lstOperaciones;
    }

    public void setLstOperaciones(ArrayList<TipoOperacion> lstOperaciones) {
        this.lstOperaciones = lstOperaciones;
    }

    public ArrayList<Caja> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Caja> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public CorteCaja getCorteAnterior() {
        return corteAnterior;
    }

    public void setCorteAnterior(CorteCaja corteAnterior) {
        this.corteAnterior = corteAnterior;
    }

    
    public BigDecimal getIdOperacionBean() {
        return idOperacionBean;
    }

    public void setIdOperacionBean(BigDecimal idOperacionBean) {
        this.idOperacionBean = idOperacionBean;
    }

}
