package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.model.PaginacionLazyDataModel;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceEntradaProductoCentral;
import com.web.chon.util.ViewState;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class BeanEntradaMercancia extends SimpleViewBean<EntradaMercancia> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(EntradaMercancia.class);
    @Autowired
    private IfaceEntradaProductoCentral ifaceEntradaProductoCentral;

    private String title;



 

    @Override
    protected Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initModel() {
        actionNew();
        data = new EntradaMercancia();
        model = new PaginacionLazyDataModel<EntradaMercancia,BigDecimal>(ifaceEntradaProductoCentral);

        //setTitle("Registro Entradas de Mercancias");
        //setViewEstate("init");
    }

    @Override
    public String search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String searchDatabyId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String save() {
        try {
            ifaceEntradaProductoCentral.saveEntradaProductoCentral(data);
        } catch (Exception e) {
            System.out.println("Error > " + e.getMessage());
        }

        return "entradaMercancia";
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
