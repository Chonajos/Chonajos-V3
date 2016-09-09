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
import com.web.chon.service.IfaceEntradaSalida;
import com.web.chon.service.IfaceTiposOperacion;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
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
public class BeanCaja implements Serializable {

    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceConceptos ifaceConceptos;
    @Autowired
    private IfaceEntradaSalida ifaceEntradaSalida;
    @Autowired
    private IfaceTiposOperacion ifaceTiposOperacion;

    private UsuarioDominio usuario;
    private OperacionesCaja data;
    private Caja caja;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;
    private ArrayList<OperacionesCaja> listaMovimientosSalidas;
    private ArrayList<ConceptosES> listaConceptos;
    private ArrayList<TipoOperacion> listaOperaciones;

    private String title;
    private String viewEstate;

    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private Date hoy;
    private boolean verCajas;
    private BigDecimal saldoCaja;

    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();
        saldoCaja = new BigDecimal(0);
        hoy = new Date();
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), new BigDecimal(1));
        saldoCaja = caja.getMonto();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()), new BigDecimal(1));
        idSucursalBean = new BigDecimal(usuario.getSucId());
        listaMovimientosSalidas = new ArrayList<OperacionesCaja>();
        listaMovimientosSalidas = ifaceEntradaSalida.getMovimientosByIdCaja(caja.getIdCajaPk(), TiempoUtil.getFechaDDMMYYYY(hoy), TiempoUtil.getFechaDDMMYYYY(hoy));
        listaConceptos = new ArrayList<ConceptosES>();
        listaOperaciones = new ArrayList<TipoOperacion>();
        listaOperaciones = ifaceTiposOperacion.getOperaciones();
        //listaConceptos = ifaceConceptos.getConceptos();
        idCajaBean = caja.getIdCajaPk();
        verCajas = false;
        data = new OperacionesCaja();
        setTitle("Operaciones de Caja");
        setViewEstate("init");
    }

    public void reloadCaja() {
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), new BigDecimal(1));
        saldoCaja = caja.getMonto();
    }

    public void changeCombo() {
        listaConceptos = ifaceConceptos.getConceptosByTipoOperacion(data.getIdTipoOperacionFk());
        if (data.getIdTipoOperacionFk().intValue() == 2) {
            verCajas = true;
        } else {
            verCajas = false;
        }

    }

    public void getMovimientos() {
        listaMovimientosSalidas = ifaceEntradaSalida.getMovimientosByIdCaja(caja.getIdCajaPk(), TiempoUtil.getFechaDDMMYYYY(hoy), TiempoUtil.getFechaDDMMYYYY(hoy));

    }

    public void generarSalida() {
        data.setIdEntradaSalidaPk(new BigDecimal(ifaceEntradaSalida.getNextVal()));
        data.setIdCajaFk(caja.getIdCajaPk());
        data.setTipoES(new BigDecimal(1));
        System.out.println("======================================================");
        System.out.println("Data: "+data.toString());
        System.out.println("======================================================");
        if(data.getMonto().compareTo(caja.getApertura())<0)
        {
        switch (data.getIdTipoOperacionFk().intValue()) {
            case 1:
                System.out.println("Ejecuto Pago de Servicios");
                caja.setMonto(caja.getMonto().subtract(data.getMonto(), MathContext.UNLIMITED));
                caja.setServicios(caja.getServicios().add(data.getMonto(), MathContext.UNLIMITED));
                if (ifaceCaja.updateMontoCaja(caja) == 1) {
                    //System.out.println("Entrada Salida: " + data.toString());
                    if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                        JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                        getMovimientos();
                        data.reset();

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                    }
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
                }
                break;
            case 2:
                data.setIdConceptoFk(new BigDecimal(5));
                System.out.println("Se ejecuto una transferencia");
                System.out.println("1.-Caja: " + caja);
                //Descontar de caja:
                caja.setMonto(caja.getMonto().subtract(data.getMonto(), MathContext.UNLIMITED));
                caja.setTransferencias_OUT(caja.getTransferencias_OUT().add(data.getMonto(), MathContext.UNLIMITED));
                Caja cajaDestino = new Caja();
                cajaDestino = ifaceCaja.getCajaByIdPk(data.getIdCajaDestino());
                System.out.println("1.-Caja Destino: " + cajaDestino);
                cajaDestino.setMonto(cajaDestino.getMonto().add(data.getMonto(), MathContext.UNLIMITED));
                cajaDestino.setTransferencias_IN(cajaDestino.getTransferencias_IN().add(data.getMonto(), MathContext.UNLIMITED));
                System.out.println("2.-Caja: " + caja);
                System.out.println("2.-Caja Destino: " + cajaDestino);
                if (ifaceCaja.updateMontoCaja(caja) == 1) {
                    //System.out.println("Entrada Salida: " + data.toString());
                    data.setIdCajaOrigen(data.getIdCajaFk());
                    if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                        if (ifaceCaja.updateMontoCaja(cajaDestino) == 1) {
                            JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                            data.setTipoES(new BigDecimal(2));
                            data.setIdEntradaSalidaPk(new BigDecimal(ifaceEntradaSalida.getNextVal()));
                            if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                                getMovimientos();
                            }
                            data.reset();
                        } else {
                            JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja 2");
                        }

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                    }
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
                }

                break;
            case 3:
                caja.setMonto(caja.getMonto().subtract(data.getMonto(), MathContext.UNLIMITED));
                caja.setProvedores(caja.getProvedores().add(data.getMonto(), MathContext.UNLIMITED));
                if (ifaceCaja.updateMontoCaja(caja) == 1) {
                    //System.out.println("Entrada Salida: " + data.toString());
                    if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                        JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                        getMovimientos();
                        data.reset();

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                    }
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
                }
                break;
            case 4:
                System.out.println("Ejecuto Faltante de Dinero");
                caja.setMonto(caja.getMonto().subtract(data.getMonto(), MathContext.UNLIMITED));
                caja.setFaltante(caja.getFaltante().add(data.getMonto(), MathContext.UNLIMITED));
                data.setTipoES(new BigDecimal(2));
                if (ifaceCaja.updateMontoCaja(caja) == 1) {
                    if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                        JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                        getMovimientos();
                        data.reset();
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                    }
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
                }
                break;
            case 5:
                System.out.println("Ejecuto Sobrante de Dinero");
                caja.setMonto(caja.getMonto().add(data.getMonto(), MathContext.UNLIMITED));
                caja.setSobrante(caja.getSobrante().add(data.getMonto(), MathContext.UNLIMITED));
                if (ifaceCaja.updateMontoCaja(caja) == 1) {
                    if (ifaceEntradaSalida.insertaMovimiento(data) == 1) {
                        JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                        getMovimientos();
                        data.reset();

                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                    }
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
                }
                break;
        }
        }
        else
        {
            JsfUtil.addErrorMessageClean("No puedes dejar la caja con menos de : $"+caja.getApertura());
        }
        reloadCaja();
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

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
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

    public ArrayList<OperacionesCaja> getListaMovimientosSalidas() {
        return listaMovimientosSalidas;
    }

    public void setListaMovimientosSalidas(ArrayList<OperacionesCaja> listaMovimientosSalidas) {
        this.listaMovimientosSalidas = listaMovimientosSalidas;
    }

    public OperacionesCaja getData() {
        return data;
    }

    public void setData(OperacionesCaja data) {
        this.data = data;
    }

    public ArrayList<ConceptosES> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaConceptos(ArrayList<ConceptosES> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public ArrayList<TipoOperacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(ArrayList<TipoOperacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public boolean isVerCajas() {
        return verCajas;
    }

    public void setVerCajas(boolean verCajas) {
        this.verCajas = verCajas;
    }

    public BigDecimal getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(BigDecimal saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

}
