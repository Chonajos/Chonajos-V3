/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.DominioCajas;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceOperacionesCaja;
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
 * @author jramirez
 */
@Component
@Scope("view")
public class BeanCajas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private String title;
    private String viewEstate;
    private UsuarioDominio usuario;
    private BigDecimal idCajaBean;
    private BigDecimal idSucursalBean;
    private BigDecimal idUsuarioCajaBean;

    private BigDecimal totalEntradasEfectivo;
    private BigDecimal totalEntradasCheques;
    private BigDecimal totalEntradasCuentas;
    private BigDecimal totalSalidasEfectivo;
    private BigDecimal totalSalidasCheques;
    private BigDecimal totalSalidasCuentas;

    private ArrayList<Caja> listaCajas;
    private ArrayList<OperacionesCaja> lstOperacionesEntrada;
    private ArrayList<OperacionesCaja> lstOperacionesSalida;

    private ArrayList<OperacionesCaja> lstOperacionesGeneralesEntrada;
    private ArrayList<OperacionesCaja> lstOperacionesGeneralesSalida;

    private ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaPendiente;
    private ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaPendiente;

    private ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaRechazada;
    private ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaRechazada;

    private ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaCancelada;
    private ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaCancelada;

    private ArrayList<OperacionesCaja> lstOperacionesDetallesEntrada;
    private ArrayList<OperacionesCaja> lstOperacionesDetallesSalida;

    private ArrayList<DominioCajas> listaGeneral;
    private ArrayList<Sucursal> listaSucursal;
    private CorteCaja corteAnterior;
    private Caja caja;
    private ArrayList<Usuario> listaResponsables;
    private ArrayList<Caja> listaSucursales;
    private DominioCajas data;

    private static final BigDecimal ENTRADA = new BigDecimal(1);
    private static final BigDecimal SALIDA = new BigDecimal(2);
    private static final BigDecimal CERO = new BigDecimal(0);
    private static final BigDecimal STATUSOPERACIONREALIZADA = new BigDecimal(1);
    private static final BigDecimal TIPO_1 = new BigDecimal(1);
    private static final BigDecimal TIPO_2 = new BigDecimal(1);
    private static final BigDecimal STATUSOPERACIONPENDIENTE = new BigDecimal(2);
    private static final BigDecimal STATUSOPERACIONRECHAZADA = new BigDecimal(3);

    private BigDecimal totalAperturaEfectivo;
    private BigDecimal totalAperturaCheques;
    private BigDecimal totalAperturaCuentas;
    private BigDecimal totalEfectivo;
    private BigDecimal totalCheques;
    private BigDecimal totalCuentas;
    private BigDecimal totalSaldoActual;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;

    @PostConstruct
    public void init() {
        setTitle("Relación de Operaciones de Cajas");
        setViewEstate("init");
        data = new DominioCajas();
        listaCajas = new ArrayList<Caja>();
        lstOperacionesEntrada = new ArrayList<OperacionesCaja>();
        lstOperacionesGeneralesEntrada = new ArrayList<OperacionesCaja>();
        lstOperacionesSalida = new ArrayList<OperacionesCaja>();
        lstOperacionesEntrada = new ArrayList<OperacionesCaja>();
        lstOperacionesGeneralesSalida = new ArrayList<OperacionesCaja>();
        lstOperacionesDetallesEntrada = new ArrayList<OperacionesCaja>();
        lstOperacionesDetallesSalida = new ArrayList<OperacionesCaja>();
        listaSucursal = new ArrayList<Sucursal>();
        listaSucursal = ifaceCatSucursales.getSucursales();
        listaGeneral = new ArrayList<DominioCajas>();
        corteAnterior = new CorteCaja();
        usuario = context.getUsuarioAutenticado();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        idCajaBean = caja.getIdCajaPk();
        listaResponsables = new ArrayList<Usuario>();

        listaSucursales = new ArrayList<Caja>();
        listaSucursales = ifaceCaja.getSucursalesByIdCaja(caja.getIdCajaPk());
        idSucursalBean = new BigDecimal(usuario.getSucId());

        //---pruebas
        listaCajas.clear();
        listaResponsables.clear();
        listaCajas = ifaceCaja.getCajasByIdSucusal(idSucursalBean);
        //--------
        //buscarCajas();
        buscarReponsables();
        buscar();

    }

    public void buscarCajas() {
        listaCajas.clear();
        listaResponsables.clear();
        listaCajas = ifaceCaja.getCajasByIdSucusal(idSucursalBean);
        idCajaBean = null;
        buscarReponsables();
    }

    public void buscarReponsables() {
        System.out.println("IDCajaBean: " + idCajaBean);
        listaResponsables.clear();
        listaResponsables = ifaceOperacionesCaja.getResponsables(idCajaBean);
        if (idCajaBean != null) {
            if (!listaResponsables.isEmpty()) {
                idUsuarioCajaBean = listaResponsables.get(0).getIdUsuarioPk();
            }
        } else {
            idUsuarioCajaBean = null;
            listaResponsables.clear();
        }
    }

    public void generarCorte()
    {
        for (DominioCajas dc : listaGeneral) 
        {
            
        }

    }

    public void sumaTotales() {
        totalEntradas=CERO;
        totalSalidas=CERO;
        totalAperturaEfectivo = CERO;
        totalAperturaCheques = CERO;
        totalAperturaCuentas = CERO;
        totalEfectivo = CERO;
        totalCheques = CERO;
        totalCuentas = CERO;
        totalSaldoActual = CERO;

        for (DominioCajas dc : listaGeneral) 
        {
            totalAperturaEfectivo = totalAperturaEfectivo.add(dc.getAperturaEfectivo(), MathContext.UNLIMITED);
            totalAperturaCheques = totalAperturaCheques.add(dc.getAperturaCheques(), MathContext.UNLIMITED);
            totalAperturaCuentas = totalAperturaCuentas.add(dc.getAperturaCuentas(), MathContext.UNLIMITED);
            totalEfectivo = totalEfectivo.add(dc.getEfectivo(), MathContext.UNLIMITED);
            totalCheques = totalCheques.add(dc.getCheques(), MathContext.UNLIMITED);
            totalCuentas = totalCuentas.add(dc.getCuentas(), MathContext.UNLIMITED);
            totalSaldoActual = totalSaldoActual.add(dc.getSaldoActual(), MathContext.UNLIMITED);
        }
        for(OperacionesCaja o : lstOperacionesDetallesEntrada)
        {
            totalEntradas=totalEntradas.add(o.getMonto(), MathContext.UNLIMITED);
        }
        for(OperacionesCaja o : lstOperacionesDetallesSalida)
        {
            totalSalidas=totalSalidas.add(o.getMonto(), MathContext.UNLIMITED);
        }
    }

    public void buscar() {
        listaGeneral.clear();
        lstOperacionesGeneralesEntrada.clear();
        lstOperacionesGeneralesSalida.clear();
        lstOperacionesEntrada.clear();
        lstOperacionesSalida.clear();
        lstOperacionesDetallesSalida.clear();
        lstOperacionesDetallesEntrada.clear();

//        lstOperacionesGeneralesEntrada = ifaceOperacionesCaja.getGenerales(idCajaBean, ENTRADA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(1));
//        lstOperacionesGeneralesSalida = ifaceOperacionesCaja.getGenerales(idCajaBean, SALIDA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(1));
        lstOperacionesDetallesEntrada = ifaceOperacionesCaja.getGenerales(idCajaBean, ENTRADA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(2));
        lstOperacionesDetallesSalida = ifaceOperacionesCaja.getGenerales(idCajaBean, SALIDA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(2));

        if (listaCajas.isEmpty()) {
            listaCajas = ifaceCaja.getCajas();
        }
        for (Caja c : listaCajas) {

            DominioCajas dc = new DominioCajas();
            dc.setIdUsuarioFk(c.getIdUsuarioFK());
            dc.setNombreCaja(c.getNombre());
            dc.setNombreSucursal(c.getNombreSucursal());
            BigDecimal cheques = CERO;
            BigDecimal efectivo = CERO;
            BigDecimal cuentas = CERO;
            if (idUsuarioCajaBean == null && idCajaBean == null) {

                lstOperacionesGeneralesEntrada = ifaceOperacionesCaja.getGenerales(idCajaBean, ENTRADA, c.getIdUsuarioFK(), STATUSOPERACIONREALIZADA, c.getIdSucursalFk(), new BigDecimal(1));
                lstOperacionesGeneralesSalida = ifaceOperacionesCaja.getGenerales(idCajaBean, SALIDA, c.getIdUsuarioFK(), STATUSOPERACIONREALIZADA, c.getIdSucursalFk(), new BigDecimal(1));

                lstOperacionesEntrada = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), ENTRADA, null);
                lstOperacionesSalida = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), SALIDA, null);
                corteAnterior = ifaceCorteCaja.getLastCorteByCaja(c.getIdCajaPk());

                if (corteAnterior.getIdCorteCajaPk() == null) {
                    dc.setAperturaEfectivo(CERO);
                    dc.setAperturaCuentas(CERO);
                    dc.setAperturaCheques(CERO);
                } else {
                    dc.setAperturaEfectivo(corteAnterior.getSaldoNuevo());
                    dc.setAperturaCuentas(corteAnterior.getMontoCuentaNuevo());
                    dc.setAperturaCheques(corteAnterior.getMontoChequesNuevos());
                }
                cheques = CERO;
                cuentas = CERO;
                efectivo = CERO;
                for (OperacionesCaja ope : lstOperacionesGeneralesEntrada) {
                    switch (ope.getIdFormaPago().intValue()) {
                        case 1:
                            //efectivo
                            efectivo = ope.getMonto();
                            break;
                        case 2:
                            //transferencia
                            cuentas = cuentas.add(ope.getMonto(), MathContext.UNLIMITED);
                            break;
                        case 3:
                            //cheque
                            cheques = ope.getMonto();
                            break;
                        case 4:
                            //deposito
                            cuentas = cuentas.add(ope.getMonto(), MathContext.UNLIMITED);

                            break;
                        default:
                            //Error
                            break;
                    }
                }
                for (OperacionesCaja ops : lstOperacionesGeneralesSalida) {

                    switch (ops.getIdFormaPago().intValue()) {
                        case 1:
                            //efectivo
                            System.out.println("Efectivo: " + efectivo);
                            efectivo = efectivo.subtract(ops.getMonto(), MathContext.UNLIMITED);
                            break;
                        case 2:
                            //transferencia
                            System.out.println("Cuentas: " + cuentas);
                            cuentas = cuentas.subtract(ops.getMonto(), MathContext.UNLIMITED);
                            break;
                        case 3:
                            //cheque
                            cheques = cheques.subtract(ops.getMonto(), MathContext.UNLIMITED);
                            break;
                        case 4:
                            //deposito
                            cuentas = cuentas.subtract(ops.getMonto(), MathContext.UNLIMITED);

                            break;
                        default:
                            //Error
                            System.out.println("Errror");
                            break;
                    }
                }

                dc.setCheques(cheques.add(dc.getAperturaCheques(), MathContext.UNLIMITED));
                dc.setCuentas(cuentas.add(dc.getAperturaCuentas(), MathContext.UNLIMITED));
                dc.setEfectivo(efectivo.add(dc.getAperturaEfectivo(), MathContext.UNLIMITED));

                dc.setSaldoActual(dc.getCheques().add(dc.getCuentas().add(dc.getEfectivo(), MathContext.UNLIMITED), MathContext.UNLIMITED));
                listaGeneral.add(dc);
            } else {
                if (c.getIdCajaPk().intValue() == idCajaBean.intValue()) {
                    lstOperacionesGeneralesEntrada = ifaceOperacionesCaja.getGenerales(c.getIdCajaPk(), ENTRADA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(1));
                    lstOperacionesGeneralesSalida = ifaceOperacionesCaja.getGenerales(c.getIdCajaPk(), SALIDA, idUsuarioCajaBean, STATUSOPERACIONREALIZADA, idSucursalBean, new BigDecimal(1));

                    lstOperacionesEntrada = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), ENTRADA, null);
                    lstOperacionesSalida = ifaceOperacionesCaja.getOperaciones(c.getIdCajaPk(), SALIDA, null);

                    corteAnterior = ifaceCorteCaja.getLastCorteByCaja(c.getIdCajaPk());

                   
                    if (corteAnterior.getIdCorteCajaPk() == null) {
                        dc.setAperturaEfectivo(CERO);
                        dc.setAperturaCuentas(CERO);
                        dc.setAperturaCheques(CERO);
                    } else {

                        dc.setAperturaEfectivo(corteAnterior.getSaldoNuevo());
                        dc.setAperturaCuentas(corteAnterior.getMontoCuentaNuevo());
                        dc.setAperturaCheques(corteAnterior.getMontoChequesNuevos());
                    }

                    for (OperacionesCaja ope : lstOperacionesGeneralesEntrada) {

                        switch (ope.getIdFormaPago().intValue()) {
                            case 1:
                                //efectivo
                                efectivo = ope.getMonto();
                                break;
                            case 2:
                                //transferencia
                                cuentas = cuentas.add(ope.getMonto(), MathContext.UNLIMITED);
                                break;
                            case 3:
                                //cheque
                                cheques = ope.getMonto();
                                break;
                            case 4:
                                //deposito
                                cuentas = cuentas.add(ope.getMonto(), MathContext.UNLIMITED);

                                break;
                            default:
                                //Error
                                break;
                        }
                    }
                    for (OperacionesCaja ops : lstOperacionesGeneralesSalida) {

                        switch (ops.getIdFormaPago().intValue()) {
                            case 1:
                                //efectivo
                                efectivo = efectivo.subtract(ops.getMonto(), MathContext.UNLIMITED);
                                break;
                            case 2:
                                //transferencia
                                cuentas = cuentas.subtract(ops.getMonto(), MathContext.UNLIMITED);
                                break;
                            case 3:
                                //cheque
                                cheques = cheques.subtract(ops.getMonto(), MathContext.UNLIMITED);
                                break;
                            case 4:
                                //deposito
                                cuentas = cuentas.subtract(ops.getMonto(), MathContext.UNLIMITED);

                                break;
                            default:
                                //Error
                                break;
                        }
                    }

                    dc.setCheques(cheques.add(dc.getAperturaCheques(), MathContext.UNLIMITED));
                    dc.setCuentas(cuentas.add(dc.getAperturaCuentas(), MathContext.UNLIMITED));
                    dc.setEfectivo(efectivo.add(dc.getAperturaEfectivo(), MathContext.UNLIMITED));

                    dc.setSaldoActual(dc.getCheques().add(dc.getCuentas().add(dc.getEfectivo(), MathContext.UNLIMITED), MathContext.UNLIMITED));
                    listaGeneral.add(dc);
                }

            }
        }
        // lstOperacionesEntrada = ifaceOperacionesCaja.getOperaciones(idCajaBean, entrada, null);
        sumaTotales();
    }

    public BigDecimal getIdCajaBean() {
        return idCajaBean;
    }

    public void setIdCajaBean(BigDecimal idCajaBean) {
        this.idCajaBean = idCajaBean;
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

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesEntrada() {
        return lstOperacionesEntrada;
    }

    public void setLstOperacionesEntrada(ArrayList<OperacionesCaja> lstOperacionesEntrada) {
        this.lstOperacionesEntrada = lstOperacionesEntrada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesSalida() {
        return lstOperacionesSalida;
    }

    public void setLstOperacionesSalida(ArrayList<OperacionesCaja> lstOperacionesSalida) {
        this.lstOperacionesSalida = lstOperacionesSalida;
    }

    public BigDecimal getTotalEntradasEfectivo() {
        return totalEntradasEfectivo;
    }

    public void setTotalEntradasEfectivo(BigDecimal totalEntradasEfectivo) {
        this.totalEntradasEfectivo = totalEntradasEfectivo;
    }

    public BigDecimal getTotalEntradasCheques() {
        return totalEntradasCheques;
    }

    public void setTotalEntradasCheques(BigDecimal totalEntradasCheques) {
        this.totalEntradasCheques = totalEntradasCheques;
    }

    public BigDecimal getTotalEntradasCuentas() {
        return totalEntradasCuentas;
    }

    public void setTotalEntradasCuentas(BigDecimal totalEntradasCuentas) {
        this.totalEntradasCuentas = totalEntradasCuentas;
    }

    public BigDecimal getTotalSalidasEfectivo() {
        return totalSalidasEfectivo;
    }

    public void setTotalSalidasEfectivo(BigDecimal totalSalidasEfectivo) {
        this.totalSalidasEfectivo = totalSalidasEfectivo;
    }

    public BigDecimal getTotalSalidasCheques() {
        return totalSalidasCheques;
    }

    public void setTotalSalidasCheques(BigDecimal totalSalidasCheques) {
        this.totalSalidasCheques = totalSalidasCheques;
    }

    public BigDecimal getTotalSalidasCuentas() {
        return totalSalidasCuentas;
    }

    public void setTotalSalidasCuentas(BigDecimal totalSalidasCuentas) {
        this.totalSalidasCuentas = totalSalidasCuentas;
    }

    public ArrayList<DominioCajas> getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(ArrayList<DominioCajas> listaGeneral) {
        this.listaGeneral = listaGeneral;
    }

    public BigDecimal getIdSucursalBean() {
        return idSucursalBean;
    }

    public void setIdSucursalBean(BigDecimal idSucursalBean) {
        this.idSucursalBean = idSucursalBean;
    }

    public BigDecimal getIdUsuarioCajaBean() {
        return idUsuarioCajaBean;
    }

    public void setIdUsuarioCajaBean(BigDecimal idUsuarioCajaBean) {
        this.idUsuarioCajaBean = idUsuarioCajaBean;
    }

    public ArrayList<Sucursal> getListaSucursal() {
        return listaSucursal;
    }

    public void setListaSucursal(ArrayList<Sucursal> listaSucursal) {
        this.listaSucursal = listaSucursal;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesEntrada() {
        return lstOperacionesGeneralesEntrada;
    }

    public void setLstOperacionesGeneralesEntrada(ArrayList<OperacionesCaja> lstOperacionesGeneralesEntrada) {
        this.lstOperacionesGeneralesEntrada = lstOperacionesGeneralesEntrada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesSalida() {
        return lstOperacionesGeneralesSalida;
    }

    public void setLstOperacionesGeneralesSalida(ArrayList<OperacionesCaja> lstOperacionesGeneralesSalida) {
        this.lstOperacionesGeneralesSalida = lstOperacionesGeneralesSalida;
    }

    public CorteCaja getCorteAnterior() {
        return corteAnterior;
    }

    public void setCorteAnterior(CorteCaja corteAnterior) {
        this.corteAnterior = corteAnterior;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public ArrayList<Usuario> getListaResponsables() {
        return listaResponsables;
    }

    public void setListaResponsables(ArrayList<Usuario> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

    public ArrayList<Caja> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Caja> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesDetallesEntrada() {
        return lstOperacionesDetallesEntrada;
    }

    public void setLstOperacionesDetallesEntrada(ArrayList<OperacionesCaja> lstOperacionesDetallesEntrada) {
        this.lstOperacionesDetallesEntrada = lstOperacionesDetallesEntrada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesDetallesSalida() {
        return lstOperacionesDetallesSalida;
    }

    public void setLstOperacionesDetallesSalida(ArrayList<OperacionesCaja> lstOperacionesDetallesSalida) {
        this.lstOperacionesDetallesSalida = lstOperacionesDetallesSalida;
    }

    public DominioCajas getData() {
        return data;
    }

    public void setData(DominioCajas data) {
        this.data = data;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesEntradaPendiente() {
        return lstOperacionesGeneralesEntradaPendiente;
    }

    public void setLstOperacionesGeneralesEntradaPendiente(ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaPendiente) {
        this.lstOperacionesGeneralesEntradaPendiente = lstOperacionesGeneralesEntradaPendiente;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesSalidaPendiente() {
        return lstOperacionesGeneralesSalidaPendiente;
    }

    public void setLstOperacionesGeneralesSalidaPendiente(ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaPendiente) {
        this.lstOperacionesGeneralesSalidaPendiente = lstOperacionesGeneralesSalidaPendiente;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesEntradaRechazada() {
        return lstOperacionesGeneralesEntradaRechazada;
    }

    public void setLstOperacionesGeneralesEntradaRechazada(ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaRechazada) {
        this.lstOperacionesGeneralesEntradaRechazada = lstOperacionesGeneralesEntradaRechazada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesSalidaRechazada() {
        return lstOperacionesGeneralesSalidaRechazada;
    }

    public void setLstOperacionesGeneralesSalidaRechazada(ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaRechazada) {
        this.lstOperacionesGeneralesSalidaRechazada = lstOperacionesGeneralesSalidaRechazada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesEntradaCancelada() {
        return lstOperacionesGeneralesEntradaCancelada;
    }

    public void setLstOperacionesGeneralesEntradaCancelada(ArrayList<OperacionesCaja> lstOperacionesGeneralesEntradaCancelada) {
        this.lstOperacionesGeneralesEntradaCancelada = lstOperacionesGeneralesEntradaCancelada;
    }

    public ArrayList<OperacionesCaja> getLstOperacionesGeneralesSalidaCancelada() {
        return lstOperacionesGeneralesSalidaCancelada;
    }

    public void setLstOperacionesGeneralesSalidaCancelada(ArrayList<OperacionesCaja> lstOperacionesGeneralesSalidaCancelada) {
        this.lstOperacionesGeneralesSalidaCancelada = lstOperacionesGeneralesSalidaCancelada;
    }

    public BigDecimal getTotalAperturaEfectivo() {
        return totalAperturaEfectivo;
    }

    public void setTotalAperturaEfectivo(BigDecimal totalAperturaEfectivo) {
        this.totalAperturaEfectivo = totalAperturaEfectivo;
    }

    public BigDecimal getTotalAperturaCheques() {
        return totalAperturaCheques;
    }

    public void setTotalAperturaCheques(BigDecimal totalAperturaCheques) {
        this.totalAperturaCheques = totalAperturaCheques;
    }

    public BigDecimal getTotalAperturaCuentas() {
        return totalAperturaCuentas;
    }

    public void setTotalAperturaCuentas(BigDecimal totalAperturaCuentas) {
        this.totalAperturaCuentas = totalAperturaCuentas;
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

    public BigDecimal getTotalCuentas() {
        return totalCuentas;
    }

    public void setTotalCuentas(BigDecimal totalCuentas) {
        this.totalCuentas = totalCuentas;
    }

    public BigDecimal getTotalSaldoActual() {
        return totalSaldoActual;
    }

    public void setTotalSaldoActual(BigDecimal totalSaldoActual) {
        this.totalSaldoActual = totalSaldoActual;
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
    

}
