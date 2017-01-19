package com.web.chon.bean;

import com.web.chon.dominio.Rol;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.security.service.PasswordEncoderChonajos;
import com.web.chon.security.service.PlataformaSecurityContext;
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
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanCatUsuario implements BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatRol ifaceCatRol;
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private PasswordEncoderChonajos passwordEncoder;
    @Autowired
    private PlataformaSecurityContext context;

    private List<Rol> lstRol;
    private ArrayList<Usuario> model;
    private List<Sucursal> lstSucursal;
    private ArrayList<Usuario> selectedUsuario;

    private Usuario data;
    private String title;
    private String viewEstate;

    @PostConstruct
    public void init() {

        data = new Usuario();
        model = new ArrayList<Usuario>();
        lstRol = new ArrayList<Rol>();
        selectedUsuario = new ArrayList<Usuario>();
        lstSucursal = new ArrayList<Sucursal>();

        model = ifaceCatUsuario.getUsuarios();
        lstRol = ifaceCatRol.getAll();
        lstSucursal = ifaceCatSucursales.getSucursales();
        setTitle("Catalogo de Usuarios");
        setViewEstate("init");

    }

    //Es una eliminacion logica solo cambia el estatus del usuario a inactivo
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

            //Se codifica la contraseña del usuario
            CharSequence encoder = passwordEncoder.encode(data.getClaveUsuario()).toString().toUpperCase();
            data.setContrasenaUsuario(encoder.toString());
            data.setFechaAltaUsuario(context.getFechaSistema());
            if (ifaceCatUsuario.insertarUsuarios(data) == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La clave del usuario " + data.getClaveUsuario() + " ya existe. Intenta con otra clave diferente"));
                return null;
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
            //Se codifica la contraseña del usuario
            CharSequence encoder = passwordEncoder.encode(data.getClaveUsuario()).toString().toUpperCase();
            data.setContrasenaUsuario(encoder.toString());
                    
            if (ifaceCatUsuario.updateUsuario(data) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombreUsuario() + "."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al intentar modificar el registro :" + data.getNombreUsuario() + "."));
        }

        return "usuario";
    }

    @Override
    public void searchById() {
        setTitle("Editar Usuario");
        setViewEstate("searchById");

    }

    public void viewNew() {
        data = new Usuario();
        setTitle("Alta de Usuarios");
        setViewEstate("new");
    }

    public void backView() {
        setTitle("Catalogo de Usuarios");
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

    public List<Rol> getLstRol() {
        return lstRol;
    }

    public void setLstRol(List<Rol> lstRol) {
        this.lstRol = lstRol;
    }

    public List<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(List<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }
}
