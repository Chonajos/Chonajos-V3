package com.web.chon.bean;

import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.util.JsfUtil;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para Restablecer la contraseña de los usuarios
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRestablecerContrasena {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;

    private ArrayList<Usuario> lstUsuario;

    private Usuario data;
    private Usuario usuario;
    private UsuarioDominio usuarioDominio;

    private String title;

    @PostConstruct
    public void init() {
        try {

            usuarioDominio = context.getUsuarioAutenticado();
            data = new Usuario();

        } catch (ClassCastException ce) {

            data = new Usuario();
        }
        setTitle("Restablecer Contraseña.");
    }

    public String restablecer() {
        try {

            data.setContrasenaUsuario(data.getClaveUsuario());
            if (ifaceCatUsuario.updateUsuario(data) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Contraseña modificada."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar la Contraseña :" + data.getNombreUsuario() + "."));
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar la Contraseña :" + data.getNombreUsuario() + "."));
        }

        return "restablecerContrasena";
    }

    public void getUserByClave() {

        if (usuarioDominio.getPerId() == 2) {
            data = ifaceCatUsuario.getUsuarioByClave(data.getClaveUsuario(), usuarioDominio.getSucId());
        } else {
            data = ifaceCatUsuario.getUsuarioByClave(data.getClaveUsuario(), 0);
        }

        if (data == null) {
            data = new Usuario();
        }

        if (data.getClaveUsuario() == null) {
            JsfUtil.addErrorMessage("No se Encontraron Registros.");
        }

        usuario = data;
    }

    public ArrayList<Usuario> autoCompleteUsuario(String nombreUsuario) {
        if (usuarioDominio.getPerId() == 2) {
            lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase(), usuarioDominio.getSucId());
        } else {
            lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase(), 0);
        }
        return lstUsuario;

    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        data = usuario;
    }

}
