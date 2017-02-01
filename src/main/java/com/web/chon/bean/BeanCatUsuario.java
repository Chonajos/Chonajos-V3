package com.web.chon.bean;

import com.web.chon.dominio.DiaDescansoUsuario;
import com.web.chon.dominio.HorarioUsuario;
import com.web.chon.dominio.Rol;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.security.service.PasswordEncoderChonajos;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatRol;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatUsuario;
//import com.web.chon.util.SendEmail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import javax.mail.Authenticator;
//import javax.mail.BodyPart;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
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
            CharSequence encoder = passwordEncoder.encode(data.getClaveUsuario()).toUpperCase();
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

//    public void sendEmail() {
//        
//        SendEmail.send();
//        System.out.println("enviar correo sencillo 1");
////        if (data.getCorreoUsuario() == null || data.getCorreoUsuario().equals("")) {
////            System.out.println("no se tiene registrado un correo");
////        } else {
//
//        // La dirección de envío (to)
//        System.out.println("direcion de envio1");
//        String para = "juancruzh91@gmail.com";
//
//        // La dirección de la cuenta de envío (from)
//        System.out.println("direcion from1");
//        String de = "juancruzh91@gmail.com";
//
//        // El servidor (host). En este caso usamos localhost
//        System.out.println("localhost 1");
//
////        =
////=
////=
////
////
//        
//
//        String host = "smtp.gmail.com";
//
//        // Obtenemos las propiedades del sistema
//        Properties propiedades = System.getProperties();
//
//        // Configuramos el servidor de correo
//        System.out.println("configurar el servidor de correo 1");
////        propiedades.setProperty("mail.smtp.host", host);
//        propiedades.put("mail.smtp.host","smtp.gmail.com");
//        propiedades.put("mail.smtp.socketFactory.port", "465");
//        propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        propiedades.put("mail.smtp.auth", "true");
//        propiedades.put("mail.smtp.port", "465");
//
//        // Obtenemos la sesión por defecto
//        System.out.println("se obtiene la session por defect");
//        Session session = Session.getInstance(propiedades,new Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication("juancruzh91@gmail.com", "juancruzh91");}});
////        Session session = Session.getDefaultInstance(propiedades,new Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication("juancruzh91@gmail.com", "juancruzh91");}});
//
//        
//        try {
//            // Creamos un objeto mensaje tipo MimeMessage por defecto.
//            MimeMessage mensaje = new MimeMessage(session);
//
//            // Asignamos el “de o from” al header del correo.
//            mensaje.setFrom(new InternetAddress(de));
//
//            // Asignamos el “para o to” al header del correo.
//            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
//
//            // Asignamos el asunto
//            mensaje.setSubject("Primer correo sencillo");
//
//            // Asignamos el mensaje como tal
//            mensaje.setText("El mensaje de nuestro primer correo");
//
//            // Enviamos el correo
//            Transport.send(mensaje);
//            System.out.println("Mensaje enviado");
//        } catch (MessagingException e) {
//            System.out.println("error :(");
//            e.printStackTrace();
//        }
//    }

    

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
