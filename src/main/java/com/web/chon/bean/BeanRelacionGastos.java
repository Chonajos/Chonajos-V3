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
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jramirez
 */
@Component
@Scope("session")
public class BeanRelacionGastos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;
    @Autowired
    private IfaceCaja ifaceCaja;

    private String title;
    private String viewEstate;

    private OperacionesCaja data;
    private UsuarioDominio usuario;
    private Caja caja;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<OperacionesCaja> listaOperaciones;
    private ArrayList<Caja> listaCajas;
    private ArrayList<ConceptosES> listaConceptos;
    private ArrayList<TipoOperacion> listaTiposOperaciones;

    private BigDecimal idSucursalBean;

    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;
    private boolean enableCalendar;
    
    private BigDecimal idStatusFk;
    private BigDecimal idSucursalFk;
    private BigDecimal idCajaFk;
    private BigDecimal idTipoOperacionFk;
    private BigDecimal idConceptoFk;
    private BigDecimal idCategoriaFk;

    private BigDecimal total;
    private StreamedContent variable;
    private int filtro;
    
    //--Constrantes--//
    private static final BigDecimal STATUS_REALIZADA = new BigDecimal(1);
    private static final BigDecimal STATUS_PENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUS_RECHAZADA = new BigDecimal(3);
    private static final BigDecimal STATUS_CANCELADA = new BigDecimal(4);

    @PostConstruct
    public void init() {
        idCategoriaFk= new BigDecimal(1);
        usuario = context.getUsuarioAutenticado();
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        idSucursalFk =new BigDecimal(usuario.getSucId());
        setTitle("Relación de Gastos");
        idCajaFk= caja.getIdCajaPk();
        listaCajas = ifaceCaja.getCajas();
        setViewEstate("init");
        idStatusFk= new BigDecimal(2);
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaConceptos =ifaceConceptos.getConceptosByIdCategoria(new BigDecimal(1));
        listaTiposOperaciones=new ArrayList<TipoOperacion>();
        listaTiposOperaciones = ifaceTiposOperacion.getOperacionesByIdCategoria(new BigDecimal(1));
        data = new OperacionesCaja();
        listaSucursales = ifaceCatSucursales.getSucursales();
        buscar();
        
    }
    public void rechazarGasto()
    {
        data.setIdStatusFk(STATUS_RECHAZADA);
        if(ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), data.getIdStatusFk(), data.getIdConceptoFk())==1)
        {
            JsfUtil.addSuccessMessageClean("Se ha rechazado el gasto correctamente");
        }
        else
        {
            JsfUtil.addErrorMessageClean("Ocurrió un error al rechazar el gasto");
        }
        buscar();
        
    }
    public void aceptarGasto()
    {
        data.setIdStatusFk(STATUS_REALIZADA);
        if(ifaceOperacionesCaja.updateStatusConcepto(data.getIdOperacionesCajaPk(), data.getIdStatusFk(), data.getIdConceptoFk())==1)
        {
            JsfUtil.addSuccessMessageClean("Se ha aceptado el gasto correctamente");
        }
        else
        {
            JsfUtil.addErrorMessageClean("Ocurrió un error al aceptar el gasto");
        }
        buscar();
    }
    public StreamedContent getProductImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        String imageType = "image/jpg";
        if(data.getFichero()==null)
        {
            JsfUtil.addErrorMessageClean("Esta operación no cuenta con comprobante");
            return variable;
        }
        else{
            variable = null;
            System.out.println("Data:" +data.toString());
             byte[] image = data.getFichero();
             variable = new DefaultStreamedContent(new ByteArrayInputStream(image), imageType, data.getIdOperacionesCajaPk().toString());
            return variable;
        }
           
    }
    public void verificarCombo() 
    {
        if (filtro == -1) {
            //se habilitan los calendarios.
            //fechaFiltroInicio = null;
            //fechaFiltroFin = null;
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
                    fechaFiltroFin = null;
                    fechaFiltroFin = null;
                    
                    break;
            }
            enableCalendar = true;
        }
    }

    public void buscar() 
    {
        listaOperaciones = ifaceOperacionesCaja.getOperacionesByCategoria(idCategoriaFk,  idSucursalFk, idCajaFk, idStatusFk, idConceptoFk, idTipoOperacionFk,TiempoUtil.getFechaDDMMYYYY(fechaFiltroInicio),TiempoUtil.getFechaDDMMYYYY(fechaFiltroFin));
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    public ArrayList<OperacionesCaja> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(ArrayList<OperacionesCaja> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
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

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
        this.data = data;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public BigDecimal getIdSucursalBean() {
        return idSucursalBean;
    }

    public void setIdSucursalBean(BigDecimal idSucursalBean) {
        this.idSucursalBean = idSucursalBean;
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

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public BigDecimal getIdSucursalFk() {
        return idSucursalFk;
    }

    public void setIdSucursalFk(BigDecimal idSucursalFk) {
        this.idSucursalFk = idSucursalFk;
    }

    public BigDecimal getIdCajaFk() {
        return idCajaFk;
    }

    public void setIdCajaFk(BigDecimal idCajaFk) {
        this.idCajaFk = idCajaFk;
    }

    public BigDecimal getIdTipoOperacionFk() {
        return idTipoOperacionFk;
    }

    public void setIdTipoOperacionFk(BigDecimal idTipoOperacionFk) {
        this.idTipoOperacionFk = idTipoOperacionFk;
    }

    public BigDecimal getIdConceptoFk() {
        return idConceptoFk;
    }

    public void setIdConceptoFk(BigDecimal idConceptoFk) {
        this.idConceptoFk = idConceptoFk;
    }

    public BigDecimal getIdCategoriaFk() {
        return idCategoriaFk;
    }

    public void setIdCategoriaFk(BigDecimal idCategoriaFk) {
        this.idCategoriaFk = idCategoriaFk;
    }

    public BigDecimal getIdStatusFk() {
        return idStatusFk;
    }

    public void setIdStatusFk(BigDecimal idStatusFk) {
        this.idStatusFk = idStatusFk;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
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

    public StreamedContent getVariable() {
        return variable;
    }

    public void setVariable(StreamedContent variable) {
        this.variable = variable;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }
    

}
