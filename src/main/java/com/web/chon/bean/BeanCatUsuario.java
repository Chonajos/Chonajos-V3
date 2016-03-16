package com.web.chon.bean;

import com.web.chon.dominio.Usuario;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.ServiceCatUsuario;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author marcogante
 */
@Component
@Scope("view")
public class BeanCatUsuario implements BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;

    private ArrayList<Usuario> model;
    private ArrayList<Usuario> selectedUsuario;
    private String title;
    private String viewEstate;
    private Usuario data;

    @PostConstruct
    public void init() {

        data = new Usuario();
        model = new ArrayList<Usuario>();
        selectedUsuario = new ArrayList<Usuario>();
        model = ifaceCatUsuario.getUsuarios();

        setTitle("Catalogo de Usuarios.");
        setViewEstate("init");

    }

    @Override
    public String delete() {
        if (!selectedUsuario.isEmpty()) {
            for (Usuario usuario : selectedUsuario) {
                try {
                    ifaceCatUsuario.deleteUsuarios(usuario.getIdUsuarioPk().intValue());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro eliminado."));
                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar eliminar el registro :" + data.getNombreUsuario() + "."));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Elija un registro a eliminar."));
        }

        return "usuario";
    }

    @Override
    public String insert() {
        try {
            System.out.println("data" + data.toString());
            if (ifaceCatUsuario.insertarUsuarios(data) == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La clave del usuario " + data.getClaveUsuario() + " ya existe. Intenta con otra clave diferente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado."));
            }

        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar insertar el registro :" + data.getNombreUsuario() + "."));
        }
        backView();
        return "usuario";
    }

    @Override
    public String update() {
        try {
            ifaceCatUsuario.updateUsuario(data);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombreUsuario() + "."));
        }

        return "usuario";
    }

    @Override
    public void searchById() {
        setTitle("Editar Usuario.");
        setViewEstate("searchById");

    }

    public void viewNew() {
        data = new Usuario();
        setTitle("Alta de Usuarios.");
        setViewEstate("new");
    }

    public void backView() {
        setTitle("Catalogo de Usuarios.");
        setViewEstate("init");
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    public ArrayList<Usuario> getModel() {
        return model;
    }

    public void setModel(ArrayList<Usuario> model) {
        this.model = model;
    }

    public ArrayList<Usuario> getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(ArrayList<Usuario> selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
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

}
