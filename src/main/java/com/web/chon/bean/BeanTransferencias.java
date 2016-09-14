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
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;
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
public class BeanTransferencias {
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
    private static final BigDecimal statusOperacion = new BigDecimal(1);
    private static final BigDecimal idConcepto = new BigDecimal(11);
    
    @PostConstruct
    public void init() 
    {
        usuario = context.getUsuarioAutenticado();
        setTitle("Pago de Servicios");
        setViewEstate("init");
        caja = new Caja();
        caja = ifaceCaja.getCajaByIdUsuarioPk(usuario.getIdUsuario());
        opcajaOrigen = new OperacionesCaja();
        opcajaOrigen.setIdCajaFk(caja.getIdCajaPk());
        opcajaOrigen.setIdUserFk(usuario.getIdUsuario());
        opcajaOrigen.setEntradaSalida(salida);
        opcajaOrigen.setIdStatusFk(statusOperacion);
        
    }
     public void transferir()
    {
        
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

    
    
    
    
    
}
