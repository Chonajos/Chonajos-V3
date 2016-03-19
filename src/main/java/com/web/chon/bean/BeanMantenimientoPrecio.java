package com.web.chon.bean;

import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceSubProducto;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el Mantenimiento a Precios
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanMantenimientoPrecio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    
    private ArrayList<Subproducto> lstProducto;

    private String title = "";
    private String viewEstate = "";
    private String idProductoSelecionado = "";
    private MantenimientoPrecios data;
    private Subproducto subproducto;

    @PostConstruct
    public void init() {

        data = new MantenimientoPrecios();
        subproducto = new Subproducto();
        lstProducto = new ArrayList<Subproducto>();

        setTitle("Mantenimiento de Precios.");
        setViewEstate("init");

    }

    public String updatePrecio() {

//        if (ifaceSubProducto.updateSubProducto(null) == 1) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Registro modificado."));
//        }
        return "mantenimientoPrecios";
    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto);
        return lstProducto;

    }

    public void searchById() {
//        data = ifaceSubProducto.getSubProductoById(subproducto.getIdSubproductoPk());
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

    public MantenimientoPrecios getData() {
        return data;
    }

    public void setData(MantenimientoPrecios data) {
        this.data = data;
    }

    public String getIdProductoSelecionado() {
        return idProductoSelecionado;
    }

    public void setIdProductoSelecionado(String idProductoSelecionado) {
        this.idProductoSelecionado = idProductoSelecionado;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public Subproducto getSubproducto() {
        return subproducto;
    }

    public void setSubproducto(Subproducto subproducto) {
        this.subproducto = subproducto;
    }

}
