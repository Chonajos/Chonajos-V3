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
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.service.IfaceTiposOperacion;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class BeanTransferencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private IfaceOperacionesCaja ifaceOperacionesCaja;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;
    @Autowired
    private IfaceConceptos ifaceConceptos;

    private OperacionesCaja data;
    private UsuarioDominio usuario;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;

    private Caja caja;
    private OperacionesCaja opcajaOrigen;
    private OperacionesCaja opcajaDestino;

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
    private static final BigDecimal statusOperacionPendiente = new BigDecimal(2);

    private static final BigDecimal idTransferenciaEfectivo = new BigDecimal(16);
    private static final BigDecimal idTransferenciaCheques = new BigDecimal(17);

    //--Variables para Verificar Maximo en Caja --//
    private ArrayList<TipoOperacion> lstOperacionesEntrada;
    private ArrayList<TipoOperacion> lstOperacionesSalida;
    private CorteCaja corteAnterior;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    //--Variables para Verificar Maximo en Caja --//
    private BigDecimal idTipoTransferenciaFk;

    //==========codigo para verificar dinero en caja =========//
    private ArrayList<OperacionesCaja> listaDetalleEntradas;
    private ArrayList<OperacionesCaja> listaDetalleSalidas;
    private static final BigDecimal statusAplicado = new BigDecimal(1);
    private static final BigDecimal statusPendiente = new BigDecimal(2);
    private static final BigDecimal CERO = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
    private BigDecimal nuevoSaldo;
    private BigDecimal nuevoSaldoCheques;
    private BigDecimal nuevoSaldoCuentas;
    private BigDecimal saldoAnteriorEfectivo;
    private BigDecimal saldoAnteriorCheques;
    private BigDecimal saldoAnteriorCuentas;
    private BigDecimal totalEfectivo;
    private BigDecimal totalCheques;
    private BigDecimal totalCuentasBancarias;
    //========codigo para verificar dinero en caja ============//

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        setTitle("Transferencias entre Cajas");
        setViewEstate("init");
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        listaCajas = ifaceCaja.getCajas();
        opcajaOrigen = new OperacionesCaja();
        opcajaOrigen.setIdCajaFk(caja.getIdCajaPk());
        opcajaOrigen.setIdUserFk(usuario.getIdUsuario());
        opcajaOrigen.setEntradaSalida(salida);
        opcajaOrigen.setIdStatusFk(statusOperacionPendiente);
        opcajaOrigen.setIdSucursalFk(new BigDecimal(usuario.getSucId()));

        //--Maximo Pago--//
        corteAnterior = new CorteCaja();
        corteAnterior = ifaceCorteCaja.getLastCorteByCaja(caja.getIdCajaPk());
        nuevoSaldo = new BigDecimal(0);
        //--Maximo Pago--//
        listaCajas.remove(caja);
        Caja cRemove = new Caja();
        for (Caja c : listaCajas) {
            if (c.getIdCajaPk().equals(caja.getIdCajaPk())) {
                cRemove = c;
            }

        }

        listaCajas.remove(cRemove);

        //---codigo para verificar dinero en caja --//
        nuevoSaldo = CERO;
        nuevoSaldoCheques = CERO;
        nuevoSaldoCuentas = CERO;
        saldoAnteriorEfectivo = CERO;
        saldoAnteriorCheques = CERO;
        saldoAnteriorCuentas = CERO;
        totalEfectivo = CERO;
        totalCheques = CERO;
        totalCuentasBancarias = CERO;

        if (corteAnterior.getSaldoNuevo() == null) {
            corteAnterior.setSaldoNuevo(CERO);
        }
        saldoAnteriorEfectivo = corteAnterior.getSaldoNuevo();
        saldoAnteriorCheques = corteAnterior.getMontoChequesNuevos();
        saldoAnteriorCuentas = corteAnterior.getMontoCuentaNuevo();
        if (saldoAnteriorCheques == null) {
            saldoAnteriorCheques = CERO;
        }
        if (saldoAnteriorEfectivo == null) {
            saldoAnteriorEfectivo = CERO;
        }
        if (saldoAnteriorCuentas == null) {
            saldoAnteriorCuentas = CERO;
        }

        //---codigo para verificar dinero en caja --//
        System.out.println("Saldo Anterior: " + saldoAnteriorEfectivo.setScale(2, RoundingMode.CEILING));
        System.out.println("Saldo Anterior Cheques: " + saldoAnteriorCheques.setScale(2, RoundingMode.CEILING));
        System.out.println("Saldo Anterior Cuentas: " + saldoAnteriorCuentas.setScale(2, RoundingMode.CEILING));

    }

    //----Funciones para Verificar Maximo de Dinero en Caja --//
    public void verificarDinero() {
        totalEfectivo = CERO;
        totalCheques = CERO;
        totalCuentasBancarias = CERO;
        nuevoSaldo=CERO;
        nuevoSaldoCheques=CERO;
        nuevoSaldoCuentas=CERO;
        
        listaDetalleEntradas= new ArrayList<OperacionesCaja>();
        listaDetalleEntradas =new ArrayList<OperacionesCaja>();
        listaDetalleSalidas=new ArrayList<OperacionesCaja>();
        
        listaDetalleEntradas = ifaceOperacionesCaja.getDetalles(caja.getIdCajaPk(), caja.getIdUsuarioFK(), entrada, statusAplicado);
        listaDetalleSalidas = ifaceOperacionesCaja.getDetalles(caja.getIdCajaPk(), caja.getIdUsuarioFK(), salida, statusAplicado);

        for (OperacionesCaja ope : listaDetalleEntradas) {
            if (ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13) {

                totalEfectivo = totalEfectivo.add(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {

                totalCheques = totalCheques.add(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {
                totalCuentasBancarias = totalCuentasBancarias.add(ope.getMonto(), MathContext.UNLIMITED);
            }

        }
        for (OperacionesCaja ope : listaDetalleSalidas) {
            if (ope.getIdConceptoFk().intValue() == 7 || ope.getIdConceptoFk().intValue() == 1 || ope.getIdConceptoFk().intValue() == 3 || ope.getIdConceptoFk().intValue() == 4 || ope.getIdConceptoFk().intValue() == 6 || ope.getIdConceptoFk().intValue() == 11 || ope.getIdConceptoFk().intValue() == 10 || ope.getIdConceptoFk().intValue() == 8 || ope.getIdConceptoFk().intValue() == 9 || ope.getIdConceptoFk().intValue() == 16 || ope.getIdConceptoFk().intValue() == 13 || ope.getIdConceptoFk().intValue() == 2 || ope.getIdConceptoFk().intValue() == 25 || ope.getIdConceptoFk().intValue() == 24
                    || ope.getIdConceptoFk().intValue() == 21 || ope.getIdConceptoFk().intValue() == 20 || ope.getIdConceptoFk().intValue() == 23
                    || ope.getIdConceptoFk().intValue() == 22) {
                totalEfectivo = totalEfectivo.subtract(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 30 || ope.getIdConceptoFk().intValue() == 33 || ope.getIdConceptoFk().intValue() == 27 || ope.getIdConceptoFk().intValue() == 17 || ope.getIdConceptoFk().intValue() == 36) {

                totalCheques = totalCheques.subtract(ope.getMonto(), MathContext.UNLIMITED);
            } else if (ope.getIdConceptoFk().intValue() == 12 || ope.getIdConceptoFk().intValue() == 29 || ope.getIdConceptoFk().intValue() == 31 || ope.getIdConceptoFk().intValue() == 28 || ope.getIdConceptoFk().intValue() == 32 || ope.getIdConceptoFk().intValue() == 34 || ope.getIdConceptoFk().intValue() == 37 || ope.getIdConceptoFk().intValue() == 35) {

                totalCuentasBancarias = totalCuentasBancarias.subtract(ope.getMonto(), MathContext.UNLIMITED);
            }

        }
        nuevoSaldo = totalEfectivo.add(saldoAnteriorEfectivo.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
        nuevoSaldoCheques = totalCheques.add(saldoAnteriorCheques.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
        nuevoSaldoCuentas = totalCuentasBancarias.add(saldoAnteriorCuentas.setScale(2, RoundingMode.CEILING), MathContext.UNLIMITED);
    }

    //----Funciones para Verificar Maximo de Dinero en Caja --//
    public void transferir() {
        verificarDinero();
        System.out.println("Saldo Actual: " + nuevoSaldo.setScale(2, RoundingMode.CEILING));
        System.out.println("Saldo Actual Cheques: " + nuevoSaldoCheques.setScale(2, RoundingMode.CEILING));
        System.out.println("Saldo Actual Cuentas: " + nuevoSaldoCuentas.setScale(2, RoundingMode.CEILING));

        boolean bandera = false;
        if (nuevoSaldo.compareTo(monto.setScale(2, RoundingMode.CEILING)) >= 0) {
            opcajaOrigen.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcajaOrigen.setMonto(monto);
            opcajaOrigen.setComentarios(comentarios);
            System.out.println("Monto: " + opcajaOrigen.getMonto().setScale(2, RoundingMode.CEILING));
            if (idTipoTransferenciaFk.intValue() == 1) {
                System.out.println("monto: " + opcajaOrigen.getMonto());
                System.out.println("nuevoSaldo: " + nuevoSaldo);
                if ((opcajaOrigen.getMonto().setScale(2, RoundingMode.CEILING)).compareTo((nuevoSaldo.setScale(2, RoundingMode.CEILING))) >= 0) {
                    System.out.println("efectivo se puede transferir");
                    System.out.println("monto: " + opcajaOrigen.getMonto().setScale(2, RoundingMode.CEILING));
                    System.out.println("nuevoSaldo: " + nuevoSaldo.setScale(2, RoundingMode.CEILING));
                    bandera = true;
                }
                opcajaOrigen.setIdConceptoFk(idTransferenciaEfectivo);
            } else {
                if (opcajaOrigen.getMonto().setScale(2, RoundingMode.CEILING).compareTo(nuevoSaldoCheques.setScale(2, RoundingMode.CEILING)) >= 0) {
                    bandera = true;
                }
                opcajaOrigen.setIdConceptoFk(idTransferenciaCheques);
            }

            opcajaOrigen.setIdCajaDestinoFk(idCajaDestinoBean);

            if (caja.getIdCajaPk() != null) {
                if (bandera == false) {
                    if (ifaceOperacionesCaja.insertaOperacion(opcajaOrigen) == 1) {
                        JsfUtil.addSuccessMessageClean("Transferencia Registrada Correctamente");
                        monto = null;
                        comentarios = null;
                        idCajaDestinoBean = null;
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al registrar transferencia, contactar al administrador");
                    }

                } else {
                    JsfUtil.addErrorMessageClean("El monto a transferir es mayor al registrado en caja");
                    
                }
            } else {
                JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
            }
        } else {
            JsfUtil.addErrorMessageClean("No hay suficiente dinero en caja");
        }
    }

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
        this.data = data;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public ArrayList<Caja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<Caja> listaCajas) {
        this.listaCajas = listaCajas;
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

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
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

    public BigDecimal getIdTipoTransferenciaFk() {
        return idTipoTransferenciaFk;
    }

    public void setIdTipoTransferenciaFk(BigDecimal idTipoTransferenciaFk) {
        this.idTipoTransferenciaFk = idTipoTransferenciaFk;
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

    public BigDecimal getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(BigDecimal nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
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

    public BigDecimal getSaldoAnteriorEfectivo() {
        return saldoAnteriorEfectivo;
    }

    public void setSaldoAnteriorEfectivo(BigDecimal saldoAnteriorEfectivo) {
        this.saldoAnteriorEfectivo = saldoAnteriorEfectivo;
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

}
