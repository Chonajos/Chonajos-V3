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
    private BigDecimal saldoAnterior;
    private BigDecimal nuevoSaldo;
    private BigDecimal totalEntradas;
    private BigDecimal totalSalidas;
    //--Variables para Verificar Maximo en Caja --//
    private BigDecimal idTipoTransferenciaFk;

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

        //--Maximo Pago--//
        corteAnterior = new CorteCaja();
        corteAnterior = ifaceCorteCaja.getLastCorteByCaja(caja.getIdCajaPk());
        totalEntradas = new BigDecimal(0);
        totalSalidas = new BigDecimal(0);
        saldoAnterior = new BigDecimal(0);
        nuevoSaldo = new BigDecimal(0);
        //--Maximo Pago--//
        listaCajas.remove(caja);
        Caja cRemove = new Caja();
        for(Caja c : listaCajas)
        {
            if(c.getIdCajaPk().equals(caja.getIdCajaPk()))
            {
                cRemove = c;
            }
            
        }
        
        listaCajas.remove(cRemove);

    }

    //----Funciones para Verificar Maximo de Dinero en Caja --//
    public void verificarDinero() {

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
    public void transferir() 
    {
        verificarDinero();

        if (nuevoSaldo.compareTo(monto) >= 0) {
            opcajaOrigen.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
            opcajaOrigen.setMonto(monto);
            opcajaOrigen.setComentarios(comentarios);
            if(idTipoTransferenciaFk.intValue()==1)
            {
                opcajaOrigen.setIdConceptoFk(idTransferenciaEfectivo);
            }
            else
            {
                opcajaOrigen.setIdConceptoFk(idTransferenciaCheques);
            }
            
            
            opcajaOrigen.setIdCajaDestinoFk(idCajaDestinoBean);

            if (caja.getIdCajaPk() != null) {
                if (ifaceOperacionesCaja.insertaOperacion(opcajaOrigen) == 1) {
                    JsfUtil.addSuccessMessageClean("Transferencia Registrada Correctamente");
                    monto = null;
                    comentarios = null;
                    idCajaDestinoBean = null;
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrió un error al registrar la transferencia");
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
    

}
