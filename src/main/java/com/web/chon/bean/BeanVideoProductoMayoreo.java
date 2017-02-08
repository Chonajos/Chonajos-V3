package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.Usuario;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.util.JsfUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para cambiar la contraseña del usuario Logeado
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanVideoProductoMayoreo {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;

    private FacesContext facesContext;
    
    private EntradaMercanciaProducto data;

    private BigDecimal idEMP;

    private String title;

    @PostConstruct
    public void init() {
        facesContext = FacesContext.getCurrentInstance();

        String idEMPSTR = facesContext.getExternalContext().getRequestParameterMap().get("idEMP");
        setTitle("Productos.");
        
        if (idEMPSTR != null) {
            idEMP = new BigDecimal(idEMPSTR);
            getVideoProducto(idEMP);
        }
        
    }
    
    private void getVideoProducto(BigDecimal id){
        
        data = ifaceEntradaMercanciaProducto.getById(id);
        String precioVenta = data.getPrecio() == null? "No Disponible.":data.getPrecio()+".";
        setTitle(data.getNombreProducto().trim()+" "+data.getNombreEmpaque()+". Precio de Venta:$ "+precioVenta);
        
    }

    private String getUrl(BigDecimal valorParametro, String nombreParametro, String nombrePantalla) {
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        String urlCustom = "";
        String urlMap[];

        urlMap = url.split(":");
        urlCustom += urlMap[0] + ":";
        urlCustom += urlMap[1];

        urlCustom += ":" + urlMap[2].split("/")[0];

        urlCustom += "/" + nombrePantalla + "?" + nombreParametro + "=" + valorParametro;

        return urlCustom;
    }

    public EntradaMercanciaProducto getData() {
        return data;
    }

    public void setData(EntradaMercanciaProducto data) {
        this.data = data;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
