package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia;

import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceEntradaProductoCentral;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.ViewState;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.JstlUtils;

@Component
@Scope("view")
public class BeanEntradaMercancia extends SimpleViewBean<EntradaMercancia> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(EntradaMercancia.class);
    private EntradaMercancia dataModel;
    private int filtro;
    private Date filtroFechaInicio;
    private Date filtroFechaFin;
    @Autowired
    private IfaceEntradaProductoCentral ifaceEntradaProductoCentral;

    private String title;

    @Override
    protected Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initModel() {

        data = new EntradaMercancia();
//        model = new PaginationLazyDataModel<EntradaMercancia, BigDecimal>(ifaceEntradaProductoCentral,new EntradaMercancia());

        setTitle("Registro Entradas de Mercancias");

    }
    
    public void setFechaInicioFin(){
        
    }

    @Override
    public String search() {
        setTitle("Graficas.");
        actionSearching();

        return "entradaMercancia";
    }

    public void cancel() {
        actionBack();
        initModel();
    }

    @Override
    public String searchDatabyId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String save() {
        try {
            ifaceEntradaProductoCentral.saveEntradaProductoCentral(data);
            JsfUtil.addSuccessMessage("Registro insertado Correctamente.");
        } catch (Exception e) {
            logger.error("Erros al insertar", "Error", e.getMessage());
        }

        return "entradaMercancia";
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public Date getFiltroFechaInicio() {
        return filtroFechaInicio;
    }

    public void setFiltroFechaInicio(Date filtroFechaInicio) {
        this.filtroFechaInicio = filtroFechaInicio;
    }

    public Date getFiltroFechaFin() {
        return filtroFechaFin;
    }

    public void setFiltroFechaFin(Date filtroFechaFin) {
        this.filtroFechaFin = filtroFechaFin;
    }
    
    

}
