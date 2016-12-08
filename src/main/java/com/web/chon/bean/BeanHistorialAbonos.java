/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Caja;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTipoAbono;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class BeanHistorialAbonos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private PlataformaSecurityContext context;
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceOperacionesCaja IfaceOperacionesCaja;

    private ArrayList<Cliente> lstCliente;
    private ArrayList<Usuario> lstUsuariosCaja;
    private ArrayList<AbonoCredito> lstAbonosCreditos;
    private ArrayList<TipoAbono> lstTipoAbonos;

    private BigDecimal idClienteBeanFk;
    private BigDecimal idCobradorFk;
    private BigDecimal idTipoAbonoFk;
    private BigDecimal idAbonoPk;
    private BigDecimal idCreditoFk;

    private Date fechaFiltroInicio;
    private Date fechaFiltroFin;

    private String title;
    private String viewEstate;

    private Cliente cliente;
    private UsuarioDominio usuarioDominio;

    private boolean enableCalendar;
    private BigDecimal total;
    AbonoCredito abono;
    
    private static final BigDecimal conceptoAbonoEfectivo = new BigDecimal(7);
    private static final BigDecimal conceptoAbonoTransferencia = new BigDecimal(12);
    private static final BigDecimal conceptoAbonoCheque = new BigDecimal(30);
    private static final BigDecimal conceptoAbonoDeposito = new BigDecimal(31);
    
    private static final BigDecimal salida = new BigDecimal(2);
    @PostConstruct
    public void init() 
    {
         abono= new AbonoCredito();
        total = new BigDecimal(0);
        setTitle("Historial de Abonos");
        setViewEstate("init");
        lstCliente = new ArrayList<Cliente>();
        lstUsuariosCaja = new ArrayList<Usuario>();
        lstUsuariosCaja = IfaceOperacionesCaja.getResponsables(new BigDecimal(-1));
        lstAbonosCreditos = new ArrayList<AbonoCredito>();
        lstTipoAbonos = new ArrayList<TipoAbono>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        idClienteBeanFk = null;
        usuarioDominio = context.getUsuarioAutenticado();
        idCobradorFk = usuarioDominio.getIdUsuario();
        fechaFiltroInicio = context.getFechaSistema();
        fechaFiltroFin = context.getFechaSistema();
        cliente = new Cliente();
        //idTipoAbonoFk = null;
        if (cliente == null) {
            cliente = new Cliente();

        }
        lstAbonosCreditos = ifaceAbonoCredito.getHistorialAbonos(cliente.getId_cliente(), idCobradorFk, fechaFiltroInicio, fechaFiltroFin, idTipoAbonoFk, idAbonoPk, idCreditoFk);
        sumaTotal();
    }
    public void cancelarAbono()
    {
        //cambiar estatus de abono a cancelado = 2
        //cambiar el estatus de credito a o finalizado
        //insertar una operacion de caja de salida ( abono credito) 
        //en caso de haber documento por cobrar cambiar el estatus del documento por cobrar a cancelado
        
        System.out.println("Abono a Eliminar: "+abono.toString());
        //ifaceAbonoCredito.delete(abono.getIdAbonoCreditoPk());
        
        //ifaceCredito.activarCredito(abono.getIdCreditoFk());
        
    }

    public void buscar() {
        if (cliente == null) {
            cliente = new Cliente();

        }
        lstAbonosCreditos = ifaceAbonoCredito.getHistorialAbonos(cliente.getId_cliente(), idCobradorFk, fechaFiltroInicio, fechaFiltroFin, idTipoAbonoFk, idAbonoPk, idCreditoFk);
        sumaTotal();
    }

    public void sumaTotal() {
        total = new BigDecimal(0);
        for (AbonoCredito abono : lstAbonosCreditos) {
            total = total.add(abono.getMontoAbono(), MathContext.UNLIMITED);
        }
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public BigDecimal getIdClienteBeanFk() {
        return idClienteBeanFk;
    }

    public void setIdClienteBeanFk(BigDecimal idClienteBeanFk) {
        this.idClienteBeanFk = idClienteBeanFk;
    }

    public BigDecimal getIdCobradorFk() {
        return idCobradorFk;
    }

    public void setIdCobradorFk(BigDecimal idCobradorFk) {
        this.idCobradorFk = idCobradorFk;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public ArrayList<Usuario> getLstUsuariosCaja() {
        return lstUsuariosCaja;
    }

    public void setLstUsuariosCaja(ArrayList<Usuario> lstUsuariosCaja) {
        this.lstUsuariosCaja = lstUsuariosCaja;
    }

    public boolean isEnableCalendar() {
        return enableCalendar;
    }

    public void setEnableCalendar(boolean enableCalendar) {
        this.enableCalendar = enableCalendar;
    }

    public BigDecimal getIdTipoAbonoFk() {
        return idTipoAbonoFk;
    }

    public void setIdTipoAbonoFk(BigDecimal idTipoAbonoFk) {
        this.idTipoAbonoFk = idTipoAbonoFk;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public ArrayList<AbonoCredito> getLstAbonosCreditos() {
        return lstAbonosCreditos;
    }

    public void setLstAbonosCreditos(ArrayList<AbonoCredito> lstAbonosCreditos) {
        this.lstAbonosCreditos = lstAbonosCreditos;
    }

    public BigDecimal getIdAbonoPk() {
        return idAbonoPk;
    }

    public void setIdAbonoPk(BigDecimal idAbonoPk) {
        this.idAbonoPk = idAbonoPk;
    }

    public BigDecimal getIdCreditoFk() {
        return idCreditoFk;
    }

    public void setIdCreditoFk(BigDecimal idCreditoFk) {
        this.idCreditoFk = idCreditoFk;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public AbonoCredito getAbono() {
        return abono;
    }

    public void setAbono(AbonoCredito abono) {
        this.abono = abono;
    }
    
    

}
