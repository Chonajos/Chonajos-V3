/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
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
public class BeanCorteCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    IfaceCorteCaja ifaceCorteCaja;

    private ArrayList<TipoOperacion> lstOperacionesEntrada;
    private ArrayList<TipoOperacion> lstOperacionesSalida;
    private ArrayList<OperacionesCaja> listaOperaciones;

    private ArrayList<OperacionesCaja> listaChequesEntrada;
    private ArrayList<OperacionesCaja> listaChequesSalida;

    private UsuarioDominio usuario;
    private Caja caja;
    private CorteCaja cj;
    private CorteCaja corteAnterior;
    private String title;
    private String viewEstate;

    private static final BigDecimal entrada = new BigDecimal(1);
    private static final BigDecimal salida = new BigDecimal(2);
    private BigDecimal saldoAnterior;
    private BigDecimal nuevoSaldo;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;
    private BigDecimal totalChequesEntradas;
    private BigDecimal totalChequesSalidas;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Realizar Corte de Caja");
        setViewEstate("init");
        caja = new Caja();
        corteAnterior = new CorteCaja();

        cj = new CorteCaja();
        totalEntradas = new BigDecimal(0);
        totalSalidas = new BigDecimal(0);
        saldoAnterior = new BigDecimal(0);
        nuevoSaldo = new BigDecimal(0);
        totalChequesEntradas = new BigDecimal(0);
        totalChequesSalidas = new BigDecimal(0);
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        corteAnterior = ifaceCorteCaja.getLastCorteByCaja(caja.getIdCajaPk());
        listaChequesEntrada = new ArrayList<OperacionesCaja>();
        listaChequesSalida = new ArrayList<OperacionesCaja>();
        listaOperaciones = new ArrayList<OperacionesCaja>();
        lstOperacionesEntrada = new ArrayList<TipoOperacion>();
        lstOperacionesSalida = new ArrayList<TipoOperacion>();
        lstOperacionesEntrada = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        lstOperacionesSalida = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        getsumaEntradas();
        getsumaSalidas();
         
        if (corteAnterior.getSaldoNuevo() == null) {
            corteAnterior.setSaldoNuevo(new BigDecimal(0));
        }
        saldoAnterior = corteAnterior.getSaldoNuevo();
        nuevoSaldo = totalEntradas.subtract(totalSalidas, MathContext.UNLIMITED);
        nuevoSaldo = nuevoSaldo.add(saldoAnterior, MathContext.UNLIMITED);
        listaChequesEntrada = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        listaChequesSalida = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        getsumaChequesEntradas();
        getsumaChequesSalidas();
    }

    public void reload() {
        lstOperacionesEntrada = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        lstOperacionesSalida = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        getsumaEntradas();
        getsumaSalidas();
        if (corteAnterior.getSaldoNuevo() == null) {
            corteAnterior.setSaldoNuevo(new BigDecimal(0));
        }
        saldoAnterior = corteAnterior.getSaldoNuevo();
        nuevoSaldo = totalEntradas.subtract(totalSalidas, MathContext.UNLIMITED);
        nuevoSaldo = nuevoSaldo.add(saldoAnterior, MathContext.UNLIMITED);
        listaChequesEntrada = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        listaChequesSalida = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        getsumaChequesEntradas();
        getsumaChequesSalidas();
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
    public void getsumaChequesEntradas() {
        for (OperacionesCaja t : listaChequesEntrada) {
            totalChequesEntradas = totalChequesEntradas.add(t.getMonto(), MathContext.UNLIMITED);
        }
    }

    public void getsumaChequesSalidas() {
        for (OperacionesCaja t : listaChequesSalida) {
            totalSalidas = totalSalidas.add(t.getMonto(), MathContext.UNLIMITED);
        }
    }

    public void generarCorte() {

        listaOperaciones = ifaceOperacionesCaja.getOperaciones(caja.getIdCajaPk(), usuario.getIdUsuario());
        boolean bandera = false;
        int llave = ifaceCorteCaja.getNextVal();
        cj.setIdCorteCajaPk(new BigDecimal(llave));
        cj.setSaldoNuevo(nuevoSaldo);
        cj.setIdCajaFk(caja.getIdCajaPk());
        cj.setIdStatusFk(new BigDecimal(1));
        cj.setIdUserFk(usuario.getIdUsuario());
        if (ifaceCorteCaja.insertCorte(cj) == 1) {
            for (OperacionesCaja oc : listaOperaciones) {
                if (ifaceOperacionesCaja.updateCorte(oc.getIdOperacionesCajaPk(), cj.getIdCorteCajaPk()) != 1) {
                    bandera = true;
                }
            }
            if (bandera != false) {
                JsfUtil.addErrorMessageClean("Ha ocurrido un error al generar el corte de caja");
            } else {
                reload();
                JsfUtil.addSuccessMessageClean("Se ha realizado el corte de caja correctamente");
            }
        } else {
            JsfUtil.addErrorMessageClean("Error al insertar corte de caja");
        }
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

    public CorteCaja getCj() {
        return cj;
    }

    public void setCj(CorteCaja cj) {
        this.cj = cj;
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

    public void setTotalSalidas(BigDecimal totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public ArrayList<OperacionesCaja> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(ArrayList<OperacionesCaja> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public static BigDecimal getEntrada() {
        return entrada;
    }

    public static BigDecimal getSalida() {
        return salida;
    }

    public BigDecimal getTotalSalidas() {
        return totalSalidas;
    }

    public CorteCaja getCorteAnterior() {
        return corteAnterior;
    }

    public void setCorteAnterior(CorteCaja corteAnterior) {
        this.corteAnterior = corteAnterior;
    }

    public ArrayList<OperacionesCaja> getListaChequesEntrada() {
        return listaChequesEntrada;
    }

    public void setListaChequesEntrada(ArrayList<OperacionesCaja> listaChequesEntrada) {
        this.listaChequesEntrada = listaChequesEntrada;
    }

    public ArrayList<OperacionesCaja> getListaChequesSalida() {
        return listaChequesSalida;
    }

    public void setListaChequesSalida(ArrayList<OperacionesCaja> listaChequesSalida) {
        this.listaChequesSalida = listaChequesSalida;
    }

    public BigDecimal getTotalChequesEntradas() {
        return totalChequesEntradas;
    }

    public void setTotalChequesEntradas(BigDecimal totalChequesEntradas) {
        this.totalChequesEntradas = totalChequesEntradas;
    }

    public BigDecimal getTotalChequesSalidas() {
        return totalChequesSalidas;
    }

    public void setTotalChequesSalidas(BigDecimal totalChequesSalidas) {
        this.totalChequesSalidas = totalChequesSalidas;
    }

    
}
