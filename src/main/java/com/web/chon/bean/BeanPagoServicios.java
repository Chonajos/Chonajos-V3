/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Caja;
import com.web.chon.dominio.ConceptosES;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCaja;
import com.web.chon.service.IfaceConceptos;
import com.web.chon.service.IfaceOperacionesCaja;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
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
    }

    public void pagar() {
        
        opcaja.setIdOperacionesCajaPk(new BigDecimal(ifaceOperacionesCaja.getNextVal()));
        opcaja.setMonto(totalServicio);
        opcaja.setComentarios(comentarios);
        opcaja.setIdConceptoFk(idConceptoBean);

        if(caja.getIdCajaPk()!=null)
        {
        if (ifaceOperacionesCaja.insertaOperacion(opcaja) == 1) {

            JsfUtil.addSuccessMessageClean("Pago de Servicio Registrado Correctamente");
        } else {
            JsfUtil.addErrorMessageClean("Ocurrió un error al registrar el pago de servicio");
        }
        }
        else
        {
             JsfUtil.addErrorMessageClean("Su usuario no cuenta con caja registrada para realizar el pago de servicios");
        }
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
    
}
