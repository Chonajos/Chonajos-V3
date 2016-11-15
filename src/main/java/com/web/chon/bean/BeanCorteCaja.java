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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
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
    private IfaceCorteCaja ifaceCorteCaja;

    private ArrayList<TipoOperacion> lstOperacionesEntrada;
    private ArrayList<TipoOperacion> lstOperacionesSalida;
    private ArrayList<OperacionesCaja> listaOperaciones;
    private ArrayList<OperacionesCaja> listaDetalleEntradas;
    private ArrayList<OperacionesCaja> listaDetalleEntradasEfectivo;
    private ArrayList<OperacionesCaja> listaDetalleEntradasCheques;
    private ArrayList<OperacionesCaja> listaDetalleEntradasCuentasBancarias;
    private ArrayList<OperacionesCaja> listaDetalleSalidas;
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
    private static final BigDecimal statusAplicado = new BigDecimal(1);
    private static final BigDecimal statusPendiente = new BigDecimal(2);
    private static final BigDecimal statusRechazado = new BigDecimal(3);
    private static final BigDecimal cero = new BigDecimal(0).setScale(2, RoundingMode.CEILING);

    private BigDecimal saldoAnteriorEfectivo;
    private BigDecimal saldoAnteriorCheques;
    private BigDecimal saldoAnteriorCuentas;
    private BigDecimal nuevoSaldo;
    private BigDecimal nuevoSaldoCheques;
    private BigDecimal nuevoSaldoCuentas;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;
    private BigDecimal totalChequesEntradas;
    private BigDecimal totalChequesSalidas;
    private BigDecimal totalEfectivo;
    private BigDecimal totalCheques;
    private BigDecimal totalCuentasBancarias;

    private BigDecimal sumaMegaAnterior;
    private BigDecimal sumaMegaActual;

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Realizar Corte de Caja");
        setViewEstate("init");
        caja = new Caja();
        corteAnterior = new CorteCaja();

        cj = new CorteCaja();
        totalEntradas = cero;
        totalSalidas = cero;
        saldoAnteriorEfectivo = cero;
        nuevoSaldo = cero;
        saldoAnteriorCheques = cero;
        totalChequesEntradas = cero;
        totalChequesSalidas = cero;
        nuevoSaldoCuentas = cero;
        nuevoSaldoCheques = cero;
        saldoAnteriorCuentas = cero;
        totalChequesEntradas = cero;
        totalChequesSalidas = cero;

        totalEfectivo = cero;
        totalCheques = cero;
        totalCuentasBancarias = cero;
        sumaMegaAnterior = cero;
        sumaMegaActual = cero;

        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        corteAnterior = ifaceCorteCaja.getLastCorteByCaja(caja.getIdCajaPk());
        listaChequesEntrada = new ArrayList<OperacionesCaja>();
        listaChequesSalida = new ArrayList<OperacionesCaja>();
        listaOperaciones = new ArrayList<OperacionesCaja>();
        lstOperacionesEntrada = new ArrayList<TipoOperacion>();
        lstOperacionesSalida = new ArrayList<TipoOperacion>();
        lstOperacionesEntrada = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        lstOperacionesSalida = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        listaDetalleEntradasEfectivo = new ArrayList<OperacionesCaja>();
        listaDetalleEntradasCheques = new ArrayList<OperacionesCaja>();
        listaDetalleEntradasCuentasBancarias = new ArrayList<OperacionesCaja>();

        getsumaEntradas();
        getsumaSalidas();

        if (corteAnterior.getSaldoNuevo() == null) {
            corteAnterior.setSaldoNuevo(cero);
        }
        saldoAnteriorEfectivo = corteAnterior.getSaldoNuevo();
        saldoAnteriorCheques = corteAnterior.getMontoChequesNuevos();
        saldoAnteriorCuentas = corteAnterior.getMontoCuentaNuevo();
        if (saldoAnteriorCheques == null) {
            saldoAnteriorCheques = cero;
        }
        if (saldoAnteriorEfectivo == null) {
            saldoAnteriorEfectivo = cero;
        }
        if (saldoAnteriorCuentas == null) {
            saldoAnteriorCuentas = cero;
        }

        //nuevoSaldo = totalEntradas.subtract(totalSalidas, MathContext.UNLIMITED);
        //nuevoSaldo = nuevoSaldo.add(saldoAnterior, MathContext.UNLIMITED);
        setDetalle();
        sumaMegas();

    }

    public void viewDetallesEfectivo() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("detallesEfectivo", options, null);
    }

    public void changeView() {
        setViewEstate("init");
    }

    public void reload() {
        lstOperacionesEntrada = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        lstOperacionesSalida = ifaceOperacionesCaja.getOperacionesCorteBy(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        getsumaEntradas();
        getsumaSalidas();
        if (corteAnterior.getSaldoNuevo() == null) {
            corteAnterior.setSaldoNuevo(new BigDecimal(0));
        }
        //saldoAnterior = corteAnterior.getSaldoNuevo();
        //nuevoSaldo = totalEntradas.subtract(totalSalidas, MathContext.UNLIMITED);
        //nuevoSaldo = nuevoSaldo.add(saldoAnterior, MathContext.UNLIMITED);
        listaChequesEntrada = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada);
        listaChequesSalida = ifaceOperacionesCaja.getCheques(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida);
        //getsumaChequesEntradas();
        //getsumaChequesSalidas();
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

    public void sumaMegas() {
        sumaMegaAnterior = sumaMegaAnterior.add(saldoAnteriorEfectivo, MathContext.UNLIMITED);
        sumaMegaAnterior = sumaMegaAnterior.add(saldoAnteriorCheques, MathContext.UNLIMITED);
        sumaMegaAnterior = sumaMegaAnterior.add(saldoAnteriorCuentas, MathContext.UNLIMITED);

        sumaMegaActual = sumaMegaActual.add(nuevoSaldo, MathContext.UNLIMITED);
        sumaMegaActual = sumaMegaActual.add(nuevoSaldoCheques, MathContext.UNLIMITED);
        sumaMegaActual = sumaMegaActual.add(nuevoSaldoCuentas, MathContext.UNLIMITED);
    }

    public void setDetalle() {
        listaDetalleEntradas = ifaceOperacionesCaja.getDetalles(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada, statusAplicado);
        listaDetalleSalidas = ifaceOperacionesCaja.getDetalles(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida, statusAplicado);
        System.out.println("----------------------------------------------------------------------");
        listaDetalleEntradasCuentasBancarias = new ArrayList<OperacionesCaja>();
        listaDetalleEntradasCheques = new ArrayList<OperacionesCaja>();
        listaDetalleEntradasEfectivo = new ArrayList<OperacionesCaja>();
        for (OperacionesCaja ope : listaDetalleEntradas) {
            System.out.println("Operacion: " + ope);
            if (ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13) {
                listaDetalleEntradasEfectivo.add(ope);
                totalEfectivo = totalEfectivo.add(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {
                listaDetalleEntradasCheques.add(ope);
                totalCheques = totalCheques.add(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {
                listaDetalleEntradasCuentasBancarias.add(ope);
                totalCuentasBancarias = totalCuentasBancarias.add(ope.getMonto(), MathContext.UNLIMITED);
            }
            System.out.println("*******************************************************");
        }

        //-- lo mismo para salidas---
        System.out.println("Comenzando con salidas");
        for (OperacionesCaja ope : listaDetalleSalidas) {
            System.out.println("Operacion: " + ope);
            if (ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 1 || ope.getIdConceptoFk().intValue() == 3 || ope.getIdConceptoFk().intValue() == 4 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13 || ope.getIdConceptoFk().intValue() == 2 || ope.getIdConceptoFk().intValue() == 25 || ope.getIdConceptoFk().intValue() == 24
                    || ope.getIdConceptoFk().intValue() == 21 || ope.getIdConceptoFk().intValue() == 20 || ope.getIdConceptoFk().intValue() == 23
                    || ope.getIdConceptoFk().intValue() == 22) {
                listaDetalleEntradasEfectivo.add(ope);
                System.out.println("monto: " + ope.getMonto());
                totalEfectivo = totalEfectivo.subtract(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {
                listaDetalleEntradasCheques.add(ope);
                totalCheques = totalCheques.subtract(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {
                listaDetalleEntradasCuentasBancarias.add(ope);
                totalCuentasBancarias = totalCuentasBancarias.subtract(ope.getMonto(), MathContext.UNLIMITED);
            }
            System.out.println("*******************************************************");
        }
        System.out.println("Total Efectivo: " + totalEfectivo);
        nuevoSaldo = totalEfectivo.add(saldoAnteriorEfectivo, MathContext.UNLIMITED);
        //nuevoSaldo = nuevoSaldo.subtract(totalSalidas, MathContext.UNLIMITED);
        nuevoSaldoCheques = totalCheques.add(saldoAnteriorCheques, MathContext.UNLIMITED);
        nuevoSaldoCuentas = totalCuentasBancarias.add(saldoAnteriorCuentas, MathContext.UNLIMITED);
    }

    public void verDetalle() {

        setViewEstate("second");
    }

    public void generarCorte() {

        listaOperaciones = ifaceOperacionesCaja.getOperaciones(caja.getIdCajaPk(), usuario.getIdUsuario());
        boolean bandera = false;
        int llave = ifaceCorteCaja.getNextVal();
        cj.setIdCorteCajaPk(new BigDecimal(llave));

        cj.setIdCajaFk(caja.getIdCajaPk());
        cj.setIdStatusFk(new BigDecimal(1));
        cj.setIdUserFk(usuario.getIdUsuario());

        cj.setMontoChequesAnt(saldoAnteriorCheques);
        cj.setMontoCuentaAnterior(saldoAnteriorCuentas);
        cj.setSaldoAnterior(saldoAnteriorEfectivo);

        cj.setSaldoNuevo(nuevoSaldo);
        cj.setMontoChequesNuevos(nuevoSaldoCheques);
        cj.setMontoCuentaNuevo(nuevoSaldoCuentas);

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

    public ArrayList<OperacionesCaja> getListaDetalleEntradas() {
        return listaDetalleEntradas;
    }

    public void setListaDetalleEntradas(ArrayList<OperacionesCaja> listaDetalleEntradas) {
        this.listaDetalleEntradas = listaDetalleEntradas;
    }

    public ArrayList<OperacionesCaja> getListaDetalleSalidas() {
        return listaDetalleSalidas;
    }

    public void setListaDetalleSalidas(ArrayList<OperacionesCaja> listaDetalleSalidas) {
        this.listaDetalleSalidas = listaDetalleSalidas;
    }

    public BigDecimal getSaldoAnteriorCheques() {
        return saldoAnteriorCheques;
    }

    public void setSaldoAnteriorCheques(BigDecimal saldoAnteriorCheques) {
        this.saldoAnteriorCheques = saldoAnteriorCheques;
    }

    public BigDecimal getSaldoAnteriorCuentas() {
        return saldoAnteriorCuentas;
    }

    public void setSaldoAnteriorCuentas(BigDecimal saldoAnteriorCuentas) {
        this.saldoAnteriorCuentas = saldoAnteriorCuentas;
    }

    public BigDecimal getNuevoSaldoCheques() {
        return nuevoSaldoCheques;
    }

    public void setNuevoSaldoCheques(BigDecimal nuevoSaldoCheques) {
        this.nuevoSaldoCheques = nuevoSaldoCheques;
    }

    public BigDecimal getNuevoSaldoCuentas() {
        return nuevoSaldoCuentas;
    }

    public void setNuevoSaldoCuentas(BigDecimal nuevoSaldoCuentas) {
        this.nuevoSaldoCuentas = nuevoSaldoCuentas;
    }

    public ArrayList<OperacionesCaja> getListaDetalleEntradasEfectivo() {
        return listaDetalleEntradasEfectivo;
    }

    public void setListaDetalleEntradasEfectivo(ArrayList<OperacionesCaja> listaDetalleEntradasEfectivo) {
        this.listaDetalleEntradasEfectivo = listaDetalleEntradasEfectivo;
    }

    public ArrayList<OperacionesCaja> getListaDetalleEntradasCheques() {
        return listaDetalleEntradasCheques;
    }

    public void setListaDetalleEntradasCheques(ArrayList<OperacionesCaja> listaDetalleEntradasCheques) {
        this.listaDetalleEntradasCheques = listaDetalleEntradasCheques;
    }

    public ArrayList<OperacionesCaja> getListaDetalleEntradasCuentasBancarias() {
        return listaDetalleEntradasCuentasBancarias;
    }

    public void setListaDetalleEntradasCuentasBancarias(ArrayList<OperacionesCaja> listaDetalleEntradasCuentasBancarias) {
        this.listaDetalleEntradasCuentasBancarias = listaDetalleEntradasCuentasBancarias;
    }

    public BigDecimal getTotalEfectivo() {
        return totalEfectivo;
    }

    public void setTotalEfectivo(BigDecimal totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    public BigDecimal getTotalCheques() {
        return totalCheques;
    }

    public void setTotalCheques(BigDecimal totalCheques) {
        this.totalCheques = totalCheques;
    }

    public BigDecimal getTotalCuentasBancarias() {
        return totalCuentasBancarias;
    }

    public void setTotalCuentasBancarias(BigDecimal totalCuentasBancarias) {
        this.totalCuentasBancarias = totalCuentasBancarias;
    }

    public BigDecimal getSaldoAnteriorEfectivo() {
        return saldoAnteriorEfectivo;
    }

    public void setSaldoAnteriorEfectivo(BigDecimal saldoAnteriorEfectivo) {
        this.saldoAnteriorEfectivo = saldoAnteriorEfectivo;
    }

    public BigDecimal getSumaMegaAnterior() {
        return sumaMegaAnterior;
    }

    public void setSumaMegaAnterior(BigDecimal sumaMegaAnterior) {
        this.sumaMegaAnterior = sumaMegaAnterior;
    }

    public BigDecimal getSumaMegaActual() {
        return sumaMegaActual;
    }

    public void setSumaMegaActual(BigDecimal sumaMegaActual) {
        this.sumaMegaActual = sumaMegaActual;
    }

}
