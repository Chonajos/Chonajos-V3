package com.web.chon.bean;

import com.web.chon.dominio.Usuario;
import com.web.chon.service.IfaceUsuario;
import com.web.chon.service.ServiceUsuario;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("session")
public class BeanUsuario {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceUsuario ifaceUsuario;

    private Usuario usuario;

    @PostConstruct
    public void BeanUsuario() {
        usuario = new Usuario();
    }

    public String validarLogin() {

        try {
            System.out.println("validar login :" + usuario.getClaveUsuario());
            usuario = ifaceUsuario.validarLogin(usuario);

            if (usuario.getStatus()) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/views/welcome.jr");

                return "/content/welcome";
            } else {
                return "login";
            }

        } catch (Exception e) {
            usuario.setMensaje(e.toString());
            return "login";
        }

    }

    public String doLogout() throws IOException {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/login.jr");

        return "login";

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
