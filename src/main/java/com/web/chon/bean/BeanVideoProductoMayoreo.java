package com.web.chon.bean;

import com.web.chon.dominio.Usuario;
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

    private FacesContext facesContext;

    private BigDecimal idEMP;
    
    private String title;

    @PostConstruct
    public void init() {
        facesContext = FacesContext.getCurrentInstance();

        String idEMPSTR = facesContext.getExternalContext().getRequestParameterMap().get("idEMP");
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        System.out.println("url "+url);

        if (idEMPSTR != null) {
            idEMP = new BigDecimal(idEMPSTR);
        }
        setTitle("Productos.");
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
