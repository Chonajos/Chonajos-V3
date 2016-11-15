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
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class BeanPagoServicios implements Serializable {

    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;

    private ArrayList<ConceptosES> listaConceptos;
    private String title;
    private String viewEstate;
    private BigDecimal idConceptoBean;

    private UsuarioDominio usuario;

    private Caja caja;
    private OperacionesCaja opcaja;

    private static final BigDecimal entradaSalida = new BigDecimal(2);
    private static final BigDecimal statusOperacion = new BigDecimal(1);

    private BigDecimal totalServicio;
    private String referencia;
    private String comentarios;

    //--Variables para Verificar Maximo en Caja --//
    private ArrayList<TipoOperacion> lstOperacionesEntrada;
    private ArrayList<TipoOperacion> lstOperacionesSalida;
    private ArrayList<Caja> listaSucursales;
    private CorteCaja corteAnterior;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    private BigDecimal saldoAnterior;
    private BigDecimal nuevoSaldo;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;
    private static final BigDecimal entrada = new BigDecimal(1);
    private static final BigDecimal salida = new BigDecimal(2);
    private BigDecimal idSucursalFk;
    //--Variables para Verificar Maximo en Caja --//

    @PostConstruct
    public void init() 
    {
        usuario = context.getUsuarioAutenticado();
        setTitle("Pago de Servicios");
        setViewEstate("init");
        
        listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(new BigDecimal(1));
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        opcaja = new OperacionesCaja();
        opcaja.setIdCajaFk(caja.getIdCajaPk());
        opcaja.setIdUserFk(usuario.getIdUsuario());
        opcaja.setEntradaSalida(entradaSalida);
        opcaja.setIdStatusFk(statusOperacion);
        
        //--Maximo Pago--//
        corteAnterior = new CorteCaja();
        corteAnterior = ifaceCorteCaja.getLastCorteByCaja(caja.getIdCajaPk());
        totalEntradas = new BigDecimal(0);
        totalSalidas = new BigDecimal(0);
        saldoAnterior = new BigDecimal(0);
        nuevoSaldo = new BigDecimal(0);
        listaSucursales = new ArrayList<Caja>();
        System.out.println("=================idCaja: "+caja.getIdCajaPk());
        listaSucursales = ifaceCaja.getSucursalesByIdCaja(caja.getIdCajaPk());

        
    }

    public void reset() {
        totalServicio = null;
        referencia = null;
        comentarios = null;
        idConceptoBean = null;
    }

    //----Funciones para Verificar Maximo de Dinero en Caja --//
    public void verificarDinero() {
        
//        lstOperacionesEntrada = new ArrayList<TipoOperacion>();
//        lstOperacionesSalida = new ArrayList<TipoOperacion>();
//        lstOperacionesEntrada = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
//        lstOperacionesSalida = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
//        getsumaEntradas();
//        getsumaSalidas();
//
//        if (corteAnterior.getSaldoNuevo() == null) 
//        {
//            corteAnterior.setSaldoNuevo(new BigDecimal(0));
//        }
//        saldoAnterior = corteAnterior.getSaldoNuevo();
//        nuevoSaldo = totalEntradas.subtract(totalSalidas, MathContext.UNLIMITED);
//        nuevoSaldo = nuevoSaldo.add(saldoAnterior, MathContext.UNLIMITED);
    }

    public void getsumaEntradas() {
        for (TipoOperacion t : lstOperacionesEntrada) {
            totalEntradas = totalEntradas.add(t.getMontoTotal(), MathContext.UNLIMITED);
        }
    }

    public void getsumaSalidas() {
        for (TipoOperacion t : lstOperacionesSalida) {
            totalSalidas = totalSalidas.add(t.getMontoTotal(), MathContext.UNLIMITED);
        }
    }

    //----Funciones para Verificar Maximo de Dinero en Caja --//
    public void pagar() {

//        verificarDinero();
//
//        if (nuevoSaldo.compareTo(totalServicio) >= 0) 
//        {
            opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcaja.setMonto(totalServicio);
            opcaja.setComentarios(comentarios);
            
            opcaja.setIdConceptoFk(idConceptoBean);
            opcaja.setIdSucursalFk(idSucursalFk);

            if (caja.getIdCajaPk() != null) 
            {
                if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {

                    JsfUtil.addSuccessMessageClean("Pago de Servicio Registrado Correctamente");
                    reset();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de servicio");
                }
            } else {
                JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
            }
//        } else {
//            JsfUtil.addErrorMessageClean("No hay suficiente dinero en caja");
//        }
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

    public BigDecimal getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(BigDecimal nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
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

    public ArrayList<TipoOperacion> getLstOperacionesEntrada() {
        return lstOperacionesEntrada;
    }

    public void setLstOperacionesEntrada(ArrayList<TipoOperacion> lstOperacionesEntrada) {
        this.lstOperacionesEntrada = lstOperacionesEntrada;
    }

    public ArrayList<TipoOperacion> getLstOperacionesSalida() {
        return lstOperacionesSalida;
    }

    public void setLstOperacionesSalida(ArrayList<TipoOperacion> lstOperacionesSalida) {
        this.lstOperacionesSalida = lstOperacionesSalida;
    }

    public ArrayList<Caja> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Caja> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    
    
}
