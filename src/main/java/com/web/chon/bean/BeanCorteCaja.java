/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.CorteCaja;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCorteCaja;
import com.web.chon.service.IfaceEntradaSalida;
import com.web.chon.service.IfaceVenta;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
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
public class BeanCorteCaja {

    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private IfaceCaja ifaceCaja;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCorteCaja ifaceCorteCaja;
    @Autowired
    private IfaceEntradaSalida ifaceEntradaSalida;

    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<Caja> listaCajas;

    private String title;
    private String viewEstate;

    private UsuarioDominio usuario;
    private CorteCaja data;
    private OperacionesCaja dataEntradaSalida;

    private BigDecimal idSucursalBean;
    private BigDecimal idCajaBean;
    private Date fecha;

    private Caja temporal;
    private BigDecimal saldoCaja;
    private BigDecimal saldoRealCaja;

    private static final BigDecimal CERO = new BigDecimal(0);

    @PostConstruct
    public void init() {
        usuario = context.getUsuarioAutenticado();
        listaSucursales = ifaceCatSucursales.getSucursales();
        idSucursalBean = new BigDecimal(usuario.getSucId());
        saldoCaja = new BigDecimal(0);
        saldoRealCaja = new BigDecimal(0);
        data = new CorteCaja();
        listaCajas = ifaceCaja.getCajas(new BigDecimal(usuario.getSucId()), new BigDecimal(1));
        temporal = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), new BigDecimal(1));
        saldoCaja = temporal.getMonto();
        idCajaBean = temporal.getIdCajaPk();
        llenarDatos();
        dataEntradaSalida = new OperacionesCaja();

        setTitle("Generar Corte de Caja");
        setViewEstate("init");
        fecha = new Date();

    }

    public void llenarDatos() {
        data.setVentasMayoreo(temporal.getMontoMayoreo());
        data.setVentasMenudeo(temporal.getMontoMenudeo());
        data.setAbonosCreditos(temporal.getMontoCredito());
        data.setTransferenciasIN(temporal.getTransferencias_IN());
        data.setAnticipos(temporal.getMontoAnticipos());
        data.setCantCheques(temporal.getCantCheques());
        data.setMontoCheques(temporal.getMontoCheques());
        data.setTransferenciasOUT(temporal.getTransferencias_OUT());
        data.setServicios(temporal.getServicios());
        data.setPagoProvedores(temporal.getProvedores());
        data.setPrestamos(temporal.getPrestamos());
        data.setIdCajaFk(temporal.getIdCajaPk());
        data.setIdUserFk(usuario.getIdUsuario());
        data.setSaldoAnterior(temporal.getSaldoAnterior());

        data.setTotalEntradas(new BigDecimal(0));
        data.setTotalSalidas(new BigDecimal(0));

        data.setTotalEntradas(data.getTotalEntradas().add(data.getAbonosCreditos(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(data.getVentasMayoreo(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(data.getVentasMenudeo(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(data.getTransferenciasIN(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(data.getAnticipos(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(temporal.getSobrante(), MathContext.UNLIMITED));
        data.setTotalEntradas(data.getTotalEntradas().add(data.getSaldoAnterior(), MathContext.UNLIMITED));

        data.setTotalSalidas(data.getTotalSalidas().add(data.getTransferenciasOUT(), MathContext.UNLIMITED));
        data.setTotalSalidas(data.getTotalSalidas().add(data.getServicios(), MathContext.UNLIMITED));
        data.setTotalSalidas(data.getTotalSalidas().add(data.getPrestamos(), MathContext.UNLIMITED));
        data.setTotalSalidas(data.getTotalSalidas().add(temporal.getFaltante(), MathContext.UNLIMITED));
        data.setTotalSalidas(data.getTotalSalidas().add(temporal.getProvedores(), MathContext.UNLIMITED));

        data.setSaldoNuevo(data.getTotalEntradas().subtract(data.getTotalSalidas(), MathContext.UNLIMITED));
    }

    public void reloadCaja() {
        temporal = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario(), new BigDecimal(1));
        saldoCaja = temporal.getMonto();
    }

    public void generarCorte() {
        if ((temporal.getMonto().compareTo(saldoRealCaja)) == 0) {
            if (transferir()) {
                if (ifaceCorteCaja.insertCorte(data) == 1) {
                    JsfUtil.addSuccessMessage("Se ingreso correctamente el corte de caja");
                    data.reset();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un error al ingresar el corte de caja");
                }
            }

        } else {
            JsfUtil.addErrorMessageClean("No coincide el corte de caja, ingresar movimientos deudores o sobrantes");
        }

    }

    public boolean transferir() {
        boolean bandera = false;
        dataEntradaSalida.setIdEntradaSalidaPk(new BigDecimal(ifaceEntradaSalida.getNextVal()));
        dataEntradaSalida.setIdCajaFk(temporal.getIdCajaPk());
        dataEntradaSalida.setTipoES(new BigDecimal(1));
        dataEntradaSalida.setIdConceptoFk(new BigDecimal(5));
        dataEntradaSalida.setMonto(temporal.getMonto().subtract(temporal.getApertura(), MathContext.UNLIMITED));
        System.out.println("Se ejecuto una transferencia");
        System.out.println("1.-Caja: " + temporal);
        //Descontar de caja:
        temporal.setMonto(temporal.getMonto().subtract(dataEntradaSalida.getMonto(), MathContext.UNLIMITED));
        temporal.setTransferencias_OUT(temporal.getTransferencias_OUT().add(dataEntradaSalida.getMonto(), MathContext.UNLIMITED));
        Caja cajaDestino = new Caja();
        cajaDestino = ifaceCaja.getCajaByIdPk(dataEntradaSalida.getIdCajaDestino());
        System.out.println("1.-Caja Destino: " + cajaDestino);
        cajaDestino.setMonto(cajaDestino.getMonto().add(dataEntradaSalida.getMonto(), MathContext.UNLIMITED));
        cajaDestino.setTransferencias_IN(cajaDestino.getTransferencias_IN().add(dataEntradaSalida.getMonto(), MathContext.UNLIMITED));
        System.out.println("2.-Caja: " + temporal);
        System.out.println("2.-Caja Destino: " + cajaDestino);
        
        cajaDestino.setCantCheques(cajaDestino.getCantCheques().add(temporal.getCantCheques(), MathContext.UNLIMITED));
        cajaDestino.setMontoCheques(cajaDestino.getMontoCheques().add(temporal.getMontoCheques(), MathContext.UNLIMITED));
        
        temporal.setCantCheques(CERO);
        temporal.setFaltante(CERO);
        temporal.setMontoAnticipos(CERO);
        temporal.setMontoCheques(CERO);
        temporal.setMontoCredito(CERO);
        temporal.setMontoMayoreo(CERO);
        temporal.setMontoMenudeo(CERO);
        temporal.setPrestamos(CERO);
        temporal.setProvedores(CERO);
        temporal.setServicios(CERO);
        temporal.setTransferencias_IN(CERO);
        temporal.setSobrante(CERO);
        temporal.setTransferencias_OUT(CERO);
        //temporal.setMonto(CERO);
        temporal.setSaldoAnterior(temporal.getMonto());
        if (ifaceCaja.updateMontoCaja(temporal) == 1) {
            //System.out.println("Entrada Salida: " + data.toString());
            dataEntradaSalida.setIdCajaOrigen(dataEntradaSalida.getIdCajaFk());
            if (ifaceEntradaSalida.insertaMovimiento(dataEntradaSalida) == 1) {
                if (ifaceCaja.updateMontoCaja(cajaDestino) == 1) {
                    JsfUtil.addSuccessMessageClean("Movimiento Registrado con exito");
                    dataEntradaSalida.setTipoES(new BigDecimal(2));
                    dataEntradaSalida.setIdEntradaSalidaPk(new BigDecimal(ifaceEntradaSalida.getNextVal()));
                    if (ifaceEntradaSalida.insertaMovimiento(dataEntradaSalida) == 1) 
                    {
                        JsfUtil.addSuccessMessageClean("Corte Realizado con éxito");
                        bandera = true;
                    }
                    dataEntradaSalida.reset();
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja 2");
                    bandera = false;
                }
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error al ingresar nuevo movimiento");
                bandera = false;
            }
        } else {
            JsfUtil.addErrorMessageClean("Ocurrio un error al actualizar saldo en caja");
            bandera = false;
        }
        return bandera;
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

    public String getViewEstate() {
        return viewEstate;
    }

    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CorteCaja getData() {
        return data;
    }

    public void setData(CorteCaja data) {
        this.data = data;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public Caja getTemporal() {
        return temporal;
    }

    public void setTemporal(Caja temporal) {
        this.temporal = temporal;
    }

    public BigDecimal getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(BigDecimal saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

    public BigDecimal getSaldoRealCaja() {
        return saldoRealCaja;
    }

    public void setSaldoRealCaja(BigDecimal saldoRealCaja) {
        this.saldoRealCaja = saldoRealCaja;
    }

    public OperacionesCaja getDataEntradaSalida() {
        return dataEntradaSalida;
    }

    public void setDataEntradaSalida(OperacionesCaja dataEntradaSalida) {
        this.dataEntradaSalida = dataEntradaSalida;
    }

}
