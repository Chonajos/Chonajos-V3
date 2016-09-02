package com.web.chon.bean;

import com.web.chon.dominio.EntregaMercancia;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la cruz
 */
@Component
@Scope("view")
public class BeanEntregaMercancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private ArrayList<Sucursal> lstSucursal;

    private EntregaMercancia data;
    private EntregaMercancia entregaMercancia;
    private ArrayList<EntregaMercancia> model;
    private ArrayList<EntregaMercancia> lstEntregaMercanciaSelected;
    

    private String title = "";
    private String viewEstate = "";

    private UsuarioDominio usuarioDominio;

    private BigDecimal BIGDECIMAL_UNO = new BigDecimal(1);
    private BigDecimal BIGDECIMAL_ZERO = new BigDecimal(0);
    private int INT_UNO = 1;

    @PostConstruct
    public void init() {
        usuarioDominio = context.getUsuarioAutenticado();
        
        data = new EntregaMercancia();
        entregaMercancia = new EntregaMercancia();
        model = new ArrayList<EntregaMercancia>();
        lstSucursal = ifaceCatSucursales.getSucursales();

        setTitle("Entrega Mercancia");
        setViewEstate("init");

    }

    public void entregar() {
        try {
            System.out.println("entrega");

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Error > " + ex.getMessage().toString());
            ex.printStackTrace();
        }

    }

    public void buscaFolioVenta() {
        System.out.println("busca folio" + data.getIdFolioVenta());

    }

    public void clean() {

    }

    public void setKilosPromedio(AjaxBehaviorEvent event){
        
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

    public ArrayList<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(ArrayList<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public EntregaMercancia getData() {
        return data;
    }

    public void setData(EntregaMercancia data) {
        this.data = data;
    }

    public ArrayList<EntregaMercancia> getModel() {
        return model;
    }

    public void setModel(ArrayList<EntregaMercancia> model) {
        this.model = model;
    }

    public ArrayList<EntregaMercancia> getLstEntregaMercanciaSelected() {
        return lstEntregaMercanciaSelected;
    }

    public void setLstEntregaMercanciaSelected(ArrayList<EntregaMercancia> lstEntregaMercanciaSelected) {
        this.lstEntregaMercanciaSelected = lstEntregaMercanciaSelected;
    }

    public EntregaMercancia getEntregaMercancia() {
        return entregaMercancia;
    }

    public void setEntregaMercancia(EntregaMercancia entregaMercancia) {
        this.entregaMercancia = entregaMercancia;
    }
    
    

}
