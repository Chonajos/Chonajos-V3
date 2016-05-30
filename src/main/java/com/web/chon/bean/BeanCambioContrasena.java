package com.web.chon.bean;

import com.web.chon.dominio.Rol;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.security.service.PlataformaUserDetailsService;
import com.web.chon.security.service.PlataformaUserDetailsServiceImpl;
import com.web.chon.service.IfaceCatRol;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class BeanCambioContrasena implements BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;

    private UsuarioDominio usuario;

    private Usuario data;
    private String title;
    private String contrasenaActual;
    private String contrasenaActualComfirmacion;
    
    @PostConstruct
    public void init() {

        usuario = context.getUsuarioAutenticado();

        data = new Usuario();

        data = ifaceCatUsuario.getUsuariosById(usuario.getIdUsuario().intValue());
        contrasenaActual = usuario.getUsuPassword();
        System.out.println("data :" + data.toString());

        setTitle("Cambio de Contraseña.");

    }

    @Override
    public String delete() {

        return null;
    }

    @Override
    public String insert() {

        return null;
    }

    @Override
    public String update() {
        try {
            System.out.println("contraseña actual :"+contrasenaActual +"Contraseña Actual "+contrasenaActual);
            if (contrasenaActualComfirmacion.equals(contrasenaActual)) {
                if (ifaceCatUsuario.updateUsuario(data) == 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Contraseña modificada."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar la Contraseña :" + data.getNombreUsuario() + "."));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La Contraseña Escrita no es la Correcta.Vuelva a Escribir la Contraseña Actual."));
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar la Contraseña :" + data.getNombreUsuario() + "."));
        }

        return "welcome";
    }

    public void cancel(){
        contrasenaActualComfirmacion = "";
        data.setConfirmaUsuario("");
        data.setContrasenaUsuario("");
    }
    
    @Override
    public void searchById() {

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

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getContrasenaActualComfirmacion() {
        return contrasenaActualComfirmacion;
    }

    public void setContrasenaActualComfirmacion(String contrasenaActualComfirmacion) {
        this.contrasenaActualComfirmacion = contrasenaActualComfirmacion;
    }
    
    

}
