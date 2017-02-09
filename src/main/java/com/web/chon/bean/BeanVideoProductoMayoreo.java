package com.web.chon.bean;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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

    private void getVideoProducto(BigDecimal id) {

        data = ifaceEntradaMercanciaProducto.getById(id);
        String precioVenta = data.getPrecio() == null ? "No Disponible." : data.getPrecio() + ".";
        if (data.getIdSubProductoFK() != null) {
            setTitle(data.getNombreProducto().trim() + " " + data.getNombreEmpaque() + ". Precio de Venta:$ " + precioVenta);
        }

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
